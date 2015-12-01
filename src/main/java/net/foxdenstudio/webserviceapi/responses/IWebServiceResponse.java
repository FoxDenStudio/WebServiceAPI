package net.foxdenstudio.webserviceapi.responses;

import net.foxdenstudio.webserviceapi.novacula.server.Mimes;

import java.io.DataOutputStream;
import java.io.InputStream;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDSFDS-WSAPI
 */
public interface IWebServiceResponse {

    /**
     * @return A byte array that will be written out to the clients stream.
     */
    byte[] getByteData();

    /**
     * @return A string like plain/text that is the content type.  If the plugin is a dependency, you can use the Mimes.getMime(".{fileext}")
     */
    default String mimeType() {
        return Mimes.getMimes(".html");
    }
}
