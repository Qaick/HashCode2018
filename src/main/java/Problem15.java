import java.util.Arrays;
import java.util.Scanner;

public class Problem15 {

    /**
     * m,n <= 100
     * pathLength <= 198
     * 0..30_000. TODO Limit of max number can be reached.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int height = in.nextInt();
        int width = in.nextInt();
        int[][] map = new int[height][width];
        for (int i = map.length - 1; i >= 0; i--) { // flipped up and down. Now it's easier to move in the map.
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = in.nextInt();
            }
        }
        System.out.println(findPathFast(map));
    }

    public static String findPathFast(int[][] map) {
        // reset values without meaning. Not necessary
        map[0][0] = 0;
        int height = map.length;
        int width = map[0].length;
        map[height -1][width -1] = 0;
        // iterate over diagonals
        for (int iteration = 1; iteration < height + width - 2; iteration++) {
            int i = iteration, j = 0;
            while (i >= 0) {
                if (j < width && i < height) {
                    int up = i > 0 ? map[i - 1][j] : 0;
                    int left = j > 0 ? map[i][j - 1] : 0;
                    map[i][j] += Math.max(up, left);
                }
                i--;
                j++;
            }
        }
        // now in the right bottom corner is the best result. I can go revers and find out the best path
        int i = height-1, j=width-1;
        StringBuilder route = new StringBuilder();
        while(i>0 || j>0) {
            int up = i > 0 ? map[i - 1][j] : -1;
            int left = j > 0 ? map[i][j - 1] : -1;
            if (up > left) {
                route.append('F');
                i--;
            } else {
                route.append('R');
                j--;
            }
        }
        return route.reverse().toString();
    }

    /**
     * This solution calculates rewards for every path taken.
     */
    public static String findPath(int[][] map) {
        int height = map.length, width = map[0].length; // height - bottom - 0, width - right - 1

        int[] path = new int[height + width - 2];
        for (int i = 0, ones = width - 1; i < path.length; i++) {
            if (ones > 0) {
                path[i] = 1;
                ones--;
            } else path[i] = 0;
        }
        int maxReward = calcReward(map, path);
        int[] maxRewardPath = path;
        while ((path = tryToMakeNewPath(path)) != null) {
            int reward = calcReward(map, path);
            if (reward > maxReward) {
                maxReward = reward;
                maxRewardPath = path;
            }
        }

        // convert path to the Forward Right letters
        StringBuilder route = new StringBuilder();
        for (int side : maxRewardPath) {
            route.append(side == 0 ? 'F' : 'R');
        }
        return route.toString();
    }

    private static int calcReward(int[][] map, int[] path) {
        int reward = map[0][0]; // this value doesn't mean anything since every path contains it
        int i = 0;
        int j = 0;
        for (int value : path) {
            if (value == 0) i++;
            else j++;
            reward += map[i][j];
        }
        return reward;
    }

    public static int[] tryToMakeNewPath(int[] path) {
        int[] newPath = Arrays.copyOf(path, path.length);
        if (newPath[newPath.length-1] == 0) {
            for (int i = newPath.length - 1; i >= 0; i--) {
                if (newPath[i] == 1) { // I'm looking for the first 1 and moving  it to the right
                    newPath[i] = 0;
                    newPath[i+1] = 1;
                    return newPath;
                }
            }
            return null; // this means that whole path are zeroes
        } else {
            // this time I have 1 in the end of my path
            // I need to find 0
            // after that I'm looking for 1
            // swapping 2 values
            int firstZero =-1;
            for (int i = newPath.length - 2; i >= 0; i--) {
                if (newPath[i] == 0) { // I'm looking for the first 0 and remembering it
                    firstZero = i;
                    break;
                }
            }
            if (firstZero == -1 || firstZero == 0) return null;
            for (int i = firstZero - 1; i >= 0; i--) {
                if (newPath[i] == 1) { // I'm looking for the first 1 and switching it
                    newPath[i] = 0;
                    newPath[i+1] = 1;
                    // but also I need to move all the ones close to the one
                    if (firstZero != i+1) {
                        int numberOfZeroes = firstZero-i-1; // number of zeroes
                        for (int j = newPath.length - 1; j >= i + 2; j--) {
                            if (numberOfZeroes != 0) {
                                newPath[j] = 0;
                                numberOfZeroes--;
                            } else {
                                newPath[j] = 1;
                            }
                        }
                    }
                    return newPath;
                }
            }
            return null; // this means that whole path contains zeroes on the left and ones on the right
        }
    }
}
