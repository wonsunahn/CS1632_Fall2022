
public class CatScale {
	public String report(Cat cat) {
		if(cat.getWeight() > 40) {
			return "Overweight";
		}
		else if(cat.getWeight() < 10) {
			return "Underweight";
		}
		else {
			return "Normal";
		}
	}
}

