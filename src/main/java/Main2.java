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
//        Scanner in = new Scanner(System.in);
//        int k = in.nextInt();
        System.out.println(find(31));
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
            for (int i = 24; i < Integer.MAX_VALUE - 3; i+=12) {
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
            System.out.print("\""+i+","+findFast(i, 2)+"\",");
        }
        System.out.println("\n\n");
        System.out.println("set = " + set);
    }

    private static Set<Integer> set = new TreeSet<>();

    private static int findFast(int k, int timeout) {
        RunnableFuture<Integer> future = new FutureTask<>(() -> find(k));
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(future);
        try{
            return future.get(timeout, TimeUnit.SECONDS);    // wait 1 second
        }
        catch (TimeoutException | InterruptedException | ExecutionException ex){
            // timed out. Try to stop the code if possible.
            future.cancel(true); // Thread.interrupt
        }
        finally {
            service.shutdown();
        }
        return -1;
    }

    private static void sleep(int last) {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            System.out.println("last:"+last);
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
}