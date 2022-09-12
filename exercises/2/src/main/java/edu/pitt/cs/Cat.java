package edu.pitt.cs;

public class Cat {

	/**
	 * Indicates whether or not the cat is rented.
	 */

	private boolean rented = false;

	/**
	 * ID of the cat By default, -1
	 */
	private int id = -1;

	/**
	 * Name of the cat
	 */

	private String name;

	/**
	 * Whether bug injection is turned on for the Cat class. Turn off before actual
	 * usage. Only leave on during JUnit testing.
	 */

	public static boolean bugInjectionOn = true;

	/**
	 * Constructor - creates a new Cat object Note there are no checks that this ID
	 * is not taken by another cat! This is probably something that we would fix in
	 * a production system.
	 * 
	 * @param id - the id number of this cat
	 * @param name - the name of this Cat
	 */

	public Cat(int id, String name) {
		if (bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for constructor");
		}
		rented = false;
		this.id = id;
		this.name = name;
	}

	/**
	 * Rent cat. Simply sets the rented flag to true.
	 */

	public void rentCat() {
		if (bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for rentCat()");
		}
		rented = true;
	}

	/**
	 * Return cat. Simply sets the rented flag to false.
	 */

	public void returnCat() {
		if (bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for returnCat()");
		}
		rented = false;
	}

	/**
	 * Accessor for name variable. Returns the name of this cat.
	 * 
	 * @return String name of cat
	 */

	public String getName() {
		if (bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for getName()");
		}
		return name;
	}

	/**
	 * Accessor for id variable. Returns the ID of this cat.
	 * 
	 * @return int ID of this cat
	 */

	public int getId() {
		if (bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for getId()");
		}
		return id;
	}

	/**
	 * Accessor for rented variable. Returns if cat is rented.
	 * 
	 * @return boolean - true if rented, false otherwise
	 */

	public boolean getRented() {
		if (bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for getRented()");
		}
		return rented;
	}

	/**
	 * Returns string version of this cat, in form: "ID *id_num*. *name*" Example
	 * for cat of ID 1, name Jennyanydots: "ID 1. Jennyanydots"
	 * 
	 * @return String string version of cat
	 */

	public String toString() {
		if (bugInjectionOn) {
			throw new UnsupportedOperationException("Injected bug for toString()");
		}
		return "ID " + id + ". " + name;
	}

}
