package main;

import controller.Controller;
import view.MainWindow;
/**
 * instantiates a controller, and passes it to a new MainWindow to start the CMS
 * program
 * 
 * @author Chris Stiteler
 *
 */
public class App {
	private static Controller controller = new Controller();
	
	public App() {
		MainWindow mainWindow = new MainWindow(controller);
	}
}
