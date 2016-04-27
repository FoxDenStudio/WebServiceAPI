package net.foxdenstudio.webserviceapi.server.routes;

import net.foxdenstudio.webserviceapi.server.HTTPHeaderParser;
import net.foxdenstudio.webserviceapi.util.TriConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Route {
    private final String key;
    private final List<Route> children;
    private TriConsumer<HTTPHeaderParser, Socket, List<String>> consumer;

    public Route(@Nonnull String key, @Nullable TriConsumer<HTTPHeaderParser, Socket, List<String>> consumer) {
        this.key = key;
        this.consumer = consumer;
        this.children = new ArrayList<>();
    }

    public void call(@Nonnull HTTPHeaderParser httpHeaderParser, @Nonnull Socket outputStream, @Nonnull List<String> excess) {
        this.consumer.accept(httpHeaderParser, outputStream, excess);
    }

    @Nonnull
    public Route addChild(@Nonnull Route child) {
        if (!children.contains(child)) {
            children.add(child);
            children.sort((o1, o2) -> o1.key.compareTo(o2.key));
        }
        return this;
    }

    @Nullable
    public TriConsumer<HTTPHeaderParser, Socket, List<String>> getConsumer() {
        return consumer;
    }

    @Nonnull
    public Route setConsumer(@Nullable TriConsumer<HTTPHeaderParser, Socket, List<String>> consumer) {
        this.consumer = consumer;
        return this;
    }

    @Nonnull
    public List<Route> getChildren() {
        return children;
    }

    @Nonnull
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Route " + key + " { " + getChildren() + " }";
    }
}
