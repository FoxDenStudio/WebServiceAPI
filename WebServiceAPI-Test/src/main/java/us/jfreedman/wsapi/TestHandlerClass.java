package us.jfreedman.wsapi;

import net.foxdenstudio.webserviceapi.requests.IWebServiceRequest;
import net.foxdenstudio.webserviceapi.responses.FileWebResponse;
import net.foxdenstudio.webserviceapi.responses.IWebServiceResponse;
import net.foxdenstudio.webserviceapi.annotations.RequestHandler;
import org.spongepowered.api.Sponge;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestHandlerClass {

    @RequestHandler(name = "home", requestType = RequestHandler.RequestType.CONTENT)
    public IWebServiceResponse pageHome(IWebServiceRequest webServiceRequest) throws IOException {
        File pageFolder = new File("pages");
        if (!pageFolder.exists()) pageFolder.mkdirs();
        return new FileWebResponse(new File(pageFolder, "index.html"));
    }

    @RequestHandler(name = "lol", requestType = RequestHandler.RequestType.CONTENT)
    public IWebServiceResponse pageLol(IWebServiceRequest webServiceRequest) {
        File pageFolder = new File("pages");
        if (!pageFolder.exists()) pageFolder.mkdirs();

        String retString = "";
        for (Map.Entry<String, Object> entry : Sponge.getPlatform().asMap().entrySet()) {
            retString += "[ " + entry.getKey() + " :: " + entry.getValue() + " ]<br />";
        }

        return retString::getBytes;//Sponge.getPlatform().toString()::getBytes;

    }

}
