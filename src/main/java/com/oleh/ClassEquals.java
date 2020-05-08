package com.oleh;

public abstract class ClassEquals {
    void work() {
        System.out.println(getClass());
        System.out.println(super.getClass());
        if (ClassEquals.class != getClass()) {
            System.out.println("adfa");
        }
        if (1 == 1)
            System.out.println("true");
    }

    public static void main(String[] args) {
        new Bash();
        new C().work();
    }
    static class C extends ClassEquals {
    }
}
