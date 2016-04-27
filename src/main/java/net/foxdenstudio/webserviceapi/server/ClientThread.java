package net.foxdenstudio.webserviceapi.server;


import net.foxdenstudio.webserviceapi.WSAPIMainClass;
import net.foxdenstudio.webserviceapi.server.routes.Route;
import net.foxdenstudio.webserviceapi.server.routes.RouteService;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ClientThread implements Runnable {
    private final Socket socket;
    private final RouteService router;

    public ClientThread(@Nonnull Socket socket, @Nonnull RouteService router) {
        this.socket = socket;
        this.router = router;
    }

    @Override
    public void run() {
        try {
            HTTPHeaderParser headers = new HTTPHeaderParser(socket.getInputStream());
            headers.parseRequest();
            if (!router.routeAvailable(headers)) {
                error404(socket);
            } else if (router.isRouteSpecial(headers)) {
                Route route = router.getSpecialRoute(headers);
                List<String> excess = router.getExcess(headers);
                route.call(headers, socket, excess);
            } else {
                Route route = router.getRoute(headers);
                List<String> excess = router.getExcess(headers);
                if (route != null) {
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("HTTP/1.1 200 OK");
                    printWriter.println("Date: " + new Date().toString());
                    printWriter.println("Server: " + WSAPIMainClass.SERVER_NAME);
                    printWriter.println("Accept-Ranges: bytes");
                    printWriter.println("Content-Type: " + router.getContentType());
                    printWriter.println("Set-Cookie: pyid=" + UUID.randomUUID());
                    printWriter.println();
                    printWriter.flush();
                    route.call(headers, socket, excess);
                } else {
                    error404(socket);
                }
            }
            socket.close();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void error404(Socket client) throws IOException {
        PrintWriter printWriter = new PrintWriter(client.getOutputStream());
        printWriter.println("HTTP/1.1 404 Not Found");
        printWriter.println("Date: " + new Date().toString());
        printWriter.println("Server: " + WSAPIMainClass.SERVER_NAME);
        printWriter.println("Accept-Ranges: bytes");
        printWriter.println("Content-Type: text/html");
        printWriter.println();
        printWriter.println("<html>");
        printWriter.println("<Title>404 File Not Found</Title>");
        printWriter.println("<body style='background-color: #2A3132;'>");
        printWriter.println("<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>");
        printWriter.println("<div align='center'><center>");
        printWriter.println("<div style='width: 60%;padding: 7px;background-color: #763626;'>");
        printWriter.println("<p align='center'><font color='#FFFFFF' size='6'><strong>404 File Not Found</strong></font></p>");
        printWriter.println("<p><font color='#FFFFFF' size='4'>The Web Server cannot find the requested file or script.  Please check the URL to be sure that it is correct.</font></p>");
        printWriter.println("</div>");
        printWriter.println("</center></div>");
        printWriter.println("</html>");
        printWriter.flush();
    }

    public void stop() {
    }

    public Socket getSocket() {
        return socket;
    }
}
