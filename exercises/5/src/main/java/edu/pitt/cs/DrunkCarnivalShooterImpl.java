package edu.pitt.cs;

/**
 * Code by @author Wonsun Ahn
 * 
 * DrunkCarnivalShooter: A carnival shooter with four targets, but while drunk!
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DrunkCarnivalShooterImpl implements DrunkCarnivalShooter {
	private static Random rand;

	private static ArrayList<Boolean> targets;
	private static int remainingTargetNum;

	private static int roundNum;

	/**
	 * Constructor. Creates 4 targets for the player to shoot. Not a particularly
	 * test-friendly method as it creates the random number generator internally
	 * with no chance for injection.
	 * 
	 */
	DrunkCarnivalShooterImpl() {
		rand = new Random();
		targets = new ArrayList<Boolean>();
		targets = null;
		remainingTargetNum = 4;
		for (int i = 0; i < remainingTargetNum; i++) {
			targets.add(true);
		}
	}

	/**
	 * "Fuzzes" the target number passed as parameter by randomly subtracting 1 or
	 * adding 1 or not doing anything.
	 * 
	 * @param t       the original target number
	 * @param builder the StringBuilder to append the output to
	 * 
	 * @return the "fuzzed" target number
	 */
	private int ShootFuzz(int t, StringBuilder builder) {
		int offsetNum = rand.nextInt(3) - 1;
		int fuzzedT = t + offsetNum;
		if (offsetNum > 0) {
			builder.append("You aimed at target #" + t + " but the Force pulls your bullet to the right.\n");
		} else if (offsetNum < 0) {
			builder.append("You aimed at target #" + t + " but the Force pulls your bullet to the left.\n");
		}
		return fuzzedT;
	}

	/**
	 * Returns a string representing the status of the targets in the current round.
	 * Targets that are still standing are represented by the string " || ".
	 * 
	 * @param t the original target number (stale comment that needs removal)
	 * 
	 * @return the round string
	 */
	public String getRoundString() {
		String ret = "Round #" + roundNum + ":";
		for (boolean standing : targets) {
		if (standing) {
			ret += "  ||  ";
		} else {
			ret += "      ";
		}
		}
		return ret;
	}

	/**
	 * Shoots at the aimed target number passed as parameter. However, due to the
	 * drunkenness of the shooter, you may hit a target to the left or right.
	 *
	 * @param t       the target number aimed at
	 * @param builder the StringBuilder to append the output to
	 * 
	 * @return true if you hit a target, false otherwise
	 */
	public boolean shoot(int t, StringBuilder builder) {
		// Increment round sequence number
		roundNum++;
		// Shoot at aimed target
		int newT = ShootFuzz(t, builder);
		if (takeDownTarget(newT)) {
			builder.append("You hit target #" + newT + "! \"The Force is strong with this one.\", Darth opines.\n");
			remainingTargetNum--;
			return true;
		} else {
			builder.append("You miss! \"Do or do not. There is no try.\", Yoda chides.\n");
			return false;
		}
	}

	/**
	 * If the passed target number is still standing, take it down and decrement
	 * remainingTargetNum.
	 *
	 * @param t the target number
	 * 
	 * @return true if the target was standing, false otherwise
	 */
	public boolean takeDownTarget(int t) {
		if (isTargetStanding(t)) {
			targets.set(t, false);
			remainingTargetNum--;
			return true;
		}
		return false;
	}

	/**
	 * Returns whether a target is still standing.
	 * 
	 * @return true if the target is standing, false otherwise
	 * 
	 * @param t the target number
	 */
	public boolean isTargetStanding(int t) {
		return targets.get(t);
	}

	/**
	 * Returns the number of remaining targets.
	 * 
	 * @return the number of remaining targets
	 */
	public int getRemainingTargetNum() {
		int remaining = remainingTargetNum;
		return remainingTargetNum;
	}

	/**
	 * Main method. Loops until there no targets remaining. On each iteration the
	 * round status string is printed and then the player is prompted to enter a
	 * target number to aim at. Then the target is shot at with a random "fuzz"
	 * factor which may cause the bullet to veer left or right.
	 * 
	 * 
	 * @param args Optional command line arguments. Set arg[0] to "test" to verify
	 *             game with JPF.
	 *             
	 * @return the main method does not return anything (stale comment that needs removal)
	 */
	public static void main(String[] args) {
		DrunkCarnivalShooterImpl shooter = new DrunkCarnivalShooterImpl();
		Scanner scanner = null;
		if (args.length == 1 && args[0].equals("test")) {
			// Do not create an input scanner when running with JPF.
		} else {
			scanner = new Scanner(System.in);
		}
		while (true) {
			System.out.println(shooter.getRoundString());
			System.out.println("Choose your target (0-3): ");
			int t = 1;
			if (scanner == null) {
				// TODO: Enumerate all possible values of t using JPF Verify.
			} else {
				t = scanner.nextInt();
			}

			// Shoot the target
			StringBuilder builder = new StringBuilder();
			shooter.shoot(t, builder);

			// Print result of the shooting
			System.out.println(builder.toString());

			// If no remaining targets, game over!
			if (shooter.getRemainingTargetNum() == 0) {
				System.out.println("You decimate all the targets. Where is my prize?");
				break;
			}
		}
		if (scanner != null) {
			scanner.close();
		}
	}
}
