package net.foxdenstudio.webserviceapi;

import com.google.inject.Inject;
import org.spongepowered.api.plugin.PluginManager;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class SimpleRegistrationService implements IRegistrationService {

    @Override
    public void registerPlugin(PluginManager pluginManager, String pluginID, String pluginWebPath, Object... classesToRegister) {
        WSAPIMainClass mainClass = (WSAPIMainClass) pluginManager.getPlugin("fds-wsapi").get().getInstance();
        mainClass.registerPlugin(pluginID, pluginWebPath, classesToRegister);
    }
}
