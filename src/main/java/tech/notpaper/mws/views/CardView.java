package tech.notpaper.mws.views;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.JSONP;

import tech.notpaper.mws.controllers.CardController;
import tech.notpaper.mws.models.Card;

@Path("card")
public class CardView {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getCard(InputStream json) throws IOException {
    	Card card = CardController.handle(json);
    	
    	return card.toString();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("isalive")
    public String isAlive() {
    	return "{\n\tisAlive: true\n}";
    }
}
