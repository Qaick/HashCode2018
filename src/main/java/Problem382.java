import java.util.Scanner;

public class Problem382 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int all = in.nextInt(), first = in.nextInt(), added=in.nextInt();
        System.out.println(pears(all, first, added));
    }
    static int pears(int left, int eating, int add) {
        if (eating >= left) return left;
        return pears(left - eating, eating + add, add);
    }
}
