package net.foxdenstudio.webserviceapi.responses;

import net.foxdenstudio.webserviceapi.novacula.server.Mimes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    @Override
    public String mimeType() {
        return mimeType;
    }

    @Override
    public byte[] getByteData() {
        return byteData;
    }
}
