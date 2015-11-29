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
    WSAPIMainClass wsapiMainClassInstance;

    public NovaServerOverride(NovaLogger logger, WSAPIMainClass wsapiMainClass) {
        super(logger);
        wsapiMainClassInstance = wsapiMainClass;
    }

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
