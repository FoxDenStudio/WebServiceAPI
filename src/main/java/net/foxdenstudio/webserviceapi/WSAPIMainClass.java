package net.foxdenstudio.webserviceapi;

import com.google.inject.Inject;
import net.foxdenstudio.webserviceapi.annotations.RequestHandler;
import net.foxdenstudio.webserviceapi.requests.IWebServiceRequest;
import net.foxdenstudio.webserviceapi.responses.IWebServiceResponse;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void registerPlugin(String pluginID, String pluginWebPath, Object... classesToRegister) {
//        pluginAndPathRegistry.put(pluginWebPath, new HashMap<>());
        HashMap<String, RequestHandlerData> tempHashMap = new HashMap<>();
        for (Object object : classesToRegister) {
            Class clazz = object.getClass();
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(RequestHandler.class)) {
                    RequestHandler annotation = method.getAnnotation(RequestHandler.class);
                    String name = annotation.name();
                    tempHashMap.put(name, new RequestHandlerData(annotation.requestType(), object, method));
                }
                method = null;
            }
            clazz = null;
        }

        pluginAndPathRegistry.put(pluginWebPath, tempHashMap);

        System.out.println("TEST:" + pluginAndPathRegistry);
    }

    public void callTest(String path, String path2, IWebServiceRequest serviceRequest) {
        if (pluginAndPathRegistry.containsKey(path)) {
            HashMap<String, RequestHandlerData> dataHashMap = pluginAndPathRegistry.get(path);
            if (dataHashMap.containsKey(path2)) {
                RequestHandlerData requestHandlerData = dataHashMap.get(path2);
                try {
                    System.out.println("ATTEMPT: " + path + " / " + path2);
                    requestHandlerData.getMethod().invoke(requestHandlerData.getClassInstance(), serviceRequest);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
