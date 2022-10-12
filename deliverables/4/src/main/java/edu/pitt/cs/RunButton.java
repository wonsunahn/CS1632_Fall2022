package edu.pitt.cs;

import javax.swing.*;
import java.awt.event.*;

public class RunButton extends JButton {

	private MainPanel m;

	public RunButton(MainPanel m) {
		super("Run");
		this.m = m;
		addActionListener(new RunButtonListener());
	}

	class RunButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			m.run();
		}
	}

}
