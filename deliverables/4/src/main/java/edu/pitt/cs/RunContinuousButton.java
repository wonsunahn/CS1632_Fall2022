package edu.pitt.cs;

import javax.swing.*;
import java.awt.event.*;

public class RunContinuousButton extends JButton {

	private MainPanel m;

	public RunContinuousButton(MainPanel m) {
		super("Run Continuous");
		this.m = m;
		addActionListener(new RunContinuousButtonListener());
	}

	class RunContinuousButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			(new Thread(new GameRunnable())).start();
		}
	}

	class GameRunnable implements Runnable {
		public void run() {
			m.runContinuous();
		}
	}

}
