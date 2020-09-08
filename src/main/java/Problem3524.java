import java.util.*;

public class Problem3524 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String s;
		while (!(s = in.nextLine()).equals("end")) {
			System.out.println(solveSudoku(s));
		}
	}
	private static int[][] backupSolution(int[][] sol) {
		int[][] a = new int[sol.length][];
		for (int i = 0; i < sol.length; i++) {
			a[i] = Arrays.copyOf(sol[i], sol.length);
		}
		return a;
	}
	private static Set<Integer>[][] backupSolution(Set<Integer>[][] sol) {
		Set<Integer>[][] a = new Set[sol.length][sol.length];
		for (int i = 0; i < sol.length; i++) {
			for (int j = 0; j < sol.length; j++) {
				a[i][j] = new HashSet<>(sol[i][j]);
			}
		}
		return a;
	}
	static int[][] convertToArray(String s) {
		int[][] arr = new int[9][9];
		char[] s2 = s.toCharArray();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				char c = s2[i * 9 + j];
				arr[i][j] = Integer.parseInt("" + c);
			}
		}
		return arr;
	}
	static Set<Integer>[][] sol;
	static List<Set<Integer>[][]> historySol;
	static int[][] solution;
	static List<int[][]> historySolution;
	static List<int[]> history;
	static int full;
	static String solveSudoku(String s) {
		solution = new int[9][9];
		historySolution = new ArrayList<>();
		sol = new Set[9][9];
		historySol = new ArrayList<>();
		history = new ArrayList<>();
		full = 81;

		{
			char[] s2 = s.toCharArray();
			Set<Integer> set = new HashSet<>();
			for (int i = 1; i <= 9; i++) {
				set.add(i);
			}
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					sol[i][j] = new HashSet<>(set);
				}
			}
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					char c = s2[i * 9 + j];
					if (c != '.') queueAdd(i, j, Integer.parseInt("" + c));
				}
			}
		}
		int prevFull = 0;
		while(!isSolved()) {
			if (prevFull == full) { // this means I don't know what to do
				// trying to apply try/fail approach. I need to backup
				if (historyContinue()) {
					prevFull++;
					continue;
				}
				break;
			}
			prevFull = full;
			// look for unique number in the row/column/square
			for (int i = 0; i < 9; i++) {
				int[] row = new int[9];
				int[] col = new int[9];
				int[] sq = new int[9];
				int ca = i/3*3, cb = i%3*3; // 0 00 1 03 2 06 3 30 4 33 5 36 6 60 7 63 8 66 i-тый сквер
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
							if (sol[i][k].contains(number)) queueAdd(i,k, number);
						}
					}
					if (col[number-1] == 1) {
						for (int k = 0; k < 9; k++) {
							if (sol[k][i].contains(number)) queueAdd(k,i, number);
						}
					}
					if (sq[number-1] == 1) {
						for (int k = 0; k < 9; k++) {
							int a = k / 3, b = k % 3;
//                            System.out.printf("%d %d - %d %d - %d %d\n", a, b, ca, cb, ca+a, cb+b);
							if (sol[ca+a][cb+b].contains(number)) queueAdd(ca+a,cb+b, number);
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

			solveCandidateCol();
			rotate();
			solveCandidateCol();
			rotate();
			rotate();
			rotate();

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
//        System.out.println(Arrays.toString(vals));
//        System.out.println(Arrays.toString(vals2));
		return sb.toString();
	}

	private static boolean isSolved() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (solution[i][j] == 0) return false;
			}
		}
		if (!isSolutionValid(solution)) {
			return false;
		}
		return true;
	}

	private static boolean historyContinue() {
		if (isSolutionValid(solution))
		{
			historySol.add(backupSolution(sol));
			historySolution.add(backupSolution(solution));
			// set some value
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (!sol[i][j].isEmpty()) {
						Integer next = sol[i][j].iterator().next();
						int[] e = {i, j, next};
						history.add(e);
						queueAdd(i, j, next);
						return true;
					}
				}
			}
			assert false;
		}
		else {
			while(true) {
				// solution is not valid, need to reset to the last backup
				sol = backupSolution(historySol.get(historySol.size() - 1));
				solution = backupSolution(historySolution.get(historySolution.size() - 1));
				// set next value
				int[] history1 = history.remove(history.size() - 1);
				Iterator<Integer> iterator = sol[history1[0]][history1[1]].iterator();
				while (iterator.hasNext()) {
					if (iterator.next() == history1[2] && iterator.hasNext()) {
						Integer next = iterator.next();
						int[] e = {history1[0], history1[1], next};
						history.add(e);
						queueAdd(history1[0], history1[1], next);
						return true;
					}
				}
				for (int i = history1[0]; i < 9; i++) {
					for (int j = i == history1[0] ? history1[1] + 1 : 0; j < 9; j++) {
						if (!sol[i][j].isEmpty()) {
							Integer next = sol[i][j].iterator().next();
							int[] e = {i, j, next};
							history.add(e);
							queueAdd(i, j, next);
							return true;
						}
					}
				}
				historySolution.remove(historySolution.size() - 1);
				historySol.remove(historySol.size() - 1);
			}
		}
		return false;
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
		int sn = 9;
		Set<Integer>[][] arr = new Set[sn][sn];
		int[][] arr2 = new int[sn][sn];
		for (int i = 0; i < sn; i++) {
			for (int j = 0; j < sn; j++) {
				arr[j][sn - i - 1] = sol[i][j];
				arr2[j][sn-i-1]=solution[i][j];
			}
		}
		sol = arr;
		solution = arr2;
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

	private static void solveCandidateCol() {
		// row/col has value only in the sq cells of this row/col -> remove values from the other cells of the sq
		// it's actually the same as for square but
		for (int squareRow = 0; squareRow < 3; squareRow++) {
			for (int squareCol = 0; squareCol < 3; squareCol++) {
//                System.out.println("squareCol = " + squareCol);
				for (int cellCol = 0; cellCol < 3; cellCol++) {
//                    System.out.println("cellCol = " + cellCol);
					Set<Integer> colValues = new HashSet<>();
					int cellCol2 = squareCol * 3 + cellCol;
					int[] rowIdx = leftIndexes.get(squareRow);
					int sqRowStart = rowIdx[0], sqRowEnd = rowIdx[1];
					int[] leftColumns = leftThings.get(cellCol);
					int remove1 = squareCol * 3 +leftColumns[0], remove2 = squareCol * 3 +leftColumns[1];
					{
						for (int i = sqRowStart; i < sqRowEnd; i++) {
//                            System.out.println("col add : " + i + cellCol2);
							colValues.addAll(sol[i][cellCol2]);
						}
						for (int i = rowIdx[2]; i < rowIdx[3]; i++) {
//                            System.out.printf("col remove all : %d\n", i);
							colValues.removeAll(sol[i][cellCol2]);
						}
						for (int i = rowIdx[4]; i < rowIdx[5]; i++) {
//                            System.out.printf("col remove all 2 : %d\n", i);
							colValues.removeAll(sol[i][cellCol2]);
						}
					}
					if (!colValues.isEmpty()) {
//                        System.out.println("there is a way column " + colValues);
						for (int i = sqRowStart; i < sqRowEnd; i++) {
//                            System.out.printf("col remove : %d %d %d\n", i, remove1, remove2);
							removeAll(i, remove1, colValues);
							removeAll(i, remove2, colValues);
						}
					}
				}
			}
		}
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
//            System.out.printf("%d %d %d\n", i, j, next);
			queueAdd(i, j, next);
		}
	}

	static void queueAdd(int i, int j, int k) {
		if (solution[i][j] != 0) {
			assert solution[i][j] == k : solution[i][j] + " " + k;
			return;
		}
		solution[i][j] = k; // the only place where solution is placed
		full--;
		removeAfterSolutionPasted(i, j, k);
	}

	static void removeAll(int i, int j, Collection<Integer> k) {
		for (Integer integer : k) {
			removeEl(i,j,integer);
		}
	}

	static boolean isSolutionValid(int[][] solution) {
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution.length; j++) {
				int val = solution[i][j];
				if (val == 0) continue; // solution is not complete yet
				int ca = i/3*3, cb = j/3*3; // 0 00 1 03 2 06 3 30 4 33 5 36 6 60 7 63 8 66
				for (int k = 0; k < solution.length; k++) {
					if (k!=j && val == solution[i][k]) {
						return false; // match with column value
					}
					if (k!=i && val == solution[k][j]) {
						return false; // match with row value
					}
					int ka = ca + k / 3, kb = cb + k % 3;
					if (!(ka==i && kb==j) && val == solution[ka][kb]) {
						return false; // match with sq value
					}
				}
			}
		}
		return true;
	}
}
