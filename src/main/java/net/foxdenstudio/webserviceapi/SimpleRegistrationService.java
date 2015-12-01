/*
 *  This file is part of a FoxDenStudio Project, licensed under the MIT License (MIT).
 *
 *  Copyright (c) 2015. FoxDenStudio - http://foxdenstudio.net/ and contributors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 */

package net.foxdenstudio.webserviceapi;

import org.spongepowered.api.plugin.PluginManager;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDS-WSAPI
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
