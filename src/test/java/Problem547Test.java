import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO для middle не покрыты ситуации по кругу а только с одной стороны.
 * TODO middle 2 is not covered
 */
public class Problem547Test {

    @BeforeEach
    void setOneLoop() {
        Problem547.loopsCounterForTests = 1;
    }

    @Test
    void test_corners_0() { // O
        int[][] arr = {
                {1, -1, 0},
                {-1, -1, -1},
                {0, -1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("\\\\\n\\/\n", solve);
    }

    @Test
    void test_corners_1() { // X
        int[][] arr = {
                {1, -1, 1},
                {-1, -1, -1},
                {1, -1, 1}};
        String solve = Problem547.solve(arr);
        assertEquals("\\/\n/\\\n", solve);
    }

    @Test
    void test_sides_top_0_idx1() { // \/.
        int[][] arr = {
                {-1, 0, -1,-1},
                {-1, -1, -1,-1},
                {-1, -1, -1,-1},
                {-1, -1, -1,-1}};
        String solve = Problem547.solve(arr);
        assertEquals("\\/.\n...\n...\n", solve);
    }

    @Test
    void test_sides_top_0_idx2() { // .\/
        int[][] arr = {
                {-1, -1, 0,-1},
                {-1, -1, -1,-1},
                {-1, -1, -1,-1},
                {-1, -1, -1,-1}};
        String solve = Problem547.solve(arr);
        assertEquals(".\\/\n...\n...\n", solve);
    }

    @Test
    void test_sides_0() { // X
        int[][] arr = {
                {-1, 0, -1},
                {0, -1, 0},
                {-1, 0, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("\\/\n/\\\n", solve);
    }

    @Test
    void test_sides_1_topBot_1() {
        int[][] arr = {
                {1, 1, -1},
                {-1, -1, -1},
                {-1, 1, 1}};
        String solve = Problem547.solve(arr);
        assertEquals("\\\\\n\\\\\n", solve);
    }

    @Test
    void test_sides_1_topBot_2() {
        int[][] arr = {
                {-1, 1, 1},
                {-1, -1, -1},
                {1, 1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("//\n//\n", solve);
    }

    @Test
    void test_sides_1_rightLeft_1() {
        int[][] arr = {
                {-1, -1, 1},
                {1, -1, 1},
                {1, -1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("//\n//\n", solve);
    }

    @Test
    void test_sides_1_rightLeft_2() {
        int[][] arr = {
                {1, -1, -1},
                {1, -1, 1},
                {-1, -1, 1}};
        String solve = Problem547.solve(arr);
        assertEquals("\\\\\n\\\\\n", solve);
    }

    @Test
    void test_sides_2() { // O
        int[][] arr = {
                {-1, 2, -1},
                {-1, -1, 2},
                {-1, -1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("/\\\n//\n", solve);
    }

    @Test
    void test_sides_2_2() { // O
        int[][] arr = {
                {-1, -1, -1},
                {2, -1, -1},
                {-1, 2, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("//\n\\/\n", solve);
    }

    @Test
    void test_middle_4() {
        int[][] arr = {
                {-1, -1, -1},
                {-1, 4, -1},
                {-1, -1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("\\/\n/\\\n", solve);
    }

    @Test
    void test_middle_3_0() {
        int[][] arr = {
                {0, -1, -1},
                {-1, 3, -1},
                {-1, -1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("//\n/\\\n", solve);
    }

    @Test
    void test_middle_2_top() {
        int[][] arr = {
                {1, -1, 1},
                {-1, 2, -1},
                {-1, -1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("\\/\n\\/\n", solve);
        arr = new int[][]{
                {0, -1, 0},
                {-1, 2, -1},
                {-1, -1, -1}};
        solve = Problem547.solve(arr);
    }

    @Test
    void test_middle_2_bot() {
        int[][] arr = {
                {-1, -1, -1},
                {-1, 2, -1},
                {0, -1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("\\/\n\\/\n", solve);
        arr = new int[][]{
                {-1, -1, -1},
                {-1, 2, -1},
                {1, -1, 1}};
        solve = Problem547.solve(arr);
        assertEquals("/\\\n/\\\n", solve);
    }

    @Test
    void test_middle_3_3() {
        int[][] arr = {
                {-1, -1, 1},
                {-1, 3, -1},
                {1, -1, 1}};
        String solve = Problem547.solve(arr);
        assertEquals("//\n/\\\n", solve);
    }

    @Test
    void test_middle_1_0() {
        int[][] arr = {
                {0, -1, 0},
                {-1, 1, -1},
                {0, -1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("/\\\n\\\\\n", solve);
    }

    @Test
    void test_middle_1_3() {
        int[][] arr = {
                {-1, -1, 1},
                {-1, 1, -1},
                {1, -1, 1}};
        String solve = Problem547.solve(arr);
        assertEquals("/\\\n\\\\\n", solve);
    }

    @Test
    void test_circle_tl() {
        int[][] arr = {
                {-1, -1, 0},
                {-1, -1, -1},
                {0, -1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("\\\\\n\\/\n", solve);
    }

    @Test
    void test_circle_tr() {
        int[][] arr = {
                {0, -1, -1},
                {-1, -1, -1},
                {0, -1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("//\n\\/\n", solve);
    }

    @Test
    void test_circle_br() {
        int[][] arr = {
                {0, -1, 0},
                {-1, -1, -1},
                {0, -1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("/\\\n\\\\\n", solve);
    }

    @Test
    void test_circle_bl() {
        int[][] arr = {
                {0, -1, 0},
                {-1, -1, -1},
                {-1, -1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("/\\\n//\n", solve);
    }

    @Test
    void test_circle_advanced() {
        // circles bigger than 1x1
        fail("TBD");
    }

    @Test
    void test_1() {
        Problem547.loopsCounterForTests = -1;
        int[][] arr = {
                {-1, -1, -1},
                {-1, 2, 1},
                {-1, 1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("//\n//\n", solve);
    }

    @Test
    void test_3() {
        Problem547.loopsCounterForTests = -1;
        int[][] arr = {
                {-1, 2, 1, -1, -1, -1},
                {-1, -1, 3, 3, -1, 0},
                {-1, -1, -1, -1, -1, -1},
                {-1, -1, 3, 3, -1, -1},
                {0, -1, -1, 3, 3, -1},
                {-1, 2, 1, -1, 1, 1}};
        assertEquals("/\\\\//\n//\\\\\\\n\\\\\\//\n\\/\\\\/\n///\\\\\n", Problem547.solve(arr));
    }

    @Test
    void test_advanced() {
        // https://en.wikipedia.org/wiki/Gokigen_Naname
        Problem547.loopsCounterForTests = -1;
        String[] arr = {
                ".0...02....",
                "..21.3.31.1",
                "0.1421..23.",
                "..2..222.2.",
                "....3..3221",
                ".3.....2.3.",
                "1.2..23..2.",
                "..4.11.1.11",
                ".3..4......",
                "0223..22320",
                "....1..1..."};
        String[] solution = {};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < solution.length; i++) {
            sb.append(solution).append('\n');
        }

        assertTimeoutPreemptively(Duration.ofMillis(100), () -> assertEquals(sb.toString(), Problem547.solve2(arr)));
    }
}