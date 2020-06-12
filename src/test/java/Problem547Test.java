import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO для middle не покрыты ситуации по кругу а только с одной стороны.
 * TODO middle 2 left right is not covered
 * BUT all simple tests pass. So there is no reason to write additional tests for the code.
 * 22/27
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
    void test_circle_1x1() {
        int[][] arr = {
                {-1, -1, 0},
                {-1, -1, -1},
                {0, -1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("\\\\\n\\/\n", solve);

        arr = new int[][]{
                {0, -1, -1},
                {-1, -1, -1},
                {0, -1, 0}};
        solve = Problem547.solve(arr);
        assertEquals("//\n\\/\n", solve);

        arr = new int[][]{
                {0, -1, 0},
                {-1, -1, -1},
                {0, -1, -1}};
        solve = Problem547.solve(arr);
        assertEquals("/\\\n\\\\\n", solve);

        arr = new int[][]{
                {0, -1, 0},
                {-1, -1, -1},
                {-1, -1, 0}};
        solve = Problem547.solve(arr);
        assertEquals("/\\\n//\n", solve);
    }

    @Test
    void test_circle_advanced() {
        // circles bigger than 1x1
        Problem547.loopsCounterForTests = 4;
        int[][] arr = {
                {0, -1, 0, -1},
                {-1, -1, -1, 0},
                {0, -1, -1, -1},
                {-1, 0, -1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("/\\/\n\\\\\\\n/\\\\\n", solve);
    }

    @Test
    void test_advanced_doublethink() {
        int[][] arr = {
                {-1, -1, 1},
                {1, 2, -1},
                {-1, -1, -1}};
        assertEquals("./\n./\n", Problem547.solve(arr));
    }

    @Test
    void test_advanced_doublethink_1() {
        int[][] arr = {
                {-1, -1, -1},
                {1, 1, -1},
                {-1, -1, -1}};
        assertEquals(".\\\n./\n", Problem547.solve(arr));
    }

    @Test
    void test_advanced_doublethink_2() {
        int[][] arr = {
                {-1, -1, -1, -1},
                {-1, -1, 1, -1},
                {-1, 1, -1, -1},
                {-1, -1, -1, -1}};
        assertEquals("...\n.\\.\n...\n", Problem547.solve(arr));
    }

    @Test
    void test_advanced_doublethink_3() {
        int[][] arr = {
                {-1, -1, -1},
                {1, 3, -1},
                {-1, -1, -1}};
        assertEquals("./\n.\\\n", Problem547.solve(arr));
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
        /*
\/\\\/\/\/
\/\///\\\/
///\\/\\\\
\\\\\/\\\\
\///\//\\\
\\///\/\/\
\\///\\\/\
\/\\//\/\\
\\\/\/////
////////\\
         */
        String solution =
                        "\\/\\\\\\/\\/\\/\n" +
                        "\\/\\///\\\\\\/\n" +
                        "///\\\\/\\\\\\\\\n" +
                        "\\\\\\\\\\/\\\\\\\\\n" +
                        "\\///\\//\\\\\\\n" +
                        "\\\\///\\/\\/\\\n" +
                        "\\\\///\\\\\\/\\\n" +
                        "\\/\\\\//\\/\\\\\n" +
                        "\\\\\\/\\/////\n" +
                        "////////\\\\\n";

        assertTimeoutPreemptively(Duration.ofMillis(100), () -> assertEquals(solution, Problem547.solve2(arr)));
    }
}