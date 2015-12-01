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
import net.foxdenstudio.webserviceapi.novacula.server.ClientConnectionThread;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

import static net.foxdenstudio.webserviceapi.Constants.DEFAULT_INDEX;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDS-WSAPI
 */
@SuppressWarnings("SameParameterValue")
public class ClientConnectionThreadOverride extends ClientConnectionThread {

    /**
     * An instance of the main plugin class.  Allows for access to non-static methods and variables.
     */
    private final WSAPIMainClass wsapiMainClassInstance;

    /**
     * A version of the ClientConnectionThread object modified slightly to fit the needs of this plugin.
     *
     * @param clientSocket           Socket - An instance of a socket that the client connected with.
     * @param wsapiMainClassInstance WSAPIMainClass - An instance of the plugin, allows for non-static access to methods.
     * @param serverText             String - Server name... Not in use.
     */
    public ClientConnectionThreadOverride(Socket clientSocket, WSAPIMainClass wsapiMainClassInstance, String serverText) {
        super(clientSocket, serverText);
        this.wsapiMainClassInstance = wsapiMainClassInstance;
    }

    /**
     * When called will process client requests.
     * Directly overrides the ClientConnectionThread start.
     */
    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            long time = System.currentTimeMillis();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tmp = bufferedReader.readLine(); //read from the stream
            if (tmp.isEmpty()) return;

            int start = 0;
            int end = 0;
            for (int a = 0; a < tmp.length(); a++) {
                if (tmp.charAt(a) == ' ' && start != 0) {
                    end = a;
                    break;
                }
                if (tmp.charAt(a) == ' ' && start == 0) {
                    start = a;
                }
            }

            String path = tmp.substring(start + 2, end);
            path = path.trim().equalsIgnoreCase("") ? DEFAULT_INDEX : path;
            String query = path.indexOf('?') > -1 ? path.substring(path.indexOf('?') + 1).trim() : "";
            path = path.indexOf('?') > -1 ? path.substring(0, path.indexOf('?')) : path;
            String pluginName = path.indexOf('/') > -1 ? path.substring(0, path.indexOf('/')).trim() : "DEFPLUG";
            String file = path.indexOf('/') > -1 ? path.substring(path.indexOf('/') + 1).trim() : "home"; //TODO change path so that file checks if not there or ret home

            @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
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
         * Date : DD/MM/YYYY + crlf
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
