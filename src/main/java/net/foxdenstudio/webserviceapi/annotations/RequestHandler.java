package net.foxdenstudio.webserviceapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestHandler {

    /**
     * The url of the page.
     * Ex. http://{host}:{port}/{pluginpath}/{NAME/THIS}
     *
     * @return String - Page access url.
     */
    String name();

    /**
     * Requested content type. Not in use right now.
     * @return RequestType - Expected content type.
     */
    RequestType requestType() default RequestType.CONTENT;


    /**
     * an Enum of the possible seperate types of expected content, will add more at a later date.  This will eventually allow for different methods to handle the same url but with different data types.
     */
    enum RequestType {
        AUDIO("An Audio file/stream"), VIDEO("A Video file/stream"), IMAGE("An image file"), CONTENT("Any web content - HTML, JAVASCRIPT, CSS, etc."), OTHER("Anything else...");

        private String helpData;

        RequestType(String helpData) {
            this.helpData = helpData;
        }

        /**
         * @return String - A short description of what the type is.
         */
        public String getHelpData() {
            return helpData;
        }
    }
}
