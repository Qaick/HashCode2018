package com.oleh;

import java.io.Closeable;
import java.io.IOException;

public class New {

    public static void main(String[] args) throws IOException {
//        A out = null;
        int i = 0;
//        try (
//                out = new A("out")
//        ) {
//            System.out.println("out code");
//        }
        try (A in = new A("in"); A out = new A("out")) {
            System.out.println("in code");
        }
    }

    static class A implements Closeable {
        String text;
        A(String text) {
            this.text = text;
        }

        @Override
        public void close() throws IOException {
            System.out.println("close called: "+ text);
        }
    }
}
