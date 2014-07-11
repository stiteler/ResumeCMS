package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Model.Candidate;

//TODO Maybe create a super TablePanel class if it will be implemented 
// multiple times in the project, so far twice, most of the code is cookie cutter
public class SearchResultsTablePanel extends JPanel {
	private JTable resultsTable;
	private SearchResultsTableModel model;
	private TableEventListener tableEventListener;
	
	public SearchResultsTablePanel() {
		model = new SearchResultsTableModel();
		resultsTable = new JTable(model);
		
		resultsTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleMouseClicked(me);
			}
		});
		
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		setLayout(new BorderLayout());
		add(new JScrollPane(resultsTable), BorderLayout.CENTER);
	}
	
	private void handleMouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) {
			JTable clicked = (JTable) me.getSource();
			int rowClicked = clicked.getSelectedRow();

			// if nothing was clicked
			if (rowClicked != -1) {
				//TODO: This table event is not being executed correctly by the controller
				// it's not returning the right candidate for the resume viewer if, for instance,
				// the table is not in order of the database.. need to decouple this ASAP.
				TableEvent te = new TableEvent(this, rowClicked);

				if (tableEventListener != null) {
					System.out.println("Search Table row Clicked: " + rowClicked);
					tableEventListener.tableEventOccurred(te);
				}
			}
		}
	}
	
	public void refresh() {
		model.fireTableDataChanged();
	}
	
	public void setData(Map<Candidate, Integer> results) {
		model.setResults(results);
	}
	
	public void setTableEventListener(TableEventListener tel) {
		this.tableEventListener = tel;
	}
	
	public int getSelectedRow() {
		return resultsTable.getSelectedRow();
	}
}
