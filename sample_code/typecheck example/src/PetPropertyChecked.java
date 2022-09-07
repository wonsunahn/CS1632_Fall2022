public class PetPropertyChecked {
	String name;
	Boolean isCat;

	public PetPropertyChecked(String name, Boolean isCat) {
		this.name = name;
		this.isCat = isCat;
	}

	public void meow() {
		assert isCat == true;
		System.out.println(name + " meows!");
	}

	public void bark() {
		assert isCat == false;
		System.out.println(name + " barks!");
	}

	public static void converse(PetPropertyChecked cat, PetPropertyChecked dog) {
		cat.meow();
		dog.bark();
	}

	public static void main(String[] args) {
		PetPropertyChecked dog = new PetPropertyChecked("Snoopy", false);
		PetPropertyChecked cat = new PetPropertyChecked("Garfield", true);
		converse(dog, cat);
	}
}
