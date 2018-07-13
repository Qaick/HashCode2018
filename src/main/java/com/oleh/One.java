package com.oleh;

public class One {
    public static void main(String[] args) {
//        meth1();
//        meth2();
        String s = new String("abc");
        String s2 = String.valueOf("abc");
        String text = "aaa. bbb. ccc";
        String[] sentences = text.split("\\.");
        sentences[0].contains("hello");
    }

//    public static void main(String[] args) {}
//    static void test(){
//        main(new String[]{"is", "language", "lear"});
//    }
    
    static void meth1(){
        String[] WordArray = {"is", "language", "lear"};
        System.out.println(WordArray);
    }
    
    static void meth2(){
        String[] WordArray = new String[] {"is", "language", "lear"};
        System.out.println(WordArray);
    }
}
