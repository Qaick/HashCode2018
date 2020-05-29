import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class Problem547Test {

    @Test
    void test_1() {
        int[][] arr = {{-1,-1,-1},{-1,2,1},{-1,1,0}};
        String solve = Problem547.solve(arr);
        assertEquals("", solve);
    }

    @Test
    void test_2() {
        int[][] arr = {
                {-1,2,1,-1,-1,-1},
                {-1,-1,3,3,-1,0},
                {-1,-1,-1,-1,-1,-1},
                {-1,-1,3,3,-1,-1},
                {0,-1,-1,3,3,-1},
                {-1,2,1,-1,1,1}};
        assertEquals("", Problem547.solve(arr));
    }
}
