import java.util.LinkedList;
import java.util.Queue;

public class Control {

	/*
	 * Variable that control how many ships are entering.
	 */
	int entering = 0;

	/*
	 * Variable that control how many ships are exiting.
	 */
	int exiting = 0;

	/*
	 * Variable that control how many ships are waiting for entering or wciting.
	 */
	int waiting = 0;

	Queue<Ship> enterOrder = new LinkedList<Ship>();

	Queue<Ship> exitOrder = new LinkedList<Ship>();

	private static Control ControlS = null;

	/*
	 * GetInstance create for Singleton of Control.
	 */
	public static synchronized Control getInstance() {
		if (ControlS == null)
			ControlS = new Control();
		return ControlS;
	}

	public synchronized void entryPermission(Ship ship) {
		enterOrder.add(ship);
		while (exiting > 0 || waiting > 0 || enterOrder.peek() != ship) {
			try {
				wait();
			} catch (Exception e) {
				;
			}
		}
		enterOrder.poll();
		notifyAll();
		entering++;
	}

	public synchronized void exitPermission(Ship ship) {
		exitOrder.add(ship);
		while (entering > 0 || exitOrder.peek() != ship) {
			try {
				waiting++;
				wait();
				waiting--;
			} catch (Exception e) {
				;
			}
		}
		exitOrder.poll();
		notifyAll();
		exiting++;
	}

	public synchronized void entryNotification() {
		entering--;
		if (entering == 0)
			notifyAll();
	}

	public synchronized void exitNotification() {
		exiting--;
		if (exiting == 0)
			notifyAll();
	}
}
