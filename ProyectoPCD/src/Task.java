import java.util.concurrent.BrokenBarrierException;

public class Task implements Runnable {

	static final Integer maxContainers = 1000;
	static final Integer maxTake = 1000;
	
	private int task;
	
	OilShip ship;
	
	public Task (int task, OilShip ship) {
		this.task = task;
		this.ship = ship;
	}
	
	public void run () {
		Platform platform = Platform.getInstance();
		
		if (task == 0) {
			if (platform.oilContainers[ship.getOilPlatform()] != 0) {
				platform.oilContainers[ship.getOilPlatform()] = 0;
				ship.setOilContainer(ship.getOilContainer() + maxTake);
				System.out.println("The OilShip number: " + ship.getId() + " and platform number: " + ship.getOilPlatform()
						+ " have take OIL and the quantity is: " + ship.getOilContainer());

				try {
					if (platform.oilContainers[ship.getOilPlatform()] == 0)
						platform.barrera.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}

				try {
					platform.controlFiller.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (platform.controlOilContainers() == true)
					platform.filler();
				platform.controlFiller.release();
			}
		} else if (task == 1) {
			try {
				platform.control.acquire();
				ship.setWaterContainer(ship.getWaterContainer() + maxTake);
				System.out.println("The OilShip number: " + ship.getId() + " and platform number: " + ship.getOilPlatform()
						+ " have take WATER and the quantity that have is: " + ship.getWaterContainer());

				platform.control.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
