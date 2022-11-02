package edu.pitt.cs;

public class BeanCounterGUI {

	public static void showUsage() {
		System.out.println("Usage: java BeanCounterGUI <number of beans> <luck | skill>");
		System.out.println("Example: java BeanCounterGUI 400 luck");
	}

	/**
	 * Main method. Creates the main frame for the app.
	 * 
	 * @param args args[0] is an integer bean count, args[1] is a string which is
	 *             either luck or skill.
	 */

	public static void main(String[] args) {
		if (args.length != 2) {
			showUsage();
			return;
		}

		int beanCount;
		try {
			beanCount = Integer.parseInt(args[0]);
		} catch (NumberFormatException ne) {
			showUsage();
			return;
		}
		if (beanCount < 0) {
			showUsage();
			return;
		}

		boolean luck;
		if (args[1].equals("luck")) {
			luck = true;
		} else if (args[1].equals("skill")) {
			luck = false;
		} else {
			showUsage();
			return;
		}
		
		// Create the main frame for the app
		new MainFrame(beanCount, luck);
	}

}
