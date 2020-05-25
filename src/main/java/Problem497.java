import java.util.Scanner;

public class Problem497 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int n2 = in.nextInt();
            int start = in.nextInt(), end = in.nextInt();
            for (int j = 1; j < n2; j++) {
                start = Math.max(start, in.nextInt());
                end = Math.min(end, in.nextInt());
            }
            System.out.println(start <= end ? "YES" : "NO");
        }
    }
    static int meth() {
        return 0;
    }
}
