
public class Crane implements Runnable {

	product type;

	public Crane(product type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc) TODO
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		Platform pt = Platform.getInstance();

		while (!pt.isNoMore()) {
			pt.get(type);
		}
	}
}
