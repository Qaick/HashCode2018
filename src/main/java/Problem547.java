import java.util.Arrays;
import java.util.Scanner;

public class Problem547 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine(); // jump to next line because I'm still on 'n' line
        int[][] puzzle = new int[n][n];
        char[] tmp;
        for (int i = 0; i < n; i++) {
            tmp = in.nextLine().toCharArray();
            for (int j = 0; j < n; j++) {
                puzzle[i][j] = (tmp[j] == '.') ? -1 : tmp[j] - '0';
            }
        }
        System.out.println(solve(puzzle));
    }

    static String solve(int[][] puzzle) {
        int sSize = puzzle.length - 1; // solution size
        int sLastIdx = sSize - 1; // solution last index
        char[][] solution = new char[sSize][sSize];
        for (char[] aChar : solution) {
            Arrays.fill(aChar, '.');
        }
        // check 4 corners. If corner contain 1 or 0 I know the solve
        if (puzzle[0][0] != -1) solution[0][0] = puzzle[0][0] == 1 ? '\\' : '/';
        if (puzzle[0][sSize] != -1) solution[0][sSize - 1] = puzzle[0][sSize] == 1 ? '/' : '\\';
        if (puzzle[sSize][sSize] != -1) solution[sSize - 1][sSize - 1] = puzzle[sSize][sSize] == 1 ? '\\' : '/';
        if (puzzle[sSize][0] != -1) solution[sSize - 1][0] = puzzle[sSize][0] == 1 ? '/' : '\\';

        // check 4 sides. Do it in cycle until there will be no changes?
        // I added 2 tests for top side it gives good guarantee that this part of code is scalable
        while (!isSolved(solution)) {
            for (int i = 1; i < sSize; i++) {
                int top = puzzle[0][i];
                solveSide(solution, top, 0, i - 1, 0, i);
                int right = puzzle[i][sSize];
                solveSide(solution, right, i, sLastIdx, i - 1, sLastIdx);
                int bot = puzzle[sSize][i];
                solveSide(solution, bot, sLastIdx, i, sLastIdx, i - 1);
                int left = puzzle[i][0];
                solveSide(solution, left, i - 1, 0, i, 0);
            }
            // handle middle part
            // 0 can't be in the middle because it means a circle
            // middle can have 1 2 3 4
            for (int i = 1; i < sSize; i++) {
                for (int j = 1; j < sSize; j++) {
                    // continue if all the squares are filled
                    if (puzzle[i][j] != '.' && solution[i][j - 1] != '.' && solution[i - 1][j] != '.'
                            && solution[i - 1][j - 1] != '.') continue;
                    if (puzzle[i][j] == 4) { // draw around
                        solution[i - 1][j - 1] = '\\';
                        solution[i - 1][j] = '/';
                        solution[i][j - 1] = '/';
                        solution[i][j] = '\\';
                    } else if (puzzle[i][j] == 3) {
                        solveOneThree(solution, i, j, '/', '\\');
                    } else if (puzzle[i][j] == 2) {
                        // if you know half you can find out the second half
                        // \/
                        // ..
                        if (solution[i - 1][j - 1] == '\\' && solution[i - 1][j] == '/') {
                            solution[i][j - 1] = '\\';
                            solution[i][j] = '/';
                        } else if (solution[i - 1][j - 1] == '/' && solution[i - 1][j] == '\\') {
                            solution[i][j - 1] = '/';
                            solution[i][j] = '\\';
                        }
                        // .\
                        // ./
                        if (solution[i - 1][j] == '\\' && solution[i][j] == '/') {
                            solution[i][j - 1] = '/';
                            solution[i-1][j-1] = '\\';
                        } else if (solution[i - 1][j] == '/' && solution[i][j] == '\\') {
                            solution[i][j - 1] = '\\';
                            solution[i - 1][j - 1] = '/';
                        }
                        // . .
                        // \/
                        if (solution[i][j] == '\\' && solution[i][j-1] == '/') {
                            solution[i-1][j - 1] = '\\';
                            solution[i-1][j] = '/';
                        } else if (solution[i][j] == '/' && solution[i][j-1] == '\\') {
                            solution[i-1][j - 1] = '/';
                            solution[i - 1][j] = '\\';
                        }
                        // \.
                        // /.
                        if (solution[i][j-1] == '/' && solution[i-1][j-1] == '\\') {
                            solution[i-1][j] = '\\';
                            solution[i][j] = '/';
                        } else if (solution[i][j-1] == '\\' && solution[i-1][j-1] == '/') {
                            solution[i-1][j ] = '/';
                            solution[i ][j ] = '\\';
                        }
                        // ./
                        // /.
                        if (solution[i][j-1] == '/' && solution[i-1][j] == '/') {
                            solution[i-1][j-1] = '/';
                            solution[i][j] = '/';
                        } else if (solution[i][j-1] == '\\' && solution[i-1][j] == '\\') {
                            solution[i-1][j -1] = '\\';
                            solution[i ][j ] = '\\';
                        }
                        // \.
                        // .\
                        if (solution[i-1][j-1] == '\\' && solution[i][j] == '\\') {
                            solution[i-1][j] = '\\';
                            solution[i][j-1] = '\\';
                        } else if (solution[i-1][j-1] == '/' && solution[i][j] == '/') {
                            solution[i-1][j ] = '/';
                            solution[i ][j -1] = '/';
                        }
                    } else if (puzzle[i][j] == 1) {
                        solveOneThree(solution, i, j, '\\', '/');
                    }
                }
            }
            // у меня появилась идея что все эти / и \ можно закодировать автоматически
            // по парности проверять. В теории это должно много проблем и лишнего кода упростить

            // handle circles. result should not contain circles
            for (int i = 0; i < sSize; i++) {
                for (int j = 0; j < sSize; j++) {
                    // continue if all the squares are filled
//                if (puzzle[i][j] != '.' && solution[i][j - 1] != '.' && solution[i - 1][j] != '.'
//                        && solution[i - 1][j - 1] != '.') continue;
                    if (i - 1 >= 0) {
                        // bottom right
                        if (j - 1 >= 0) {
                            // left, left top, top
                            if (solution[i][j - 1] == '\\' && solution[i - 1][j - 1] == '/' && solution[i - 1][j] == '\\') {
                                solution[i][j] = '\\';
                            }
                        }
                        // bottom left
                        if (j + 1 < sSize) {
                            if (solution[i - 1][j] == '/' && solution[i - 1][j + 1] == '\\' && solution[i][j + 1] == '/') {
                                solution[i][j] = '/';
                            }
                        }
                    }
                    if (i + 1 < sSize) {
                        // right top
                        if (j - 1 >= 0) {
                            // left, left bottom, bottom
                            if (solution[i][j - 1] == '/' && solution[i + 1][j - 1] == '\\' && solution[i + 1][j] == '/') {
                                solution[i][j] = '/';
                            }
                        }
                        // left top
                        if (j + 1 < sSize) {
                            // bottom, right bottom, right
                            if (solution[i + 1][j] == '\\' && solution[i + 1][j + 1] == '/' && solution[i][j + 1] == '\\') {
                                solution[i][j] = '\\';
                            }
                        }
                    }
                }
            }
        }

        // collect to string
        StringBuilder sb = new StringBuilder();
        for (char[] aChar : solution) {
            for (char c : aChar) {
                sb.append(c);
            }
            sb.append('\n');
        }
        String s = sb.toString();
        System.out.println(s);
        return s;
    }

    private static boolean isSolved(char[][] solution) {
        for (char[] chars : solution) {
            for (char aChar : chars) {
                if (aChar == '.') return false;
            }
        }
        return true;
    }

    private static void solveOneThree(char[][] solution, int i, int j, char a, char b) {
        if (solution[i][j] == a) {
            solution[i - 1][j - 1] = b;
            solution[i - 1][j] = a;
            solution[i][j - 1] = a;
        } else if (solution[i-1][j] == b) {
            solution[i - 1][j - 1] = b;
            solution[i][j - 1] = a;
            solution[i][j] = b;
        } else if (solution[i][j-1] == b) {
            solution[i - 1][j - 1] = b;
            solution[i - 1][j] = a;
            solution[i][j] = b;
        } else if (solution[i-1][j-1] == a) {
            solution[i - 1][j] = a;
            solution[i][j - 1] = a;
            solution[i][j] = b;
        }
        // inverted
        if (solution[i - 1][j - 1] == b &&
            solution[i - 1][j] == a &&
            solution[i][j - 1] == a) {
            solution[i][j] = a;
        } else if (
            solution[i - 1][j - 1] == b &&
            solution[i][j - 1] == a &&
            solution[i][j] == b){
            solution[i-1][j] = b;
        } else if (
            solution[i - 1][j - 1] == b &&
            solution[i - 1][j] == a &&
            solution[i][j] == b){
            solution[i][j-1] = b;
        } else if (
            solution[i - 1][j] == a &&
            solution[i][j - 1] == a &&
            solution[i][j] == b){
            solution[i-1][j-1] = a;
        }
    }

    private static void solveSide(char[][] solution, int sideNumber, int i1, int i2, int i3, int i4) {
        if (sideNumber == -1) return; // cannot be solved
        if (solution[i1][i2] != '.' && solution[i3][i4] != '.') return; // solved
        if (sideNumber == 0) {
            solution[i1][i2] = '\\';
            solution[i3][i4] = '/';
        } else if (sideNumber == 2) {
            solution[i1][i2] = '/';
            solution[i3][i4] = '\\';
        } else /* == 1 */ {
            if (solution[i1][i2] == '.' && solution[i3][i4] != '.') { // хоть одна стоит
                solution[i1][i2] = solution[i3][i4] == '/' ? '/' : '\\';
            } else if (solution[i1][i2] != '.' && solution[i3][i4] == '.') {
                solution[i3][i4] = solution[i1][i2] == '/' ? '/' : '\\';
            }
        }
    }
}
