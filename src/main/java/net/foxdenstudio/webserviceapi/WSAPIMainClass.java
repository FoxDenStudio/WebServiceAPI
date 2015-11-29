package net.foxdenstudio.webserviceapi;

import com.google.inject.Inject;
import net.foxdenstudio.webserviceapi.annotations.RequestHandler;
import net.foxdenstudio.webserviceapi.novacula.server.NovaServer;
import net.foxdenstudio.webserviceapi.novacula.utils.NovaLogger;
import net.foxdenstudio.webserviceapi.requests.IWebServiceRequest;
import net.foxdenstudio.webserviceapi.responses.IWebServiceResponse;
import net.foxdenstudio.webserviceapi.webserver.ClientConnectionThreadOverride;
import net.foxdenstudio.webserviceapi.webserver.NovaServerOverride;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginManager;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
@Plugin(name = "FoxDenStudio - WSAPI", id = "ffds-wsapi")
public class WSAPIMainClass {

    @Inject
    private Game game;
    @Inject
    private Logger logger;
    @Inject
    private PluginManager pluginManager;

    HashMap<String, HashMap<String, RequestHandlerData>> pluginAndPathRegistry = new HashMap<>();

    NovaServerOverride novaServer;


    @Listener
    public void onGameInitializationEvent(GameInitializationEvent event) {
        novaServer = new NovaServerOverride(new NovaLogger(), this);
        new Thread(novaServer::start).start();//novaServer.start();
    }

    @Listener
    public void onGameServerStoppingEvent(GameStoppingEvent event) {
        novaServer.stop();
        novaServer.getLogger().saveLog();
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
