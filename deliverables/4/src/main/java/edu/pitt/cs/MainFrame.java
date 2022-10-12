package edu.pitt.cs;

import java.awt.*;
import javax.swing.*;

public class MainFrame {

	private final int HEIGHT = 600;
	private final int WIDTH = 800;

	private JFrame frame = new JFrame("Game of Life");

	private MainPanel mainPanel;

	private ButtonPanel buttonPanel;

	public MainFrame(int size) {

		frame.setSize(WIDTH, HEIGHT);
		// Close program when window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add Main Panel and Button Panel

		mainPanel = new MainPanel(size);

		buttonPanel = new ButtonPanel(mainPanel);

		frame.add(mainPanel, BorderLayout.NORTH);
		frame.add(buttonPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

}
