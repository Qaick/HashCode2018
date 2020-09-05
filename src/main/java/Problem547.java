import java.util.*;

/**
 * What are the triggers for AA situation?
 */
public class Problem547 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt() + 1;
		in.nextLine();
		String[] strings = new String[n];
		for (int i = 0; i < n; i++) {
			strings[i] = in.nextLine();
		}
		System.out.println(parseAndSolve(strings));
	}

	static String parseAndSolve(String[] strings) {
		int n = strings.length;
		int[][] puzzle = new int[n][n];
		char[] tmp;
		for (int i = 0; i < strings.length; i++) {
			tmp = strings[i].toCharArray();
			for (int j = 0; j < n; j++) {
				puzzle[i][j] = (tmp[j] == '.') ? -1 : tmp[j] - '0';
			}
		}
		return solve(puzzle);
	}

	private static void solveLoops() {
		refreshGroups();
		// iterate over empty cells, requesting connection between diagonal vertexes
		// if there are cells that can create loop I know that solution should be opposite
		for (int i = 0; i < sn; i++) {
			for (int j = 0; j < sn; j++) {
				if (sol[i][j] == '.') {
					connectCellIfLoop(i, j);
				}
			}
		}
		solveWithRotation(() -> solveCycleTwoCells());
	}

	private static void connectCellIfLoop(int i, int j) {
		assert sol[i][j] == '.';
		int topLeft = i * pn + j;
		int botLeft = (i + 1) * pn + j;
		if (areConnected(groups, topLeft, botLeft + 1)) sol[i][j] = '/';
		if (areConnected(groups, topLeft + 1, botLeft)) sol[i][j] = '\\';
	}

	private static void refreshGroups() {
		for (int i = 0; i < groups.length; i++) {
			groups[i] = i;
		}
		for (int i = 0; i < sn; i++) {
			for (int j = 0; j < sn; j++) {
				int leftTop = i * pn + j;
				int leftBot = (i + 1) * pn + j;
				if (sol[i][j] == '/') {
					connect(groups, leftTop + 1, leftBot);
				} else if (sol[i][j] == '\\') {
					connect(groups, leftTop, leftBot + 1);
				}
			}
		}
	}

	private static void solveCycleTwoCells() {
		for (int i = 0; i < sn-1; i++) {
			for (int j = 1; j < sn-1; j++) {
				if (sol[i][j] == '.' && sol[i+1][j] == '.') {
					int topLeft = i * pn + j;
					int botLeft = (i + 2) * pn + j;
					if (areConnected(groups, topLeft, botLeft)) {
						if (puzzle[i+1][j+1] == 3) {
							sol[i][j+1] = '/';
							sol[i+1][j+1] = '\\';
						} else if (puzzle[i+1][j+1] == 2) {
							if (sol[i][j+1] == '\\') {
								sol[i+1][j+1] = '\\';
							} else if (sol[i+1][j+1] == '/') {
								sol[i][j+1] = '/';
							}
						}
					}
				}
			}
		}
	}

	static String solutionToString() {
		StringBuilder sb = new StringBuilder();
		for (char[] aChar : sol) {
			for (char c : aChar) {
				sb.append(c);
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	private static boolean areConnected(int[] arr, int a, int b) {
		int rootA = getRoot(arr, a);
		int rootB = getRoot(arr, b);
		return rootA == rootB;
	}

	static void connect(int[] arr, int a, int b) {
		int rootA = getRoot(arr, a);
		int rootB = getRoot(arr, b);
		if (rootA != rootB) arr[rootB] = rootA; // setting b's root to rootA
	}

	private static int getRoot(int[] arr, int a) {
		while (arr[a] != a) a = arr[a];
		return a;
	}

	static int[][] puzzle;
	static char[][] sol; // solution
	static int[] groups;
	static int pn, sn;
	static List<char[][]> history = new ArrayList<>();
	static List<int[]> historyCell = new ArrayList<>();
	// TODO refer all to here

	static String solve(int[][] _puzzle) {
		puzzle = _puzzle;
		pn = puzzle.length;
		groups = new int[pn * pn];
		sn = pn - 1;
		sol = new char[sn][sn];
		int sLastIdx = sn - 1; // solution last index
		solPreviousCycle = new char[sn][sn];
		Arrays.fill(solPreviousCycle[0], '-');
		for (char[] aChar : sol) {
			Arrays.fill(aChar, '.');
		}
		// solve corners
		if (puzzle[0][0] != -1) sol[0][0] = puzzle[0][0] == 1 ? '\\' : '/';
		if (puzzle[0][sn] != -1) sol[0][sn - 1] = puzzle[0][sn] == 1 ? '/' : '\\';
		if (puzzle[sn][sn] != -1) sol[sn - 1][sn - 1] = puzzle[sn][sn] == 1 ? '\\' : '/';
		if (puzzle[sn][0] != -1) sol[sn - 1][0] = puzzle[sn][0] == 1 ? '/' : '\\';

		for (int i = 1; i < sn; i++) {
			int top = puzzle[0][i];
			solveSideZeroAndTwo(sol, top, 0, i - 1, 0, i);
			int right = puzzle[i][sn];
			solveSideZeroAndTwo(sol, right, i, sLastIdx, i - 1, sLastIdx);
			int bot = puzzle[sn][i];
			solveSideZeroAndTwo(sol, bot, sLastIdx, i, sLastIdx, i - 1);
			int left = puzzle[i][0];
			solveSideZeroAndTwo(sol, left, i - 1, 0, i, 0);
		}

		solveDiagonalOnes();
		solveDoublethinkTwoInRow();

		// check 4 sides. Do it in cycle until there will be no changes?
		// I added 2 tests for top side it gives good guarantee that this part of code is scalable
		while (!isSolved()) {
			for (int i = 1; i < sn; i++) {
				int top = puzzle[0][i];
				solveSideOnes(sol, top, 0, i - 1, 0, i);
				int right = puzzle[i][sn];
				solveSideOnes(sol, right, i, sLastIdx, i - 1, sLastIdx);
				int bot = puzzle[sn][i];
				solveSideOnes(sol, bot, sLastIdx, i, sLastIdx, i - 1);
				int left = puzzle[i][0];
				solveSideOnes(sol, left, i - 1, 0, i, 0);
			}
			// 0 can't be in the middle because it means a circle
			// middle can have 1 2 3 4
			solveMiddle(sn);
			solveLoops();
			solveWithRotation(() -> solveDoublethinkAdvanced());
		}

		return solutionToString();
	}

	private static void solveDiagonalOnes() {
		for (int i = 1; i < pn-2; i++) {
			for (int j = 1; j < pn - 2; j++) {
				if (puzzle[i][j] == 1 && puzzle[i+1][j+1] == 1) sol[i][j] = '/';
				else if (puzzle[i][j+1] == 1 && puzzle[i+1][j] == 1) sol[i][j] = '\\';
			}
		}
	}

	private static char[][] backupSolution(char[][] sol) {
		char[][] a = new char[sol.length][];
		for (int i = 0; i < sol.length; i++) {
			a[i] = Arrays.copyOf(sol[i], sol.length);
		}
		return a;
	}

	private static void solveDoublethinkAdvanced() {
		// 1 on the wall
		for (int i = 1, j = 0; i < pn - 1; i++) {
			if (puzzle[i][j] == 1 && sol[i - 1][j] == '.' && sol[i][j] == '.') {
				bazzinga(i, j + 1);
			}
		}
		// 2 one side empty another full
		for (int i = 1; i < pn - 1; i++) {
			for (int j = 1; j < pn - 1; j++) {
				if (puzzle[i][j] == 2 && sol[i - 1][j] == '.' && sol[i][j] == '.' && sol[i - 1][j - 1] != '.' && sol[i][j - 1] != '.') {
					bazzinga(i, j + 1);
				}
			}
		}
	}

	private static void solveWithRotation(Runnable solve) {
		for (int i = 0; i < 4; i++) {
			solve.run();
			rotatePuzzleAndGroups();
			rotateSol();
		}
	}

	private static void bazzinga(int i, int j) {
		try {
			if (puzzle[i][j] == 1) {
				sol[i - 1][j] = '\\';
				sol[i][j] = '/';
			} else if (puzzle[i][j] == 2) {
				if (sol[i-1][j] != '.' || sol[i][j] != '.') {
					if (sol[i-1][j] == '/') {
						sol[i][j] = '/';
					} else if (sol[i-1][j] == '\\') {
						sol[i][j] = '\\';
					} else if (sol[i][j] == '/') {
						sol[i-1][j] = '/';
					} else if (sol[i][j] == '\\') {
						sol[i-1][j] = '\\';
					}
				}
				bazzinga(i, j + 1);
			} else if (puzzle[i][j] == 3) {
				sol[i - 1][j] = '/';
				sol[i][j] = '\\';
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	static void rotatePuzzleAndGroups() {
		int[][] arr = new int[pn][pn];
		int[] groupsT = new int[pn*pn];
		for (int i = 0; i < pn; i++) {
			for (int j = 0; j < pn; j++) {
				arr[j][pn - i - 1] = puzzle[i][j];
				int group = groups[i * pn + j];
				int ni = group / pn;
				int nj = group % pn;
				groupsT[j * pn + pn - i - 1] = nj * pn + pn - ni - 1;
			}
		}
		puzzle = arr;
		groups = groupsT;
	}

	static void printGroups(int[] g) {
		for (int i = 0; i < pn; i++) {
			for (int j = 0; j < pn; j++) {
				System.out.print(String.format("%1$4s ", g[i * pn + j]));
			}
			System.out.println();
		}
	}

	static void rotateSol() {
		char[][] arr = new char[sn][sn];
		for (int i = 0; i < sn; i++) {
			for (int j = 0; j < sn; j++) {
				arr[j][sn - i - 1] = sol[i][j] == '.' ? '.' : sol[i][j] == '/' ? '\\' : '/';
			}
		}
		sol = arr;
	}

	private static void solveDoublethinkTwoInRow() {
		// horizontal
		for (int i = 1; i < pn - 1; i++) {
			for (int j = 0; j < pn - 1; j++) {
				if ((puzzle[i][j] == 1 && puzzle[i][j+1] == 1) || (puzzle[i][j] == 3 && puzzle[i][j + 1] == 3)) {
					if (j > 0) {
						if (puzzle[i][j] == 1) {
							sol[i - 1][j - 1] = '/';
							sol[i][j - 1] = '\\';
						} else {
							sol[i - 1][j - 1] = '\\';
							sol[i][j - 1] = '/';
						}
					}
					if (j + 1 < sn) {
						if (puzzle[i][j + 1] == 1) {
							sol[i - 1][j + 1] = '\\';
							sol[i][j + 1] = '/';
						} else {
							sol[i - 1][j + 1] = '/';
							sol[i][j + 1] = '\\';
						}
					}
				}
			}
		}
		// vertical
		for (int i = 0; i < pn - 1; i++) {
			for (int j = 1; j < pn - 1; j++) {
				if ((puzzle[i][j] == 1 && puzzle[i+1][j] == 1) || (puzzle[i][j] == 3 && puzzle[i+1][j] == 3)) {
					if (i > 0) {
						if (puzzle[i][j] == 1) {
							sol[i - 1][j - 1] = '/';
							sol[i - 1][j] = '\\';
						} else {
							sol[i - 1][j - 1] = '\\';
							sol[i - 1][j] = '/';
						}
					}
					if (i + 1 < sn) {
						if (puzzle[i+1][j] == 1) {
							sol[i + 1][j - 1] = '\\';
							sol[i + 1][j] = '/';
						} else {
							sol[i + 1][j - 1] = '/';
							sol[i + 1][j] = '\\';
						}
					}
				}
			}
		}
	}

	private static void solveMiddle(int sSize) {
		for (int i = 1; i < sSize; i++) {
			for (int j = 1; j < sSize; j++) {
				// continue if all the squares are filled
				if (sol[i][j] != '.' && sol[i][j - 1] != '.' && sol[i - 1][j] != '.'
						&& sol[i - 1][j - 1] != '.') continue;
				// TODO faster main 4: n*n-4n
				if (puzzle[i][j] == 4) { // draw around
					sol[i - 1][j - 1] = '\\';
					sol[i - 1][j] = '/';
					sol[i][j - 1] = '/';
					sol[i][j] = '\\';
				} else if (puzzle[i][j] == 3) {
					solveOneThree(i, j, '/', '\\');
				} else if (puzzle[i][j] == 2) {
					// if you know half you can find out the second half
					// top
					if (sol[i - 1][j - 1] == '\\' && sol[i - 1][j] == '/') {
						sol[i][j - 1] = '\\';
						sol[i][j] = '/';
					} else if (sol[i - 1][j - 1] == '/' && sol[i - 1][j] == '\\') {
						sol[i][j - 1] = '/';
						sol[i][j] = '\\';
					} else
						// right
						if (sol[i - 1][j] == '\\' && sol[i][j] == '/') {
							sol[i][j - 1] = '/';
							sol[i - 1][j - 1] = '\\';
						} else if (sol[i - 1][j] == '/' && sol[i][j] == '\\') {
							sol[i][j - 1] = '\\';
							sol[i - 1][j - 1] = '/';
						} else
							// bot
							if (sol[i][j] == '\\' && sol[i][j - 1] == '/') {
								sol[i - 1][j - 1] = '/';
								sol[i - 1][j] = '\\';
							} else if (sol[i][j] == '/' && sol[i][j - 1] == '\\') {
								sol[i - 1][j - 1] = '\\';
								sol[i - 1][j] = '/';
							} else
								// left
								if (sol[i][j - 1] == '/' && sol[i - 1][j - 1] == '\\') {
									sol[i - 1][j] = '\\';
									sol[i][j] = '/';
								} else if (sol[i][j - 1] == '\\' && sol[i - 1][j - 1] == '/') {
									sol[i - 1][j] = '/';
									sol[i][j] = '\\';
								} else
									// diagonal /
									if (sol[i][j - 1] == '/' && sol[i - 1][j] == '/') {
										sol[i - 1][j - 1] = '/';
										sol[i][j] = '/';
									} else if (sol[i][j - 1] == '\\' && sol[i - 1][j] == '\\') {
										sol[i - 1][j - 1] = '\\';
										sol[i][j] = '\\';
									} else
										// diagonal \
										if (sol[i - 1][j - 1] == '\\' && sol[i][j] == '\\') {
											sol[i - 1][j] = '\\';
											sol[i][j - 1] = '\\';
										} else if (sol[i - 1][j - 1] == '/' && sol[i][j] == '/') {
											sol[i - 1][j] = '/';
											sol[i][j - 1] = '/';
										}
				} else if (puzzle[i][j] == 1) {
					solveOneThree(i, j, '\\', '/');
				}
			}
		}
	}

	private static char[][] solPreviousCycle;

	private static boolean isSolved() {
		for (int i = 0; i < sol.length; i++) {
			if (!Arrays.equals(sol[i], solPreviousCycle[i])) {
				solPreviousCycle = backupSolution(sol);
				return false;
			}
		}
		{
			if (hasLoops() || hasNumberProblems()) {
				// I brake this solution, I need to revert it to the latest good one
				while(true) {
					int[] ints = historyCell.get(historyCell.size() - 1);
					int i = ints[0];
					int j = ints[1];
					char value = sol[i][j];
					if (value == '/') {
						sol = history.get(history.size() - 1);
						sol[i][j] = '\\';
						return false;
					}
					// else is '\\', means for this cell I already checked 2 values
					// I need to jump one step back in the history
					history.remove(history.size() - 1);
					historyCell.remove(historyCell.size() - 1);
				}
			}
			// time for extreme move if it has empty cells
			for (int i=0; i < sn; i++) {
				for (int j=0; j < sn; j++) {
					if (sol[i][j] == '.') {
						history.add(backupSolution(sol));
						historyCell.add(new int[]{i, j});
						sol[i][j] = '/';
						solPreviousCycle = backupSolution(sol);
						return false;
					}
				}
			}
		}
		return true;
	}

	static void randomMove() {
		for (int i = 0; i < sn-1; i++) {
			for (int j = 0; j < sn - 1; j++) {
			}
		}
	}

	private static void solveOneThree(int i, int j, char a, char b) {
		if (sol[i][j] == a) {
			sol[i - 1][j - 1] = b;
			sol[i - 1][j] = a;
			sol[i][j - 1] = a;
		} else if (sol[i - 1][j] == b) {
			sol[i - 1][j - 1] = b;
			sol[i][j - 1] = a;
			sol[i][j] = b;
		} else if (sol[i][j - 1] == b) {
			sol[i - 1][j - 1] = b;
			sol[i - 1][j] = a;
			sol[i][j] = b;
		} else if (sol[i - 1][j - 1] == a) {
			sol[i - 1][j] = a;
			sol[i][j - 1] = a;
			sol[i][j] = b;
		}
		// inverted
		if (sol[i - 1][j - 1] == b &&
				sol[i - 1][j] == a &&
				sol[i][j - 1] == a) {
			sol[i][j] = a;
		} else if (
				sol[i - 1][j - 1] == b &&
						sol[i][j - 1] == a &&
						sol[i][j] == b) {
			sol[i - 1][j] = b;
		} else if (
				sol[i - 1][j - 1] == b &&
						sol[i - 1][j] == a &&
						sol[i][j] == b) {
			sol[i][j - 1] = b;
		} else if (
				sol[i - 1][j] == a &&
						sol[i][j - 1] == a &&
						sol[i][j] == b) {
			sol[i - 1][j - 1] = a;
		}
	}

	private static void solveSideZeroAndTwo(char[][] solution, int sideNumber, int i1, int i2, int i3, int i4) {
		if (sideNumber == -1) return;
		if (solution[i1][i2] != '.' && solution[i3][i4] != '.') return;
		if (sideNumber == 0) {
			solution[i1][i2] = '\\';
			solution[i3][i4] = '/';
		} else if (sideNumber == 2) {
			solution[i1][i2] = '/';
			solution[i3][i4] = '\\';
		}
	}

	private static void solveSideOnes(char[][] solution, int sideNumber, int i1, int i2, int i3, int i4) {
		if (sideNumber == -1) return;
		if (solution[i1][i2] != '.' && solution[i3][i4] != '.') return;
		if (sideNumber == 1) {
			if (solution[i1][i2] == '.' && solution[i3][i4] != '.') { // хоть одна стоит
				solution[i1][i2] = solution[i3][i4] == '/' ? '/' : '\\';
			} else if (solution[i1][i2] != '.' && solution[i3][i4] == '.') {
				solution[i3][i4] = solution[i1][i2] == '/' ? '/' : '\\';
			}
		}
	}

	static boolean hasLoops() {
		/* For me it's important to solve this puzzle. To find a way to solve this puzzle. I concentrate on this problem.
		I have a solution that could be wrong.
		I have memory. Memory is currently is not a problem.
		I need to answer on the question: does my solution has a loop or not. I can solve it with low performance.
		*/
		// my solution will be next: I will iterate over all the cells
		for (int i = 0; i < sn; i++) {
			for (int j = 0; j < sn; j++) {
				if (sol[i][j] != '.') {
					char savedValue = sol[i][j];
					sol[i][j] = '.';
					refreshGroups();
					connectCellIfLoop(i, j);
					if (sol[i][j]!='.' && sol[i][j] != savedValue) {
						sol[i][j] = savedValue;
//						System.out.println("has loops "+ i +" " + j);
						return true;
					}
					sol[i][j] = savedValue;
				}
			}
		}
		return false;
	}

	static boolean hasNumberProblems() {
		for (int i = 0; i < pn; i++) {
			for (int j = 0; j < pn; j++) {
				if (puzzle[i][j] != -1) { // 0 1 2 3 4
					// calculate how many cells go in this cell
					// here can be 2 situations
					// 1: there are empty cells in the solution
					//    break when there are too much points, but don't break when there are not enough
					// 2: there are no empty cells in the solution
					//    break when cell has not enough points or too much
					// buy soy soy, and morning food
					int sum = 0;
					if (i>0 && j>0 && sol[i-1][j-1] == '\\') sum++;
					if (i>0 && j<sn && sol[i-1][j] == '/') sum++;
					if (i<sn && j>0 && sol[i][j-1] == '/') sum++;
					if (i<sn && j<sn && sol[i][j] == '\\') sum++;
					boolean hasEmpty = false;
					if (i>0 && j>0 && sol[i-1][j-1] == '.') hasEmpty = true;
					if (i>0 && j<sn && sol[i-1][j] == '.') hasEmpty = true;
					if (i<sn && j>0 && sol[i][j-1] == '.') hasEmpty = true;
					if (i<sn && j<sn && sol[i][j] == '.') hasEmpty = true;

					if(!hasEmpty && sum != puzzle[i][j]) {
//						System.out.println("number problem, no empty");
						return true;
					}
					if (hasEmpty && sum > puzzle[i][j]) {
//						System.out.println("number problem, has empty");
						return true;
					}
				}
			}
		}
		return false;
	}
}
