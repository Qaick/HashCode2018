import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Все числа парные кроме единицы.
 * Есть критерий в том что все числа которым надо количество множителей больше 4-х должны делиться на 1 2 3 4
 *
 * Два сомножителя
 * Какое наименьшее число n можно представить в виде произведения n = a ∙ b ровно k (1 ≤ k ≤ 50) способами? Произведения a ∙ b и b ∙ a считаются одним способом, все числа натуральные.
 *
 * 1->1
 * 2->4
 */
public class Problem5 {

    @ParameterizedTest
    @CsvSource({"1,1", "2,4", "3,12", "4,24", "5,36", "6,60", "7,192", "8,120", "9,180", "10,240", "11,576", "12,360", "13,1296", "14,900", "15,720", "16,840", "17,9216", "18,1260", "19,786432", "20,1680", "21,2880", "22,15360", "23,3600", "24,2520", "25,6480", "26,61440", "27,6300", "28,6720", "29,2359296", "30,5040", "31,3221225472", "32,7560", "33,46080", "34,983040", "35,25920", "36,10080", "37,206158430208", "38,32400", "39,184320", "40,15120", "41,44100", "42,20160", "43,5308416", "44,107520", "45,25200", "46,2985984", "47,9663676416", "48,27720", "49,233280", "50,45360"})
    void test_values_duration(int k, long exp) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(exp, Problem5.findFastest(k)));
    }

    public static void main(String[] args) {
//        printTestCSV();
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        System.out.println(findFastest(k));
    }

    static int find(int k) {
        log(k + " :");
        if (k == 1)
            return 1;
        else if (k == 2)
            return 4;
        else if (k == 3)
            return 12;
        else {
            for (int i = 24; i < Integer.MAX_VALUE - 3; i += 12) {
                sleep(i);
//                System.out.print(i+":");
                if (suggest(i, k)) {
                    return i;
                }
            }
        }
        throw new RuntimeException();
    }

    private static boolean suggest(int suggestedN, int k) {
        int currentK = 4;
        int lim = (int) Math.sqrt(suggestedN);
        StringBuilder dividers = new StringBuilder(" 1 2 3 4");
        Set<Integer> subset = new TreeSet();
        for (int i = 5; i <= lim; i++)

            if (suggestedN % i == 0) {
                subset.add(i);
                currentK++;
                dividers.append(" ").append(i);
//                System.out.println(i+" * "+ n/i);
            }
        if (currentK == k) {
            set.addAll(subset);
            log(dividers);
        }
//        System.out.println(" "+currentK);
        return currentK == k;
    }

    private static void printTestCSV() {
        for (int i = 1; i <= 50; i++) {
            System.out.print("\"" + i + "," + findInterruptible(i, 2) + "\",");
        }
        System.out.println("\n\n");
        System.out.println("set = " + set);
    }

    private static Set<Integer> set = new TreeSet<>();

    private static int findInterruptible(int k, int timeout) {
        RunnableFuture<Integer> future = new FutureTask<>(() -> find(k));
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(future);
        try {
            return future.get(timeout, TimeUnit.SECONDS);    // wait 1 second
        } catch (TimeoutException | InterruptedException | ExecutionException ex) {
            // timed out. Try to stop the code if possible.
            future.cancel(true); // Thread.interrupt
        } finally {
            service.shutdown();
        }
        return -1;
    }

    private static void sleep(int last) {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            System.out.println("last:" + last);
            throw new RuntimeException(e);
        }
    }

    /**
     * Я могу использовать этот метод для логирования определенных вещей и тогда комментировать становиться проще.
     * k : dividers
     */
    private static void log(Object s) {
        System.out.print(s);
    }

    /**
     * @see src/main/resources/number with minimum dividers.PNG
     */
    static long findFastest(int k) {
        List<Long> numbers = numbersWDividers(k);
        long min = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            min = Math.min(numbers.get(i), min);
        }
        return min;
    }

    /**
     * Returns number representation of multiplication prime numbers.
     */
    private static List<Integer> factorize(int x) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 2; i <= Math.sqrt(x); i++) { // might be problems with rounding of sqrt
            while (x % i == 0) {
                factors.add(0, i);
                x /= i;
            }
        }
        if (x != 1) {
            factors.add(0, x);
        }
        return factors;
    }

    private static final int[] PRIMES = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61,
            67, 71, 73, 79, 83, 89, 97 };

    private static Map<Integer, Integer> getCalculable(List<Integer> numbers) {
        Map<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < numbers.size(); i++) {
            result.put(PRIMES[i], numbers.get(i) - 1);
        }
        return result;
    }

    private static List<Long> numbersWDividers(int k) {
        List<Long> longs = new ArrayList<>();
        longs.addAll(numberWithDividers(k * 2 - 1));
        longs.addAll(numberWithDividers(k * 2));
        return longs;
    }

    private static List<Long> numberWithDividers(int k) {
        List<List<Integer>> allFactors = getFactorsList(factorize(k));
        List<Long> results = new ArrayList<>();
        for (List<Integer> factor : allFactors) {
            Map<Integer, Integer> calculable = getCalculable(factor);
            long result = 1;
            for (Map.Entry<Integer, Integer> entry : calculable.entrySet()) {
                result *= Math.pow(entry.getKey(), entry.getValue());
            }
            results.add(result);
        }
        return results;
    }

    private static List<List<Integer>> getFactorsList(List<Integer> factors) {
        ArrayList<List<Integer>> lists = new ArrayList<>();
        lists.add(factors);

        addFactors(factors, lists);
        return lists;
    }

    private static void addFactors(List<Integer> factors, ArrayList<List<Integer>> lists) {
        if (factors.size() > 2 && new HashSet<>(factors).size() < factors.size()) {
            {
                List<Integer> newFactors = new ArrayList<>(factors);
                for (int i = newFactors.size() - 1; i >= 1; i--) {
                    if (newFactors.get(i - 1) == newFactors.get(i)) {
                        newFactors.set(i - 1, newFactors.get(i - 1) * newFactors.get(i - 1));
                        newFactors.remove(i);
                        break;
                    }
                }
                newFactors.sort((a, b) -> -Integer.compare(a, b));
                lists.add(newFactors);
                addFactors(newFactors, lists);
            }
            second: {
                List<Integer> newFactors = new ArrayList<>(factors);
                boolean changed = false;
                for (int i = newFactors.size() - 1; i >= 1; i--) {
                    if (newFactors.get(i - 1) != newFactors.get(i)) {
                        newFactors.set(i - 1, newFactors.get(i - 1) * newFactors.get(i));
                        newFactors.remove(i);
                        changed = true;
                        break;
                    }
                }
                if (!changed) break second;
                newFactors.sort((a, b) -> -Integer.compare(a, b));
                lists.add(newFactors);
            }
        }
    }
}
