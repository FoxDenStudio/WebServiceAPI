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

import com.google.inject.Inject;
import net.foxdenstudio.webserviceapi.server.Server;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Platform;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDS-WSAPI
 */
@Plugin(name = "FoxDenStudio - WSAPI", id = "foxdenstudio.wsapi", version = "0.2-BETA")
public class WSAPIMainClass {

    /**
     * A threadsafe queue that contains all requests made to the web-server.
     */
    public static final BlockingQueue<Runnable> requestQueue = new LinkedBlockingQueue<>();

    public static final int CORE_PORT = 2048;
    public static final byte[] CORE_BIND_IP = {0, 0, 0, 0};
    public static final int CORE_BACKLOG = 50;
    public static final int DEFAULT_CORE_THREADS = 10;
    public static final int MAX_CORE_THREADS = 20;
    public static final int CORE_THREAD_BACKLOG = MAX_CORE_THREADS * 2;
    public static final long CORE_THREAD_KEEP_ALIVE = 5;
    public static final TimeUnit CORE_THREAD_KEEP_ALIVE_TIME_UNIT = TimeUnit.MINUTES;
    public static final String SERVER_NAME = "WSAPI";

    @Inject
    private Game game;
    @Inject
    private Logger logger;
    @Inject
    private PluginManager pluginManager;

    private Server server;

    @Listener
    public void onGamePreInitializationEvent(GamePreInitializationEvent event) {
        if (game.getPlatform().getType() == Platform.Type.SERVER) {

            try {
                game.getServiceManager().setProvider(this, IRegistrationService.class, new SimpleRegistrationService());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

//            game.getScheduler().createTaskBuilder().execute((task) -> {
//                try {
//                    if (!requestQueue.isEmpty())
//                        requestQueue.take().run();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).name("FDS-WSAPI - CheckRequestsTask").delayTicks(50).interval(1, TimeUnit.MILLISECONDS).submit(this);
        }
    }

    @Listener
    public void onGameInitializationEvent(GameInitializationEvent event) {
        if (game.getPlatform().getType() == Platform.Type.SERVER) {
            server = new Server();
            server.start();
        }
    }

    @Listener
    public void onGameServerStoppingEvent(GameStoppingEvent event) {
        if (game.getPlatform().getType() == Platform.Type.SERVER) {
            server.stop();
        }
    }

    public Logger getLogger() {
        return logger;
    }
}
