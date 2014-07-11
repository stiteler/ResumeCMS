package view;

import java.util.EventObject;
/**
 * Class to hold data on Table events emitted by GUI
 * 
 * @author Chris Stiteler
 * 
 * TODO: Need to fix the problem of decoupling the TableEvent from the candidate
 * order that's displayed, essentially need to give table event info about the candidate? 
 *
 */
public class TableEvent extends EventObject {
	//methods: 1 is view resume, leaving open for additions later
	private int method;
	private int rowSelected;
	
	/**
	 * Constructor sets source (table) and row selected for
	 * event
	 * 
	 * @param source Object source
	 * @param rowSelected int row that was selected at the time of event
	 */
	public TableEvent(Object source, int rowSelected) {
		super(source);
		this.rowSelected = rowSelected;
	}
	
	public int getRowSelected() {
		return rowSelected;
	}
}
