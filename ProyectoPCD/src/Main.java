import java.util.ArrayList;

public class Main {

	public static int tam = 10;

	public static void main(String[] args) {

		Ship ships[] = new Ship[tam];
		OilShip oilShips[] = new OilShip[5];
		Crane cranes[] = new Crane[3];

		for (int i = 0; i < ships.length; i++) {
			ships[i] = new Ship((i % 2) + 1, i + 1);
		}
		Merchant merchant = new Merchant(11);

		ArrayList<product> prods = new ArrayList<product>();

		cranes[0] = new Crane(product.sugar);
		cranes[1] = new Crane(product.flour);
		cranes[2] = new Crane(product.salt);

		for (int j = 0; j < oilShips.length; j++) {
			oilShips[j] = new OilShip(j + 15, j);
		}

		for (int i = 0; i < 5; i++) {
			prods.add(product.sugar);
		}
		for (int i = 0; i < 10; i++) {
			prods.add(product.flour);
		}
		for (int i = 0; i < 2; i++) {
			prods.add(product.salt);
		}

		merchant.addProducts(prods);

		Thread threads[] = new Thread[tam + 4 + oilShips.length];

		for (int i = 0; i < ships.length; i++) {
			threads[i] = new Thread(ships[i]);
		}
		threads[ships.length] = new Thread(merchant);

		threads[ships.length + 1] = new Thread(cranes[0]);
		threads[ships.length + 2] = new Thread(cranes[1]);
		threads[ships.length + 3] = new Thread(cranes[2]);

		for (int j = 14; j < threads.length; j++) {
			threads[j] = new Thread(oilShips[j - 14]);
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
	}
}
