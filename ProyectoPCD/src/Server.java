import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
	
	private ThreadPoolExecutor executor;
	
	public Server () {
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
	}

	public void executeOilFill (OilHose oilHose) {
		executor.execute(oilHose);
	}
	
	public void executeWaterFill (WaterHose waterHose) {
		executor.execute(waterHose);
	}
	
	public void endServer () {
		executor.shutdown();
	}
	
}
