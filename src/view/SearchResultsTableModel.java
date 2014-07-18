package view;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import Model.Candidate;

/**
 * TODO: Add functionality to sort table by score? 
 * Do this here, hmm will think on this. 
 * 
 * @author Steaz
 *
 */
public class SearchResultsTableModel extends AbstractTableModel {
	private Map<Candidate, Integer> results;
	private ArrayList<Candidate> resultSet;
	private String[] columnHeaders = { "Score", "MI", "Last", "Phone #",
			"City", "State/Prov", "Zip/Postal", "Available?" };
	// this relative score will be a percentile integer
	private double highestScore = 1;

	public SearchResultsTableModel() {

	}

	public int getColumnCount() {
		return columnHeaders.length;
	}

	public int getRowCount() {
		return results.size();
	}
	
	public String getColumnName(int col){
		return columnHeaders[col];
	}

	public Object getValueAt(int row, int col) {
		Candidate cand = resultSet.get(row);

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
	
	public void setResults(Map<Candidate, Integer> results) {
		this.results = results;
		// better way to do this? I don't like it.. hm.
		Set<Candidate> keySet = results.keySet();
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		for(Candidate c : keySet) {
			candidates.add(c);
		}
		this.resultSet = candidates;
		calculateResultsTotalScore();
		
	}
	
	private Integer scoreCandidate(int rawScore) {
		System.out.println("Raw score: " + rawScore + "Total Score: " + highestScore);
		return (int) (rawScore/highestScore * 100);
	}
	
	// TODO test this, should this be in the Search object? Or is it ok here? 
	private void calculateResultsTotalScore() {
		int hits = 0;
		for(Candidate c : resultSet) {
			if(results.get(c) > highestScore) {
				hits = results.get(c);
			}
		}
		highestScore = hits;
	}
	
	public Candidate getCandidateByRowSelected(int row) {
		return resultSet.get(row);
	}
}
