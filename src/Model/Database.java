package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class contains our database. It should contain methods for adding and
 * removing candidates, loading from file, and saving to. The data structure
 * will be a linked list of candidate objects.
 * 
 * @author Chris Stiteler
 * 
 */
public class Database {
	/**
	 * Our data structure holding candidates
	 */
	private List<Candidate> candidates;
	/**
	 * file name for the database
	 */
	private String fileName = "src/candidates.db";
	/**
	 * file for the database
	 */
	private File file;

	/**
	 * new database creates a new data structure, and instantiates
	 * the file.
	 */
	public Database() {
		candidates = new LinkedList<Candidate>();
		file = new File(fileName);
	}
	/**
	 * adds a candidate to our candidate list.
	 * 
	 * @param cand Candidate to add
	 */
	public void addCandidate(Candidate cand) {
		candidates.add(cand);
	}
	/**
	 * remvoes a candidate to our candidate list.
	 * 
	 * @param cand Candidate to remove
	 */
	public void removeCandidate(int index) {
		candidates.remove(index);
	}
	/**
	 * retrieves Candidate object from the list
	 * 
	 * @param id int ID of candidate to retrieve (is index in list)
	 * @return Candidate requested.
	 */
	public Candidate getCandidateById(int id) {
		return candidates.get(id);
	}
	/**
	 * returns a non-editable list of candidates for 
	 * display purposed only. (the table).
	 * 
	 * @return List<Candidate> 
	 */
	public List<Candidate> getListOfCandidates() {
		return Collections.unmodifiableList(candidates);
	}
	/**
	 * Converts data structure to an array of type Candidate,
	 * which is then serialized to disk.
	 */
	public void saveDatabaseToFile() {
		// we're going to put the candidate list into array for saving
		Candidate[] candidateArrayToSave = candidates
				.toArray(new Candidate[candidates.size()]);

		try {
			// create file output and object output streams
			FileOutputStream fileOutStream = new FileOutputStream(file);
			ObjectOutputStream objOutStream = new ObjectOutputStream(
					fileOutStream);

			// write the array to file, then close the output stream
			objOutStream.writeObject(candidateArrayToSave);
			
			objOutStream.close();
		} catch (IOException e) {
			System.out.println("Error writing database to file");
			e.printStackTrace();
		}
	}
	/**
	 * Loads Candidate[] object from file, and reinstantiates the
	 * array as an arraylist of candidates, then sets that to the current 
	 * data model.  
	 */
	public void loadDatabaseFromFile() {
		// pretty much the opposite from the saving, except input streams:
		FileInputStream fileInStream;
		ObjectInputStream objInStream;

		try {
			// instantiate the streams
			fileInStream = new FileInputStream(file);
			objInStream = new ObjectInputStream(fileInStream);

			// now since we saved to file as an array, we need to create an
			// array for load
			Candidate[] candidatesFromFile = (Candidate[]) objInStream
					.readObject();

			// so we need to first make sure our current model is clear
			this.candidates.clear();

			// then add the candidates from file to the local candidates list:
			this.candidates.addAll(Arrays.asList(candidatesFromFile));
			
			objInStream.close();

		} catch (FileNotFoundException e) {
			System.out.println("Could not find database file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error loading database from file"); 
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Could not load the data objects from file");
			e.printStackTrace();
		}
	}
}
