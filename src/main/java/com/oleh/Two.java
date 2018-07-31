package com.oleh;

public class Two {
    public static void main(String[] args) {
        testFormatter();
        
        String a = "no";
        double percentage = 1123.6;
        System.out.println("percentage = " + Math.round(1.234));
        System.out.println("percentage = " + Math.round(1.000));
        System.out.println("percentage = " + Math.round(1.5));
        System.out.println("percentage = " + Math.round(1.0 * 2 / 3));
        long rounded = Math.round(percentage);
        String k = String.format("%+,d%%", Long.valueOf(rounded));
        System.out.println(k);
    }
    
    static void testFormatter() {
        System.out.println(String.format("my%sstrin%sg", "YES","NO"));
    }
}
