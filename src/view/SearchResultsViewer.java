package view;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;

import Model.Candidate;
import controller.Controller;

public class SearchResultsViewer extends JFrame implements TableEventListener {
	private SearchResultsTablePanel searchPanel;
	private Controller controller;
	
	public SearchResultsViewer(Controller controller, Map<Candidate, Integer> results) {
		this.controller = controller;
		searchPanel = new SearchResultsTablePanel();
		searchPanel.setData(results);
		searchPanel.setTableEventListener(this);
		
		add(searchPanel);
		
		setMinimumSize(new Dimension(700, 800));
		setSize(700, 600);
		setVisible(true);
	}

	public void tableEventOccurred(TableEvent te) {
		String resume = controller.handleTableEvent(te);
		ResumeViewer rv = new ResumeViewer(resume);
	}
}
