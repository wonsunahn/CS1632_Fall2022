package edu.pitt.cs;

public class Config {
	// Whether to intentionally inject bugs
	private static boolean buggy = false;

	public static void setBuggy(boolean val) {
		buggy = val;
	}

	public static boolean getBuggy() {
		return buggy;
	}
}
