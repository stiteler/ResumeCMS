package Model;

import java.io.Serializable;

/**
 * This serializable class represents the primary unit of storage
 * for our data model, and contains data on each candidate in our program.
 * Requires serializable in order to save a list of candidates in our
 * data model.
 * 
 * @author Chris Stiteler
 *
 */
public class Candidate implements Serializable {
	private static final long serialVersionUID = 1337L;

	// allow us to assign unique IDs to each candidate.
	private static int candidateIDGenerator = 0;

	private int candidateID;
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

	/**
	 * Constructor gives each candidate a unique ID number;
	 */
	public Candidate() {
		candidateID = candidateIDGenerator;
		candidateIDGenerator++;
	}

	/**
	 * Setters and getters: 
	 */
	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneString() {
		return phoneString;
	}

	public void setPhoneString(String phoneString) {
		this.phoneString = phoneString;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public Available getAvailability() {
		return availability;
	}

	public void setAvailability(Available availability) {
		this.availability = availability;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
