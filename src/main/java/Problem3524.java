import java.util.*;

public class Problem3524 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s;
        while (!(s = in.nextLine()).equals("end")) {
            System.out.println(solveSudoku(s));
        }
    }
    static Set<Integer>[][] sol = new Set[9][9];
    static Queue<int[]> queue = new LinkedList<>(); // i j number
    static String solveSudoku(String s) {
        int[][] solution = new int[9][9];
        {
            char[] s2 = s.toCharArray();
            Set<Integer> set = new HashSet<>();
            for (int i = 1; i <= 9; i++) {
                set.add(i);
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sol[i][j] = new HashSet<>(set);
                    char c = s2[i * 9 + j];
                    if (c != '.') queue.add(new int[]{i, j, Integer.parseInt("" + c)});
                }
            }
        }
        int full = 81;
        int prevFull = 0;
        big:
        while(true) {
            if (prevFull == full) {
                System.out.println("break " + queue.isEmpty());
                break;
            }
            prevFull = full;
            while (!queue.isEmpty()) {
                int[] e = queue.remove();
                int i = e[0];
                int j = e[1];
                if (solution[i][j] != 0) {
                    assert solution[i][j] == e[2];
                    continue;
                }
                solution[i][j] = e[2]; // the only place where solution is placed
                if(--full <= 0) break big;
                removeAfterSolutionPasted(i, j, e[2]);
            }
            // look for unique number in the row/column/square
            for (int i = 0; i < 9; i++) {
                int[] row = new int[9];
                int[] col = new int[9];
                int[] sq = new int[9];
                int ca = i/3*3, cb = i%3*3; // 0 00 1 03 2 06 3 30 4 33 5 36 6 60 7 63 8 66
//                System.out.println(ca+" "+cb);
                for (int j = 0; j < 9; j++) {
                    for (int i1 : sol[i][j]) {
                        row[i1-1]++;
                    }
                    for (int i1 : sol[j][i]) {
                        col[i1-1]++;
                    }
                    int a = j / 3, b = j % 3;
                    for (int i1 : sol[ca+a][cb+b]) {
                        sq[i1-1]++;
                    }
                }
                for (int number = 1; number <= 9; number++) {
                    if (row[number-1] == 1) {
                        for (int k = 0; k < 9; k++) {
                            if (sol[i][k].contains(number)) queue.add(new int[]{i,k, number});
                        }
                    }
                    if (col[number-1] == 1) {
                        for (int k = 0; k < 9; k++) {
                            if (sol[k][i].contains(number)) queue.add(new int[]{k,i, number});
                        }
                    }
                    if (sq[number-1] == 1) {
                        for (int k = 0; k < 9; k++) {
                            int a = k / 3, b = k % 3;
                            if (sol[ca+a][cb+b].contains(number)) queue.add(new int[]{ca+a,cb+b, number});
                        }
                    }
                }
            }
            // remove candidate
            solveCandidateSq();
            rotate();
            solveCandidateSq();
            rotate();
            rotate();
            rotate();


            // row/col has value only in the sq cells of this row/col -> remove values from the other cells of the sq

            // I guess that if those 2 methods will not work out I'll need to go with try and fail way

            // find pairs 24 24 sq-row sq-col
            // col, row, sq - if there are sets with 2 values and those
        }
        int[] vals = new int[10];
        int[] vals2 = new int[82];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                vals2[i*9+j] = sol[i][j].size();
                vals[solution[i][j]]++;
                sb.append(solution[i][j]);
            }
        }
        System.out.println(Arrays.toString(vals));
        System.out.println(Arrays.toString(vals2));
        return sb.toString();
    }

    static int[][] rotate(int[][] arr) {
        int[][] arr2 = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr2[j][arr.length - i-1 ] = arr[i][j];
            }
        }
        return arr2;
    }

    static boolean rotated = false;
    private static void rotate() {
        rotated = !rotated;
        System.out.println("_________________________");
        for (int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(sol[i]));
        }
        System.out.println("_________________________");
        int sn = 9;
        Set<Integer>[][] arr = new Set[sn][sn];
        for (int i = 0; i < sn; i++) {
            for (int j = 0; j < sn; j++) {
                arr[j][sn - i - 1] = sol[i][j];
            }
        }
        sol = arr;

        for (int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(sol[i]));
        }
    }

    static Map<Integer,int[]> leftThings = new HashMap<>();
    static Map<Integer,int[]> leftIndexes = new HashMap<>();
    static {
        leftThings.put(0, new int[]{1,2});
        leftThings.put(1, new int[]{0,2});
        leftThings.put(2, new int[]{0,1});
        leftThings = Collections.unmodifiableMap(leftThings);
        leftIndexes.put(0, new int[]{0,3,3,6,6,9});
        leftIndexes.put(1, new int[]{3,6,0,3,6,9});
        leftIndexes.put(2, new int[]{6,9,0,3,3,6});
        leftIndexes = Collections.unmodifiableMap(leftIndexes);
    }

    private static void solveCandidateSq() {
        // sq has one value in the row/col -> remove that value from other cells of the row/col
        // 3x3 board with squares with 3x3 cells
        for (int squareRow = 0; squareRow < 3; squareRow++) {
//            System.out.println("squareRow = " + squareRow);
            for (int squareCol = 0; squareCol < 3; squareCol++) {
//                System.out.println("squareCol = " + squareCol);
                for (int cellCol = 0; cellCol < 3; cellCol++) {
//                    System.out.println("cellCol = " + cellCol);
                    Set<Integer> colValues = new HashSet<>();
                    int cellCol2 = squareCol * 3 + cellCol;
                    int[] rowIdx = leftIndexes.get(squareRow);
                    {
                        int sqRowStart = rowIdx[0], sqRowEnd = rowIdx[1];
                        int[] leftColumns = leftThings.get(cellCol);
                        int remove1 = squareCol * 3 +leftColumns[0], remove2 = squareCol * 3 +leftColumns[1];
                        for (int i = sqRowStart; i < sqRowEnd; i++) {
//                            System.out.println("col add : " + i + cellCol2);
                            colValues.addAll(sol[i][cellCol2]);
                        }
                        for (int i = sqRowStart; i < sqRowEnd; i++) {
//                            System.out.printf("col remove : %d %d %d\n", i, remove1, remove2);
                            colValues.removeAll(sol[i][remove1]);
                            colValues.removeAll(sol[i][remove2]);
                        }
                    }
                    if (!colValues.isEmpty()) {
//                        System.out.println("there is a way " + colValues);
                        for (int i = rowIdx[2]; i < rowIdx[3]; i++) {
//                            System.out.printf("col remove all : %d\n", i);
                            removeAll(i, cellCol2, colValues);
                        }
                        for (int i = rowIdx[4]; i < rowIdx[5]; i++) {
//                            System.out.printf("col remove all 2 : %d\n", i);
                            removeAll(i, cellCol2, colValues);
                        }
                    }
                }
            }
        }
    }
    static String hackSolution = "415378962763429185928561374396745218284196753157832496672984531831257649549613827";
    // rotated
    static String hackSolutionR = "586123974437589261912746835629817543158394627374265198865472319243951786791638452";

    static void removeAfterSolutionPasted(int i, int j, int k) {
        sol[i][j] = Collections.emptySet();
        int ca = i-i%3;
        int cb = j-j%3;
        for (int l = 0; l < 9; l++) {
            removeEl(i, l, k);
            removeEl(l, j, k);
            int a = l / 3, b = l % 3;
            removeEl(ca + a, cb + b, k);
        }
//        System.out.printf("%d %d - %d %d%", i,j,a,b);
    }
    static void removeEl(int i, int j, int k) {
        Set<Integer> el = sol[i][j];
        el.remove(k);
        if (el.size() == 1) {
            Integer next = el.iterator().next();
            int[] getij = getij(i, j);
            System.out.printf("%d %d %d\n", getij[0], getij[1], next);
            queue.add(new int[]{getij[0], getij[1], next});
            el.remove(next);
        }
    }

    private static int[] getij(int i, int j) {
        return !rotated ? new int[]{i,j} : new int[]{9 - j- 1,i  };
    }

    static void removeAll(int i, int j, Collection<Integer> k) {
        for (Integer integer : k) {
            removeEl(i,j,integer);
        }
    }
}
