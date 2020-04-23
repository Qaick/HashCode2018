package com.oleh;

import java.util.Arrays;

public class ClassReflection {
    public static void main(String[] args) {
        System.out.println(getD());
        Class<?> returnType = ClassReflection.class.getMethods()[1].getReturnType();
        System.out.println("return type: " + returnType);
        System.out.println("assignable from: " + A.class.isAssignableFrom(returnType));
        Arrays.stream(ClassReflection.class.getMethods()).forEach(m -> System.out.println(m.getName()+" "+Arrays.toString(m.getReturnType().getInterfaces())));

//        System.out.println(returnType);
    }
    static interface A{}
    static interface B extends A{}
    static interface C extends B{}
    static class D implements C{}
    static class E extends D{}
    public static D getD() {
        return new D() {};
    }
    public static E getE() {
        return new E() {};
    }
}
