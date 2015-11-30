package net.foxdenstudio.webserviceapi.responses;

import com.google.common.io.ByteStreams;
import net.foxdenstudio.webserviceapi.novacula.server.Mimes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
