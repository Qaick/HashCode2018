import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class Problem547Test {

    @Test
    void test_1(int a, int b, int c, int expected) {
        int[][] arr = {{-1,-1,-1},{-1,2,1},{-1,1,0}};
        assertEquals(expected, Problem547.meth(new int[1][0]));
    }

    @Test
    void test_2(int a, int b, int c, int expected) {
        int[][] arr = {
                {-1,2,1,-1,-1,-1},
                {-1,-1,3,3,-1,0},
                {-1,-1,-1,-1,-1,-1},
                {-1,-1,3,3,-1,-1},
                {0,-1,-1,3,3,-1},
                {-1,2,1,-1,1,1}};
        assertEquals(expected, Problem547.meth(new int[1][0]));
    }
}
