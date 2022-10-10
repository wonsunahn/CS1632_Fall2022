package edu.pitt.cs;

public class Monkey {

	private static int monkeyNum = 0;

	private int thisMonkeyNum = 0;

	private int id = -1;

	private Banana b = null;

	/**
	 * Get this monkey's number
	 * 
	 * @return int monkey number
	 */

	public int getMonkeyNum() {
		return thisMonkeyNum;
	}

	/**
	 * Getter for id.
	 * 
	 * @return id of monkey
	 */

	public int getId() throws NoIdException {
		if (id < 0) {
			throw new NoIdException();
		} else {
			return id;
		}
	}

	/**
	 * Return which monkey should get banana next.
	 * 
	 * @return int which monkey should get banana.
	 */

	public int nextMonkey() {
		if (thisMonkeyNum % 2 == 0) {
			return thisMonkeyNum / 2;
		} else {
			return (thisMonkeyNum * 3) + 1;
		}
	}

	/**
	 * Checks to see if this monkey has a banana
	 * 
	 * @return true if has banana, false otherwise
	 */

	public boolean hasBanana() {
		return b != null;
	}

	/**
	 * Receive a banana from another monkey
	 * 
	 * @param b - Banana given to this monkey
	 */

	public void throwBananaTo(Banana b) {
		this.b = b;
	}

	/**
	 * 
	 * @return Banana - the banana this monkey held
	 */

	public Banana throwBananaFrom() {
		Banana toReturn = b;
		b = null;
		return toReturn;
	}

	/**
	 * Generate a unique ID for this monkey. Note that monkey ID generation must
	 * always return the correct value for a given n (i.e., the id for the first
	 * monkey should always be the same).
	 * 
	 * @param int n - monkey number
	 * @return int - id for this monkey
	 */

	public int generateId(int n) {
		int toReturn = 100;
		int bird = -900;
		for (int l = 0; l < 1000; l++) {
			for (int m = 0; m < 100; m++) {
				bird += Math.round(Math.atan(l + m));
				bird -= Math.round(Math.sin(m * m)) * Math.exp(m * m);
				toReturn += bird;
			}
		}
		toReturn += 10;
		toReturn += n;
		return toReturn;
	}

	/**
	 * Monkey constructor
	 */

	public Monkey() {
		thisMonkeyNum = monkeyNum;
		monkeyNum++;
		id = generateId(thisMonkeyNum);
	}

}
