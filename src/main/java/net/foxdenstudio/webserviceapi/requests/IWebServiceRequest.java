package net.foxdenstudio.webserviceapi.requests;

import java.util.HashMap;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDSFDS-WSAPI
 */
public interface IWebServiceRequest {

    /**
     * A Map of key to value data corresponding to query data (like GET)
     * {something}/index?hello=test  would return a map containing: <br><code>hello | test</code>
     *
     * @return A HashMap (String, String) that contains query data.
     */
    HashMap<String, String> queryData();

}
