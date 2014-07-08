package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Model.Available;
/**
 * Panel for manual entry of candidate information. It implements the 
 * grid bag layout, and is being listened to by the main window for events
 * 
 * @author Chris Stiteler
 *
 */
public class CandidatePanel extends JPanel {
	// labels:
	private JLabel nameLabel;
	private JLabel emailLabel;
	private JLabel cityLabel;
	private JLabel stateLabel;
	private JLabel zipLabel;
	private JLabel phoneLabel;
	private JLabel availabilityLabel;
	private JLabel resumeLabel;

	// components
	private JTextField firstNameField;
	private JTextField middleInitField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JTextField cityField;
	private JTextField stateField;
	private JTextField zipField;
	private JTextField phoneField;
	private JRadioButton availableRadio;
	private JRadioButton unavailableRadio;
	private JRadioButton soonRadio;
	private ButtonGroup availabilityButtonGroup;
	private JTextArea resumeField;
	private JScrollPane resumeScrollPane;
	private JButton saveButton;

	// listeners
	private CandidateEventListener candEventListener;

	// formatting objects
	private Insets leftInsets;
	private Insets noInsets;

	/**
	 * Constructor instantiates components, sets them up, adds action listeners
	 * and lays out the components
	 */
	public CandidatePanel() {
		// setup
		Border inner = BorderFactory.createTitledBorder("Manually Add New Candidate: ");
		Border outer = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border subBorder = BorderFactory.createCompoundBorder(outer, inner);

		Border mainBorder = BorderFactory.createCompoundBorder(subBorder,
				BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBorder(mainBorder);

		// instantiate labels
		nameLabel = new JLabel("Name:*");
		emailLabel = new JLabel("Email: ");
		cityLabel = new JLabel("City: ");
		stateLabel = new JLabel("State/Province: ");
		zipLabel = new JLabel("Zip/Postal Code: ");
		phoneLabel = new JLabel("Phone Number: ");
		availabilityLabel = new JLabel("Available?: ");
		resumeLabel = new JLabel("Resume: ");

		// instantiate components
		firstNameField = new JTextField(9);
		middleInitField = new JTextField(1);
		lastNameField = new JTextField(9);
		emailField = new JTextField(20);
		cityField = new JTextField(20);
		stateField = new JTextField(20);
		zipField = new JTextField(9);
		phoneField = new JTextField(20);
		availableRadio = new JRadioButton("Yes");
		unavailableRadio = new JRadioButton("No");
		soonRadio = new JRadioButton("Soon");
		resumeField = new JTextArea(10, 20);
		saveButton = new JButton("Save");

		// set up components
		availableRadio.setActionCommand("available");
		unavailableRadio.setActionCommand("unavailable");
		soonRadio.setActionCommand("soon");
		availabilityButtonGroup = new ButtonGroup();
		availabilityButtonGroup.add(availableRadio);
		availabilityButtonGroup.add(unavailableRadio);
		availabilityButtonGroup.add(soonRadio);
		leftInsets = new Insets(0, 0, 0, 3);
		noInsets = new Insets(0, 0, 0, 0);
		resumeField.setLineWrap(true);
		resumeScrollPane = new JScrollPane(resumeField);
		// default yes to availability
		availableRadio.setSelected(true);

		// add ActionListeners
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				handleSaveButtonClicked();
			}
		});

		// layout components
		layoutComponents();
	}
	
	/**
	 * Packages information from form when save button is clicked
	 * and emits a new candidate event to the listener. then clears
	 * the form
	 */
	private void handleSaveButtonClicked() {
		// if required fields completed...
		if (requiredFieldsComplete()) {
			// build the event:
			String resume = resumeField.getText();
			String firstName = firstNameField.getText();
			String middle = middleInitField.getText();
			String lastName = lastNameField.getText();
			String city = cityField.getText();
			String state = stateField.getText();
			String zip = zipField.getText();
			String email = emailField.getText();
			String phoneString = phoneField.getText();
			Available availability = null;

			// set availability:
			if (availabilityButtonGroup.getSelection()
					.getActionCommand() == "available") {
				availability = Available.YES;
			} else if (availabilityButtonGroup.getSelection()
					.getActionCommand() == "unavailable") {
				availability = Available.NO;
			} else if (availabilityButtonGroup.getSelection()
					.getActionCommand() == "soon") {
				availability = Available.SOON;
			}
			

			CandidateEvent ce = new CandidateEvent(this, 1, resume,
					firstName, middle, lastName, city, state, zip,
					email, phoneString, availability);

			if (candEventListener != null) {
				System.out.println("candidate event emitted from save button");
				candEventListener.candidateEventOccurred(ce);
			}
		} else {
			JOptionPane.showMessageDialog(saveButton, "Missing required Fields");
		}

		// then clear the fields.
		clearForm();
	}
	
	/**
	 * Clears the form panel of data
	 */
	private void clearForm() {
		firstNameField.setText("");
		middleInitField.setText("");
		lastNameField.setText("");
		emailField.setText("");
		cityField.setText("");
		stateField.setText("");
		zipField.setText("");
		phoneField.setText("");
		resumeField.setText("");
	}

	/**
	 * helper method checks if the user has input a first and last name,
	 * this is easily extensible to include further requirements.
	 * 
	 * @return boolean if fields complete or not.
	 */
	private boolean requiredFieldsComplete() {
		// for now, just the name, required, but can be extended
		if (firstNameField.getText().length() > 0) {
			if (lastNameField.getText().length() > 0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Sets the candidate event listener
	 * 
	 * @param cel CandidateEventListener to set
	 */
	public void setCandidateEventListener(CandidateEventListener cel) {
		candEventListener = cel;
	}

	/**
	 * This method isntantiates our layout manager, controls the gridbagconstraints
	 * and then adds the components to the panel.
	 */
	private void layoutComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		// Row 1
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.insets = leftInsets;
		gc.gridx = 0;
		gc.gridy = 0;
		add(nameLabel, gc);

		gc.gridx = 1;
		gc.insets = noInsets;
		gc.anchor = GridBagConstraints.LINE_START;
		add(firstNameField, gc);
		gc.gridx++;
		add(middleInitField, gc);
		gc.gridx++;
		add(lastNameField, gc);

		// Row 2
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.insets = leftInsets;
		add(emailLabel, gc);

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = noInsets;
		gc.gridwidth = 3;
		add(emailField, gc);

		// Row 3
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.insets = leftInsets;
		gc.gridwidth = 1;
		add(cityLabel, gc);
		gc.gridwidth = 3;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.insets = noInsets;
		add(cityField, gc);

		// Row 4
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.insets = leftInsets;
		gc.gridwidth = 1;
		add(stateLabel, gc);
		gc.gridwidth = 3;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.insets = noInsets;
		add(stateField, gc);

		// Row 5
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.insets = leftInsets;
		gc.gridwidth = 1;
		add(zipLabel, gc);
		gc.gridwidth = 3;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.insets = noInsets;
		add(zipField, gc);

		// Row 6
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.insets = leftInsets;
		gc.gridwidth = 1;
		add(phoneLabel, gc);
		gc.gridwidth = 3;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.insets = noInsets;
		add(phoneField, gc);

		// Row 7
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.insets = leftInsets;
		gc.gridwidth = 1;
		add(availabilityLabel, gc);
		gc.gridwidth = 3;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx++;
		gc.insets = noInsets;
		add(availableRadio, gc);

		gc.gridy++;
		add(unavailableRadio, gc);

		gc.gridy++;
		add(soonRadio, gc);

		// Row 7
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.4;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.insets = leftInsets;
		gc.gridwidth = 1;
		add(resumeLabel, gc);
		gc.gridwidth = 3;
		gc.gridx++;
		add(resumeScrollPane, gc);

		// Row 7
		gc.gridy += 2;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.insets = leftInsets;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.insets = noInsets;
		add(saveButton, gc);

	}
}
