import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        findMatching(n);
    }

    static void findMatching(int n) throws InterruptedException {
        if (n == 1)
            System.out.println(1);
        else {
            for (int i = 4; i < Integer.MAX_VALUE; i++) {
                Thread.sleep(0);
                System.out.println("he");
                int ans = 1;
                int lim = (int) Math.sqrt(n);
                for (int j = 2; j <= lim; j++) {
                    if (n % j == 0) {
                        ans++;
                    }
                }
                if (ans == n) {
                    System.out.println(i);
                    break;
                }
            }
        }
    }

}