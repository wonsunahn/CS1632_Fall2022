package edu.pitt.cs;

import javax.swing.*;
import java.awt.event.*;

public class ClearButton extends JButton {

	private MainPanel m;

	public ClearButton(MainPanel m) {
		super("Clear");
		this.m = m;
		addActionListener(new ClearButtonListener());
	}

	class ClearButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			m.clear();
		}
	}

}
