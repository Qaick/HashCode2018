import java.util.Scanner;

public class CashFlow {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while(in.hasNext()) {
			int price = in.nextInt();
			float flow = in.nextInt(); // per month
			System.out.println("percentage = " + 100.0 * flow / price + " " + 100.0 * flow / price * 12);
		}
	}
}
