package view;

import java.io.File;
import java.util.EventObject;

import Model.Available;
/**
 * Class is used to pass event information from the GUI to the controller
 * 
 * @author Chris Stiteler
 *
 */
public class CandidateEvent extends EventObject {
	// 1 for save, 0 for delete, 3 for parse? for now, 2 for search
	private int eventType;

	private int id;
	private String resume;
	private String firstName;
	private String middle;
	private String lastName;
	private String city;
	private String state;
	private String zip;
	private String email;
	private String phoneString;
	private Available availability;
	private File resumeFile;
	/**
	 * Simple constructor when on info is needed (deletion event)
	 * 
	 * @param source Object where event emitted
	 * @param eventType int type of event emitted
	 */
	public CandidateEvent(Object source, int eventType) {
		super(source);
		this.eventType = eventType;
	}
	
	/**
	 * Constructor with basic information (called when user wants to parse resume file).
	 * 
	 * @param arg0 Object where event was emitted
	 * @param eventType int type of event
	 * @param resumeFile File containing selected resume file
	 * @param firstName String first name of candidate
	 * @param lastName String last name of candidate
	 */
	public CandidateEvent(Object arg0, int eventType, File resumeFile, String firstName, String lastName) {
		super(arg0);
		this.eventType = eventType;
		this.resumeFile = resumeFile;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	/**
	 * Fully featured constructor for manual user candidate addition
	 * 
	 * @param arg0 Object source of event
	 * @param eventType int type of event
	 * @param resume string resume string
	 * @param firstName string first name
	 * @param middle string middle initial
	 * @param lastName String last name
	 * @param city String city of candidate
	 * @param state String state
	 * @param zip String zip 
	 * @param email String email
	 * @param phoneString String phone number
	 * @param availability Available availability of candidate (YES, NO, OR SOON)
	 * @param optional Usage depends on circumstance
	 */
	public CandidateEvent(Object arg0, int eventType, String resume,
			String firstName, String middle, String lastName, String city,
			String state, String zip, String email, String phoneString,
			Available availability) {
		super(arg0);

		this.eventType = eventType;
		this.resume = resume;
		this.firstName = firstName;
		this.middle = middle;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.email = email;
		this.phoneString = phoneString;
		this.availability = availability;
	}

	/**
	 * setters/getters
	 */
	public int getEventType() {
		return eventType;
	}

	public String getResume() {
		return resume;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddle() {
		return middle;
	}

	public String getLastName() {
		return lastName;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneString() {
		return phoneString;
	}

	public Available getAvailability() {
		return availability;
	}
	
	public File getResumeFile() {
		return this.resumeFile;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
