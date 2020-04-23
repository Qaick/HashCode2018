import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
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
        // I want to see the number, and the divisors:
    }
}