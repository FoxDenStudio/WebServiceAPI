package net.foxdenstudio.webserviceapi.server.routes;

import net.foxdenstudio.webserviceapi.server.HTTPHeaderParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface RouteService {
    /**
     * @param route Route to add to the base Route node
     * @return RouteService instance
     */
    RouteService addRouteToBase(Route route);

    /**
     * @return An instance of the base Route node
     */
    Route getBaseRoute();

    boolean routeAvailable(@Nonnull HTTPHeaderParser headers);

    boolean routeAvailable(@Nonnull String requestURL);

    boolean isRouteSpecial(@Nonnull HTTPHeaderParser headers);

    @Nullable
    Route getRoute(@Nonnull HTTPHeaderParser headers);

    Route getSpecialRoute(HTTPHeaderParser headers);

    List<String> getExcess(HTTPHeaderParser headers);

    String getContentType();
}
