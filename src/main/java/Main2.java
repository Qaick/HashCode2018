import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        find(n);
    }

    static void find(int n) throws InterruptedException {
        if (n == 1)
            System.out.println(1);
        else {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                Thread.sleep(0);
//                System.out.println("      "+i);
                if (mult(i, n)) {
                    System.out.println(i);
                    break;
                }
            }
        }
    }

    static boolean mult(int n, int find) {
        int ans = 1;
        int lim = (int) Math.sqrt(n);
        for (int i = 2; i <= lim; i++)
            if (n % i == 0) {
                ans++;
//                System.out.println(i+" * "+ n/i);
            }
        return ans == find;
    }
}