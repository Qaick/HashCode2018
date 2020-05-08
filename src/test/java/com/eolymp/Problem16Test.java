package com.eolymp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class Problem16Test {

    @ParameterizedTest
    @CsvSource({"4, 7, 35, 36, 2"})
    void test_values_duration(int lm, int hd, int ha, int la, long expected) {
        assertTimeoutPreemptively(Duration.ofMillis(2000), () -> assertEquals(expected, Problem16.findDragonLegs(lm, hd, ha, la)));
    }
}