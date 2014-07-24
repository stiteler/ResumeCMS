package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
/**
 * CMSMenuBar generates a menu bar for the main window.
 * 
 * @author Chris Stiteler
 *
 */
public class CMSMenuBar {
	/**
	 * CandidateEventListener to add to the menubar.
	 */
	private static CandidateEventListener candEventListener;
	
	/**
	 * This method genetares a menu bar with menus, and menu items in those
	 * menus.  It adds the corresponding action listeners, and sets up the 
	 * mnemonic controls as well before return the menubar object.
	 * 
	 * @param cel CandidateEventListener for the menubar.
	 * @return JMenuBar we generated
	 */
	public static JMenuBar generateMenuBar(CandidateEventListener cel) {
		//set listener:
		candEventListener = cel;
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		//JMenu searchMenu = new JMenu("Search");
		
		//add all the menus to the menu bar:
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		//menuBar.add(searchMenu);

		// File menu items
		//JMenuItem emailResumeItem = new JMenuItem("Email Resume...");
		//JMenuItem importResumeItem = new JMenuItem("Import Resume..");
		JMenuItem exitMenuItem = new JMenuItem("Exit");

		// add File Menu items to file menu
		//fileMenu.add(emailResumeItem);
		//fileMenu.add(importResumeItem);
		fileMenu.add(exitMenuItem);

		// Edit menu items
		JMenuItem deleteResumeItem = new JMenuItem("Delete...");

		// add Edit Menu items to edit menu
		editMenu.add(deleteResumeItem);

		// Search menu Item
		JTextField searchBox = new JTextField(25);

		// add Search Menu Item to search menu
		//searchMenu.add(searchBox);

		// set up mnemonics:
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu.setMnemonic(KeyEvent.VK_E);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));

		// set up action listeners:
		deleteResumeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CandidateEvent ce = new CandidateEvent(this, CandidateEventType.DELETE);
				candEventListener.candidateEventOccurred(ce);
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to exit?", "Are you sure?",
						JOptionPane.OK_CANCEL_OPTION);
				if(choice == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
		
		//to be implemented in further versions:
		/*
		importResumeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Import resume menu item clicked");
				
			}
		});
		*/
		/*
		emailResumeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Email Menu Item clicked");
			}
		});
		*/
		
		/*
		searchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField searchBox = (JTextField) e.getSource();
				System.out.println("Search: " + searchBox.getText());
			}
		});
		*/
		return menuBar;
	}
}
