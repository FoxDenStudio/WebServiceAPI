package us.jfreedman.wsapi;

import com.google.inject.Inject;
import net.foxdenstudio.webserviceapi.WSAPIMainClass;
import net.foxdenstudio.webserviceapi.requests.IWebServiceRequest;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginManager;

@Plugin(id = "fds-wsapi-test", name = "FoxDenStudio - WSAPI - Test", dependencies = "after:fds-wsapi")
public class TestMainClass {

    @Inject
    private Game game;
    @Inject
    private Logger logger;
    @Inject
    private PluginManager pluginManager;

    @Listener
    public void onServerStarting(GameInitializationEvent event) {
        WSAPIMainClass wsapiMainClass = (WSAPIMainClass) pluginManager.getPlugin("fds-wsapi").get().getInstance();
        wsapiMainClass.registerPlugin("ffds-wsapi-test", "test", new TestHandlerClass());
    }
}
