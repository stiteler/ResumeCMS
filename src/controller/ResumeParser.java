package controller;

import java.io.*;
import java.util.*;

/**
 * ResumeParser is a class that is designed to parse out
 * the string representation of a resume, the zip code, email,
 * state, and phone number of a candidate
 * 
 * @author Chris Stiteler
 *
 */
public class ResumeParser {
	/**
	 * This object contains each string in a given resume
	 */
	private List<String> resumeStringList;

	private String parsedResumeString;
	private String parsedZip;
	private String parsedEmail;
	private String parsedState;
	private String parsedPhone;

	/**
	 * Class constructor receives a filename, and then calls the methods to 
	 * parse the data
	 * 
	 * @param fileName String filename to be parsed. (txt only)
	 */
	public ResumeParser(String fileName) {
		File resumeFile = new File(fileName);
		this.resumeStringList = getResumeStringList(resumeFile);

		// do all the parsing in here?
		parseForZip();
		parseForState();
		parseForPhone();
		parseForEmail();
		parsedResumeString = parseForResumeString(resumeFile);
	}

	/**
	 * Searches the resumeStringList for strings between 9-16 in length,
	 * counts the number of digits, and guesses that it's a phone number if
	 * count is 10
	 * 
	 * @return boolean representative of successful parse for phone
	 */
	public boolean parseForPhone() {
		// iterate over string list
		for (String s : resumeStringList) {
			// if possible to be a phone number
			if (s.length() > 9 && s.length() < 16) {
				int digitCount = 0;
				char[] possiblePhone = s.toCharArray();
				for (int i = 0; i < possiblePhone.length; i++) {
					if (Character.isDigit(possiblePhone[i])) {
						digitCount++;
					}
				}
				if (digitCount == 10) {
					parsedPhone = s;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Searches the resumeStringList for strings that could possibly
	 * be a zip code (5 digits no more, no less), and then cleans that
	 * string a tad.
	 * 
	 * @return boolean representative of successful parse
	 */
	public boolean parseForZip() {
		for (String s : resumeStringList) {
			int digitCount = 0;
			int firstInstanceOfDigit = 0;
			boolean checkingForFirstInstance = true;
			char[] possibleZip = s.toCharArray();
			for (int i = 0; i < possibleZip.length; i++) {
				if (Character.isDigit(possibleZip[i])) {
					digitCount++;
					if (checkingForFirstInstance) {
						firstInstanceOfDigit = i;
						checkingForFirstInstance = false;
					}
				}
			}
			if (digitCount == 5) {
				parsedZip = s.substring(firstInstanceOfDigit,
						firstInstanceOfDigit + 5);
				return true;
			}
		}
		return false;
	}

	/**
	 * Searches the resumeStringList for strings that could possibly
	 * be an email, (containing "@"), then sets the data to cleaned version
	 * 
	 * @return boolean representative of successful parse
	 */
	public boolean parseForEmail() {
		for (String s : resumeStringList) {
			if (s.length() > 6 && s.contains("@")) {
				String probableEmail = s;
				// need to clean the email a bit for extra string afterwards
				parsedEmail = cleanEmail(s);
				return true;
			}
		}
		return false;
	}
	/**
	 * helper method for email parsing that grabs only the email string, no 
	 * whitespace or following characters (new lines, punctuation, etc).
	 * 
	 * @param toClean String email to clean
	 * @return String cleaned email string
	 */
	private String cleanEmail(String toClean) {
		// get first instance of . after @, return the substring + 3 (for "com"
		// "net" etc, wouldn't work for .io though..)
		int index = toClean.indexOf(".");
		String cleaned = toClean.substring(0, index + 4);

		return cleaned;

	}

	/**
	 * Searches the resumeStringList for strings that could possibly
	 * be the candidate's state.  Relies on the fact that most resumes
	 * will have an address before any other regional mention. Even if a secondary
	 * regional mention is brought up, it's likely also where the candidate lives. 
	 * 
	 * This function references two data structures, containing state acronyms and names
	 * 
	 * @return boolean representative of successful parse
	 */
	public boolean parseForState() {
		String[] states = getStateNames();
		String[] stateAcronyms = getStateAcronyms();

		for (String s : resumeStringList) {
			if (s.length() < 4) {
				// check state acronym
				for (int i = 0; i < stateAcronyms.length; i++) {
					if (s.contains(stateAcronyms[i])) {
						parsedState = states[i];
						return true;
					}
				}
			} else {
				// check state name full:
				for (int i = 0; i < stateAcronyms.length; i++) {
					if (s.contains(states[i])) {
						parsedState = states[i];
						return true;
					}
				}

			}
		}
		return false;
	}

	/**
	 * Receives a resumeFile and iterates through the file with a scanner.
	 * A " " delimter is used to grab each string in the text file. Each string
	 * is then added to the resumeStringList
	 * 
	 * @param resumeFile file to parse for string list.
	 * @return List<String> for this resume.
	 */
	private List<String> getResumeStringList(File resumeFile) {
		String delimiter = " ";
		Scanner scanner;
		List<String> listOfWords = new LinkedList<String>();

		try {
			scanner = new Scanner(resumeFile);
			scanner.useDelimiter(delimiter);

			while (scanner.hasNext()) {
				String next = scanner.next();
				if (next.length() > 1) {
					listOfWords.add(next);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception in Parser");
		}
		return listOfWords;
	}

	/**
	 * This function converts the resume to a String for storage.
	 * 
	 * @param resumeFile File to parse into 1 whole string
	 * @return String representation of resume
	 */
	private String parseForResumeString(File resumeFile) {
		BufferedReader br;
		String nextLine = null;
		StringBuilder resume = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(resumeFile));
			while ((nextLine = br.readLine()) != null) {
				resume.append(nextLine);
				resume.append("\n");
			}

		} catch (FileNotFoundException fnf) {
			System.out.println("File not found exception in Parser");
		} catch (IOException ioe) {
			System.out.println("I/O exception in Parser");
		}

		return resume.toString();
	}

	/**
	 * helper that gets an array of strings representing state names
	 * 
	 * @return String[] of state names
	 */
	private String[] getStateNames() {
		String[] stateNames = { "Alabama", "Alaska", "Arizona", "Arkansas",
				"California", "Colorado", "Connecticut", "Delaware",
				"District of Columbia", "Florida", "Georgia", "Hawaii",
				"Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
				"Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
				"Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
				"Nevada", "New Hampshire", "New Jersey", "New Mexico",
				"New York", "North Carolina", "North Dakota", "Ohio",
				"Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
				"South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
				"Vermont", "Virginia", "Washington", "West Virginia",
				"Wisconsin", "Wyoming", };
		return stateNames;
	}

	/**
	 * helper that gets an array of strings representing state acronyms
	 * 
	 * @return String[] of state acronyms
	 */
	private String[] getStateAcronyms() {
		// sorted by order of full name alphabeticality, yes that's a word
		String[] stateAcronyms = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT",
				"DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
				"KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
				"NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK",
				"OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA",
				"WA", "WV", "WI", "WY" };
		return stateAcronyms;
	}

	// setters/getters
	public String getParsedZip() {
		return parsedZip;
	}

	public String getParsedEmail() {
		return parsedEmail;
	}

	public String getParsedState() {
		return parsedState;
	}

	public String getParsedPhone() {
		return parsedPhone;
	}

	public String getParsedResumeString() {
		return parsedResumeString;
	}
}
