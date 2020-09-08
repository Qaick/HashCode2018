import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class Problem3524Test {

    @Test
    void test_1() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> assertEquals("527389416819426735436751829375692184194538267268174593643217958951843672782965341",
                Problem3524.solveSudoku(".2738..1..1...6735.......293.5692.8...........6.1745.364.......9518...7..8..6534.")));
    }
    @Test
    void test_2() {
        assertTimeoutPreemptively(Duration.ofMillis(1000000), () -> assertEquals("416837529982465371735129468571298643293746185864351297647913852359682714128574936",
                Problem3524.solveSudoku("......52..8.4......3...9...5.1...6..2..7........3.....6...1..........7.4.......3.")));
    }
    @Test
    void test_3() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> assertEquals("157946832396128745284573196928734561763815429415692378672351984831469257549287613",
                Problem3524.solveSudoku("1.7....3..961...4.2..5.3.9..28.345.17.3.1..2.41.69.37..72.....4..146.2....928..1.")));
    }
    @Test
    void test_4() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> assertEquals("531267894649183527827954163496715382218639475753428916962541738185376249374892651",
                Problem3524.solveSudoku("...26.....4...35..8.7...1....6..53.2.....947.7..4.8.....2......18.37.249...89.6.1")));
    }
    @Test
    void test_5() {
        assertTimeoutPreemptively(Duration.ofMillis(1000000), () -> assertEquals("415378962763429185928561374396745218284196753157832496672984531831257649549613827",
                Problem3524.solveSudoku(".1...89..7.34....5....6...4.9...5..8.8........5783...6..2....3.8...5........13.2.")));
    }
    @Test
    void test_6() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> assertEquals("531267894649183527827954163496715382218639475753428916962541738185376249374892651",
                Problem3524.solveSudoku("...65......84..91........83.6.......35......1.197...5...7.....21............2976.")));
    }

    @Test
    void test_rotate() {
        char[] s2 = "415378962763429185928561374396745218284196753157832496672984531831257649549613827".toCharArray();
        int[][] arr = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = s2[i * 9 + j];
                arr[i][j] = Integer.parseInt("" + c);
            }
        }
//        int[][] arr = new int[4][4];
//        int inc = 0;
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr.length; j++) {
//                arr[i][j] = inc++;
//            }
//        }
        int[][] rotate = Problem3524.rotate(arr);
        for (int i = 0; i < rotate.length; i++) {
            for (int j = 0; j < rotate.length; j++) {
                System.out.print(rotate[i][j]);
            }
//            System.out.println();
        }
    }
}
