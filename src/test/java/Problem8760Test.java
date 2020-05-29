import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class Problem8760Test {

    @ParameterizedTest
    @CsvSource({"2,5,3,0"})
    void test_values_duration(int a, int b, int c, int expected) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected,
                Problem8760.dfs(null, 0)));
    }
}
