import java.util.concurrent.CountDownLatch;

public class OilShip extends Ship {
	/*
	 * Maximum quantity of water that can carry a OilShip.
	 */
	static final Integer maxWater = 5000;

	/*
	 * Maximum quantity of oil that can carry a OilShip.
	 */
	static final Integer maxOil = 3000;

	/*
	 * Container of oil that is use for carry it.
	 */
	private Integer oilContainer = 0;

	/*
	 * Container of water that is use for carry it.
	 */
	private Integer waterContainer = 0;

	/*
	 * Platform where the OilShip is going to work.
	 */
	private int oilPlatform;

	CountDownLatch controlPasar = new CountDownLatch(5);

	/*
	 * OilShip parameterized constructor
	 * 
	 * @param id
	 * 
	 * @param oilPlatform
	 */
	public OilShip(int id, int oilPlatform) {
		super(1, id);
		this.oilPlatform = oilPlatform;
	}

	/*
	 * Getter of the variable Id.
	 * 
	 * @see Ship#getId()
	 */
	public int getId() {
		return super.getId();
	}

	/*
	 * Getter of the variable OilPlatform.
	 */
	public int getOilPlatform() {
		return oilPlatform;
	}

	/*
	 * Getter of the variable MaxWater.
	 */
	public Integer getMaxWater() {
		return maxWater;
	}

	/*
	 * Getter of the variable MaxOil.
	 */
	public Integer getMaxOil() {
		return maxOil;
	}

	/*
	 * Getter of the variable OilContainer.
	 */
	public Integer getOilContainer() {
		return oilContainer;
	}

	/*
	 * Setter of the variable OilContainer.
	 */
	public void setOilContainer(Integer amount) {
		oilContainer = amount;
	}

	/*
	 * Getter of the variable WaterContainer.
	 */
	public Integer getWaterContainer() {
		return waterContainer;
	}

	/*
	 * Setter of the variable WaterContainer.
	 */
	public void setWaterContainer(Integer amount) {
		waterContainer = amount;
	}

	/*
	 * (non-Javadoc) TODO
	 * 
	 * @see Ship#run()
	 */
	public void run() {

		Platform platform = Platform.getInstance();
		Server server = new Server();
		OilHose oilHose = new OilHose(this);
		WaterHose waterHose = new WaterHose(this);

		super.run();

		try {
			controlPasar.countDown();
			controlPasar.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (getOilContainer() < maxOil) {
			server.executeOilFill(oilHose);
		}

		while (getWaterContainer() < maxWater) {
			server.executeWaterFill(waterHose);
		}

		server.endServer();

		this.setDirection(2);
		super.run();
	}
}
