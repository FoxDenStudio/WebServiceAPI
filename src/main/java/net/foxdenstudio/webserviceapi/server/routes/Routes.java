package net.foxdenstudio.webserviceapi.server.routes;

import net.foxdenstudio.webserviceapi.server.HTTPHeaderParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Routes implements RouteService {
    protected Route rootRoute = new Route("", null);

    @Override
    public boolean routeAvailable(@Nonnull HTTPHeaderParser headers) {
        return routeAvailable(headers.getRequestURL());
    }

    @Override
    public boolean routeAvailable(@Nonnull String requestURL) {
        List<String> segments = new ArrayList<>(Arrays.asList(requestURL.split("/")));
        return segments.size() <= 1 ? rootRoute.getConsumer() != null : iterateThroughRoutes(rootRoute, segments);
    }

    private boolean iterateThroughRoutes(@Nonnull Route rootRoute, @Nonnull List<String> segments) {
        if (segments.size() == 1 && rootRoute.getKey().equalsIgnoreCase(segments.get(0))) {
            return rootRoute.getConsumer() != null;
        }
        if (rootRoute.getChildren().size() == 0 && segments.size() == 0) {
            return rootRoute.getConsumer() != null;
        }
        if ((rootRoute.getChildren().size() == 0 && segments.size() != 0) || segments.size() == 0) {
            return false;
        }
        Optional<Route> tr = rootRoute.getChildren().parallelStream().filter(route -> route.getKey().equalsIgnoreCase(segments.get(1))).findFirst();
        if (tr.isPresent()) {
            if (tr.get() instanceof RouteHandler) {
                return true;
            }
            segments.remove(0);
            return iterateThroughRoutes(tr.get(), segments);
        } else {
            return false;
        }
    }

    @Override
    public boolean isRouteSpecial(@Nonnull HTTPHeaderParser headers) {
        return false;
    }

    @Override
    @Nullable
    public Route getRoute(@Nonnull HTTPHeaderParser headers) {
        List<String> segments = new ArrayList<>(Arrays.asList(headers.getRequestURL().split("/")));
//        if (segments.size() > 0) segments.remove(0);
        return segments.size() <= 1 ? rootRoute : getRoute(rootRoute, segments);
    }

    @Nullable
    private Route getRoute(@Nonnull Route rootRoute, @Nonnull List<String> segments) {
        if (segments.size() == 1 && rootRoute.getKey().equalsIgnoreCase(segments.get(0))) {
            return rootRoute;
        }
        if (rootRoute.getChildren().size() == 0 && segments.size() == 0) {
            return rootRoute;
        }
        if (rootRoute instanceof RouteHandler) {
            return rootRoute;
        }
        if ((rootRoute.getChildren().size() == 0 && segments.size() != 0) || segments.size() == 0) {
            return null;
        }
        Optional<Route> tr = rootRoute.getChildren().parallelStream().filter(route -> route.getKey().equalsIgnoreCase(segments.get(1))).findFirst();
        if (tr.isPresent()) {
            segments.remove(0);
            return getRoute(tr.get(), segments);
        } else {
            return null;
        }
    }

    @Override
    public RouteService addRouteToBase(Route route) {
        rootRoute.addChild(route);
        return this;
    }

    @Override
    public Route getBaseRoute() {
        return rootRoute;
    }

    @Override
    public Route getSpecialRoute(HTTPHeaderParser headers) {
        // TODO Make special routes be able to return http codes etc.
        return rootRoute;
    }

    @Override
    public List<String> getExcess(HTTPHeaderParser headers) {
        List<String> segments = new ArrayList<>(Arrays.asList(headers.getRequestURL().split("/")));
        return segments.size() <= 1 ? segments : getExcess(rootRoute, segments);
    }

    private List<String> getExcess(@Nonnull Route rootRoute, @Nonnull List<String> segments) {
        if (segments.size() == 1 && rootRoute.getKey().equalsIgnoreCase(segments.get(0))) {
            return segments;
        }
        if (rootRoute.getChildren().size() == 0 && segments.size() == 0) {
            return segments;
        }
        Optional<Route> tr = rootRoute.getChildren().parallelStream().filter(route -> route.getKey().equalsIgnoreCase(segments.get(1))).findFirst();
        if (tr.isPresent()) {
            segments.remove(0);
            if (tr.get() instanceof RouteHandler) {
                segments.remove(0);
                return segments;
            }
            return getExcess(tr.get(), segments);
        } else {
            return segments;
        }
    }
}
