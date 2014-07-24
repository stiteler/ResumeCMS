package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Candidate;
/**
 * This class provides a model for the table GUI component
 * It retrieves the data for the table and controls functionality
 * 
 * @author Chris Stiteler
 *
 */
public class CMSDataTableModel extends AbstractTableModel {
	// data for the table:
	//need to really narrow down what I want to see here based on space later
	//probably don't need zip codes... 
	private List<Candidate> database;
	private String[] columnHeaders = {"First", "MI", "Last",
			"Phone #", "City", "State/Prov", "Zip/Postal", "Available?" };

	// constructor
	public CMSDataTableModel() {
		
	}

	/**
	 * must be defined to tell GUI how many columns
	 */
	public int getColumnCount() {
		return columnHeaders.length;
	}
	/**
	 * must be defined to tell GUI names of each column
	 */
	public String getColumnName(int col) {
		return columnHeaders[col];
	}
	/**
	 * must be defined to tell GUI number of rows
	 */
	public int getRowCount() {
		return database.size();
	}

	/**
	 * must be defined to tell GUI what data
	 * to put in each cell.
	 */
	public Object getValueAt(int row, int col) {
		// have to implement fetch from database
		// Candidate for each row
		Candidate cand = database.get(row);

		// do a switch here?
		switch (col) {
		case 0:
			return cand.getFirstName();
		case 1:
			return cand.getMiddle();
		case 2:
			return cand.getLastName();
		case 3:
			return cand.getPhoneString();
		case 4:
			return cand.getCity();
		case 5:
			return cand.getState();
		case 6:
			return cand.getZip();
		case 7:
			return cand.getAvailability();
		default:
			return " ";
		}
	}

	/**
	 * sets the table's data.
	 */
	public void setData(List<Candidate> database) {
		this.database = database;
	}

}
