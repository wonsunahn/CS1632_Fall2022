package edu.pitt.cs;

import java.awt.*;
import javax.swing.*;

public class ButtonPanel extends JPanel {

	private RunButton run;

	private RunContinuousButton runContinuous;

	private StopButton stop;

	private WriteButton write;

	private UndoButton undo;

	private LoadButton load;

	private ClearButton clear;

	/**
	 * Constructor - add all of the buttons to the ButtonPanel.
	 */

	public ButtonPanel(MainPanel m) {

		// Send a reference to the Main Panel
		// to all of the buttons.

		run = new RunButton(m);
		runContinuous = new RunContinuousButton(m);
		stop = new StopButton(m);
		write = new WriteButton(m);
		undo = new UndoButton(m);
		load = new LoadButton(m);
		clear = new ClearButton(m);
		setLayout(new FlowLayout());

		// Add all of the buttons

		add(run);
		add(runContinuous);
		add(stop);
		add(write);
		add(undo);
		add(load);
		add(clear);
	}

}
