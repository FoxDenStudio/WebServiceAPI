package net.foxdenstudio.webserviceapi.novacula.servers;

import net.foxdenstudio.webserviceapi.novacula.utils.NovaInfo;
import net.foxdenstudio.webserviceapi.novacula.utils.NovaLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class NovaServer {
    private NovaLogger logger;

    private ServerSocket serverSocket = null;
    private boolean isStopped = false;  //this should be changed to 'running' or 'isRunning'

    protected Thread runningThread = null;

    public NovaServer(NovaLogger logger) {
        this.logger = logger;
    }

    public void start() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        logger.logQuiet(NovaLogger.ANSI_BLUE + "\n----------SERVER STARTING----------\n");
        try {
            serverSocket = new ServerSocket(3080); //TODO From Config

            logger.logQuiet(NovaLogger.ANSI_BLUE + "\n----------SERVER IS RUNNING----------\n");
            logger.logQuiet(NovaInfo.getClientInfo());
            logger.logQuiet(NovaInfo.getFileInfo());
            logger.logClear();
            logger.log("Log so far:" + logger.getLog());
        } catch (IOException e) {
            logger.logQuiet(NovaLogger.ANSI_BLUE + "\n----------SERVER START FAILED----------\n");

            e.printStackTrace();
            System.exit(1);
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
                        new ClientConnectionThread(clientSocket, "Multithreaded Server")
                ).start();
            }
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing servers", e);
        }
    }

    public NovaLogger getLogger() {
        return logger;
    }

}
