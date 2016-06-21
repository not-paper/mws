package tech.notpaper.mws.models;

public class Card extends Searchable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2702922676920706147L;

	@Override
	protected String getTableName() {
		return "CARDS";
	}
}
