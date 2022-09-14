package edu.pitt.cs;

public class Room {
	private String furnishing;
	private String adjective;
	private Item item;
	private String northDoor;
	private String southDoor;
	
	// Class constructor to populate the class variables
	public Room(String furnishing, String adjective, Item item, String northDoor, String southDoor) {
		this.furnishing = furnishing;
		this.adjective = adjective;
		this.item = item;
		this.northDoor = northDoor;
		this.southDoor = southDoor;
	}
	
	public String getFurnishing() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		return furnishing;
	}

	public String getAdjective() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		return adjective;
	}

	public Item getItem() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		return item;
	}

	public String getNorthDoor() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		return northDoor;
	}

	public String getSouthDoor() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		return southDoor;
	}
	
	// Get the description for the current room (includes adjective, furnishing, and door description)
	public String getDescription() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		String desc = "You see a " + this.adjective + " room.\nIt has a " + this.furnishing + ".\n";
		if(northDoor != null) {
			desc += "A " + this.northDoor + " door leads North.\n";
		}
		if(southDoor != null) {
			desc += "A " + this.southDoor + " door leads South.\n";
		}
		return desc;
	}
}
