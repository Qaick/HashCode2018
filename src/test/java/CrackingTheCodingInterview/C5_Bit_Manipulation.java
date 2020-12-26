package CrackingTheCodingInterview;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class C5_Bit_Manipulation {

    @Test
    public void _5_4_explain() {
        for (int n = -500; n < 25; n++) {
            System.out.println(Integer.toBinaryString(n) +" "+((n & (n-1)) == 0)); // n%2 == 1
            // 10 01=0
            // 01 01-1.0
            // 11 10
            // 110 101 100
        }
    }

    @Test
    public void _5_5_xor() {
        Assertions.assertEquals(2, calcDiff(31, 14));
    }

    int calcDiff(int a, int b) {
        return calcBits(a^b);
    }

    @Test
    public void calcBitsT() {
        Random rand = new Random();
        for (int i = 0; i < 10_000; i++) {
            int n = rand.nextInt();
            Assertions.assertEquals(Integer.bitCount(n),calcBits(n));
        }
    }

    int calcBits(int a) {
        int ans = 0;
        if (a < 0){
            ans++;
        }
        int cursor = 1;
        while(cursor!=0) {
            ans += (a & cursor) > 0 ? 1 : 0;
            cursor <<= 1;
        }
        return ans;
    }

    int calcBits2(int a) {
        int ans = 0;
        if (a < 0){
            ans++;
        }
        while(a!=0) {
            ans += a & 1;
            a >>>= 1; // shifting with filling 0
        }
        return ans;
    }
}
