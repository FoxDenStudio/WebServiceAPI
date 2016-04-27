package us.jfreedman.wsapi;

import com.google.inject.Inject;
import net.foxdenstudio.webserviceapi.IRegistrationService;
import net.foxdenstudio.webserviceapi.responses.FileWebResponse;
import net.foxdenstudio.webserviceapi.responses.RedirectWebResponse;
import net.foxdenstudio.webserviceapi.server.routes.Route;
import net.foxdenstudio.webserviceapi.server.routes.RouteHandler;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginManager;

import java.io.File;
import java.util.Map;

@Plugin(id = "foxdenstudio.wsapi-test", name = "FoxDenStudio - WSAPI - Test", version = "0.2-BETA", dependencies = @Dependency(id = "foxdenstudio.wsapi"))
public class TestMainClass {

    @Inject
    private Game game;
    @Inject
    private Logger logger;
    @Inject
    private PluginManager pluginManager;

    @Listener
    public void onServerStarting(GameInitializationEvent event) {
        game.getServiceManager().provide(IRegistrationService.class).ifPresent(irs -> {
            irs.addChild(new Route("test", new FileWebResponse(new File("pages/index.html"))::handle));
            irs.addChild(new Route("try", new RedirectWebResponse("http://google.com")::handle));
        });

        game.getServiceManager().provide(IRegistrationService.class).ifPresent(irs -> {
            irs.addChild(new RouteHandler("lol", (httpHeaderParser, socket, strings) -> {
                try {
                    String retString = "";
                    for (Map.Entry<String, Object> entry : Sponge.getPlatform().asMap().entrySet()) {
                        retString += "[ " + entry.getKey() + " :: " + entry.getValue() + " ]<br />";
                    }
                    retString += "<br /><br />";
                    socket.getOutputStream().write(retString.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
        });
    }
}
