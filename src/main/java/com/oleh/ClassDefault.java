package com.oleh;

public class ClassDefault {
    public static void main(String[] args) {
        double[] array = new double[Integer.parseInt(Double.toString(Double.POSITIVE_INFINITY))];
        System.out.println("'test'");
        System.out.println("\'test\'");
        System.out.println(getObject() instanceof Object);
        System.out.println(ClassDefault.class);
        System.out.println(InterfaceA.class);
    }

    private static Object getObject() {
        return null;
    }
}
