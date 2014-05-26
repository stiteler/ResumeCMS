package view;

import java.util.EventListener;
/**
 * Interface for candidate event listener framework
 * 
 * @author Chris Stiteler
 *
 */
public interface CandidateEventListener extends EventListener {
	public void candidateEventOccurred(CandidateEvent ce);
}
