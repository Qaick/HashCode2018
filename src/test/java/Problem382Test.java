import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class Problem382Test {

    @ParameterizedTest
    @CsvSource({"10,1,1,4"})
    void test_values_duration(int all, int first, int added, int expected) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected,
                Problem382.pears(all, first, added)));
    }
}
