package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * JPanel acting as toolbar for our application. As of now, contains
 * two important buttons, parse new candidate, and delete selected candidate
 * 
 * @author Chris Stiteler
 *
 */
public class CMSToolbar extends JPanel {

	private JButton addCandButton;
	private JButton delCandButton;
	private JLabel searchLabel;
	private JTextField searchBox;
	private JFileChooser fileChooser;
	private CandidatePanel candPanelForParse;

	private CandidateEventListener candEventListener;
	private SearchEventListener searchEventListener;

	/**
	 * Constructor instantiates components, sets the toolbar layout, &
	 * adds the action listeners
	 */
	public CMSToolbar() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		setBackground(new Color(195, 212, 230));

		addCandButton = new JButton("Parse New Candidate");
		delCandButton = new JButton("Delete Selected");
		searchLabel = new JLabel("Search: ");
		searchBox = new JTextField(20);
		fileChooser = new JFileChooser();

		// setup components:
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
				"text only", "txt"));

		// action listeners:
		addCandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleAddCandidateClicked();
			}
		});
		delCandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CandidateEvent ce = new CandidateEvent(this, CandidateEventType.DELETE);
				if(candEventListener != null) {
					candEventListener.candidateEventOccurred(ce);
				}
			}
		});
		
		searchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Searching for: " + searchBox.getText());
				SearchEvent se = new SearchEvent(this, searchBox.getText());
				if(searchEventListener != null) {
					searchEventListener.searchEventOccurred(se);
				}
			}
		});
		

		add(addCandButton);
		add(delCandButton);
		add(searchLabel);
		add(searchBox);
	}

	/**
	 * method called when parse new button clicked.  Prompts the user to 
	 * provide the first and last name of the candidate, after which the user
	 * is prompted for a text file containing that person's resume. This is then
	 * packaged into a candidate event and emitted to the listener.
	 */
	private void handleAddCandidateClicked() {
		// only doing first/last name for simplicity here.
		String firstName = null;
		String lastName = null;
		boolean cancelling = false;
		int checks = 0;

		while (true) {
			if (cancelling == true) {
				return;
			}
			firstName = JOptionPane.showInputDialog(null,
					"What is Candidate's First Name?");
			if (firstName == null) {
				cancelling = true;
			}
			if ((firstName != null) && firstName.length() < 1) {
				JOptionPane.showMessageDialog(null,
						"First Name is a Required Field");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			if (cancelling == true) {
				return;
			}
			lastName = JOptionPane.showInputDialog(null,
					"What is Candidate's Last Name?");
			if (lastName == null) {
				cancelling = true;
			}
			if (lastName != null && lastName.length() < 1) {
				JOptionPane.showMessageDialog(null,
						"Last Name is a Required Field");
				continue;
			} else {
				break;
			}
		}

		// prompt user for file chooser
		if(cancelling != true) {
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				System.out.println("File selected: "
						+ fileChooser.getSelectedFile().getAbsolutePath()
								.toString());
				File selectedResumeFile = fileChooser.getSelectedFile();

				CandidateEvent ce = new CandidateEvent(this, CandidateEventType.PARSE, selectedResumeFile,
						firstName, lastName);

				if (candEventListener != null) {
					System.out.println("candidate event emitted from toolbar: add candidate");
					candEventListener.candidateEventOccurred(ce);
				}
			}
		}
	}
	/**
	 * Set the candidate event listener.
	 * 
	 * @param cel CandidateEventListener to set
	 */
	public void setCandidateEventListener(CandidateEventListener cel) {
		candEventListener = cel;
	}
	/**
	 * Set the search event listener.
	 * 
	 * @param sel SearchEventListener to set
	 */
	public void setSearchEventListener(SearchEventListener sel) {
		searchEventListener = sel;
	}
}
