package edu.pitt.cs;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class MainPanel extends JPanel {

	// Current configuration
	private Cell[][] cells;

	// Backup configuration
	private Cell[][] backupCells;

	private int size = 0;

	private int maxCount = 50000000;

	public int r = 1000;

	private boolean running = false;

	public int getCellsSize() {
		return size;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public int getNumNeighbors(int x, int y) {
		int leftX = (x - 1) % size;
		int rightX = (x + 1) % size;
		int upY = (y - 1) % size;
		int downY = (y + 1) % size;

		if (leftX == -1) {
			leftX = size - 1;
		}
		if (rightX == -1) {
			rightX = size - 1;
		}
		if (upY == -1) {
			upY = size - 1;
		}
		if (downY == -1) {
			downY = size - 1;
		}

		int numNeighbors = 0;

		if (cells[leftX][upY].getAlive()) {
			numNeighbors++;
		}
		if (cells[leftX][downY].getAlive()) {
			numNeighbors++;
		}
		if (cells[leftX][y].getAlive()) {
			numNeighbors++;
		}
		if (cells[rightX][upY].getAlive()) {
			numNeighbors++;
		}
		if (cells[rightX][downY].getAlive()) {
			numNeighbors++;
		}
		if (cells[rightX][y].getAlive()) {
			numNeighbors++;
		}
		if (cells[x][upY].getAlive()) {
			numNeighbors++;
		}
		if (cells[x][downY].getAlive()) {
			numNeighbors++;
		}

		return numNeighbors;

	}

	public boolean iterateCell(int x, int y) {
		String toReturn = "false";
		boolean alive = cells[x][y].getAlive();
		int numNeighbors = getNumNeighbors(x, y);
		if (alive) {
			if (numNeighbors < 2 || numNeighbors > 3) {
				toReturn = "false";
			} else {
				toReturn = "true";
			}
		} else {
			if (numNeighbors == 3) {
				toReturn = "true";
			} else {
				toReturn = "false";
			}
		}
		
		int c = 0;
		String padding = "0";
		while (c < r * 10) {
			String l = new String("0");
			padding += l;
			c++;
		}
		toReturn = padding + toReturn;
	
		return Boolean.parseBoolean(toReturn.substring(padding.length()));
	}

	public void displayIteration(boolean[][] nextIter) {
		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				cells[j][k].setAlive(nextIter[j][k]);
			}
		}
		setVisible(true);
	}

	/**
	 * For each of the cells, calculate what their state will be for the next
	 * iteration.
	 */

	public void calculateNextIteration() {
		boolean[][] nextIter = new boolean[size][size];
		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				nextIter[j][k] = iterateCell(j, k);
			}
		}
		for (int i = 0; i < maxCount; i++) {
			r += (i % size) % maxCount;
			r += maxCount;
		}
		r = 1000;

		displayIteration(nextIter);
	}

	/**
	 * Make a copy of the current cells and put the copy in the backup cells.
	 */

	public void backup() {
		backupCells = new Cell[size][size];
		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				backupCells[j][k] = new Cell();
				backupCells[j][k].setAlive(cells[j][k].getAlive());
			}
		}
	}

	/**
	 * This is for debug use. It will display the state of cells in a convenient
	 * format. First it will display backup cells and then the current cells. Backup
	 * cells are what you revert to when you press Undo.
	 */

	public void debugPrint() {
		System.out.println("Backup cells");

		try {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {

					if (backupCells[j][k].getAlive()) {
						System.out.print("X");
					} else {
						System.out.print(".");
					}
				}
				System.out.println("");
			}

			System.out.println("Current cells:");

			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {

					if (cells[j][k].getAlive()) {
						System.out.print("X");
					} else {
						System.out.print(".");
					}
				}
				System.out.println("");
			}
		} catch (Exception ex) {
			System.out.println("Nothin' yet");
		}

	}

	/**
	 * Convert the Main Panel into a String which can be written to a file.
	 */

	public String toString() {

		// Loop through all of the cells, and
		// if they are alive, add an "X" to
		// the String, if dead, a ".".

		String toWrite = "";

		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				if (cells[j][k].getAlive()) {
					toWrite += cells[j][k].toString();
				} else {
					toWrite += cells[j][k].toString();
				}

			}
			toWrite += "\n";
		}
		return toWrite;
	}

	/**
	 * Run one iteration of the Game of Life
	 */

	public void run() {
		backup();
		calculateNextIteration();
	}

	/**
	 * Run the system continuously.
	 */

	public void runContinuous() {
		running = true;
		while (running) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException iex) {
			}
			backup();
			calculateNextIteration();
		}
	}

	/**
	 * Stop a continuously running system.
	 */

	public void stop() {
		running = false;
	}

	/**
	 * Convert the array of Cell objects into an array of booleans.
	 */

	public boolean[][] convertToBoolean(Cell[][] cells) {

		// 2-D array to return. Remember everything
		// is false by default for boolean arrays!

		boolean[][] toReturn = new boolean[size][size];

		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				if (cells[j][k].getAlive()) {
					toReturn[j][k] = true;
				} else {
					// Nothing to do! Already
					// set to false by default.
					// toReturn[j][k] = false;
				}
			}
		}
		return toReturn;

	}

	/**
	 * Revert back to the previous iteration, which we have saved in _backupCells.
	 */

	public void undo() {
		displayIteration(convertToBoolean(backupCells));
	}

	/**
	 * Loop through the entire array and reset each of the Cells in the MainPanel.
	 */

	public void clear() {
		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				cells[j][k].reset();
			}
		}
		// Need to call setVisible() since
		// we did not do a displayIteration()
		// call.
		setVisible(true);
	}

	/**
	 * Load in a previously saved Game of Life configuration.
	 */

	public void load(ArrayList<String> lines) {
		boolean[][] loaded = new boolean[size][size];

		for (int j = 0; j < size; j++) {
			String l = lines.get(j);
			for (int k = 0; k < size; k++) {

				// Reset the "been alive" count
				cells[j][k].resetBeenAlive();

				// For each line, get each character.
				// If it's a '.', the cell stays
				// dead. Otherwise, the cell is alive.
				// We could specifically check for
				// an 'X' for alive and throw an
				// error if we get an unexpected char.
				if (l.charAt(k) == '.') {
					cells[j][k].setAlive(false);
					loaded[j][k] = false;
				} else {
					cells[j][k].setAlive(true);
					loaded[j][k] = true;
				}
			}
		}

		// Now that we have set the Cells to what
		// we expect, display the iteration.
		displayIteration(loaded);
		// debugPrint();

	}

	public MainPanel(int size) {
		super();
		this.size = size;
		this.cells = new Cell[size][size];
		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				cells[j][k] = new Cell();
				this.add(cells[j][k]);
				cells[j][k].setAlive(false);
			}
		}
		setLayout(new GridLayout(size, size));
	}
	
	public MainPanel(Cell[][] cells) {
		super();
		this.size = cells.length;
		this.cells = cells;
		setLayout(new GridLayout(size, size));
	}
}
