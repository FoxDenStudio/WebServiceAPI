package net.foxdenstudio.webserviceapi;

import org.spongepowered.api.plugin.PluginManager;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public interface IRegistrationService {
    void registerPlugin(PluginManager pluginManager, String pluginID, String pluginWebPath, Object... classesToRegister);
}
