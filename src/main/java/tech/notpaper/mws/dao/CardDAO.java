package tech.notpaper.mws.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import tech.notpaper.mws.dao.entities.GenericEntity;
import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManager;
import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManagerFactory;

public class CardDAO {
	
	private StringBuilder where = new StringBuilder();
	private String orderByColumn = "";
	private String orderByOrder = "";
	private ConnectionManager cm;
	
	public CardDAO() {
		cm = ConnectionManagerFactory.getMySqlConnectionManager("jdbc/mws");
	}
	
	public List<GenericEntity> getAllCards() {
		String query = "SELECT * FROM CARDS";
		List<GenericEntity> cards = new LinkedList<>();
		try {
			ResultSet rs = cm.getConnection().createStatement().executeQuery(query);
			while(rs.next()) {
				cards.add(new GenericEntity(rs));
			}
			rs.close();
			cm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Unable to get results from DB.", e);
		}
		
		return cards;
	}
	
	private void whereHelper() {
		if(where.length() == 0) {
			where.append("where ");
		} else {
			where.append(" and ");
		}
	}
	
	public CardDAO where(String prop, String value) {
		whereHelper();

		where.append(prop + " = '" + value + "'");

		return this;
	}

	public CardDAO whereLike(String prop, String value) {
		whereHelper();

		where.append(prop + " like '" + value + "'");

		return this;
	}
	
	public CardDAO whereIn(String prop, String value) {
		whereHelper();
		
		where.append(prop + " in (" + value + ")");
		
		return this;
	}
	
	public CardDAO orderBy(String column, String order) {
		orderByColumn = column;
		orderByOrder = order;
		return this;
	}
	
	public List<GenericEntity> fetch() {
		String query = "SELECT * FROM CARDS" + (where.length() > 0 ? " " + where.toString() : "");
		if(!orderByColumn.equals("")) {
			query += " ORDER BY " + orderByColumn + " " + orderByOrder;
		}
		
		List<GenericEntity> cards = new LinkedList<>();
		try {
			ResultSet rs = cm.getConnection().createStatement().executeQuery(query);
			while(rs.next()) {
				cards.add(new GenericEntity(rs));
			}
			rs.close();
			cm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Could not fetch cards.", e);
		}
		
		return cards;
	}
}
