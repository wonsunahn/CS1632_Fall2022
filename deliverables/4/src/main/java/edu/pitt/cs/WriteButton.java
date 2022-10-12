package edu.pitt.cs;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class WriteButton extends JButton {

	private MainPanel m;

	public WriteButton(MainPanel m) {
		super("Write");
		this.m = m;
		addActionListener(new WriteButtonListener());
	}

	class WriteButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String toWrite = m.toString();

			boolean success = FileAccess.saveFile("backup.txt", toWrite);

			if (!success) {
				JOptionPane.showMessageDialog((Component) e.getSource(), "COULD NOT WRITE FILE backup.txt",
						"BROUGHT TO YOU BY WONSUN AHN", JOptionPane.WARNING_MESSAGE);
			}

		}
	}

}
