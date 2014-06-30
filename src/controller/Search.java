package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Candidate;

// Brainstorm:
// main functionality: search database for resume - Keyword? 
// score resumes? return list of top resumes? How to present? Table view?
// create a search object per query that contains the list of results? 

public class Search {
	private List<Candidate> candidates;
	private Map<Candidate, Integer> results;
	private String query;
	
	public Search(List<Candidate> candidates, String query) {
		this.query = query.toLowerCase();
		this.candidates = candidates;
		results = new HashMap<Candidate, Integer>();
		runSearch();
	}
	
	private void runSearch() {
		for(Candidate c : candidates) {
			int matches = scanForMatches(c);
			if (matches > 0) {
				results.put(c, matches);
			}
		}
	}
	
	private int scanForMatches(Candidate c) {
		// convert all to lower to remove capitalization mismatch
		String resume = c.getResume().toLowerCase();
		int count = 0;
		
		int subIndex = resume.indexOf(query);
		int resumeIndex = subIndex;
		while (subIndex != -1) {
			count++;
			resumeIndex += subIndex;
			resume = resume.substring(subIndex+1);
			subIndex = resume.indexOf(query);
		}
		return count;
	}
	
	public Map<Candidate, Integer> getResults() {
		return this.results;
	}
}
