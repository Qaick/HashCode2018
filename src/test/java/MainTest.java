import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34
            ,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50})
    void name(int n) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> Main.findMatching(n));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34
            ,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50})
    void test_duration(int n) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> Main2.find(n));
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,4", "3,12", "4,24", "5,36", "6,60", "7,192", "8,120", "9,180", "10,240", "11,576", "12,360", "13,1296", "14,900", "15,720", "16,840", "17,9216", "18,1260", "19,786432", "20,1680", "21,2880", "22,15360", "23,3600", "24,2520", "25,6480", "26,61440", "27,6300", "28,6720", "29,-1", "30,5040", "31,-1", "32,7560", "33,46080", "34,983040", "35,25920", "36,10080", "37,-1", "38,32400", "39,184320", "40,15120", "41,44100", "42,20160", "43,-1", "44,107520", "45,25200", "46,-1", "47,-1", "48,27720", "49,233280", "50,45360"})
    void test_values_duration(int n, int exp) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(exp, Main2.find(n)));
    }
}