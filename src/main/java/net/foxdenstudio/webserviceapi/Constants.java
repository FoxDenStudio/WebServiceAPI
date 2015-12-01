package net.foxdenstudio.webserviceapi;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class Constants {

    /**
     * The port that the web server will run on.  Will eventually be changeable in the config.
     */
    public static final int SERVER_PORT = 3080;

    /**
     * The webmaster email address. Will appear in generated errors and status reports.
     */
    public static final String ADMIN_EMAIL = "npjoshf@gmail.com";

    /**
     * The default page if the user doesn't specify one.
     */
    public static final String DEFAULT_INDEX = "home";

    /**
     * The root directory for NovaServer related things... Currently the root for web files.
     */
    public static final String BASE_PATH = "pages";

}
