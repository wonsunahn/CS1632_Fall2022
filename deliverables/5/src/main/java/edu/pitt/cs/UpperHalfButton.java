package edu.pitt.cs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class UpperHalfButton extends JButton {

	private MainPanel mainPanel;

	/**
	 * Constructor - Adds a listener to the button.
	 * 
	 * @param m the main animation panel where all the action happens
	 */
	public UpperHalfButton(MainPanel m) {
		super("Upper Half");
		mainPanel = m;
		addActionListener(new UpperHalfButtonListener());
	}

	class UpperHalfButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			mainPanel.upperHalf();
		}
	}

}
