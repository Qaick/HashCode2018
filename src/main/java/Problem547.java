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
        System.out.println(meth(map));
    }

    static int meth(int[][] map) {
        return 0;
    }
}
