package net.foxdenstudio.webserviceapi.requests;

import java.util.HashMap;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public interface IWebServiceRequest {

    /**
     * A Map of key->value data corresponding to query data (like GET)
     * {something}/index?hello=test  would return a map containing hello -> test
     *
     * @return A HashMap<String, String> that contains query data.
     */
    HashMap<String, String> queryData();

}
