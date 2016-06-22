package tech.notpaper.mws.dao.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONObject;

public class GenericEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3603963104338230951L;
	
	private JSONObject data;
	
	public GenericEntity(ResultSet rs) {	
		this.data = new JSONObject();
		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			for(int i = 1; i <= columns; i++) {
				this.data.put(rsmd.getColumnName(i), rs.getString(i));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Unable to create entity.", e);
		}
	}
	
	public JSONObject getData() {
		return this.data;
	}
}
