package com.oleh2;

import java.util.HashMap;
import java.util.Map;

public class CreateLongString {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            sb.append("w");
        }
        System.out.println(sb.toString());
        System.out.println(sb.length());
        Map<String, String> map = new HashMap<>();
    }
}
