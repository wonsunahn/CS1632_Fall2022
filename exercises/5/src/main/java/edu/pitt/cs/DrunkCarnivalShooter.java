package edu.pitt.cs;

/**
 * Code by @author Wonsun Ahn
 * 
 * <p>CarnivalShooter: Using arbitrary trial and shooter
 * numbers, find how many targets are hit in those trials.
 */

public interface DrunkCarnivalShooter {
	/**
	 * Returns the either DrunkCarnivalShooterImpl or DrunkCarnivalShooterBuggy instance
	 * depending on the Config.
	 * 
	 * @return DrunkCarnivalShooter object
	 */
	public static DrunkCarnivalShooter createInstance() {
		if (Config.getBuggy()) {
			return new DrunkCarnivalShooterBuggy();
		} else {
			return new DrunkCarnivalShooterImpl();
		}
	}

	public String getRoundString();

	public boolean shoot(int t, StringBuilder builder);

	public boolean takeDownTarget(int t);

	public boolean isTargetStanding(int t);

	public int getRemainingTargetNum();
}
