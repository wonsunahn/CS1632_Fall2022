package edu.pitt.cs;

import javax.swing.*;
import java.awt.event.*;

public class UndoButton extends JButton {

	private MainPanel m;

	public UndoButton(MainPanel m) {
		super("Undo");
		this.m = m;
		addActionListener(new UndoButtonListener());
	}

	class UndoButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// System.out.println("Undo button pressed!");
			m.undo();
		}
	}

}
