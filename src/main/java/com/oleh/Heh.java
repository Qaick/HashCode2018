package com.oleh;

/**
 * It's better to add underscore to the constructor parameter if it's name is the same as class field.
 */
public class Heh {
    int a;
    Runnable r;
    Heh(int _a) {
        this.a = _a + 1; // if further in the constructor a will be changed it could change parameter, not field
        a++;
        r = () -> {
            System.out.println("r: a = " + a); // a could be taken from local
        };
    }

    void m() {
        System.out.println("m: a = " + a);
    }

    public static void main(String[] args) {
        Heh heh = new Heh(4);
        heh.a++;
        heh.m();
        heh.r.run();
    }
}
