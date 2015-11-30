package net.foxdenstudio.webserviceapi;

import com.google.inject.Inject;
import net.foxdenstudio.webserviceapi.annotations.RequestHandler;
import net.foxdenstudio.webserviceapi.novacula.utils.NovaLogger;
import net.foxdenstudio.webserviceapi.requests.IWebServiceRequest;
import net.foxdenstudio.webserviceapi.responses.FileWebResponse;
import net.foxdenstudio.webserviceapi.responses.IWebServiceResponse;
import net.foxdenstudio.webserviceapi.webserver.ClientConnectionThreadOverride;
import net.foxdenstudio.webserviceapi.webserver.NovaServerOverride;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Platform;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.service.ProviderExistsException;
import org.spongepowered.api.service.scheduler.SchedulerService;
import org.spongepowered.api.service.scheduler.Task;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
@Plugin(name = "FoxDenStudio - WSAPI", id = "fds-wsapi")
public class WSAPIMainClass {

    @Inject
    private Game game;
    @Inject
    private Logger logger;
    @Inject
    private PluginManager pluginManager;

    HashMap<String, HashMap<String, RequestHandlerData>> pluginAndPathRegistry = new HashMap<>();

    NovaServerOverride novaServer;

    Task checkRequests;

    public static final BlockingQueue<Runnable> requestQueue = new LinkedBlockingQueue<>();

    @Listener
    public void onGamePreInitializationEvent(GamePreInitializationEvent event) {
        if (game.getPlatform().getType() == Platform.Type.SERVER) {
            try {
                game.getServiceManager().setProvider(this, IRegistrationService.class, new SimpleRegistrationService());
            } catch (ProviderExistsException e) {
                e.printStackTrace();
            }

            //API ONLY
            SchedulerService schedulerService = game.getScheduler();
            Task.Builder taskBuilder = schedulerService.createTaskBuilder();
            taskBuilder.execute((task) -> {
                try {
                    requestQueue.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).name("FDS-WSAPI - CheckRequestsTask").delayTicks(10).intervalTicks(1).submit(this);
        }
    }

    @Listener
    public void onGameInitializationEvent(GameInitializationEvent event) {
        if (game.getPlatform().getType() == Platform.Type.SERVER) {
            novaServer = new NovaServerOverride(new NovaLogger(), this);
            new Thread(novaServer::start).start();//novaServer.start();
        }
    }

    @Listener
    public void onGameServerStoppingEvent(GameStoppingEvent event) {
        if (game.getPlatform().getType() == Platform.Type.SERVER) {

            novaServer.stop();
            novaServer.getLogger().saveLog();
        }
    }


    public void registerPlugin(String pluginID, String pluginWebPath, Object... classesToRegister) {
        HashMap<String, RequestHandlerData> tempHashMap = new HashMap<>();
        for (Object object : classesToRegister) {
            Class clazz = object.getClass();
            for (Method method : clazz.getMethods()) {

                if (method.isAnnotationPresent(RequestHandler.class)) {
                    RequestHandler annotation = method.getAnnotation(RequestHandler.class);
                    String name = annotation.name();
                    tempHashMap.put(name, new RequestHandlerData(annotation.requestType(), object, method));
                }
            }
        }

        pluginAndPathRegistry.put(pluginWebPath, tempHashMap);

        logger.debug("WebData:" + pluginAndPathRegistry);
    }

    public void loadPage(ClientConnectionThreadOverride clientConnectionThread, String path, String path2, IWebServiceRequest serviceRequest) {

        if (path.equalsIgnoreCase("DEFPLUG") && path2.equalsIgnoreCase("DEFPAGE")) {
//            https://travis-ci.org/FoxDenStudio/WebServiceAPI.svg?branch=master
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                IWebServiceResponse serviceResponse = new FileWebResponse(classLoader.getResourceAsStream("BaseDoc.html"));

                clientConnectionThread.sendHTTPResponseOK(clientConnectionThread.getSocket().getOutputStream(), serviceResponse.mimeType());

                for (int i = 0; i < serviceResponse.getByteData().length; i++) {
                    clientConnectionThread.getSocket().getOutputStream().write(serviceResponse.getByteData()[i]);
                    clientConnectionThread.getSocket().getOutputStream().flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        if (pluginAndPathRegistry.containsKey(path)) {
            HashMap<String, RequestHandlerData> dataHashMap = pluginAndPathRegistry.get(path);
            if (dataHashMap.containsKey(path2)) {
                RequestHandlerData requestHandlerData = dataHashMap.get(path2);
                try {
                    Object object = requestHandlerData.getMethod().invoke(requestHandlerData.getClassInstance(), serviceRequest);
                    if (object instanceof IWebServiceResponse) {
                        IWebServiceResponse serviceResponse = (IWebServiceResponse) object;

                        clientConnectionThread.sendHTTPResponseOK(clientConnectionThread.getSocket().getOutputStream(), serviceResponse.mimeType());

                        for (int i = 0; i < serviceResponse.getByteData().length; i++) {
                            clientConnectionThread.getSocket().getOutputStream().write(serviceResponse.getByteData()[i]);
                            clientConnectionThread.getSocket().getOutputStream().flush();
                        }

                    }
                } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Logger getLogger() {
        return logger;
    }
}
