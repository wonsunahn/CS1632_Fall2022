package edu.pitt.cs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JPanel;

/**
 * Code by @author Wonsun Ahn
 *
 * <p>MainPanel: This is the display for the machine. Most internal state is
 * encapsulated inside _logic. This class mainly handles the display of that
 * internal state animations between frames.
 */

public class MainPanel extends JPanel {

	private BeanCounterLogic logic;	// The core logic of the program
	private Bean[] beans;				// The beans in the machine

	private Point[] beanPositions;		// Current bean positions in physical coordinates
	private Point[] targetPositions;	// Target positions that the beans need to move to

	private int timeBetweenFrames;		// Time (ms) between bean movement animation frames
	private int timeBetweenSteps;		// Time (ms) that is paused before executing the next step

	private boolean isRunning;			// Is the bean counter running now?

	private int barHeight;				// The height reserved for bar graphs in the panel

	public static final int SLOT_COUNT = 10;
	public static final int PEG_SIZE = 10;
	public static final int BEAN_SIZE = 10;
	public static final int TOP_MARGIN = 30;
	public static final int BOTTOM_MARGIN = 30;
	public static final int BAR_TEXT_HEIGHT = 20;

	/**
	 * Constructor - creates the main animation panel for the machine.
	 * 
	 * @param beanCount number of beans in the machine
	 * @param isLuck    whether beans progress through pure luck (or skill)
	 */
	public MainPanel(int beanCount, boolean isLuck) {
		super();
		
		// Create the internal logic
		logic = BeanCounterLogic.createInstance(SLOT_COUNT);
		// Create the beans
		beans = new Bean[beanCount];
		for (int i = 0; i < beanCount; i++) {
			beans[i] = Bean.createInstance(SLOT_COUNT, isLuck, new Random());
		}
		// Initialize the logic with the beans
		logic.reset(beans);
		// Set some display related parameters
		timeBetweenFrames = 10;
		timeBetweenSteps = 100;
		// Heuristically maximum height of a bell curve with some headroom
		barHeight = (int) Math.round(beanCount * 0.27) + 10; 
		setBackground(Color.WHITE);
	}

	/**
	 * Move a bean one pixel from p to q.
	 * 
	 * @param p current position of the bean
	 * @param q target position of the bean
	 * @return whether current position already equals target position
	 */
	private boolean moveOnePixel(Point p, Point q) {
		boolean arrivedX = false;
		boolean arrivedY = false;
		if (p.x > q.x) {
			p.x--;
		} else if (p.x < q.x) {
			p.x++;
		} else {
			arrivedX = true;
		}
		if (p.y > q.y) {
			p.y--;
		} else if (p.y < q.y) {
			p.y++;
		} else {
			arrivedY = true;
		}
		return arrivedX && arrivedY;
	}

	/**
	 * Returns the physical coordinate for logical coordinate p. The logical
	 * coordinate is the coordinate system used by BeanCounterLogic (see
	 * BeanCounterLogic for details). The physical coordinate is the actual
	 * coordinate in the MainPanel.
	 * 
	 * @param p the logical coordinate
	 * @return the translated physical coordinate.
	 */
	private Point logicalToPhysical(Point p) {
		Dimension d = getSize();
		int initialX = d.width / 2;
		int initialY = TOP_MARGIN;
		int pegXSpacing = d.width / (SLOT_COUNT);
		int pegYSpacing = (d.height - TOP_MARGIN - BOTTOM_MARGIN - barHeight - BAR_TEXT_HEIGHT)
				/ (SLOT_COUNT - 1);
		int physicalX = initialX - pegXSpacing / 2 * p.y + p.x * pegXSpacing;
		int physicalY = initialY + p.y * pegYSpacing;
		return new Point(physicalX, physicalY);
	}

	/**
	 * Calculates all the in-flight bean positions (in physical coordinates) from
	 * _logic.
	 * 
	 * @return an array of in-flight bean positions
	 */
	private Point[] getBeanPositions() {
		Point[] positions = new Point[SLOT_COUNT];
		for (int yPos = 0; yPos < SLOT_COUNT; yPos++) {
			int xPos = logic.getInFlightBeanXPos(yPos);
			if (xPos != BeanCounterLogic.NO_BEAN_IN_YPOS) {
				positions[yPos] = logicalToPhysical(new Point(xPos, yPos));
				positions[yPos].y -= PEG_SIZE + 5;
			} else {
				positions[yPos] = null;
			}
		}
		return positions;
	}

	/**
	 * Advance the machine one step.
	 */
	private void runOneStep() {
		// Get current positions
		beanPositions = getBeanPositions();
		// Drop the last bean into the slot
		beanPositions[SLOT_COUNT - 1] = null;
		// Advance one step
		logic.advanceStep();
		// Get new positions
		targetPositions = getBeanPositions();
		// Repaint
		repaint();
	}

	/**
	 * Advance the machine one step. Stop the machine if running continuously.
	 */
	public void step() {
		isRunning = false;
		timeBetweenFrames = 10;
		runOneStep();
	}

	/**
	 * Run the machine continuously in slow mode.
	 */
	public void runSlow() {
		isRunning = true;
		timeBetweenFrames = 10;
		timeBetweenSteps = 100;
		runOneStep();
	}

	/**
	 * Run the machine continuously in fast mode.
	 */
	public void runFast() {
		isRunning = true;
		timeBetweenFrames = 1;
		timeBetweenSteps = 1;
		runOneStep();
	}

	public void stop() {
		isRunning = false;
	}

	public void lowerHalf() {
		logic.lowerHalf();
		repaint();
	}

	public void upperHalf() {
		logic.upperHalf();
		repaint();
	}

	/**
	 * Stop the machine and repeat the experiment with existing beans. If you
	 * pressed "lower half" or "upper half" previously, that means you are repeating
	 * with that half of your beans only.
	 */
	public void repeat() {
		isRunning = false;
		logic.repeat();
		// repeat() clears machine of in-flight beans, so need to refresh positions
		beanPositions = getBeanPositions();
		repaint();
	}

	/**
	 * Stop the machine reset with the original beans.
	 */
	public void reset() {
		isRunning = false;
		logic.reset(beans);
		// repeat() clears machine of in-flight beans, so need to refresh positions
		beanPositions = getBeanPositions();
		repaint();
	}

	/**
	 * Draw the pegs in the machine.
	 * 
	 * @param g the graphics object
	 */
	public void drawPegs(Graphics g) {
		g.setColor(Color.BLACK);
		for (int i = 0; i < SLOT_COUNT - 1; i++) {
			for (int j = 0; j <= i; j++) {
				Point p = logicalToPhysical(new Point(j, i));
				g.drawRect(p.x, p.y, PEG_SIZE, PEG_SIZE);
			}
		}
	}

	/**
	 * Draw the in-flight beans in the machine.
	 * 
	 * @param g the graphics object
	 */
	public void drawBeans(Graphics g) {
		g.setColor(Color.BLACK);
		for (int i = 0; i < SLOT_COUNT; i++) {
			if (beanPositions[i] != null) {
				g.fillOval(beanPositions[i].x, beanPositions[i].y, BEAN_SIZE, BEAN_SIZE);
			}
		}
	}

	/**
	 * Draw the bars that represent the number of beans in each slot.
	 * 
	 * @param g the graphics object
	 */
	public void drawBars(Graphics g) {
		g.setColor(Color.BLACK);
		for (int i = 0; i < SLOT_COUNT; i++) {
			Dimension d = getSize();
			Point p = logicalToPhysical(new Point(i, SLOT_COUNT));
			g.fillRect(p.x, d.height - BOTTOM_MARGIN - BAR_TEXT_HEIGHT - logic.getSlotBeanCount(i),
					d.width / (SLOT_COUNT + 1), logic.getSlotBeanCount(i));
			g.drawString(String.valueOf(i), p.x + d.width / (SLOT_COUNT + 1) / 2, d.height - BOTTOM_MARGIN);
		}
	}

	/**
	 * Display some statistics such as the current average slot value and the
	 * remaining number of beans.
	 * 
	 * @param g the graphics object
	 */
	public void drawStats(Graphics g) {
		Dimension d = getSize();
		g.setFont(new Font("Courier", Font.PLAIN, 20));
		String average = "Average = " + new DecimalFormat("#.##").format(logic.getAverageSlotBeanCount());
		g.drawString(average, d.width - 200, TOP_MARGIN);
		String remaining = "Remaining = " + logic.getRemainingBeanCount();
		g.drawString(remaining, d.width - 200, TOP_MARGIN + 30);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (beanPositions == null) {
			beanPositions = getBeanPositions();
		}
		drawPegs(g);
		drawBeans(g);
		drawBars(g);
		drawStats(g);

		// If we have target positions, we are still moving
		if (targetPositions != null) {
			boolean allArrived = true;
			for (int i = 0; i < SLOT_COUNT - 1; i++) {
				if (beanPositions[i] != null && targetPositions[i + 1] != null) {
					allArrived &= moveOnePixel(beanPositions[i], targetPositions[i + 1]);
				}
			}
			try {
				Thread.sleep(timeBetweenFrames);
			} catch (InterruptedException ie) {
				// Nothing to do here
			}
			if (allArrived) {
				// Now that beans have arrived, reset bean positions for the next frame
				beanPositions = getBeanPositions();
				// Remove target positions such that animations stop for this step
				targetPositions = null;
				// If running, take the next step
				if (isRunning) {
					if (Arrays.stream(beanPositions).filter(p -> p != null).count() == 0) {
						isRunning = false;
					} else {
						try {
							Thread.sleep(timeBetweenSteps);
						} catch (InterruptedException ie) {
							// Nothing to do here
						}
						runOneStep();
						return;
					}
				}
			}
			// Put repaint request on the queue again for the next frame
			repaint();
		}
	}

}
