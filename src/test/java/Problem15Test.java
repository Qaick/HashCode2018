import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class Problem15Test {

//    @ParameterizedTest
//    @CsvSource({"3, 2, 4, 1, 5, 1,"})
//    void test_values_duration(int[][] map, long expected) {
//        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected, Problem15.findPath(map)));
//    }

    @Test
    void test_values_duration() {
        int[][] map = {{0,0},{1,0}};
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals("FR", Problem15.findPath(map)));
    }

    @Test
    void test_values_duration1() {
        int[][] map = {{3,2,4},{1,5,1}};
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals("RFR", Problem15.findPath(map)));
    }

    @Test
    void minimums() {
        assertEquals("", Problem15.findPath(new int[][]{{0}}));
        assertEquals("R", Problem15.findPath(new int[][]{{0,0}}));
        assertEquals("F", Problem15.findPath(new int[][]{{0},{0}}));
    }

    @Test
    void getPaths() {
        // do I need to test minimals
        // I don't care about sorting...
        assertArrayEquals(new int[][]{{1,0}, {0,1}}, Problem15.getPaths(2,2));
        assertArrayEquals(new int[][]{{1,1,0}, {1,0,1},{0,1,1}}, Problem15.getPaths(2,3));
        assertArrayEquals(new int[][]{{1,0,0}, {0,1,0}, {0,0,1}}, Problem15.getPaths(3,2));
        assertArrayEquals(new int[][]{
                {1,1,0,0},
                {1,0,1,0},
                {1,0,0,1},
                {0,1,1,0},
                {0,1,0,1},
                {0,0,1,1}}, Problem15.getPaths(3,3));
    }

    @Test
    void name() {
        assertArrayEquals(new int[]{0, 1, 1, 1, 0, 0}, Problem15.tryToMakeNewPath(new int[]{1, 0, 0, 0, 1, 1}));
    }
}