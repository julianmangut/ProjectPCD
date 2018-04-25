import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
	
	private ThreadPoolExecutor executor;
	
	public Server () {
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
	}

	public void executeTask (OilHose oilHose, WaterHose waterHose) {
		executor.execute(oilHose);
		executor.execute(waterHose);
	}
	
	public void endServer () {
		executor.shutdown();
	}
	
}
