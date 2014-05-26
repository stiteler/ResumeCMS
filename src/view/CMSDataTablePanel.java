package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;

import Model.Candidate;

/**
 * GUI component for the table panel to represent
 * Candidate records
 * 
 * @author Chris Stiteler
 *
 */
public class CMSDataTablePanel extends JPanel {
	/**
	 * the table itself
	 */
	private JTable candTable;
	/**
	 * instance of the table model
	 */
	private CMSDataTableModel model;
	/**
	 * tableEventListener for event passing
	 */
	private TableEventListener tableEventListener;

	/**
	 * Constructor instantiates the components, and adds the action listeners
	 * and controls the panel view.
	 */
	public CMSDataTablePanel() {
		// instantiate
		model = new CMSDataTableModel();
		candTable = new JTable(model);

		// add action listeners:
		candTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleMouseClicked(me);
			}
		});

		// view stuff
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		setLayout(new BorderLayout());
		add(new JScrollPane(candTable), BorderLayout.CENTER);
	}
	
	/**
	 * When a user double clicks a given row, this method fires a
	 * TableEvent to the listener containing data on that row..
	 * 
	 * @param me MouseEvent to handle
	 */
	private void handleMouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) {
			JTable clicked = (JTable) me.getSource();
			int rowClicked = clicked.getSelectedRow();

			// if nothing was clicked
			if (rowClicked != -1) {
				//1 for view resume
				TableEvent te = new TableEvent(this, rowClicked);

				if (tableEventListener != null) {
					System.out.println("Table row Clicked: " + rowClicked);
					tableEventListener.tableEventOccured(te);
				}
			}

		}
	}

	/**
	 * refresh() calls on the table model to update data represented
	 */
	public void refresh() {
		model.fireTableDataChanged();
	}
	/**
	 * passes data to the model for representation
	 * 
	 * @param listOfCandidates data to set for table
	 */
	public void setData(List<Candidate> listOfCandidates) {
		model.setData(listOfCandidates);
	}
	/**
	 * Sets the TableEventListener
	 * 
	 * @param tel TableEventListener to set
	 */
	public void setTableEventListener(TableEventListener tel) {
		this.tableEventListener = tel;
	}
	/**
	 * Getter method for selected row.
	 * 
	 * @return int row selected
	 */
	public int getSelectedRow() {
		return candTable.getSelectedRow();
	}
}
