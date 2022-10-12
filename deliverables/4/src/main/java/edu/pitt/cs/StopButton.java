package edu.pitt.cs;

import javax.swing.*;
import java.awt.event.*;

public class StopButton extends JButton {

	private MainPanel m;

	public StopButton(MainPanel m) {
		super("Stop");
		this.m = m;
		addActionListener(new StopButtonListener());
	}

	class StopButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			m.stop();
		}
	}

}
