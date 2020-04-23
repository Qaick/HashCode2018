package com.oleh;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class HashTableTest {
    public static void main(String[] args) {
        Hashtable<String, String> hashTable = new Hashtable<>();
        hashTable.put("val", "val");
        hashTable.put("val", "not val");
        System.out.println(hashTable);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("val", "val");
        hashMap.put("val", "not val");
        System.out.println(hashMap);
    }
}
