package edu.pitt.cs;


public class Planet {

	private int hitPoints = 0;

	public Planet(int n) {
		hitPoints = n;
	}
	
	public int getHitPoints() {
		return hitPoints;
	}
	
	public void damage(int n) {
		hitPoints -= n;
	}
	
	public String toString() {
		if (hitPoints > 100) {
			return "Beefy planet";
		} else {
			return "Wimpy planet";
		}
	}
}