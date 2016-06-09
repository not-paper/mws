package tech.notpaper.mws;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

@Path("identifycard")
public class IdentifyCard {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("*/*")
    public String identifyCard(InputStream stream) throws IOException {
    	byte[] image = IOUtils.toByteArray(stream);
    	
    	FileUtils.writeByteArrayToFile(new File("uploadedimage.jpg"), image);
    	
        return "{\nimage: " + new File(".").getAbsolutePath() + "/uploadimage.jpg\n}";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("isalive")
    public String isAlive() {
    	return "{\n\tisAlive: true\n}";
    }
}
