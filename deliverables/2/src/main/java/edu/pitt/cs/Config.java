package edu.pitt.cs;

public class Config {
	// Whether to intentionally inject bugs into CoffeeMakerQuest.
	private static boolean buggyCoffeeMakerQuest = false;
	// Whether to intentionally inject bugs into Player.
	private static boolean buggyPlayer = false;
	// Whether to intentionally inject bugs into Room.
	private static boolean buggyRoom = false;
	
	public static void setBuggyCoffeeMakerQuest(boolean val) {
		buggyCoffeeMakerQuest = val;
	}
	public static void setBuggyPlayer(boolean val) {
		buggyPlayer = val;
	}
	public static void setBuggyRoom(boolean val) {
		buggyRoom = val;
	}
	public static boolean getBuggyCoffeeMakerQuest() {
		return buggyCoffeeMakerQuest;
	}
	public static boolean getBuggyPlayer() {
		return buggyPlayer;
	}
	public static boolean getBuggyRoom() {
		return buggyRoom;
	}
}
