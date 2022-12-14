package edu.pitt.cs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Yes, mainPanel is exposed but it is not a security issue.")
public class RunFastButton extends JButton {

	private MainPanel mainPanel;

	/**
	 * Constructor - Adds a listener to the button.
	 * 
	 * @param m the main animation panel where all the action happens
	 */
	public RunFastButton(MainPanel m) {
		super("Fast");
		mainPanel = m;
		addActionListener(new RunFastButtonListener());
	}

	class RunFastButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			mainPanel.runFast();
		}
	}

}
