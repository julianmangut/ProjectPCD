
public class Ship implements Runnable {

	private int direction;

	private int id;

	public Ship(int direction, int id) {
		this.direction = direction;
		this.id = id;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	/*
	 * Getter of the variable Id.
	 */
	public int getId() {
		return id;
	}

	/*
	 * Setter of the variable Id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc) TODO
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		Gate gate = Gate.getInstance();
		Control control = Control.getInstance();

		if (direction == 1) {
			control.entryPermission(this);
			gate.enter(this);
			control.entryNotification();
		} else if (direction == 2) {
			control.exitPermission(this);
			gate.exit(this);
			control.exitNotification();
		}
	}
}
