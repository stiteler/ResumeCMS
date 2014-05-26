package main;
import javax.swing.SwingUtilities;

import view.MainWindow;

/**
 * CMS is just a wrapper class for the App runnable
 * 
 * @author Chris Stiteler
 */
public class CMS {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new App();
			}
		});
	}
}
