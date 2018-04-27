import java.util.ArrayList;
import java.util.Random;

public class Merchant extends Ship {

	private ArrayList<product> containers = new ArrayList<product>();

	/*
	 * Parameterized constructor of the Merchant
	 */
	public Merchant(int id) {
		super(1, id);
	}

	public void addProducts(ArrayList<product> prods) {
		containers.addAll(prods);
	}

	/*
	 * (non-Javadoc) TODO
	 * 
	 * @see Ship#run()
	 */
	public void run() {

		super.run();

		Platform pt = Platform.getInstance();
		Random delete = new Random();

		while (!containers.isEmpty()) {
			if (!pt.isAnythingInside())
				pt.put(containers.remove(delete.nextInt(containers.size())));
		}
		pt.setNoMore(true);

		this.setDirection(2);
		super.run();
	}	
}