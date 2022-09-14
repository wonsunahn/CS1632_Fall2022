package edu.pitt.cs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {	
	public static void main(String[] args) {
		ArrayList<Room> rooms;
		RoomsJSONParser parser = new RoomsJSONParser();

		try {
			rooms = parser.loadFromFile("rooms.config");
		} catch (FileNotFoundException e) {
			System.out.println("Please make sure that the rooms.config file exists in the current directory.");
			return;
		}

		CoffeeMakerQuest cmq = CoffeeMakerQuest.createInstance(new Player(), rooms);

		if (cmq.areDoorsPlacedCorrectly() == false) {
			System.out.println("Please make sure that the rooms.config file has doors at all interconnected rooms.");
			return;
		}

		if (cmq.areRoomsUnique() == false) {
			System.out.println("Please make sure that rooms in the rooms.config file have unique adjectives and furnishings.");
			return;
		}
					
		System.out.println("Coffee Maker Quest 1.0\n");
		
		Scanner scanner = new Scanner(System.in);
		
		// Main game loop
		while(cmq.isGameOver() == false) {
			System.out.println(cmq.getCurrentRoom().getDescription());
			System.out.println(cmq.getInstructionsString());
			
			String cmd = scanner.nextLine();
			String response = cmq.processCommand(cmd);
			System.out.println(response);
		}
		scanner.close();
	}
}
