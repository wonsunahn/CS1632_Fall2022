public class PetTypeChecked {
	String name;
	Boolean isCat;

	public PetTypeChecked(String name) {
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

	public static void converse(Cat cat, Dog dog) {
		cat.meow();
		dog.bark();
	}

	public static void main(String[] args) {
		Dog dog = new Dog("Snoopy");
		Cat cat = new Cat("Garfield");
		converse(dog, cat);
	}
}
