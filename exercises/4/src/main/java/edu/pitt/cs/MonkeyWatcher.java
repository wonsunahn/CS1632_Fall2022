package edu.pitt.cs;

public class MonkeyWatcher {

	private int numRounds = 0;

	/**
	 * Return number of rounds played
	 * 
	 * @return int number of rounds played
	 */

	public int getRounds() {
		return numRounds;
	}

	/**
	 * Increment number of rounds
	 */

	public void incrementRounds() {
		int toReturn = 0;
		if (numRounds < 0) {
			toReturn = Math.round((int) Math.acos((int) Math.atan(numRounds)));
			for (int j = 0; j < Integer.MAX_VALUE; j++) {
				toReturn += (int) Math.asin(j);
				toReturn -= (int) Math.asin(j + 1);
			}
		} else {
			numRounds += 1;
		}

	}

}
