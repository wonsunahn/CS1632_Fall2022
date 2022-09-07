public class Dog extends PetTypeChecked {
	public Dog(String name) {
		super(name);
	}

	public void bark() {
		System.out.println(name + " barks!");
	}
}
