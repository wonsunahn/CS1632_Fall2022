public class Pet {
	String name;
	Boolean isCat;

	public Pet(String name, Boolean isCat) {
		this.name = name;
		this.isCat = isCat;
	}

	public void meow() {
		System.out.println(name + " meows!");
	}

	public void bark() {
		System.out.println(name + " barks!");
	}

	public static void converse(Pet cat, Pet dog) {
		cat.meow();
		dog.bark();
	}

	public static void main(String[] args) {
		Pet dog = new Pet("Snoopy", false);
		Pet cat = new Pet("Garfield", true);
		converse(dog, cat);
	}
}
