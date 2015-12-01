package net.foxdenstudio.webserviceapi.responses;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joshua Freedman on 11/30/2015.
 * Project: SpongeForge | FDS-WSAPI
 */
public class CombinedWebResponse implements IWebServiceResponse {


    LinkedList<IWebServiceResponse> responseList;

    public CombinedWebResponse() {
        this(new LinkedList<>());
    }

    public CombinedWebResponse(IWebServiceResponse response) {
        this(new LinkedList<IWebServiceResponse>() {{
            add(response);
        }});
    }

    public CombinedWebResponse(LinkedList<IWebServiceResponse> responseList) {
        this.responseList = responseList;
    }

    public CombinedWebResponse addResponse(IWebServiceResponse response) {
        responseList.add(response);
        return this;
    }

    public CombinedWebResponse addResponse(Integer index, IWebServiceResponse response) {
        responseList.add(index, response);
        return this;
    }

    public CombinedWebResponse removeResponse(Integer index) {
        responseList.remove(index);
        return this;
    }

    public Integer getSize() {
        return responseList.size();
    }

    public LinkedList<IWebServiceResponse> getResponseList() {
        return responseList;
    }

    public void setResponseList(LinkedList<IWebServiceResponse> responseList) {
        this.responseList = responseList;
    }

    /**
     * @return A byte array that will be written out to the clients stream.
     */
    @Override
    public byte[] getByteData() {
        List<byte[]> list = new LinkedList<>();
        responseList.forEach(response -> list.add(response.getByteData()));
        return concatenateByteArrays(list);
    }

    public byte[] concatenateByteArrays(List<byte[]> blocks) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (byte[] b : blocks) {
            os.write(b, 0, b.length);
        }
        return os.toByteArray();
    }

    //TODO Add Mime Priorities....

    /**
     * @return A string like plain/text that is the content type.  If the plugin is a dependency, you can use the Mimes.getMime(".{fileext}")
     */
    @Override
    public String mimeType() {
        return null;
    }
}
