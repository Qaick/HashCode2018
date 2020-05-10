import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Problem21Test {
    List<Double> list(int... ints) {
        List<Double> list = new ArrayList<>();
        for (int anInt : ints) {
            list.add((double) anInt);
        }
        return list;
    }

    @Test
    void name() {
        List<Double> accounts = new ArrayList<>(Arrays.asList(1000.0, 1100.0, 1200.0, 1300.0));
        assertEquals(4151.50, Problem21.calcMoney(accounts, 5));
    }

    @Test
    void name2() {
        List<Double> accounts = list(10, 30, 60, 100);
        assertEquals(183.445, Problem21.calcMoney(accounts, 5));
    }
    @Test
    void name3() {
        List<Double> accounts = list(10, 20, 30);
        assertEquals(60, Problem21.calcMoney(accounts, 0));
        assertEquals(43.2, Problem21.calcMoney(accounts, 20));
    }
    @Test
    void name4() {
        List<Double> accounts = list(10, 11, 12);
        assertEquals(23.040000000000003, Problem21.calcMoney(accounts, 20));
    }
    @Test
    void name5() {
        List<Double> accounts = list(0,0);
        assertEquals(0, Problem21.calcMoney(accounts, 20));

        accounts = list(0,0,100);
        assertEquals(80, Problem21.calcMoney(accounts, 20));

        accounts = list(0,100,100);
        assertEquals(171, Problem21.calcMoney(accounts, 10));
    }
}