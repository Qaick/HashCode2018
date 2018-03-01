package com.oleh;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        File a_file = new File("a_example.in");
        File b_file = new File("b_should_be_easy.in");
        File c_file = new File("c_no_hurry.in");
        File d_file = new File("d_metropolis.in");
        File e_file = new File("e_high_bonus.in");
        
        Scanner in = new Scanner(a_file);
        int rows = in.nextInt();
        int columns = in.nextInt();
        int vehicles = in.nextInt();
        int rides = in.nextInt();
        int bonus = in.nextInt();
        int steps = in.nextInt();

        int[][] ridesArr = new int[rows][6];
        
        for (int i = 0; i < rows; i++)
        {
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
        
        
        int[][] data = null;
        
        saveToFile("a.out", data);
        
        System.out.println("ridesArr = " + ridesArr);
    }
    
    static void saveToFile(String filename, int[][] data) throws Exception{
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < data.length; i++)
        {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < data[i].length; j++)
            {
                sb.append(data[i][j]).append(' ');
            }
            lines.add(sb.toString());
        }
        Path file = Paths.get(filename);
        Files.write(file, lines, Charset.forName("UTF-8"));
    }
}
