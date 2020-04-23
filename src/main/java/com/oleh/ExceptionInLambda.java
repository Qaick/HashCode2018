package com.oleh;

import java.util.function.Supplier;

public class ExceptionInLambda {
    public static void main(String[] args) {
        Supplier s = () -> {
            throw new RuntimeException("lol");
        };
        s.get();
    }
}
