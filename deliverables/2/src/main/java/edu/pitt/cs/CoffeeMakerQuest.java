package edu.pitt.cs;

import java.util.ArrayList;

public interface CoffeeMakerQuest {
	public static CoffeeMakerQuest createInstance(Player player, ArrayList<Room> rooms) {
		if(Config.getBuggyCoffeeMakerQuest()) {
			return new CoffeeMakerQuestBuggy(player, rooms);
		}
		else {
			return new CoffeeMakerQuestImpl(player, rooms);
		}
	}
	
	// Public interface of CoffeeMakerQuest
	public boolean isGameOver();
	public boolean areDoorsPlacedCorrectly();
	public boolean areRoomsUnique();
	public Room getCurrentRoom();
	public boolean setCurrentRoom(Room room);
	public String getInstructionsString();
	public String processCommand(String cmd);
}