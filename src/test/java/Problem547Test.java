import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO для middle не покрыты ситуации по кругу а только с одной стороны.
 * TODO middle 2 left right is not covered
 * BUT all simple tests pass. So there is no reason to write additional tests for the code.
 * 24/27
 */
public class Problem547Test {

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

        arr = new int[][]{
                {1, -1, 1},
                {-1, 3, -1},
                {1, -1, -1}};
        solve = Problem547.solve(arr);
        assertEquals("\\/\n//\n", solve);
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

        arr = new int[][]{
                {-1, -1, 0},
                {1, 2, -1},
                {-1, -1, -1}};
        assertEquals(".\\\n.\\\n", Problem547.solve(arr));
    }

    @Test
    void test_advanced_doublethink_1() {
        int[][] arr = {
                {-1, 1, -1},
                {-1, 1, -1},
                {-1, -1, -1}};
        assertEquals("..\n\\/\n", Problem547.solve(arr));

        arr = new int[][]{
                {-1, -1, -1},
                {-1, 1, 1},
                {-1, -1, -1}};
        assertEquals("/.\n\\.\n", Problem547.solve(arr));

        arr = new int[][]{
                {-1, -1, -1},
                {-1, 1, -1},
                {-1, 1, -1}};
        assertEquals("/\\\n..\n", Problem547.solve(arr));

        arr = new int[][]{
                {-1, -1, -1},
                {1, 1, -1},
                {-1, -1, -1}};
        assertEquals(".\\\n./\n", Problem547.solve(arr));
    }

    @Test
    void test_advanced_doublethink_diagonal_ones() {
        int[][] arr = {
                {-1, -1, -1, -1},
                {-1, 1, -1, -1},
                {-1, -1, 1, -1},
                {-1, -1, -1, -1}};
        assertEquals("...\n./.\n...\n", Problem547.solve(arr));

        arr = new int[][]{
                {-1, -1, -1, -1},
                {-1, -1, 1, -1},
                {-1, 1, -1, -1},
                {-1, -1, -1, -1}};
        assertEquals("...\n.\\.\n...\n", Problem547.solve(arr));
    }

    @Test
    void test_advanced_doublethink_1_3() {
        int[][] arr = {
                {-1, -1, -1},
                {1, 3, -1},
                {-1, -1, -1}};
        assertEquals("./\n.\\\n", Problem547.solve(arr));
    }

    @Test
    void test_advanced_doublethink_3_3() {
        int[][] arr;

        arr = new int[][]{
                {-1, -1, -1, -1},
                {-1, 3, -1, -1},
                {-1, 3, -1, -1},
                {-1, -1, -1, -1}};
        assertEquals("\\/.\n...\n/\\.\n", Problem547.solve(arr));

        arr = new int[][]{
                {-1, -1, -1, -1},
                {-1, 3, 3, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1}};
        assertEquals("\\./\n/.\\\n...\n", Problem547.solve(arr));
    }

    @Test
    void test_advanced_doublethink_1_1() {
        int[][] arr;

        arr = new int[][]{
                {-1, -1, -1, -1},
                {-1, 1, -1, -1},
                {-1, 1, -1, -1},
                {-1, -1, -1, -1}};
        assertEquals("/\\.\n...\n\\/.\n", Problem547.solve(arr));

        arr = new int[][]{
                {-1, -1, -1, -1},
                {-1, 1, 1, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1}};
        assertEquals("/.\\\n\\./\n...\n", Problem547.solve(arr));
    }

    @Test
    void test_real_1() {
        int[][] arr = {
                {-1, -1, -1},
                {-1, 2, 1},
                {-1, 1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("//\n//\n", solve);
    }

    @Test
    void test_real_3() {
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
    void test_real_example_easy() {
        // https://www.interactive.onlinemathlearning.com/fun_slant.php
        String[] arr = {
                "..0..1",
                "1131.1",
                "1.22.1",
                ".31..1",
                "12.31.",
                ".1...."};
/*
\\/\//\\
/\\\\/\/
/\//\///
\\\\\\/\
///\/\/\
////\\/\
//\\\\/\
\///\\\\
 */
        String solution = "\\\\/\\/\n" +
                "\\////\n" +
                "\\////\n" +
                "\\\\///\n" +
                "\\\\/\\/\n";
        assertEquals(solution, Problem547.parseAndSolve(arr));

        arr = new String[]{
                ".........",
                ".33.3.21.",
                "...2..1.1",
                ".13.1313.",
                "..231.2.1",
                ".2....2.1",
                "1.32.2.2.",
                ".11.3.311",
                "...1.1.1."};
/*
\\/\//\\
/\\\\/\/
/\//\///
\\\\\\/\
///\/\/\
////\\/\
//\\\\/\
\///\\\\
 */
        solution = "\\\\/\\//\\\\\n" +
                "/\\\\\\\\/\\/\n" +
                "/\\//\\///\n" +
                "\\\\\\\\\\\\/\\\n" +
                "///\\/\\/\\\n" +
                "////\\\\/\\\n" +
                "//\\\\\\\\/\\\n" +
                "\\///\\\\\\\\\n";
        assertEquals(solution, Problem547.parseAndSolve(arr));

    }

    @Test
    void test_real_example() {
        // https://puzzling.stackexchange.com/questions/97701/have-you-heard-gokigen-naname
        String[] arr = new String[]{
                "....0.1....",
                ".3.23.23.3.",
                "..1..2..1..",
                ".2.22.33.2.",
                "13.21.12.21",
                "..1.....2..",
                "21.12.13.31",
                ".3.32.22.2.",
                "..3..1..1..",
                ".1.32.23.3.",
                "....1.1...."};
/*
\/\\/\\/\/
////////\\
\\//\\\///
////\/\\\\
/\\\\///\\
/\///\\/\/
\\\//\////
/\\\\\//\\
//\\\/////
\//\\//\/\
 */
        String solution = "\\/\\\\/\\\\/\\/\n" +
                "////////\\\\\n" +
                "\\\\//\\\\\\///\n" +
                "////\\/\\\\\\\\\n" +
                "/\\\\\\\\///\\\\\n" +
                "/\\///\\\\/\\/\n" +
                "\\\\\\//\\////\n" +
                "/\\\\\\\\\\//\\\\\n" +
                "//\\\\\\/////\n" +
                "\\//\\\\//\\/\\\n";
        assertEquals(solution, Problem547.parseAndSolve(arr));
    }

    @Test
    void test_advanced() {
        // https://en.wikipedia.org/wiki/Gokigen_Naname
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

        assertTimeoutPreemptively(Duration.ofMillis(100), () -> assertEquals(solution, Problem547.parseAndSolve(arr)));
    }

    @Test
    void test_cycle_2points() {
        int[][] arr = {
                {-1, -1, -1, 0},
                {2, -1, 2, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1}};
        assertEquals("/.\\\n\\.\\\n...\n", Problem547.solve(arr));

        arr = new int[][]{
                {-1, -1, -1, -1},
                {2, -1, 2, -1},
                {-1, -1, -1, 0},
                {-1, -1, -1, -1}};
        assertEquals("/./\n\\./\n..\\\n", Problem547.solve(arr));

        arr = new int[][]{
                {-1, -1, -1, -1},
                {2, -1, 3, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1}};
        assertEquals("/./\n\\.\\\n...\n", Problem547.solve(arr));
    }
}