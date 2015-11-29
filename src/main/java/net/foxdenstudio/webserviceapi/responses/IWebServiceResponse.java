package net.foxdenstudio.webserviceapi.responses;

import net.foxdenstudio.webserviceapi.novacula.server.Mimes;

import java.io.DataOutputStream;
import java.io.InputStream;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public interface IWebServiceResponse {

    byte[] getByteData();

    default String mimeType() {
        return Mimes.getMimes(".txt");
    }
}
