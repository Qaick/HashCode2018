package com.interview;

public class SameDefaultMethod {
	interface I1{
		default void m(){}
	};
	interface I2{
		default void m(){}};
	class C1 implements I1, I2 {}; // fix: implement it in class
}
