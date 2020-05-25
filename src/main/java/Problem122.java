import java.util.*;

public class Problem122 {
    static Map<Integer, Set<Integer>> map = new HashMap<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numberOfBases = in.nextInt(),
                roads = in.nextInt(),
                start = in.nextInt(),
                end = in.nextInt(),
                distance = in.nextInt();
        for (int i = 1; i <= numberOfBases; i++) {
            map.put(i, new HashSet<>());
        }
        for (int i = 0; i < roads; i++) {
            int start1 = in.nextInt();
            map.get(start1).add(in.nextInt());
        }
        System.out.println(paths(start, end, distance, new HashSet<>()));
    }

    static int paths(int start, int end, int distance, Set<Integer> visited) {
        if (start == end) return 1;
        if (distance == 0) return 0;
        if (!visited.add(start)) return 0; // remove cycles
        Set<Integer> set = map.get(start);
        int sum = 0;
        for (Integer integer : set) {
            sum += paths(integer, end, distance - 1, new HashSet<>(visited));
        }
        return sum;
    }
}
