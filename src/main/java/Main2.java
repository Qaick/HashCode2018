import java.util.Scanner;

/**
 * Все числа парные кроме единицы.
 * Есть критерий в том что все числа которым надо количество множителей больше 4-х должны делиться на 1 2 3 4
 */
public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        find(n);
    }

    static void find(int n) throws InterruptedException {
        System.out.print(n + " :");
        if (n == 1)
            System.out.println(1);
        else {
            for (int i = 4; i < Integer.MAX_VALUE - 1; i+=2) {
                Thread.sleep(0);
//                System.out.println("      "+i);
                if (mult(i, n)) {
                    System.out.println("\n"+i);
                    break;
                }
            }
        }
    }

    static boolean mult(int n, int find) {
        int ans = 1;
        int lim = (int) Math.sqrt(n);
        StringBuilder s = new StringBuilder(" 1");
        for (int i = 2; i <= lim; i++)
            if (n % i == 0) {
                ans++;
                s.append(" ").append(i);
//                System.out.println(i+" * "+ n/i);
            }
        if (ans == find)
            System.out.print(s);
        return ans == find;
    }
}