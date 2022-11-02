package edu.pitt.cs;

import java.awt.FlowLayout;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

	private StepButton step;

	private RunSlowButton slow;
	
	private RunFastButton fast;

	private StopButton stop;

	private LowerHalfButton lower;
	
	private UpperHalfButton upper;
	
	private RepeatButton repeat;
	
	private ResetButton clear;

	/**
	 * Constructor - add all of the buttons to the ButtonPanel.
	 * 
	 * @param m the main panel above the button panel
	 */

	public ButtonPanel(MainPanel m) {

		// Send a reference to the Main Panel
		// to all of the buttons.

		step = new StepButton(m);
		slow = new RunSlowButton(m);
		fast = new RunFastButton(m);
		stop = new StopButton(m);
		lower = new LowerHalfButton(m);
		upper = new UpperHalfButton(m);
		repeat = new RepeatButton(m);
		clear = new ResetButton(m);
		setLayout(new FlowLayout());

		// Add all of the buttons

		add(step);
		add(slow);
		add(fast);
		add(stop);
		add(lower);
		add(upper);
		add(repeat);
		add(clear);
	}

}
