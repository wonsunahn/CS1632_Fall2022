package edu.pitt.cs;

class Player {
	private boolean hasCoffee;
	private boolean hasCream;
	private boolean hasSugar;
	
	public Player() {
		hasCoffee = false;
		hasCream = false;
		hasSugar = false;
	}
	
	public boolean checkCoffee() {
		if (Config.getBuggyPlayer()) {
			throw new UnsupportedOperationException("Injected bug for Player class");
		}
		return hasCoffee;
	}
	
	public boolean checkCream() {
		if (Config.getBuggyPlayer()) {
			throw new UnsupportedOperationException("Injected bug for Player class");
		}
		return hasCream;
	}
	
	public boolean checkSugar() {
		if (Config.getBuggyPlayer()) {
			throw new UnsupportedOperationException("Injected bug for Player class");
		}
		return hasSugar;
	}
	
	public void addItem(Item item) {
		if (Config.getBuggyPlayer()) {
			throw new UnsupportedOperationException("Injected bug for Player class");
		}
		switch(item) {
		case COFFEE:
			hasCoffee = true;
			break;
		case CREAM:
			hasCream = true;
			break;
		case SUGAR:
			hasSugar = true;
			break;
		case NONE:
			break;
		}
	}
	
	// Return the string describing all the items in the inventory
	public String getInventoryString() {
		if (Config.getBuggyPlayer()) {
			throw new UnsupportedOperationException("Injected bug for Player class");
		}
		String str = "";
		if(hasCoffee) {
			str += "You have a cup of delicious coffee.\n";
		}
		else {
			str += "YOU HAVE NO COFFEE!\n";
		}
		if(hasCream) {
			str += "You have some fresh cream.\n";
		}
		else {
			str += "YOU HAVE NO CREAM!\n";
		}
		if(hasSugar) {
			str += "You have some tasty sugar.\n";
		}
		else {
			str += "YOU HAVE NO SUGAR!\n";
		}
		return str;
	}
}