package com.interview;

import java.util.List;
import java.util.stream.IntStream;

public class MapReduce {
	public static void main(String[] args) {

	}

	public static boolean method(List<Integer> list) {
		int sum = list.stream().mapToInt(Integer::intValue) // IntStream
				.filter(value -> value % 2 != 0) // only even numbers
				.sorted() // sort
				.peek(System.out::println)// print every element
				.sum();

		int sum2 = list.stream()
				.filter(el -> el % 2 != 0) // those which can be divided by 2
				.sorted(Integer::compareTo)
				.peek(System.out::println)// print every element
				.reduce(0, Integer::sum);

		// it's better to map it to int primitives because we work with primitives
		int sum3 = list.stream().mapToInt(Integer::intValue)
				.filter(integer -> integer % 2 != 0)
				// if I want custom sorting I'll stay with Integer object
				.sorted()
				.peek(value -> System.out.println("value = " + value))
				.sum();
		return sum == sum2 && sum == sum3;
	}
}
