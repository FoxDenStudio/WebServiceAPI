package net.foxdenstudio.webserviceapi.server;

import net.foxdenstudio.webserviceapi.WSAPIMainClass;
import net.foxdenstudio.webserviceapi.server.routes.DefaultRoutes;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class Server extends Thread implements Runnable {

    private boolean running;

    private ThreadPoolExecutor threadPoolExecutor;
    private HashMap<SocketAddress, ClientThread> clientThreads;

    public Server() {
        super("IN Statistics Server");
        setRunning(true);
        clientThreads = new HashMap<>(WSAPIMainClass.DEFAULT_CORE_THREADS);
        ThreadFactory threadFactory = r -> new Thread(r, "ClientThread");
        threadPoolExecutor = new ThreadPoolExecutor(WSAPIMainClass.DEFAULT_CORE_THREADS, WSAPIMainClass.MAX_CORE_THREADS, WSAPIMainClass.CORE_THREAD_KEEP_ALIVE, WSAPIMainClass.CORE_THREAD_KEEP_ALIVE_TIME_UNIT, new ArrayBlockingQueue<>(WSAPIMainClass.CORE_THREAD_BACKLOG), threadFactory);

    }

    @Override
    public void run() {
        while (isRunning()) {
            try (ServerSocket serverSocket = new ServerSocket(WSAPIMainClass.CORE_PORT, WSAPIMainClass.CORE_BACKLOG, InetAddress.getByAddress(WSAPIMainClass.CORE_BIND_IP))) {
                threadPoolExecutor.allowCoreThreadTimeOut(true);
                while (!serverSocket.isClosed()) {
                    try {
                        Socket client = serverSocket.accept();
                        ClientThread clientThread = new ClientThread(client, DefaultRoutes.getInstance());
                        clientThreads.put(client.getRemoteSocketAddress(), clientThread);
                        threadPoolExecutor.execute(clientThread);
                    } catch (Exception general) {
                        general.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
