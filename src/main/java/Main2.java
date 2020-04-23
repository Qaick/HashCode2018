import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 * Все числа парные кроме единицы.
 * Есть критерий в том что все числа которым надо количество множителей больше 4-х должны делиться на 1 2 3 4
 */
public class Main2 {
    public static void main(String[] args) {
        printTestCSV();
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        System.out.println(find(k));
    }

    static int find(int k) {
        System.out.print(k + " :");
        if (k == 1)
            return 1;
        else {
            for (int i = 4; i < Integer.MAX_VALUE - 1; i+=2) {
                sleep();
//                System.out.println("      "+i);
                if (mult(i, k)) {
                    return i;
                }
            }
        }
        throw new RuntimeException();
    }

    static boolean mult(int suggestedN, int k) {
        int currentK = 2;
        int lim = (int) Math.sqrt(suggestedN);
        StringBuilder dividers = new StringBuilder(" 1");
        for (int i = 3; i <= lim; i++)
            if (suggestedN % i == 0) {
                currentK++;
                dividers.append(" ").append(i);
//                System.out.println(i+" * "+ n/i);
            }
        if (currentK == k)
            System.out.print(dividers);
        return currentK == k;
    }

    static void printTestCSV() {
        for (int i = 1; i <= 50; i++) {
            System.out.print("\""+i+","+findFast(i, 2)+"\",");
        }
    }

    static int findFast(int k, int timeout) {
        RunnableFuture<Integer> future = new FutureTask<>(() -> find(k));
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(future);
        try
        {
            return future.get(timeout, TimeUnit.SECONDS);    // wait 1 second
        }
        catch (TimeoutException | InterruptedException | ExecutionException ex)
        {
            // timed out. Try to stop the code if possible.
            future.cancel(true); // Thread.interrupt
        }
        finally {
            service.shutdown();
        }
        return -1;
    }

    static void sleep() {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}