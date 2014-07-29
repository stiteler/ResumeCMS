package view;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;

import view.events.TableEvent;
import view.events.TableEventListener;
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
		// so if the TableEvent knows about the candidate, it has it's resume. But do I like this? Not sure
		// What's a best practice here? 
		//String resume = controller.handleTableEvent(te);
		String resume = te.getCandidateSelected().getResume();
		ResumeViewer rv = new ResumeViewer(resume);
	}
}
