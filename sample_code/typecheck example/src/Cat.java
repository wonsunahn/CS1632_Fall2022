public class Cat extends PetTypeChecked {
	public Cat(String name) {
		super(name);
	}

	public void meow() {
		System.out.println(name + " meows!");
	}
}
