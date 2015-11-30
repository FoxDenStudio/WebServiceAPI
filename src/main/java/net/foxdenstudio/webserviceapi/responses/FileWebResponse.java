package net.foxdenstudio.webserviceapi.responses;

import com.google.common.io.ByteStreams;
import net.foxdenstudio.webserviceapi.novacula.server.Mimes;
import org.spongepowered.api.util.command.args.parsing.InputTokenizer;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class FileWebResponse implements IWebServiceResponse {

    byte[] byteData = new byte[1024];
    String mimeType;

    public FileWebResponse(File page) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(page)) {
            byteData = new byte[(int) page.length()];

            fileInputStream.read(byteData);
        }
        mimeType = Mimes.getMimes(page.getName());
    }

    public FileWebResponse(InputStream resource) {
        try {
            System.out.println("RES AVAL : " + resource.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byteData = ByteStreams.toByteArray(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            mimeType = Mimes.getMimes(resource.toURL().toExternalForm());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        try {
            resource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String mimeType() {
        return mimeType;
    }

    @Override
    public byte[] getByteData() {
        return byteData;
    }
}
