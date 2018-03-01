package com.oleh;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
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
        
        
    }
}
