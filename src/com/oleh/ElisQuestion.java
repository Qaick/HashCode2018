package com.oleh;

public class ElisQuestion {
    public static void main(String[] args) {
        final String s = longMethod();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(s);
            }
        }).start();
    }

    private static String longMethod() {
        return "long string";
    }
}