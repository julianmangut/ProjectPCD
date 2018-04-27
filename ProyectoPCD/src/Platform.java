import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class Platform {

	/*
	 * Instance of Platform create for the Singleton.
	 */
	private static Platform platformS = null; // Singleton variable

	/*
	 * It is the type of product on the platform.
	 */
	product typeContainer = null;

	/*
	 * Variable that control if the Merchant have more products to put, True when
	 * doesn't have it, False in other case.
	 */
	private boolean noMore = false;
	static final Integer maxContainers = 1000;
	static final Integer maxTake = 1000;

	Integer oilContainers[] = new Integer[5];

	// CyclicBarrier structure that allow us to wait all threads.
	CyclicBarrier barrera = new CyclicBarrier(5);

	final BlockingQueue <product> containerInPlatform = new SynchronousQueue <> ();

	// Semaphores for control.
	final Semaphore control = new Semaphore(1);
	final Semaphore controlFiller = new Semaphore(1);
	final Semaphore stop = new Semaphore(0);

	CountDownLatch controlPasar = new CountDownLatch(5);
	
	ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

	// Get Instance create for apply the Singleton
	public synchronized static Platform getInstance() {
		if (platformS == null)
			platformS = new Platform();
		return platformS;
	}

	public Platform() {
		this.filler();
	}

	public boolean isAnythingInside () {
		return (typeContainer != null);
	}
	
	public boolean isNoMore() {
		return noMore;
	}

	public void setNoMore(boolean noMore) {
		// lock.lock();
		this.noMore = noMore;
		// lock.unlock();
	}

	public boolean controlOilContainers() {

		boolean empty = true;

		for (int i = 0; i < oilContainers.length; i++) {
			if (oilContainers[i] != 0)
				empty = false;
		}

		return empty;
	}

	public void put (product content) {
		System.out.println("Leaving " + content.toString());
		typeContainer = content;
		try {
			containerInPlatform.put(content);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
	}
	
	public void get (product type) {
		if (type == typeContainer) {
			try {
				 if (!isNoMore()) {
					System.out.println("Getting " + containerInPlatform.take().toString());
					typeContainer = null;
				 }
	        } catch (InterruptedException ie) {
	            ie.printStackTrace();
	        }
		}
	}
	
	public synchronized void filler() {

		for (int i = 0; i < oilContainers.length; i++) {
			oilContainers[i] = maxContainers;
		}
		System.out.println("THE CONTAINERS HAVE BEEN REFILLED <-----------------------------------");

	}

	public void getOil(OilShip ship) {
		if (oilContainers[ship.getOilPlatform()] != 0) {
			oilContainers[ship.getOilPlatform()] = 0;
			ship.setOilContainer(ship.getOilContainer() + maxTake);
			System.out.println("The OilShip number: " + ship.getId() + " and platform number: " + ship.getOilPlatform()
					+ " have take OIL and the quantity is: " + ship.getOilContainer());

			try {
				if (oilContainers[ship.getOilPlatform()] == 0)
					barrera.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

			try {
				controlFiller.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (controlOilContainers() == true)
				filler();
			controlFiller.release();
		}
	}

	public void getWater(OilShip ship) {
		try {
			control.acquire();
			ship.setWaterContainer(ship.getWaterContainer() + maxTake);
			System.out.println("The OilShip number: " + ship.getId() + " and platform number: " + ship.getOilPlatform()
					+ " have take WATER and the quantity that have is: " + ship.getWaterContainer());

			control.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
