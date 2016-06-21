package tech.notpaper.mws.views;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import tech.notpaper.mws.controllers.CardController;
import tech.notpaper.mws.controllers.SetController;
import tech.notpaper.mws.models.Card;
import tech.notpaper.mws.models.Searchable;
import tech.notpaper.mws.models.Set;

public abstract class GeneralView {
	
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract String getSearchable(InputStream request) throws IOException, ClassNotFoundException;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("isalive")
    public String isAlive() {
    	return "{\n\tisAlive: true\n}";
    }
    
    @Path("card")
    public static class CardView extends GeneralView {
    	@Override
    	public String getSearchable(InputStream request) throws IOException, ClassNotFoundException {
    		Searchable card = CardController.handle(request);
    		
    		return card.toString();
    	}
    }
    
    @Path("set")
    public static class SetView extends GeneralView {
    	@Override
    	public String getSearchable(InputStream request) throws IOException, ClassNotFoundException {
    		Searchable set = SetController.handle(request);
    		
    		return set.toString();
    	}
    }
}
