package com.oleh;

import java.io.File;
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
        
        
        System.out.println("ridesArr = " + ridesArr);
    }
}
