package com.interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnmodifiableList {
	public static void main(String[] args) {
		ArrayList<String> strings = new ArrayList<>();
		strings.add("hello");
		strings.add("world");
		List<String> strings1 = Collections.unmodifiableList(strings);
		System.out.println(strings1.toString());
		strings.add("yayks");
		System.out.println(strings1.toString());

	}
}
