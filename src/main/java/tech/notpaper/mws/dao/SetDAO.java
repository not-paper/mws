package tech.notpaper.mws.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import tech.notpaper.mws.dao.entities.GenericEntity;
import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManager;
import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManagerFactory;

public class SetDAO {
	
	public List<GenericEntity> getAllSets() {
		ConnectionManager cm = ConnectionManagerFactory.getMySqlConnectionManager("jdbc/mws");
		String query = "SELECT * FROM SETS";
		List<GenericEntity> sets = new LinkedList<>();
		try {
			ResultSet rs = cm.getConnection().createStatement().executeQuery(query);
			while(rs.next()) {
				sets.add(new GenericEntity(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Unable to get results from DB.", e);
		}
		
		return sets;
	}
}
