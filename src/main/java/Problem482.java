import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problem482 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int tmp;
        while ((tmp = in.nextInt()) != -1)
            System.out.println(ways(tmp));
    }

    // 0<=n<=30
    static int ways(int n) {
        if (n == 0) return 1;
        if (n % 2 == 1) return 0;
        n = n / 2; // n <=15
        return (int) (Math.pow(3, n) + comboPart(n) + comboPart2(n));
    }

    // when there is number and filling ones
    static int comboPart(int n) {
        if (n < 2) return 0;
        return (int) (2 * Math.pow(3, n - 2) * (n - 1) + comboPart(n - 1));
    }

    // all other variants
    // with slight modification should be able to handle work of comboPart
    static int comboPart2(int n) {
        if (n < 4) return 0;
        List<String> variants = getVariants(n);
        int sum = 0;
        for (String variant : variants) {
            sum += getNumber(variant);
        }
        return sum;
    }

    // 5: ["221", "32"] 4: ["22"]
    static List<String> getVariants(int n) {
        int tmp;
        List<String> list = new ArrayList<>();
        return list;
    }

    static int getNumber(String variant) {
        // all numbers other than 1 to 2, 1 to 3
        // and multiply it on number of combinations

        return convert(variant) * getNumberOfCombinations(variant);
    }

    static int convert(String variant) {
        int converted = 1;
        for (char c : variant.toCharArray()) {
            converted *= Integer.parseInt(c+"") == 1 ? 3 : 2;
        }
        return converted;
    }

    static int getNumberOfCombinations(String variant) {
        return Integer.parseInt(variant);
    }
}
