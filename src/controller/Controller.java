package controller;

import java.util.List;
import java.util.Map;

import view.CandidateEvent;
import view.TableEvent;
import Model.Available;
import Model.Candidate;
import Model.Database;

/**
 * Controller will control the communication from our GUI and the view to the
 * database model and visa versa following MVC principles.
 * 
 * @author Chris Stiteler
 * 
 */
public class Controller {
	private static final String databaseFileName = "candidates.db";
	private Database db;

	public Controller() {
		initializeData();
	}

	/**
	 * Receives candidate events from the GUI and handles the event depending on
	 * the event type.
	 * 
	 * @param ce
	 *            CandidateEvent to handle
	 */
	public void handleCandidateEvent(CandidateEvent ce) {
		switch (ce.getEventType()) {
			case 0: handleDeleteEvent(ce);
					break;
			case 1: handleSaveEvent(ce);
					break;
			case 2: handleSearchEvent(ce);
					break;
			case 3: handleParseEvent(ce);
		}

	}
	private void handleSearchEvent(CandidateEvent ce) {
		Search search = new Search(this.getCandidatesFromDatabase(), ce.getOptional());
		Map<Candidate, Integer> results = search.getResults();
		System.out.println("Name: Count");
		for(Candidate c : results.keySet()) {
			System.out.println(c.getFirstName() + ": " + results.get(c));
		}
	}

	/**
	 * handleDeleteEvent handles a delete candidate event
	 * 
	 * @param ce CandidateEvent containing data on deletion
	 */
	private void handleDeleteEvent(CandidateEvent ce) {
		requestRemoveCandidate(ce.getId());
		System.out.println("Removing candidate #: " + ce.getId());		
	}

	/**
	 * This method communicates with the data model to ask that a 
	 * candidate be deleted
	 * 
	 * @param id Candidate's ID to be deleted.
	 */
	private void requestRemoveCandidate(int id) {
		//for now id is also the row in table, would change if i added sorting
		if(db.getCandidateById(id) != null) {
			db.removeCandidate(id);
			saveData();
		}
	}
	
	/**
	 * Handles the event that a user double clicks a row in the table
	 * 
	 * @param te TableEvent to handle
	 * @return String resume of selected table row.
	 */
	public String handleTableEvent(TableEvent te) {
		//returns resume to the mainWindow to display a new JFrame with the resume? 
		int rowSelected = te.getRowSelected();
		Candidate selected = db.getCandidateById(rowSelected);
		return selected.getResume();
	}

	/**
	 * handles the event that the user wants to parse a resume in
	 * to the data model. Instantiates a parser object which parses
	 * resume, after which we grab that information and add the candidate.
	 * 
	 * @param ce CandidateEvent with data to parse in
	 */
	private void handleParseEvent(CandidateEvent ce) {
		System.out.println("Adding candidate by parse");
		Candidate newCand = new Candidate();
		ResumeParser rp = new ResumeParser(ce.getResumeFile().getAbsolutePath());
		
		newCand.setFirstName(ce.getFirstName());
		newCand.setLastName(ce.getLastName());
		newCand.setAvailability(Available.YES);
		newCand.setState(rp.getParsedState());
		newCand.setPhoneString(rp.getParsedPhone());
		newCand.setZip(rp.getParsedZip());
		newCand.setEmail(rp.getParsedEmail());
		newCand.setResume(rp.getParsedResumeString());
		
		addCandidate(newCand);
		saveData();
	}
	
	/**
	 * handles the case that a user manually enters a candidate's
	 * information on the candidate panel and wants to save that
	 * candidate to the data model
	 * 
	 * @param ce CandidateEvent with data to save
	 */
	private void handleSaveEvent(CandidateEvent ce) {
		// add candidate
		System.out.println("Adding candidate");
		Candidate newCand = new Candidate();

		newCand.setFirstName(ce.getFirstName());
		newCand.setMiddle(ce.getMiddle());
		newCand.setLastName(ce.getLastName());
		newCand.setEmail(ce.getEmail());
		newCand.setCity(ce.getCity());
		newCand.setState(ce.getState());
		newCand.setZip(ce.getZip());
		newCand.setPhoneString(ce.getPhoneString());
		newCand.setResume(ce.getResume());
		newCand.setAvailability(ce.getAvailability());

		addCandidate(newCand);
		saveData();
	}
	
	/**
	 * instantiates our database when the controller is created,
	 * and loads data into the data object.
	 */
	private void initializeData() {
		db = new Database();
		db.loadDatabaseFromFile();
	}
	
	/**
	 * gets the candidate objects in list form from the data model
	 * 
	 * @return list of candidates
	 */
	public List<Candidate> getCandidatesFromDatabase() {
		return db.getListOfCandidates();
	}
	
	/**
	 * calls on the data model to save state
	 */
	private void saveData() {
		db.saveDatabaseToFile();
	}
	
	/**
	 * calls on the data model to add a candidate
	 * 
	 * @param c Candidate to add.
	 */
	private void addCandidate(Candidate c) {
		db.addCandidate(c);
	}

}
