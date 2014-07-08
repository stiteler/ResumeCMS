package view;

import java.util.EventListener;

/**
 * Interface for search event listener framework
 * 
 * @author Chris Stiteler
 *
 */
public interface SearchEventListener extends EventListener {
	public void searchEventOccurred(SearchEvent se);
}
