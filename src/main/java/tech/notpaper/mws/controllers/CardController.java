package tech.notpaper.mws.controllers;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import tech.notpaper.mws.models.Card;

public class CardController {
	
	public static Card handle(InputStream request) throws IOException {
		Card card = Card.create();
		
		StringWriter writer = new StringWriter();
		IOUtils.copy(request, writer);
		JSONObject reqJson = new JSONObject(writer.toString());
		
		for(String key : reqJson.keySet()) {
			card.where(key, reqJson.getString(key));
		}
		
		return card;
	}
}
