import java.util.Arrays;
import java.util.Scanner;

public class Problem547 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        int[][] map = new int[n][n];
        char[] tmp;
        for (int i = 0; i < n; i++) {
            tmp = in.nextLine().toCharArray();
            for (int j = 0; j < n; j++) {
                map[i][j] = (tmp[j] == '.') ? -1 : tmp[j] - '0';
            }
        }
        System.out.println(solve(map));
    }

    static String solve(int[][] map) {
        int m = map.length-1;
        char[][] chars = new char[m][m];
        for (char[] aChar : chars) {
            Arrays.fill(aChar, '.');
        }
        // check every 4 corners. If corner contain 1 or 0 I know the solve
        if (map[0][0] !=-1) chars[0][0] = map[0][0] == 1 ? '\\' : '/';
        if (map[0][m] !=-1) chars[0][m-1] = map[0][m] == 1 ? '/' : '\\';
        if (map[m][0] !=-1) chars[m-1][0] = map[m][0] == 1 ? '/' : '\\';
        if (map[m][m] !=-1) chars[m-1][m-1] = map[m][m] == 1 ? '\\' : '/';

        // check 4 sides. Do it in cycle until there will be no changes?
        boolean changes = true;
        int m2 = m-1;
        while(changes) {
            changes = false;
            for (int i = 1; i < m; i++) {
                int top = map[0][i];
                changes = isChanges(chars, changes, top, 0, i, 0, i - 1);
                int right = map[i][m];
                changes = isChanges(chars, changes, right, i - 1, m2, i, m2);
                int bot = map[m][i];
                changes = isChanges(chars, changes, bot, m2, i, m2, i - 1);
                int left = map[i][0];
                changes = isChanges(chars, changes, left, i - 1, 0, i, 0);
            }
        }

        // what next? - handle middle part

        // handle circles. result should not contain circles

        // collect to string
        StringBuilder sb = new StringBuilder();
        for (char[] aChar : chars) {
            for (char c : aChar) {
                sb.append(c);
            }
            sb.append('\n');
        }
        String s = sb.toString();
        System.out.println(s);
        return s;
    }

    private static boolean isChanges(char[][] chars, boolean c, int side, int i1, int i2, int i3, int i4) {
        if (side != -1) {
            if (side == 0) {
                if (chars[i1][i4] == '.' || chars[i3][i2] == '.')
                    c = true;
                chars[i1][i4] = '\\';
                chars[i3][i2] = '/';
            } else if (side == 2) {
                if (chars[i1][i4] == '.' || chars[i3][i2] == '.')
                    c = true;
                chars[i1][i4] = '/';
                chars[i3][i2] = '\\';
            } else /* == 1 */ {
                if (chars[i1][i4] == '.' && chars[i3][i2] != '.') { // хоть одна стоит
                    chars[i1][i4] = chars[i3][i2] == '/' ? '/' : '\\';
                    c = true;
                } else if (chars[i1][i4] != '.' && chars[i3][i2] == '.') {
                    chars[i3][i2] = chars[i1][i4] == '/' ? '/' : '\\';
                    c = true;
                }
            }
        }
        return c;
    }
}
