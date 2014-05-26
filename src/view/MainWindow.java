package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import Model.Database;
import controller.Controller;

/**
 * This class is the master of our GUI, it contains all the main components of
 * the program, and is the primary medium of communication to the controller.
 * 
 * @author Chris Stiteler
 * 
 */
public class MainWindow extends JFrame implements CandidateEventListener,
		TableEventListener {
	private static Controller controller;

	///// Declare components /////
	/**
	 * toolbar component
	 */
	private CMSToolbar toolbar;
	/**
	 * candidate panel component
	 */
	private CandidatePanel candidatePanel;
	/**
	 * table panel component
	 */
	private CMSDataTablePanel tablePanel;
	/**
	 * Resume viewer component
	 */
	private ResumeViewer resumeViewer;

	/**
	 * Instantiates components, sets them up, adds them to the frame, and
	 * controls display settings
	 * 
	 * @param controller main controller for the application.
	 */
	public MainWindow(Controller controller) {
		super("ResumeCMS");

		// set controller
		this.controller = controller;

		// /// Instantiate Components /////
		toolbar = new CMSToolbar();
		JMenuBar menuBar = CMSMenuBar.generateMenuBar(this);
		candidatePanel = new CandidatePanel();
		tablePanel = new CMSDataTablePanel();

		// /// set up components /////
		setLayout(new BorderLayout());
		tablePanel.setData(controller.getCandidatesFromDatabase());
		candidatePanel.setCandidateEventListener(this);
		toolbar.setCandidateEventListener(this);
		tablePanel.setTableEventListener(this);

		// /// Add Components /////
		add(toolbar, BorderLayout.NORTH);
		setJMenuBar(menuBar);
		add(candidatePanel, BorderLayout.WEST);
		add(tablePanel, BorderLayout.CENTER);

		setMinimumSize(new Dimension(1280, 720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(700, 600);
		setVisible(true);
	}

	/**
	 * Method when invoked opens a Resume Viewer
	 * @param resume String to be displayed therein
	 */
	private void buildResumeWindow(String resume) {
		resumeViewer = new ResumeViewer(resume);
	}

	// event handlers:
	/**
	 * handles candidate events emitted by child components.  Control flow
	 * is to diagnose event type and ensure that the proper requirements are in place
	 * prior to sending to controller.  (ie. can't delete without selection)
	 */
	public void candidateEventOccurred(CandidateEvent ce) {
		if (ce.getEventType() == 0 && tablePanel.getSelectedRow() == -1) {
			System.out.println("Is a delete, but no row selected");
			return;
		} else if (ce.getEventType() == 0) { // else if a delete and valid row..
			int selection = JOptionPane
					.showConfirmDialog(this, "Are you sure?", "Delete",
							JOptionPane.YES_NO_CANCEL_OPTION);
			if (selection != JOptionPane.YES_OPTION) {
				return;
			}
		}

		ce.setId(tablePanel.getSelectedRow());
		controller.handleCandidateEvent(ce);
		tablePanel.refresh();
	}

	/**
	 * handles table events. Until prior functionality, the only
	 * table events are to view resumes.
	 */
	public void tableEventOccured(TableEvent te) {
		String resume = controller.handleTableEvent(te);
		// now make a new JFrame with this String:
		buildResumeWindow(resume);
	}

}
