package view.events;

import java.util.EventObject;

import Model.Candidate;
/**
 * Class to hold data on Table events emitted by GUI
 * 
 * @author Chris Stiteler
 *
 */
public class TableEvent extends EventObject {
	//methods: 1 is view resume, leaving open for additions later
	private int method;
	private int rowSelected;
	
	// Trying: CandidateID? 
	private Candidate candidateSelected;
	
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
	
	/**
	 * TableEvent constructor for when the even needs to know about the candidate
	 * when the table model isn't linked directly to database model.. which shouldn't be coupled at all.
	 * 
	 * @param source Source table
	 * @param candidateSelected Candidate that was selected in the table row
	 */
	public TableEvent(Object source, Candidate candidateSelected) {
		super(source);
		this.candidateSelected = candidateSelected;
	}
	
	public int getRowSelected() {
		return rowSelected;
	}
	
	public Candidate getCandidateSelected() {
		return this.candidateSelected;
	}
}
