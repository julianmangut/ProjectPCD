
public class WaterHose implements Runnable {
	/*
	 * Maximum quantity of water that can carry a OilShip.
	 */
	static final Integer maxWater = 5000;

	static final Integer maxTake = 1000;
	
	OilShip ship;
	
	public WaterHose (OilShip ship) {
		this.ship = ship;
	}
	
	public void run () {
		Platform platform = Platform.getInstance();
		
		while (ship.getWaterContainer() < maxWater) {
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
		ship.canExit.release();
	}
}
