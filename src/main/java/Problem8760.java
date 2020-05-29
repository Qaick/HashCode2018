import java.util.Scanner;

public class Problem8760 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int vertexes =in.nextInt();
        int edges = in.nextInt();
        int[][] edgesMap = new int[vertexes][vertexes]; // transfers
        int a,b;
        for (int i = 0; i < edges; i++) {
            a = in.nextInt();
            b = in.nextInt();
            edgesMap[a][b] = 1;
            edgesMap[b][a] = 1;
        }
        int start = in.nextInt();
        System.out.println(dfs(edgesMap, start));
    }
    static String dfs(int[][] map, int start) {
        return "";
    }
}
