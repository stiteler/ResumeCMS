package view.events;

import java.util.EventListener;
/**
 * Interface for table event listener framework
 * 
 * @author Chris Stiteler
 *
 */
public interface TableEventListener extends EventListener {
	public void tableEventOccurred(TableEvent te);
}
