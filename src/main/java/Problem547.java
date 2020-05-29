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
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 1; i < sSize; i++) {
                int top = puzzle[0][i];
                changed = solveSide(solution, changed, top, 0, i - 1, 0, i);
                int right = puzzle[i][sSize];
                changed = solveSide(solution, changed, right, i, sLastIdx, i - 1, sLastIdx);
                int bot = puzzle[sSize][i];
                changed = solveSide(solution, changed, bot, sLastIdx, i, sLastIdx, i - 1);
                int left = puzzle[i][0];
                changed = solveSide(solution, changed, left, i - 1, 0, i, 0);
            }
        }

        // handle middle part
        // 0 can't be in the middle because it means a circle
        // middle can have 1 2 3 4
        for (int i = 1; i < sSize; i++) {
            for (int j = 1; j < sSize; j++) {
                // if (puzzle[i][j] == 4) // draw around
                // 3
                // 2
                // 1
            }
        }

        // у меня появилась идея что все эти / и \ можно закодировать автоматически
        // по парности проверять. В теории это должно много проблем и лишнего кода упростить

        // handle circles. result should not contain circles
        for (int i = 0; i < sSize; i++) {
            for (int j = 0; j < sSize; j++) {
                // top 2 cases
                // bottom 2 cases
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

    private static boolean solveSide(char[][] solution, boolean changed, int sideNumber, int i1, int i2, int i3, int i4) {
        if (sideNumber != -1) {
            if (sideNumber == 0) {
                if (solution[i1][i2] == '.' || solution[i3][i4] == '.')
                    changed = true;
                solution[i1][i2] = '\\';
                solution[i3][i4] = '/';
            } else if (sideNumber == 2) {
                if (solution[i1][i2] == '.' || solution[i3][i4] == '.')
                    changed = true;
                solution[i1][i2] = '/';
                solution[i3][i4] = '\\';
            } else /* == 1 */ {
                if (solution[i1][i2] == '.' && solution[i3][i4] != '.') { // хоть одна стоит
                    solution[i1][i2] = solution[i3][i4] == '/' ? '/' : '\\';
                    changed = true;
                } else if (solution[i1][i2] != '.' && solution[i3][i4] == '.') {
                    solution[i3][i4] = solution[i1][i2] == '/' ? '/' : '\\';
                    changed = true;
                }
            }
        }
        return changed;
    }
}
