package CrackingTheCodingInterview;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * 9.2 9 checks function, tic tac toe
 */
public class C19_Moderate {
    /**
     * 19.1 Write a function to swap a number in place without temporary variables.
     */
    @Test
    public void _19_1_swap_numbers() {
        int[] a = {1, 7};
        swap(a);
        assertArrayEquals(new int[]{7,1}, a);
    }

    void swap(int[] a) {
        a[0] = a[1]^a[0];
        a[1] = a[1]^a[0];
        a[0] = a[1]^a[0];
    }

}
