
import java.util.*;
import java.lang.*;
import java.io.*;

public class Problem18 {
    //			   4 6 8 10 12 12 14*14 14 16 18 16 18 18 18 16 18 18 20 20 20
    //			   1 2 3 4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21
    static int[] arr = {0, 4, 6, 8, 10, 12, 12, 14, 14, 14};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int bigger = in.nextInt(), lower = in.nextInt();
        if (lower > bigger) {
            int tmp = lower;
            lower = bigger;
            bigger = tmp;
        }
        lower--;
        System.out.println(paint2d(lower, bigger));
    }

    static int paint2d(int a, int b) {
        return recur(a) + recur(b);
    }

    static int recur(int n) {
        if (n < 10) return arr[n];
        int min = Integer.MAX_VALUE;
        int ni;
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0) {
                if ((ni = (recur(i - 1) + recur(n / i))) < min) min = ni;
            }
        if (min == Integer.MAX_VALUE) {
            return recur(n - 1) + 4;
        }
        return min;
    }
}