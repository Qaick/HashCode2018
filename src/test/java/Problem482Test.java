import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Problem482Test {

    @ParameterizedTest
    @CsvSource({"0,1", "1,0", "7,0", "2,3", "4,11", "6,41", "8,153", "12,2131", "30,299303201"})
    void test_values_duration(int n, int expected) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected,
                Problem482.ways(n)));
    }
    @ParameterizedTest
    @CsvSource(value = {"4:[22]", "5:[221, 32]"}, delimiter = ':')
    void test_getVariants(int n, String expected) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected,
                Problem482.getVariants(n).toString()));
    }

    @ParameterizedTest
    @CsvSource({"22,4","221,12","32,4","33,4","321,12", "222,8", "2211,36"})
    void test_getNumber(String s, int expected) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected,
                Problem482.convert(parseArr(s))));
    }

    @ParameterizedTest
    @CsvSource({"33,1", "21,2", "221,3", "2221,4", "2211,6", "22111,10", "3211,12"})
    void test_getNumberOfCombinations(String s, int expected) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected,
                Problem482.getNumberOfCombinations(parseArr(s))));
    }

    static int[] parseArr(String string) {
        int[] arr = new int[string.length()];
        for (int i = 0; i < string.length(); i++) {
            arr[i] = Integer.parseInt(string.charAt(i)+"");
        }
        return arr;
    }
}
