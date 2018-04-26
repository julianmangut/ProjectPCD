import java.util.concurrent.BrokenBarrierException;

public class OilHose implements Runnable {
	/*
	 * Maximum quantity of oil that can carry a OilShip.
	 */
	static final Integer maxOil = 3000;
	
	static final Integer maxTake = 1000;
	
	OilShip ship;
	
	public OilHose (OilShip ship) {
		this.ship = ship;
	}
	
	public void run () {
		Platform platform = Platform.getInstance();
		
		while (ship.getOilContainer() < maxOil) {
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
		}
		ship.canExit.release();
	}
}
