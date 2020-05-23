import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problem21 {
    static void m3() {
        // when I have 3 numbers it's better to sum 2 smaller and then the bigger one
        double a = 1, b = 110, c = 120, p = 0.99;

        System.out.println(((a + b) * p + c) * p);
        System.out.println(((a + c) * p + b) * p);
        System.out.println(((c + b) * p + a) * p);
    }
    static void m4() {
        // when I have 4 numbers:
        double a = 10, b = 11, c = 120, d=130, p = 0.9;

        System.out.println(((a + b) * p + (c + d) * p) * p);
        System.out.println(((a + c) * p + (b + d) * p) * p);
        System.out.println(((a + d) * p + (b + c) * p) * p);

        System.out.println(((((a + b) * p + c) * p) + d) * p);
        System.out.println(((((a + b) * p + d) * p) + c) * p);
        System.out.println(((((a + c) * p + b) * p) + d) * p);
        System.out.println(((((a + c) * p + d) * p) + b) * p);
        System.out.println(((((a + d) * p + b) * p) + c) * p);
        System.out.println(((((a + d) * p + c) * p) + b) * p);

        System.out.println(((((c + d) * p + a) * p) + b) * p);


    }


    /**
     * Я могу добавить примеров в комментариях к задаче у которой сложность 93%.
     */
    public static void main(String[] args) throws Exception {
        m4();

        if (true) return;

        final BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        String[] first = bufferedReader.readLine().split("\\s");
        int n = Integer.parseInt(first[0]);
        int percent = Integer.parseInt(first[1]);
        String[] second = bufferedReader.readLine().split("\\s");
        List<Double> accounts = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            accounts.add((double) Integer.parseInt(second[i]));
        }
        String answer = String.format("%.2f", calcMoney(accounts, percent));
        System.out.println(answer);
    }

    public static double calcMoney(List<Double> _accounts, int percent) { // I can change accounts list
        List<Double> accounts = new ArrayList<>(_accounts);
        // Algorithm should be simple. Add 2 smallest numbers in the list, continue.
        final double percentMultiplier = (100.0 - percent) / 100; // stays after removing
        while(accounts.size() > 1) {
            accounts.sort(Double::compareTo);
            double sum = (accounts.get(0) + accounts.get(1)) * percentMultiplier;
            accounts.set(0, sum);
            accounts.remove(1);
        }
        return accounts.get(0);
    }
}
