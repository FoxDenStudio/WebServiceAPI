package net.foxdenstudio.webserviceapi;

import org.spongepowered.api.plugin.PluginManager;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class SimpleRegistrationService implements IRegistrationService {


    /**
     * A method that forwards the register plugin request to the main plugin.
     * This is what should be accessed from outside plugins.
     *
     * @param pluginManager     An instance of the sponge PluginManager.
     * @param pluginID          A string that represents the unique id for the plugin.
     * @param pluginWebPath     A string that contains the root path for the plugins web pages.
     * @param classesToRegister Instances of classes that contain the @RequestHandler methods.
     */
    @Override
    public void registerPlugin(PluginManager pluginManager, String pluginID, String pluginWebPath, Object... classesToRegister) {
        WSAPIMainClass mainClass = (WSAPIMainClass) pluginManager.getPlugin("fds-wsapi").get().getInstance();
        mainClass.registerPlugin(pluginID, pluginWebPath, classesToRegister);
    }
}
