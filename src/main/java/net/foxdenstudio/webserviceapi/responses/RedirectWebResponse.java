package net.foxdenstudio.webserviceapi.responses;

import java.net.URL;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class RedirectWebResponse implements IWebServiceResponse {


    private String data;

    public RedirectWebResponse(URL passTarget) {
        this(passTarget, 3);
    }

    public RedirectWebResponse(URL passTarget, Integer redirectTime) {
        this(passTarget, redirectTime, "");
    }

    public RedirectWebResponse(URL passTarget, Integer redirectTime, String customMessage) {
        data = "<head><meta http-equiv='refresh' content='" + redirectTime + "; url=" + passTarget.toExternalForm() + "'></head><body><h2>Redirect</h2>You will be redirected to " + passTarget.toExternalForm() + " in 3 seconds.<br />If the page does not automatically reload, please click <a href='" + passTarget.toExternalForm() + "'>HERE</a>.<br /><br />" + customMessage + "</body>";
    }

    /**
     * @return A byte array created from a redirect request that is passed on to the client.
     */
    @Override
    public byte[] getByteData() {
        return data.getBytes();
    }
}
