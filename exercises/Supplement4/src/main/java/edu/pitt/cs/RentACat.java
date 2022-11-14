package edu.pitt.cs;

public interface RentACat {
	public static RentACat createInstance() {
		if(Config.getBuggyRentACat()) {
			return new RentACatBuggy();
		}
		else {
			return new RentACatImpl();
		}
	}
	
	public boolean returnCat(int id);
	public boolean rentCat(int id);
	public String listCats();
	public boolean catExists(int id);
	public boolean catAvailable(int id);
	public Cat getCat(int id);
	public void addCat(Cat c);
}
