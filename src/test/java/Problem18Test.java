import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class Problem18Test {

    @ParameterizedTest
    @CsvSource({"1,1,4", "2,1,6", "3,1,8", "2,3,12"})
    void test_values_duration(int a, int b, int expected) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected,
                Problem18.paint2d(a, b)));
    }
}