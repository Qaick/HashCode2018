import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO для middle не покрыты ситуации по кругу а только с одной стороны.
 * TODO middle 2 left right is not covered
 * BUT all simple tests pass. So there is no reason to write additional tests for the code.
 * 32/35
 * TODO I need validation. Validation should contain 2 checks: one for loops, one for numbers.
 */
public class Problem547Test {

    @Test
    void testFileLength() throws Exception {
        int requiredSize = 16384;
        URL resource = Problem547.class.getResource("");
        Path path = Paths.get(resource.toURI()).getParent().resolveSibling("src/main/java/Problem547.java");
        List<String> strings = Files.readAllLines(path);

        int len = 0;
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            String s = string.replaceAll("\t", "");
            sb.append(s).append("\n");
            len += s.length() + 1;
        }

        assertTrue(len <= requiredSize, "value: "+ len+ " " + requiredSize);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(sb.toString()), null);
    }

    @Test
    void test_hasLoops() {
        char[][] sol = {
                {'/', '\\', '.'},
                {'\\', '/', '.'},
                {'.', '.', '.'}};

        Problem547.sn = sol.length;
        Problem547.pn = sol.length +1;
        int pn = sol.length +1;
        Problem547.groups = new int[pn * pn];
        Problem547.sol = sol;
        assertTrue(Problem547.hasLoops());
    }
    @Test
    void test_hasLoops2() {
        char[][] sol = new char[][]{
                {'/', '\\', '.'},
                {'\\', '\\', '.'},
                {'.', '.', '.'}};

        Problem547.sn = sol.length;
        Problem547.pn = sol.length + 1;
        int pn = sol.length + 1;
        Problem547.groups = new int[pn * pn];
        Problem547.sol = sol;
        assertFalse(Problem547.hasLoops());
    }
    @Test
    void test_validation_numbers() {

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
    void test_real_example_2() {
        String arr = "...11..2.\n" +
                "1.13.1..1\n" +
                "12..2.23.\n" +
                ".2.22.2.1\n" +
                "11.3.2.31\n" +
                "1.2.12.1.\n" +
                ".33.3..21\n" +
                "2..2.21.2\n" +
                ".1..11...";
/*
...11..2.
1.13.1..1
12..2.23.
.2.22.2.1
11.3.2.31
1.2.12.1.
.33.3..21
2..2.21.2
.1..11...
\////\/\
\\/\\\\\
\\\\\//\
\\\\\\\\
\//\\\/\
\//\//\\
//\\\\\\
\\\\\\//
 */
        String solution = "\\////\\/\\\n" +
                "\\\\/\\\\\\\\\\\n" +
                "\\\\\\\\\\//\\\n" +
                "\\\\\\\\\\\\\\\\\n" +
                "\\//\\\\\\/\\\n" +
                "\\//\\//\\\\\n" +
                "//\\\\\\\\\\\\\n" +
                "\\\\\\\\\\\\//\n";
        assertEquals(solution, Problem547.parseAndSolve(arr.split("\n")));
    }

    @Test
    void test_real_example_3() {
        String arr = "1.2.2.21.101.\n" +
                "1......32...1\n" +
                ".2.2...2.21..\n" +
                ".211..4...4.1\n" +
                "..2.1..3.1..1\n" +
                "11.22...22.2.\n" +
                ".132..32..2.1\n" +
                "....2.1.22.2.\n" +
                ".2...32....1.\n" +
                "2.3.3..32.2..\n" +
                ".1.3.2...233.\n" +
                "..1...2.2.3..\n" +
                "1..21..1.....";
/*
1.2.2.21.101.
1......32...1
.2.2...2.21..
.211..4...4.1
..2.1..3.1..1
11.22...22.2.
.132..32..2.1
....2.1.22.2.
.2...32....1.
2.3.3..32.2..
.1.3.2...233.
..1...2.2.3..
1..21..1.....
\/\/\/\\\\//
\\\\\//\\\\/
//\\\\/\\\/\
\\\/\/\\//\\
/\\\\//\\//\
/////\\\\//\
\/\\//\\\//\
/\\\///\\//\
/\///\\\\\//
\\\/\\/\\\//
\/\\\\/////\
/\\//\////\\
 */
        String solution = "\\/\\/\\/\\\\\\\\//\n" +
                "\\\\\\\\\\//\\\\\\\\/\n" +
                "//\\\\\\\\/\\\\\\/\\\n" +
                "\\\\\\/\\/\\\\//\\\\\n" +
                "/\\\\\\\\//\\\\//\\\n" +
                "/////\\\\\\\\//\\\n" +
                "\\/\\\\//\\\\\\//\\\n" +
                "/\\\\\\///\\\\//\\\n" +
                "/\\///\\\\\\\\\\//\n" +
                "\\\\\\/\\\\/\\\\\\//\n" +
                "\\/\\\\\\\\/////\\\n" +
                "/\\\\//\\////\\\\\n";
        assertEquals(solution, Problem547.parseAndSolve(arr.split("\n")));
    }

    @Test
    void test_real_example_4() {
        String arr = ".11......1...\n" +
                ".1.3..23...3.\n" +
                ".22.2..1.1.1.\n" +
                "..23..2.1131.\n" +
                ".221.13113.2.\n" +
                "..22..1......\n" +
                ".22.21.31..11\n" +
                "1.13..322..1.\n" +
                ".1...23.1....\n" +
                "1.11...321.2.\n" +
                "..2.2.....2.2\n" +
                "1222222222221\n" +
                "1111111111110";
/*
.11......1...
.1.3..23...3.
.22.2..1.1.1.
..23..2.1131.
.221.13113.2.
..22..1......
.22.21.31..11
1.13..322..1.
.1...23.1....
1.11...321.2.
..1.1.....1.1
1222222222221
1111111111110
\\\//\\/////
\////////\/\
\////\\/\\\\
///\///\\/\/
\\\\\/\\//\/
/////\\/\//\
\\\\\\////\\
\\/\\////\\/
\/\\\/\\/\/\
\\\////\///\
////////////
////////////
 */
        String solution = "\\\\\\//\\\\/////\n" +
                "\\////////\\/\\\n" +
                "\\////\\\\/\\\\\\\\\n" +
                "///\\///\\\\/\\/\n" +
                "\\\\\\\\\\/\\\\//\\/\n" +
                "/////\\\\/\\//\\\n" +
                "\\\\\\\\\\\\////\\\\\n" +
                "\\\\/\\\\////\\\\/\n" +
                "\\/\\\\\\/\\\\/\\/\\\n" +
                "\\\\\\////\\///\\\n" +
                "////////////\n" +
                "////////////\n";
        assertEquals(solution, Problem547.parseAndSolve(arr.split("\n")));
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
