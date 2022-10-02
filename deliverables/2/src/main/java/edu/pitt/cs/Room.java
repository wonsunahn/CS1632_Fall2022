package edu.pitt.cs;

public class Room {
	private String furnishing;
	private String adjective;
	private Item item;
	private String northDoor;
	private String southDoor;
	
	/**
	 * Constructor. The northDoor or the southDoor can be null if there no doors leading north or south.
	 * 
	 * @param furnishing Furnishing in the room
	 * @param adjective Adjective describing the room
	 * @param item Item present in the room
	 * @param northDoor Description of north door (null if there is no north door)
	 * @param southDoor Description of south door (null if there is no south door)
	 */
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
