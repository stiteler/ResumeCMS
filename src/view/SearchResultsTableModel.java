package view;

import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import Model.Candidate;

public class SearchResultsTableModel extends AbstractTableModel {
	private Map<Candidate, Integer> results;
	private Candidate[] resultSet;
	private String[] columnHeaders = { "Score", "MI", "Last", "Phone #",
			"City", "State/Prov", "Zip/Postal", "Available?" };
	// this relative score will be a percentile integer
	private int totalScore;

	public SearchResultsTableModel() {
		
	}

	public int getColumnCount() {
		return columnHeaders.length;
	}

	public int getRowCount() {
		return results.size();
	}

	public Object getValueAt(int row, int col) {
		Candidate cand = resultSet[row];

		switch (col) {
		case 0:
			//first column is going to be scores.
			return scoreCandidate(results.get(cand));
		case 1:
			return cand.getFirstName();
		case 2:
			return cand.getMiddle();
		case 3:
			return cand.getLastName();
		case 4:
			return cand.getPhoneString();
		case 5:
			return cand.getCity();
		case 6:
			return cand.getState();
		case 7:
			return cand.getZip();
		case 8:
			return cand.getAvailability();
		default:
			return " ";
		}
	}
	
	private Object scoreCandidate(int rawScore) {
		return rawScore/totalScore;
		
	}

	public void setResults(Map<Candidate, Integer> results) {
		this.results = results;
		// better way to do this? I don't like it.. hm.
		this.resultSet = (Candidate[]) results.keySet().toArray();
	}
	
	// TODO test this
	public void calculateResultsTotalScore() {
		int hits = 0;
		for(Candidate c : resultSet) {
			hits += results.get(c);
		}
		totalScore =  hits/results.size();
	}
}
