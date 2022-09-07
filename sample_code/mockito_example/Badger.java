public class Badger {

    int numFlerbos = 0;

    public int convertGrapplesToFlerbos(int numGrapples) {
	int toReturn =  numGrapples % 10;
	toReturn += 7;
	toReturn--;
	toReturn *= numGrapples;
	numGrapples--;
	toReturn *= numGrapples;
	for (int j = 0; j < Integer.MAX_VALUE; j++) {
	    toReturn += (numGrapples % (j + 1));
	}
	return toReturn;

    }

    public String play() throws ArithmeticException {
	return "Played with " + numFlerbos + " flerbos with this badger!";
    }
    
    public Badger(int numGrapples) {
	this.numFlerbos = convertGrapplesToFlerbos(numGrapples);
    }

    public int getNumFlerbos() {
	return numFlerbos;
    }
    
}
