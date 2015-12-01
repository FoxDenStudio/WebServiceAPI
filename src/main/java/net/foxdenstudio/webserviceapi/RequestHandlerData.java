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

    /**
     * @param requestType   RequestType Object - An enum of what type of object was requested... Currently not in use.
     * @param classInstance An object of the instance of the class that the method resides in.
     * @param method        A Method object that links to the method so that it can be called when needed.
     */
    public RequestHandlerData(RequestHandler.RequestType requestType, Object classInstance, Method method) {
        this.requestType = requestType;
        this.classInstance = classInstance;
        this.method = method;
    }

    /**
     * @return RequestType - An enum specifying what type of content should be returned. Not used yet.
     */
    public RequestHandler.RequestType getRequestType() {
        return requestType;
    }

    /**
     * @return Object - An instance of the class that the method resides in.
     */
    public Object getClassInstance() {
        return classInstance;
    }

    /**
     * @return Method - The method that will be called.
     */
    public Method getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[reqType=" + requestType.name() + ", class=" + classInstance.getClass().getSimpleName() + ", method=" + method.getName() + "]";
    }
}
