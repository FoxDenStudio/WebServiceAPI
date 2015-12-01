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

package net.foxdenstudio.webserviceapi.webserver;

import net.foxdenstudio.webserviceapi.WSAPIMainClass;
import net.foxdenstudio.webserviceapi.novacula.server.NovaServer;
import net.foxdenstudio.webserviceapi.novacula.utils.NovaInfo;
import net.foxdenstudio.webserviceapi.novacula.utils.NovaLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static net.foxdenstudio.webserviceapi.Constants.SERVER_PORT;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDS-WSAPI
 */
public class NovaServerOverride extends NovaServer {

    /**
     * An instance of the main plugin class.  Allows for access to non-static methods and variables.
     */
    private final WSAPIMainClass wsapiMainClassInstance;

    /**
     * A version of the NovaServer object modified slightly to fit the needs of this plugin.
     *
     * @param logger         NovaLogger - An instance of a nova server logger.
     * @param wsapiMainClass WSAPIMainClass - An instance of the plugin, allows for non-static access to methods.
     */
    public NovaServerOverride(NovaLogger logger, WSAPIMainClass wsapiMainClass) {
        super(logger);
        wsapiMainClassInstance = wsapiMainClass;
    }

    /**
     * When called will start the NovaServer.
     * Directly overrides the NovaServer start.
     */
    @Override
    public void start() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        logger.logQuiet(NovaLogger.ANSI_BLUE + "\n----------SERVER STARTING------------\n");
        try {
            serverSocket = new ServerSocket(SERVER_PORT); //TODO From Config

            logger.logQuiet(NovaLogger.ANSI_BLUE + "\n----------SERVER IS RUNNING----------\n");
            logger.logQuiet(NovaInfo.getClientInfo());
            logger.logQuiet(NovaInfo.getFileInfo());
        } catch (IOException e) {
            logger.logQuiet(NovaLogger.ANSI_BLUE + "\n----------SERVER START FAILED----------\n");

            e.printStackTrace();
        }

        if (serverSocket != null) {
            while (!isStopped()) {
                Socket clientSocket;
                try {
                    clientSocket = this.serverSocket.accept();
                } catch (IOException e) {
                    if (isStopped()) {
                        System.out.println("Server Stopped.");
                        return;
                    }
                    throw new RuntimeException("Error accepting client connection", e);
                }
                new Thread(
                        new ClientConnectionThreadOverride(clientSocket, wsapiMainClassInstance, WSAPIMainClass.class.getSimpleName() + " - Multithreaded Server")
                ).start();
            }
        }

    }
}
