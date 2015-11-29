package net.foxdenstudio.webserviceapi.novacula.server;

import java.io.*;
import java.net.Socket;
import java.util.Date;

import static net.foxdenstudio.webserviceapi.Constants.BASE_PATH;
import static net.foxdenstudio.webserviceapi.Constants.DEFAULT_INDEX;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class ClientConnectionThread implements Runnable {

    protected Socket socket = null;
    protected String serverText = null;
    private String fileMimeType = ""; //fileMimeType is used to hold the Requested File mime type
    protected int nbRead;//Hold the number of bytes readed
    protected byte[] buffer;//Buffer contain data readed from file.

    private final String mailto = "npjoshf@gmail.com";


    public ClientConnectionThread(Socket clientSocket, String serverText) {
        nbRead = 0;//initializing nbRead to zero
        buffer = new byte[1024];//The buffer can contain  1 KB

        this.socket = clientSocket;
        this.serverText = serverText;
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

//            output.write(("HTTP/1.1 200 OK\n\nCCT: " + this.serverText + " - " + time + "\n\n").getBytes());
//            sendHTTPResponseOK(output,".html");
//            listDirContents("", output,"jfreedman.us","");
            ReadFile(path, "jfreedman.us", outputStream, path);
            outputStream.close();
            inputStream.close();
            System.out.println("Request processed in: " + (System.currentTimeMillis() - time));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Handling The ERROR 404 Not Found (occur when a file or a directory
     * doesn't exist under the web server
     */

    void FileNotFound(OutputStream outputStream, String serverHostName) throws IOException {

        outputStream.write("<html>\r\n".getBytes());
        outputStream.flush();
        outputStream.write("<Title>404 File Not Found</Title>\r\n".getBytes());
        outputStream.flush();
        outputStream.write("<body bgcolor='#008080'>\r\n".getBytes());
        outputStream.flush();
        outputStream.write("<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>\r\n".getBytes());
        outputStream.flush();
        outputStream.write("<div align='center'><center></p>\r\n".getBytes());
        outputStream.flush();
        outputStream.write("<table border='1' width='700' bgcolor='#004080'><TR>\r\n".getBytes());
        outputStream.write(("<td align='center'><p align='center'><font color='#FFFFFF' size='6'><strong>404 File Not Found</strong></font></p>" +
                "<p align='left'><font color='#FFFFFF'><strong>The Web Server cannot find the requested file or script" +
                " . check for your URL ,to be sure that your acces path is correct. Contact the Web Server administrator " +
                " if the problem persist  <a href='mailto:>" + mailto + "'>" + mailto + "</a></strong></font></p><p>&nbsp;</p></td></TR></Table>\r\n").getBytes());
        outputStream.flush();
        outputStream.write("</center></div>\r\n".getBytes());
        outputStream.flush();
        outputStream.write(("</html>" + "\r\n").getBytes());
        outputStream.flush();
    }

    /**
     * Sending The 200 OK Response to the client = The Request Was accepted
     */

    public void sendHTTPResponseOK(OutputStream outputStreamToClient, String fileName) throws IOException {

        /**Test the file extension, then get the appropriate mime type **/

        fileMimeType = Mimes.getMimes(fileName);

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


    /**
     * method ReadFile used to either read file,execute cgi program or excute a php script
     */
    public void ReadFile(String fileName, String serverHostName, OutputStream outputStream, String filePath) throws IOException {

        System.err.println("WROK: " + fileName + " :: " + filePath);

        File f = new File(BASE_PATH, fileName);//TDOD from config

        if (f == null) {
            System.out.println("The File doesn't exist");
        } else {
            if (!f.exists()) {
                /** Requested File doesn't exist => calling the method FileNotFound  **/
                this.FileNotFound(outputStream, serverHostName);
            } else {
                if (f.canRead()) {

                    /**Testing if the file is a CGI program **/
                    if (PHPHandler.isCGIProg(fileName)) {

                        /** calling the method ProcessCgi with option CGI_PROG **/
                        PHPHandler.processCGI(fileName, outputStream, 1);
                    }
                    /**Testing if the file is a PHP file **/
                    else if (PHPHandler.isPHPProg(fileName)) {

                        /** calling the method ProcessCgi with option PHP_PROG **/
                        PHPHandler.processCGI(fileName, outputStream, 2);
                    } else {
                        /** case of a simple file => readed then it's content sent to the client **/
                        FileInputStream fileInputStream = new FileInputStream(f);

                        /** calling method sendHTTPResponseOK */
                        this.sendHTTPResponseOK(outputStream, /*f,*/ filePath);

                        /**
                         * while is not end of file, method read store number of bytes equivalent to the
                         * buffer length in buffer variable then it's content is sent by method write
                         */
                        while ((nbRead = fileInputStream.read(buffer, 0, buffer.length)) != -1) {

                            outputStream.write(buffer, 0, nbRead);
                            outputStream.flush();

                        }
                        fileInputStream.close(); //File readed must be closed
                    }
                }
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getServerText() {
        return serverText;
    }

    public String getFileMimeType() {
        return fileMimeType;
    }

    public int getNbRead() {
        return nbRead;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public String getMailto() {
        return mailto;
    }

    public int setNbRead(int read) {
        nbRead = read;
        return nbRead;
    }
}
