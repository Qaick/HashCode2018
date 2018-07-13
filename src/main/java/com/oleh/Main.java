package com.oleh;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

//        for (int i = 0; i < vehicles; i++) {
//            result.add(new ArrayList<Integer>());
//        }
//
//        int ridesCount = ridesArr.length;
//
//        for (int i = 0; i < ridesCount; i++) {
//            if (i < result.size()) {
//                result.get(i).add(i);
//            } else {
//                result.get(i % result.size()).add(i);
//            }
//        }
//
//        for (int i = 0; i < result.size(); i++) {
//            List<Integer> arrayList = result.get(i);
//            arrayList.add(0, arrayList.size());
//        }

        // Solve of the problem:

        // key = vehicleId, value = List<RideId>
        Map<Integer, ArrayList<Integer>> resMap = new HashMap<>();

        ArrayList<Vehicle> vehiclesPull = new ArrayList<>();
        for (int i = 0; i < vehicles; i++) {
            vehiclesPull.add(new Vehicle());
        }

        ArrayList<Ride> ridesPull = new ArrayList<>();
        for (int i = 0; i < rides; i++) {
            ridesPull.add(new Ride(ridesArr[i][0], ridesArr[i][1], ridesArr[i][2], ridesArr[i][3]));
        }

        int rideId = 0;

        while (!ridesPull.isEmpty()) {
            Ride ride = ridesPull.remove(0);

            int vehicleId = findNearestFreeVehicle(vehiclesPull, ride);
            Vehicle vehicle = vehiclesPull.get(vehicleId);
            vehicle.busy = true;

            if (resMap.containsKey(vehicleId)) {
                resMap.get(vehicleId).add(rideId);
            } else {
                resMap.put(vehicleId, new ArrayList<>(Collections.singleton(rideId)));
            }

            rideId++;
        }

        result.clear();
        result.addAll(resMap.values());

        for (int i = 0; i < result.size(); i++) {
            List<Integer> arrayList = result.get(i);
            arrayList.add(0, arrayList.size());
        }

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

    static int findNearestFreeVehicle(ArrayList<Vehicle> vehicles, Ride ride) {
        int vehiclePos = 0;
        int minDis = Integer.MAX_VALUE;
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            int distance;
            if (v.busy) {
                distance = getDistance(v.coor, ride.finish);
            } else {
                distance = getDistance(v.coor, ride.start);
            }
            if (distance < minDis) {
                minDis = distance;
                vehiclePos = i;
            }
        }
        return vehiclePos;
    }

    static int getDistance(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }
}

class Vehicle {

    boolean busy;

    int[] coor = new int[2];

    Vehicle() {
        busy = false;
        coor[0] = 0;
        coor[1] = 0;
    }
}

class Ride {

    int[] start = new int[2];
    int[] finish = new int[2];

    Ride(int a, int b, int x, int y) {
        start[0] = a;
        start[1] = b;
        finish[0] = x;
        finish[1] = y;
    }
}