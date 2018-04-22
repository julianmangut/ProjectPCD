
public class Gate {

	/*
	 * Instance of Gate apply for the Singleton.
	 */
	private static Gate GateS = null;

	/*
	 * GetInstance create for Singleton of Gate.
	 */
	public static synchronized Gate getInstance() {
		if (GateS == null)
			GateS = new Gate();
		return GateS;
	}

	/*
	 * Show messages when a ship is entering in the Gate.
	 */
	public void enter(Ship ship) {
		for (int i = 1; i <= 3; i++)
			System.out.println("The " + ship.getId() + " ship enters");
	}

	/*
	 * Show messages when a ship is exiting the Gate.
	 */
	public void exit(Ship ship) {
		for (int i = 1; i <= 3; i++)
			System.out.println("The " + ship.getId() + " ship exits");
	}
}
