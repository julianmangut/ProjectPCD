import java.util.concurrent.BrokenBarrierException;

public class OilHose implements Runnable {
	
	static final Integer maxContainers = 1000;
	static final Integer maxTake = 1000;
	
	Integer oilContainer;
	
	OilShip ship;
	
	public OilHose (OilShip ship) {
		this.ship = ship;
	}
	
	public void run () {
		Platform platform = Platform.getInstance();
		
		if (oilContainer != 0) {
			oilContainer = 0;
			ship.setOilContainer(ship.getOilContainer() + maxTake);
			System.out.println("The OilShip number: " + ship.getId() + " and platform number: " + ship.getOilPlatform()
					+ " have take OIL and the quantity is: " + ship.getOilContainer());

			try {
				if (oilContainer == 0)
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
}
