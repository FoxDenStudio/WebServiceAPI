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

    String name();

    RequestType requestType() default RequestType.CONTENT;

    enum RequestType {
        AUDIO("An Audio file/stream"), VIDEO("A Video file/stream"), IMAGE("An image file"), CONTENT("Any web content - HTML, JAVASCRIPT, CSS, etc."), OTHER("Anything else...");

        private String helpData;

        RequestType(String helpData) {
            this.helpData = helpData;
        }

        public String getHelpData() {
            return helpData;
        }
    }
}
