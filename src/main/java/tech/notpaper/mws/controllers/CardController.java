package tech.notpaper.mws.controllers;

import java.io.IOException;
import java.io.InputStream;

import tech.notpaper.mws.dao.CardDAO;
import tech.notpaper.mws.models.Card;
import tech.notpaper.mws.models.Searchable;

public class CardController {
	
	public static final Searchable handle(InputStream request) throws IOException, ClassNotFoundException {
		CardDAO dao = new CardDAO();
		
		
		
		Searchable s = new Card();
		
		s.readObject(request);
		
		return s;
	}
}
