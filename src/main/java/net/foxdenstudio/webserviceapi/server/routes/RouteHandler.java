package net.foxdenstudio.webserviceapi.server.routes;

import net.foxdenstudio.webserviceapi.server.HTTPHeaderParser;
import net.foxdenstudio.webserviceapi.util.TriConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.Socket;
import java.util.List;

public class RouteHandler extends Route {
    public RouteHandler(@Nonnull String key, @Nullable TriConsumer<HTTPHeaderParser, Socket, List<String>> consumer) {
        super(key, consumer);
    }
}
