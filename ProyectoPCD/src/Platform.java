import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Platform {

	/*
	 * Instance of Platform create for the Singleton.
	 */
	private static Platform platformS = null; // Singleton variable

	/*
	 * Variable that control if a container is in the plarform, is True if there is
	 * a product on the platform or False if not.
	 */
	boolean containerInside = false;

	/*
	 * It is the type of product on the platform.
	 */
	product typeContainer;

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

	// Lock and conditions of the monitor.
	final Lock lock = new ReentrantLock();
	final Condition emptySalt = lock.newCondition();
	final Condition emptySugar = lock.newCondition();
	final Condition emptyFlour = lock.newCondition();
	final Condition full = lock.newCondition();

	// Semaphores for control.
	final Semaphore control = new Semaphore(1);
	final Semaphore controlFiller = new Semaphore(1);
	final Semaphore stop = new Semaphore(0);

	CountDownLatch controlPasar = new CountDownLatch(5);

	// HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

	// Get Instance create for apply the Singleton
	public synchronized static Platform getInstance() {
		if (platformS == null)
			platformS = new Platform();
		return platformS;
	}

	public Platform() {
		this.filler();
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

	public void put(product content) throws InterruptedException {
		lock.lock();

		try {
			while (containerInside) {
				full.await();
			}
			containerInside = true;
			typeContainer = content;
			System.out.println("Leaving " + content.toString());

			switch (typeContainer) {
			case flour:
				emptyFlour.signal();
				break;
			case sugar:
				emptySugar.signal();
				break;
			case salt:
				emptySalt.signal();
				break;
			}
		} finally {
			lock.unlock();
		}
	}

	public void get(product type) throws InterruptedException {
		lock.lock();

		try {
			while ((typeContainer != type || !containerInside) && !isNoMore()) {
				switch (type) {
				case flour:
					emptyFlour.await();
					break;
				case sugar:
					emptySugar.await();
					break;
				case salt:
					emptySalt.await();
					break;
				}
			}

			if (!isNoMore() || containerInside) {
				containerInside = false;
				typeContainer = null;
				System.out.println("Getting " + type.toString());
			}

			if (isNoMore()) {
				emptyFlour.signal();
				emptySalt.signal();
				emptySugar.signal();
			}

			full.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public synchronized void filler() {

		for (int i = 0; i < oilContainers.length; i++) {
			oilContainers[i] = maxContainers;
		}
		System.out.println("THE CONTAINERS HAVE BEEN REFILLED <-----------------------------------");

	}
}
