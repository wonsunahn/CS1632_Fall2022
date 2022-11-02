package edu.pitt.cs;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainFrame {

	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;

	private JFrame frame = new JFrame("Bean Counter");

	private MainPanel mainPanel;

	private ButtonPanel buttonPanel;

	/**
	 * Constructor - creates the main panel. Inside it is the main panel and the
	 * button panel.
	 * 
	 * @param beanCount number of beans in the machine
	 * @param luck      whether beans progress through pure luck (or skill)
	 */
	public MainFrame(int beanCount, boolean luck) {

		frame.setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		// Close program when window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add Main Panel and Button Panel

		mainPanel = new MainPanel(beanCount, luck);

		buttonPanel = new ButtonPanel(mainPanel);

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

}
