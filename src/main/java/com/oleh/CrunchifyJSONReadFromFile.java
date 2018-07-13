package com.oleh;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Crunchify.com
 */

public class CrunchifyJSONReadFromFile {
    static int max;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        try {
            // 17 byteLength
            // 15 diffSize
            List<Integer> list = new ArrayList<>();
            Files.lines(new File("toRead.json").toPath()).filter(s -> s.contains("diffSize")).forEach(s -> {
                int curr = Integer.parseInt(s.substring(15));
                list.add(curr);
                System.out.println(curr);
                if (curr > max){
                    max = curr;
                }
            });
            System.out.println("list size: "+list.size());
            System.out.println("Max is: " + max);

//            JSONParser parser = new JSONParser();
//            JSONArray obj = (JSONArray) parser.parse(new FileReader(
//                    "toRead.json"));
//
//            System.out.println("obj = " + obj);
            

//            JSONObject jsonObject = (JSONObject) obj;
//
//            String name = (String) jsonObject.get("Name");
//            String author = (String) jsonObject.get("Author");
//            JSONArray companyList = (JSONArray) jsonObject.get("Company List");
//
//            System.out.println("Name: " + name);
//            System.out.println("Author: " + author);
//            System.out.println("\nCompany List:");
//            Iterator<String> iterator = companyList.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}