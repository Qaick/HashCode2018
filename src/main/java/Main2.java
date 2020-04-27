import java.util.ArrayList;
import java.util.HashMap;
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

/**
 * Все числа парные кроме единицы.
 * Есть критерий в том что все числа которым надо количество множителей больше 4-х должны делиться на 1 2 3 4
 */
public class Main2 {

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
        // TODO I would like to see the set of all the dividers and maybe I could fill up the gaps or find a pattern
        for (int i = 5; i <= lim; i++) // TODO it looks like here I have only combination of some numbers, a few primes
            // TODO right now I don't think it will be resultative
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
        // TODO use prime numbers instead of i++
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

    private static final int[] PRIMES;

    static {
        // primes before 100
        PRIMES = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
    }

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
        longs.addAll(numberWithDividers(k*2));
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
        return lists;
    }

    private static boolean isPrime(int n) {
        return false;
    }
}