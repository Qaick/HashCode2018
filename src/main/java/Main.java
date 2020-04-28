import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        findMatching(n);
    }

    static void findMatching(int n) throws InterruptedException {
    }

    @Test
    public void testBitwiseOr() {
        Assertions.assertEquals(true, true | true);
        Assertions.assertEquals(true, true | false);
        Assertions.assertEquals(false, false | false);
        boolean b = false;
        b |= false;
        Assertions.assertEquals(false, b);
        b |= true;
        Assertions.assertEquals(true, b);
        b |= false;
        Assertions.assertEquals(true, b);
    }

}