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
        List<int[]> variants = getVariants(n);
        int sum = 0;
        for (int[] variant : variants) {
            sum += getNumber(variant);
        }
        return sum;
    }

    // 5: ["221", "32"] 4: ["22"]
    // n can be 15, therefore I can't save it as string, because it will be problematic to parse it
    static List<int[]> getVariants(int n) {
        List<int[]> list = new ArrayList<>();
        if (n<4) return list;

        int[] tmp;
        int[] first = {n - 2, 2};
        list.add(first);
        while ((tmp = magic(first, 0)) !=null) {
            list.add(tmp);


        }
        return list;
    }

    // int[13]
    static int[] magic(int[] arr, int i) {
        if (arr.length <= i) return null;
        if (arr[i]>1) {
            arr[i]--;
            arr[i+1]++;
            return arr;
        }
        return null;
    }

    static int getNumber(int[] variant) {
        // all numbers other than 1 to 2, 1 to 3
        // and multiply it on number of combinations
        return convert(variant) * getNumberOfCombinations(variant);
    }

    static int convert(int[] variant) {
        int converted = 1;
        for (int c : variant) {
            converted *= c == 1 ? 3 : 2;
        }
        return converted;
    }

    /**
     * Перемещение с повторением. (k1+k2+..+kn)!/(k1!k2!..kn!)
     */
    static int getNumberOfCombinations(int[] variant) { // TODO it can exceed
        int[] arr = new int[15];
        for (int value : variant) {
            arr[value]++;
        }
        int top = 0, bottom = 1;
        for (int i : arr) {
            if(i!=0) {
                top +=i;
                bottom *= factorial(i);
            }
        }
        return factorial(top)/bottom;
    }

    static int factorial(int n) {
        int result = 1;
        while (n>1) {
            result *= n--;
        }
        return result;
    }
}
