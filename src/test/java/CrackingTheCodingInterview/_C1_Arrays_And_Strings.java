package CrackingTheCodingInterview;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * From the book written by Gayle Laakmann.
 */
public class _C1_Arrays_And_Strings {

    /**
     * 1.1 Implement an algorithm to determine if a string has all unique characters. What if you can not use
     * additional data structures?
     */
    @Test
    public void _1_1_unique_characters() {
        String s = "abcdefg";
        assertTrue(v1(s));
        assertTrue(v_book(s));
        s = "abcdefgd";
        assertFalse(v1(s));
        assertFalse(v_book(s));
    }

    boolean v1(String s) {
        // there is time complexity and space complexity
        // complexity O(n^2)
        char[] string_chars =  s.toCharArray();
        int complexity = 0;
        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < i; j++) {
//                System.out.println("compared: " + i +" and "+j);
                complexity += 1;
                if (string_chars[i] == string_chars[j]) {
//                    System.out.println("complexity: "+ complexity);
                    return false;
                }
            }
        }
//        System.out.println("complexity: "+ complexity);
        return true;
    }

    boolean v_book(String s) {
        // complexity O(n)
        // expected ASCII symbols, it has 256 symbols
        char[] string_chars =  s.toCharArray();
        boolean[] char_set = new boolean[256];
        for (int i = 0; i < string_chars.length; i++) {
            if (char_set[string_chars[i]]){
                return false;
            }
            char_set[string_chars[i]] = true;
        }
        return true;
    }

    /**
     * Write code to reverse a C-Style String. (C-String means that *abcd* is represented as five characters,
     * including the null character.)
     */
    @Test
    public void _1_2_reverse_string() {
        // book has some c++ code with pointers. I would use StringBuilder method or just swap them one by one in for.
    }

    /**
     * Design an algorithm and write code to remove the duplicate characters in a string without using any additional
     * buffer. NOTE: One or two additional variables are fine. An extra copy of the array is not. Write test cases.
     */
    @Test
    public void _1_3_remove_duplicate_characters() {
        char[] arr ="acdabababa".toCharArray();
        removeDuplicates_book(arr);
        System.out.println(Arrays.toString(arr));
    }

    void removeDuplicates_book(char[] str) {
        // book solution
        if (str == null || str.length < 2) return;
        int tail = 1;
        for (int i = 1; i < str.length; ++i) {
            int j;
            for (j = 0; j < tail; ++j) {
                if (str[i] == str[j]) break;
            }
            if (j == tail) {
                str[tail] = str[i];
                ++tail;
            }
        }
        str[tail] = 0;
    }

    /**
     * 1.4 Write a method to decide if two strings are anagrams or not.
     * Anagram is the same letters but in different order: fried - fired
     */
    @Test
    public void _1_4_anagrams() {
        assertTrue(anagrams3_book("listen".toCharArray(), "silent".toCharArray()));
        assertTrue(anagrams3_book("sadder".toCharArray(), "dreads".toCharArray()));
    }

    // income values are changed. Overhead with sorting
    private boolean anagrams1_book(char[] s1, char[] s2) {
        Arrays.sort(s1);
        Arrays.sort(s2);
        return Arrays.equals(s1, s2);
    }

    // income values are changed. Overhead with searching in the seconds string
    private boolean anagrams2(char[] s1, char[] s2) {
        if (s1.length != s2.length) return false;
        loop:
        for (char c : s1) {
            for (int j = 0; j < s2.length; j++) {
                if (s2[j] == c) {
                    s2[j] = '*';
                    continue loop;
                }
            }
            return false;
        }
        return true;
    }

    // I like this approach. But it uses additional cache array. Also, book has more complicated logic that should work faster.
    private boolean anagrams3_book(char[] s1, char[] s2) {
        if (s1.length != s2.length) return false;
        int[] chars = new int[256];
        for (int i = 0; i < s1.length; i++) {
            chars[s1[i]]++;
            chars[s2[i]]--;
        }
        for (int i = 0; i < 256; i++) {
            if (chars[i] != 0) return false;
        }
        return true;
    }

    /**
     * Write a method to replace all spaces in a string with '%20'.
     */
    @Test
    public void _1_5_replace_all_spaces() {
        String s = "hello , one more space   hehe";
        System.out.println(s.replace(" ", "%20"));
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c == ' ' ? "%20" : c);
        }
        System.out.println(sb.toString());
        assertTrue(true);
        // book: replace(char[] string)
        // 1. go through array and count spaces. Create new array with new size
        // 2. go through array and replace spaces, store in new array
    }

    /**
     * 1.6 Given an image represented by an NxN matrix, where each pizel in the image is 4 bytes, write a method to
     * rotate the image by 90 degrees. Can you do this in place?
     */
    @Test
    public void _1_6_image_rotate_90() {
        int n = 4;
        int[][] a = getArray(n), expected = getArrayRotated(n),
        a1 = getArray(n+1), expected1 = getArrayRotated(n+1),
        a2 = getArray(n+2), expected2 = getArrayRotated(n+2);

        rotate90(a);
        rotate90(a1);
        rotate90(a2);
        assertArrayEquals(a, expected);
        assertArrayEquals(a1, expected1);
        assertArrayEquals(a2, expected2);
    }

    int[][] getArray(int n) {
        int[][] a = new int[n][n];
        int tmp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = tmp++;
            }
        }
        return a;
    }

    int[][] getArrayRotated (int n) {
        int[][] a = new int[n][n];
        int tmp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = n-1; j >= 0; j--) {
                a[j][i] = tmp++;
            }
        }
        return a;
    }

    void print2dArray(int[][] a) {
        for (int[] ints : a) {
            System.out.println(Arrays.toString(ints));
        }
    }

    int[][] rotate90hack(int[][] a) {
        int n = a.length - 1;
        int[][] b = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                b[n-j][i] = a[i][j];
            }
        }
        return b;
    }

    /**
     * Clockwise parts a b c d of the square. a is top.
     * 00 01 02        00 01 02 03
     *    11    03     10 11 12 13
     * 10    12 13     20 21 22 23
     * 20 21    23     30 31 32 33
     * 30    22
     *    31 32 33
     */
    void rotate90(int[][] a) {
        int n = a.length;
        int tmp;
        for (int i = 0; i < n / 2; i++) {
            // i backward
            int ib = n - 1 - i;
            for (int j = i; j < ib; j++) {
                int jb = n - 1 - j;
                // a - d swap
                tmp = a[i][j];
                a[i][j] = a[jb][i];
                a[jb][i] = tmp;
                // a - b
                tmp = a[i][j];
                a[i][j] = a[j][ib];
                a[j][ib] = tmp;
                // c - b
                tmp = a[ib][jb];
                a[ib][jb] = a[j][ib];
                a[j][ib] = tmp;
            }
        }
    }

    /**
     * Write an algorithm such that if an element in an MxN matrix is 0, its entire row and column is set to 0.
     */
    @Test
    public void _1_7_row_and_column_0() {
        // book has almost the same algorithm, except last iteration, it was better
        int[][] arr = new int[5][6];
        if (arr.length <= 0) return;
        int[] iZero = new int[arr.length], jZero = new int[arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 0) {
                    iZero[i] = 1;
                    jZero[j] = 1;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (iZero[i] == 1 || jZero[j] == 1) arr[i][j] = 0;
            }
        }
    }

    /**
     * 1.8 Assume you have a method isSubstring which checks if one word is a substring of another. Given two
     * strings, s1, and s2, write code to check if s2 is a rotation of s1 using only one call to isSubstring(i.e.
     * "waterbottle" is a rotation of "erbottlewat").
     */
    @Test
    public void _1_8_isSubstring_rotation() {
        assertTrue(isARotation("waterbottle", "erbottlewat"));

        assertFalse(isARotation("sex", "magic"));
        assertFalse(isARotation("magic", "magicm"));
        assertFalse(isARotation("magic", "magac"));
    }

    boolean isSubstring(String s, String s2) {
        return s.contains(s2);
    }

    boolean isARotation(String s, String s2) {
        if (s.length() != s2.length()) return false;
        return isSubstring(s+s, s2);
    }

    class Node {
        Node next;
        String data;
        Node(String data) {
            this.data = data;
        }
        Node appendToTail(String data) {
            Node end = new Node(data);
            Node current = this;
            while(current.next != null) {
                current = current.next;
            }
            current.next = end;
            return end;
        }
    }

    /**
     * There is a list of numbers from 0 to 250. List is not sorted. One element is missing, write a function that
     * finds which number is missing.
     */
    @Test
    public void _find_missing_number() {
        // I know what is the sum of all numbers from 0 to 250. I can sum all elements of the list and subtract sum
        // list elements from sum of all, result will be missing number. O(n)
        int[] arr = new int[250];
        for (int i = 0; i < 250; i++) {
            arr[i] = i;
        }
        int missing = (int) Math.round(Math.random()*249);
        arr[missing] = 250;
        Random rnd = new Random();

        // shuffle array
        for (int i=arr.length; i>1; i--)
            swap(arr, i-1, rnd.nextInt(i));

        // solution
        int summ = 250, actualSumm=0;
        for (int i = 0; i < 250; i++) {
            summ+=i;
            actualSumm += arr[i];
        }
        assertEquals(missing, summ-actualSumm);
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
