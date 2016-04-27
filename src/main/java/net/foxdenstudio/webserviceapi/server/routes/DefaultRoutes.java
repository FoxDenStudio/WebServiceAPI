package net.foxdenstudio.webserviceapi.server.routes;

import net.foxdenstudio.webserviceapi.server.HTTPHeaderParser;
import net.foxdenstudio.webserviceapi.util.TriConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.Socket;
import java.util.List;

public class DefaultRoutes extends Routes {

    private static DefaultRoutes ourInstance = new DefaultRoutes();

    private DefaultRoutes() {
        rootRoute.setConsumer(null);
    }

    @Nonnull
    public static DefaultRoutes getInstance() {
        return ourInstance;
    }

    public void setRootConsumer(@Nullable TriConsumer<HTTPHeaderParser, Socket, List<String>> consumer) {
        rootRoute.setConsumer(consumer);
    }

    @Nonnull
    public Route addChild(@Nonnull Route child) {
        return rootRoute.addChild(child);
    }

    @Nonnull
    public List<Route> getChildren() {
        return rootRoute.getChildren();
    }

    @Nullable
    public TriConsumer<HTTPHeaderParser, Socket, List<String>> getConsumer() {
        return rootRoute.getConsumer();
    }

    public void call(@Nonnull HTTPHeaderParser httpHeaderParser, @Nonnull Socket outputStream, @Nonnull List<String> excess) {
        rootRoute.call(httpHeaderParser, outputStream, excess);
    }

    public Route getRoot() {
        return rootRoute;
    }

    @Override
    public String getContentType() {
        return "text/html";
    }
}
