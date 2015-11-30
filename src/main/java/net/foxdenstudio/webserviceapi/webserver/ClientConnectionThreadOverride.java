package net.foxdenstudio.webserviceapi.webserver;

import com.google.inject.Inject;
import net.foxdenstudio.webserviceapi.WSAPIMainClass;
import net.foxdenstudio.webserviceapi.novacula.server.ClientConnectionThread;
import net.foxdenstudio.webserviceapi.requests.IWebServiceRequest;
import org.slf4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

import static net.foxdenstudio.webserviceapi.Constants.DEFAULT_INDEX;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class ClientConnectionThreadOverride extends ClientConnectionThread {

    WSAPIMainClass wsapiMainClassInstance;

    public ClientConnectionThreadOverride(Socket clientSocket, WSAPIMainClass wsapiMainClassInstance, String serverText) {
        super(clientSocket, serverText);
        this.wsapiMainClassInstance = wsapiMainClassInstance;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            long time = System.currentTimeMillis();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tmp = bufferedReader.readLine(); //read from the stream
            if (tmp.isEmpty()) return;

            String tmp2 = new String(tmp);
            int start = 0;
            int end = 0;
            for (int a = 0; a < tmp2.length(); a++) {
                if (tmp2.charAt(a) == ' ' && start != 0) {
                    end = a;
                    break;
                }
                if (tmp2.charAt(a) == ' ' && start == 0) {
                    start = a;
                }
            }

            String path = tmp2.substring(start + 2, end);
            path = path.trim().equalsIgnoreCase("") ? DEFAULT_INDEX : path;
            String query = path.indexOf('?') > -1 ? path.substring(path.indexOf('?') + 1).trim() : "";
            path = path.indexOf('?') > -1 ? path.substring(0, path.indexOf('?')) : path;
            String pluginName = path.indexOf('/') > -1 ? path.substring(0, path.indexOf('/')).trim() : "DEFPLUG";
            String file = path.indexOf('/') > -1 ? path.substring(path.indexOf('/') + 1).trim() : "home";

            HashMap<String, String> tempMap = new HashMap<>();

            if (!query.equalsIgnoreCase("")) {
                if (query.indexOf('&') > -1) {
                    String[] qStringArr = query.split("&");
                    for (String elemString : qStringArr) {
                        if (elemString.indexOf('=') > -1) {
                            String[] qElemParts = elemString.split("=");
                            tempMap.put(qElemParts[0], qElemParts[1]);
                        } else {
                            tempMap.put(elemString, "true");
                        }
                    }
                } else {
                    if (query.indexOf('=') > -1) {
                        String[] qElemParts = query.split("=");
                        tempMap.put(qElemParts[0], qElemParts[1]);
                    } else {
                        tempMap.put(query, "true");
                    }
                }
            }
            final ClientConnectionThreadOverride ti = this;

            WSAPIMainClass.requestQueue.add(() -> {
                wsapiMainClassInstance.loadPage(ti, pluginName, file, () -> tempMap);
                try {
                    outputStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                wsapiMainClassInstance.getLogger().debug("Request processed in: " + (System.currentTimeMillis() - time));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendHTTPResponseOK(OutputStream outputStreamToClient, String fileMimeType) throws IOException {

        /** Sending a Http Response of type
         *
         * HTTP/1.x 200 OK + crlf
         * Date : xx/xx/xxxx + crlf
         * Server : serverName + crlf
         * content-length : X bytes + crlf
         * content-type : mime type + crlf
         *
         * */
        outputStreamToClient.write("HTTP/1.1 200 OK\r\n".getBytes());
        outputStreamToClient.flush();
        outputStreamToClient.write(("Date: " + new Date().toString() + "\r\n").getBytes());
        outputStreamToClient.flush();
        outputStreamToClient.write("Server: NovaServer1.5r\n".getBytes());
        outputStreamToClient.flush();
        outputStreamToClient.write("Accept-Ranges: bytes\r\n".getBytes());
        outputStreamToClient.flush();
        outputStreamToClient.write(("Content-Type: " + fileMimeType + "\r\n").getBytes());
        outputStreamToClient.flush();
        outputStreamToClient.write("\r\n".getBytes());
        outputStreamToClient.flush();
    }


}
