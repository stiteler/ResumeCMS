package view;

import java.util.EventObject;

public class SearchEvent extends EventObject {
	String query;
	
	//simple search by query
	SearchEvent(Object source, String query) {
		super(source);
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
}
