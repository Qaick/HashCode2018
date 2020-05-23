import java.util.Scanner;

public class Problem16 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int legsMyriapoda = in.nextInt(); // s ног у многоножки
        int headsDragon = in.nextInt(); // k глав у дракона
        int headsAll = in.nextInt(); // a вместе глав
        int legsAll = in.nextInt(); // b вместе ног
        // headsAll = headsDragon * d + m
        // legsAll = legsDragon * d + legsMyriapoda * m
        int legsDragon = findDragonLegs(legsMyriapoda, headsDragon, headsAll, legsAll); // ног у дракона
        System.out.println(legsDragon);
    }

    /**
     * I have a solution but it's poor.
     * 0 0 0 0 could not be.
     * This solution expect that there could be no dragons and no myriapodas. Let's say it's a wrong answer?
     */
    public static int findDragonLegs(int legsMyriapoda, int headsDragon, int headsAll, int legsAll) {
        int maxD = headsAll / headsDragon; // always decremented by 1, smaller than maxM
        for (int d = maxD; d > 1; d--) { // dragons, at least one
            int m = headsAll - headsDragon * d; // always integer, number of myriapodas
            if (m < 1) continue; // at least one myriapoda
            int v = legsAll - legsMyriapoda * m;
            if (v < 0) continue;
            if (v % d == 0) return v / d;
        }
        return -1;
    }
}
