package tech.notpaper.mws.models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManager;
import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManagerFactory;

public class Card implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5321301334609084938L;
	
	private static final String host = "localhost";
	private static final String user = "mwsuser";
	private static final String pass = "pegasys11";
	private static final String db = "mws";

	private StringBuilder where;
	private ConnectionManager cm;
	
	public static Card create() {
		return new Card();
	}
	
	private Card() {
		where = new StringBuilder();
		cm = ConnectionManagerFactory.getMySqlConnectionManager(host, user, pass, db);
	}
	
	public Card where(String prop, String value) {
		if(where.length() == 0) {
			where.append("where ");
		} else {
			where.append(" and ");
		}
		
		where.append(prop + "=" + value);
		
		return this;
	}
	
	public Card whereLike(String prop, String value) {
		if(where.length() == 0) {
			where.append("where ");
		} else {
			where.append(" and ");
		}
		
		where.append(prop + "like" + value);
		
		return this;
	}
	
	@Override
	public String toString() {
		String query = "SELECT * FROM CARDS" + (where.length() > 0 ? " " + where.toString() : "");
		
		Connection c;
		JSONObject result = new JSONObject();
		
		try {
			c = cm.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			List<JSONObject> cards = new LinkedList<>();
			while(rs.next()) {
				JSONObject card = new JSONObject();
				
				for(int i = 1; i <= columns; i++) {
					card.put(rsmd.getColumnName(i), rs.getString(i));
				}
				cards.add(card);
			}
			
			result.put("cards", cards);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}
}
