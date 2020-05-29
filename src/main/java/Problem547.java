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
        while(changes) {
            changes = false;
            for (int i = 1; i < m - 1; i++) {
                int top = map[0][i]; // 0 1 2
                if (top != -1) {
                    if (top == 0) {
                        if (chars[0][i - 1] == '.' || chars[0][i] == '.')
                            changes = true;
                        chars[0][i - 1] = '\\';
                        chars[0][i] = '/';
                    } else if (top == 2) {
                        if (chars[0][i - 1] == '.' || chars[0][i] == '.')
                            changes = true;
                        chars[0][i - 1] = '/';
                        chars[0][i] = '\\';
                    } else /* == 1 */ {
                        if (chars[0][i - 1] == '.' && chars[0][i] != '.') { // хоть одна стоит
                            chars[0][i - 1] = chars[0][i] == '/' ? '/' : '\\';
                            changes = true;
                        } else if (chars[0][i - 1] != '.' && chars[0][i] == '.') {
                            chars[0][i] = chars[0][i - 1] == '/' ? '/' : '\\';
                            changes = true;
                        }
                    }
                }
                int left = map[i][0]; // 0 1 2
                if (left != -1) {
                    if (left == 0) {
                        if (chars[i - 1][0] == '.' || chars[i][0] == '.')
                            changes = true;
                        chars[i-1][0] = '\\';
                        chars[i][0] = '/';
                    } else if (left == 2) {
                        if (chars[i - 1][0] == '.' || chars[i][0] == '.')
                            changes = true;
                        chars[i-1][0] = '/';
                        chars[i][0] = '\\';
                    } else /* == 1 */ {
                        if (chars[i-1][0] == '.' && chars[i][0] != '.') { // хоть одна стоит
                            chars[i-1][0] = chars[i][0] == '/' ? '/' : '\\';
                            changes = true;
                        } else if (chars[i-1][0] != '.' && chars[i][0] == '.') {
                            chars[i][0] = chars[i-1][0] == '/' ? '/' : '\\';
                            changes = true;
                        }
                    }
                }
            }
        }

        // what next?

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
}
