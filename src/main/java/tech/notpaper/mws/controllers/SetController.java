package tech.notpaper.mws.controllers;

import java.io.IOException;
import java.io.InputStream;

import tech.notpaper.mws.models.Card;
import tech.notpaper.mws.models.Searchable;
import tech.notpaper.mws.models.Set;

public abstract class SetController {
	
	public static final Searchable handle(InputStream request) throws IOException, ClassNotFoundException {
		Searchable s = new Set();
		
		s.readObject(request);
		
		return s;
	}
}