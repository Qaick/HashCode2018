package com.oleh2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Annot2 {
    public static void main(String[] args) throws NoSuchMethodException {
        Annot annotation = Annot2.class.getDeclaredMethod("testingMethod").getAnnotation(Annot.class);
        System.out.println(annotation);
    }

    @Annot
    void testingMethod() {}

    @Retention(RetentionPolicy.RUNTIME)
    @interface Annot {
        String[] arr() default {};

    }

}
