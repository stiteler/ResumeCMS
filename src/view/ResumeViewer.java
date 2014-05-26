package view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Simple JFrame to view a resume separate from application
 * 
 * @author Chris Stiteler
 *
 */
public class ResumeViewer extends JFrame {
	private String resumeString;
	private JTextArea resumeArea;
	private JScrollPane scrollPane;

	/**
	 * Constructor instantiates the resume area,
	 * adds the scroll pane, sets up components, and controls view settings
	 * 
	 * @param resumeString Resume to display in the text area.
	 */
	public ResumeViewer(String resumeString) {
		super("Resume");
		//instantiate
		this.resumeString = resumeString;
		resumeArea = new JTextArea();
		resumeArea.setFont(new Font("helvetica", Font.PLAIN, 16));
		scrollPane = new JScrollPane(resumeArea);
		
		//setup components
		resumeArea.setEditable(false);
		resumeArea.setLineWrap(true);
		resumeArea.setText(resumeString);
		
		//add components
		add(scrollPane);
		
		setMinimumSize(new Dimension(700, 800));
		setSize(700, 600);
		setVisible(true);
	}
}
