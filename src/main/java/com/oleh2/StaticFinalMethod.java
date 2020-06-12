package com.oleh2;

public class StaticFinalMethod {
    public static void main(String[] args) {
        A.meth();
        B.meth();
    }

    static class A {
        static final void meth() {
            System.out.println(Thread.currentThread().getStackTrace()[1]);
        }
    }

    static class B extends A {
        // TODO can't do this...
//        static void meth() {
//            System.out.println("hey");
//        }
    }
}
