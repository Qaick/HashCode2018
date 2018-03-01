package com.oleh;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        String[] outNames = {"a", "b", "c", "d", "e"};
        String[] inNames = {"a_example.in", "b_should_be_easy.in", "c_no_hurry.in", "d_metropolis.in", "e_high_bonus.in"};

        for (int i = 0; i < inNames.length; i++) {
            List<List<Integer>> result = readAndSolve(new File(inNames[i]));
            saveToFile(outNames[i] + ".out", result);
        }
    }

    static List<List<Integer>> readAndSolve(File file) throws Exception {
        Scanner in = new Scanner(file);
        int rows = in.nextInt();
        int columns = in.nextInt();
        int vehicles = in.nextInt();
        int rides = in.nextInt();
        int bonus = in.nextInt();
        int steps = in.nextInt();

        int[][] ridesArr = new int[rides][6];

        for (int i = 0; i < rides; i++) {
            ridesArr[i] = new int[]{
                    in.nextInt(), // row start intersection
                    in.nextInt(), // column start intersection
                    in.nextInt(), // row finish intersection
                    in.nextInt(), // column finish intersection
                    in.nextInt(), // earliest start
                    in.nextInt()  // latest start
            };
        }

        // Solve of the problem:

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < vehicles; i++) {
            result.add(new ArrayList<Integer>());
        }

        int ridesCount = ridesArr.length;

        for (int i = 0; i < ridesCount; i++) {
            if (i < result.size()) {
                result.get(i).add(i);
            } else {
                result.get(i % result.size()).add(i);
            }
        }

        for (int i = 0; i < result.size(); i++) {
            List<Integer> arrayList = result.get(i);
            arrayList.add(0, arrayList.size());
        }
//        System.out.println("ridesArr = " + ridesArr);
//        System.out.println("result = " + result);
        System.out.println("solved "+file.getName());
        return result;
    }

    static void saveToFile(String filename, List<List<Integer>> data) throws Exception {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < data.get(i).size(); j++) {
                sb.append(data.get(i).get(j)).append(' ');
            }
            lines.add(sb.toString());
        }
        Path file = Paths.get(filename);
        Files.write(file, lines, Charset.forName("UTF-8"));
    }
}
