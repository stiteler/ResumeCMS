package controller;

import java.util.Map;

import Model.Candidate;

public class Search_Test {
	public static void main(String[] args) {
		Controller testController = new Controller();
		String query1 = "Harpoon";
		Search search = new Search(testController.getCandidatesFromDatabase(), query1);
		Map<Candidate, Integer> results = search.getResults();
		
		System.out.println("Name: Count");
		for(Candidate c : results.keySet()) {
			System.out.println(c.getFirstName() + ": " + results.get(c));
		}
	}
}
