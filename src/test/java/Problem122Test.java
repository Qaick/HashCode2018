import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class Problem122Test {
    static {
        Problem122.map.put(1, new HashSet<>(Arrays.asList(2,3,5)));
        Problem122.map.put(2, new HashSet<>(Arrays.asList(1, 4)));
        Problem122.map.put(3, new HashSet<>(Arrays.asList(4, 5)));
        Problem122.map.put(4, new HashSet<>(Arrays.asList(1)));
        Problem122.map.put(5, new HashSet<>());
    }

    @ParameterizedTest
    @CsvSource({"2,5,3,3", "2,4,1,1", "1,4,2,2"})
    void test_values_duration(int a, int b, int c, int expected) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected,
                Problem122.paths(a, b, c, new HashSet<>())));
    }
}