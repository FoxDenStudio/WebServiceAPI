package net.foxdenstudio.webserviceapi;

import net.foxdenstudio.webserviceapi.annotations.RequestHandler;

import java.lang.reflect.Method;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class RequestHandlerData {

    private final RequestHandler.RequestType requestType;
    private final Object classInstance;
    private final Method method;

    public RequestHandlerData(RequestHandler.RequestType requestType, Object classInstance, Method method) {

        this.requestType = requestType;
        this.classInstance = classInstance;
        this.method = method;
    }

    public RequestHandler.RequestType getRequestType() {
        return requestType;
    }

    public Object getClassInstance() {
        return classInstance;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[reqType=" + requestType.name() + ", class=" + classInstance.getClass().getSimpleName() + ", method=" + method.getName() + "]";
    }
}
