package net.foxdenstudio.webserviceapi.webserver;

import net.foxdenstudio.webserviceapi.WSAPIMainClass;
import net.foxdenstudio.webserviceapi.novacula.server.ClientConnectionThread;
import net.foxdenstudio.webserviceapi.novacula.server.NovaServer;
import net.foxdenstudio.webserviceapi.novacula.utils.NovaInfo;
import net.foxdenstudio.webserviceapi.novacula.utils.NovaLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static net.foxdenstudio.webserviceapi.Constants.SERVER_PORT;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class NovaServerOverride extends NovaServer {

    /**
     * An instance of the main plugin class.  Allows for access to non-static methods and variables.
     */
    WSAPIMainClass wsapiMainClassInstance;

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
                Socket clientSocket = null;
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
                        new ClientConnectionThreadOverride(clientSocket, wsapiMainClassInstance, "Multithreaded Server")
                ).start();
            }
        }

    }
}
