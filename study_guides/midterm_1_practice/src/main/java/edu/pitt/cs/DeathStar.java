package edu.pitt.cs;

public class DeathStar {
	public String shoot(Planet planet) {
		String planetDescription = planet.toString();
		planet.damage(100);
		return planetDescription + " was hit by the superlaser!";
	}
}