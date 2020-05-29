import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class Problem547Test {

    @Test
    void test_corners_0() { // O
        int[][] arr = {
                {0, -1, 0},
                {-1, -1, -1},
                {0, -1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("/\\\n\\/\n", solve);
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
                {2, -1, 2},
                {-1, 2, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("/\\\n\\/\n", solve);
    }

    @Test
    void test_middle() {
        int[][] arr = {
                {-1, -1, -1},
                {-1, 0, -1},
                {-1, -1, -1}};
        String solve = Problem547.solve(arr);
        assertEquals("/\\\n\\/\n", solve);
    }

    @Test
    void test_1() {
        int[][] arr = {
                {-1, -1, -1},
                {-1, 2, 1},
                {-1, 1, 0}};
        String solve = Problem547.solve(arr);
        assertEquals("//\n//\n", solve);
    }

    @Test
    void test_3() {
        int[][] arr = {
                {-1, 2, 1, -1, -1, -1},
                {-1, -1, 3, 3, -1, 0},
                {-1, -1, -1, -1, -1, -1},
                {-1, -1, 3, 3, -1, -1},
                {0, -1, -1, 3, 3, -1},
                {-1, 2, 1, -1, 1, 1}};
        assertEquals("/\\\\//\n//\\\\\\\n\\\\\\//\n\\/\\\\/\n///\\\\\n", Problem547.solve(arr));
    }
}