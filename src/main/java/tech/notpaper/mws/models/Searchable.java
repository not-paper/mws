package tech.notpaper.mws.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManager;
import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManagerFactory;

public abstract class Searchable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5064024774779837985L;

	private static final String dataSource = "jdbc/mws";

	private JSONObject data = null;
	private StringBuilder where = new StringBuilder();
	private ConnectionManager cm = ConnectionManagerFactory.getMySqlConnectionManager(dataSource);
	
	public final Searchable where(String prop, String value) {
		whereHelper();

		where.append(prop + " = '" + value + "'");

		return this;
	}

	public final Searchable whereLike(String prop, String value) {
		whereHelper();

		where.append(prop + " like '" + value + "'");

		return this;
	}
	
	public final Searchable whereIn(String prop, String value) {
		whereHelper();
		
		where.append(prop + " in (" + value + ")");
		
		return this;
	}
	
	private void whereHelper() {
		if(where.length() == 0) {
			where.append("where ");
		} else {
			where.append(" and ");
		}
	}
	
	protected abstract String getTableName();

	@Override
	public final String toString() {
		if (data == null) {

			String query = "SELECT * FROM " + this.getTableName() + (where.length() > 0 ? " " + where.toString() : "");

			Connection c;
			data = new JSONObject();

			try {
				c = cm.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(query);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columns = rsmd.getColumnCount();
				List<JSONObject> cards = new LinkedList<>();
				while (rs.next()) {
					JSONObject card = new JSONObject();

					for (int i = 1; i <= columns; i++) {
						card.put(rsmd.getColumnName(i), rs.getString(i));
					}
					cards.add(card);
				}

				data.put("cards", cards);
			} catch (SQLException e) {
				throw new RuntimeException("Failed to load card(s). [" + query + "]", e);
			}
		}
		
		return data.toString();
	}

	public final void writeObject(OutputStream out) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
		osw.write(this.toString());
	}

	private static Pattern inPattern = Pattern.compile("in\\((.*?)\\)");
	private static Pattern likePattern = Pattern.compile("like\\((.*?)\\)");
	public final void readObject(InputStream in) throws IOException, ClassNotFoundException {
		JSONObject input = new JSONObject(IOUtils.toString(in, "UTF-8"));
		
		for(String key : input.keySet()) {
			String value = input.getString(key);
			Matcher inPMatcher = inPattern.matcher(value);
			Matcher lPMatcher = likePattern.matcher(value);
			if (inPMatcher.matches()) {
				this.whereIn(key, inPMatcher.group(1));
			} else if (lPMatcher.matches()) {
				this.whereLike(key, lPMatcher.group(1));
			} else {
				this.where(key, value);
			}
		}
	}
}
