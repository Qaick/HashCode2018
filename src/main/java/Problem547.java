import java.util.Arrays;
import java.util.Scanner;

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

	private static void solveCircles() {
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
		// iterate over empty cells, requesting connection between diagonal vertexes
		for (int i = 0; i < sn; i++) {
			for (int j = 0; j < sn; j++) {
				if (sol[i][j] == '.') {
					int topLeft = i * pn + j;
					int botLeft = (i + 1) * pn + j;
					if (areConnected(groups, topLeft, botLeft + 1)) sol[i][j] = '/';
					if (areConnected(groups, topLeft + 1, botLeft)) sol[i][j] = '\\';
				}
			}
		}
		solveWithRotation(() -> solveCycleTwoCells());
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
		// check 4 corners. If corner contain 1 or 0 I know the solve
		if (puzzle[0][0] != -1) sol[0][0] = puzzle[0][0] == 1 ? '\\' : '/';
		if (puzzle[0][sn] != -1) sol[0][sn - 1] = puzzle[0][sn] == 1 ? '/' : '\\';
		if (puzzle[sn][sn] != -1) sol[sn - 1][sn - 1] = puzzle[sn][sn] == 1 ? '\\' : '/';
		if (puzzle[sn][0] != -1) sol[sn - 1][0] = puzzle[sn][0] == 1 ? '/' : '\\';

		for (int i = 1; i < sn; i++) {
			int top = puzzle[0][i];
			solveSideOnce(sol, top, 0, i - 1, 0, i);
			int right = puzzle[i][sn];
			solveSideOnce(sol, right, i, sLastIdx, i - 1, sLastIdx);
			int bot = puzzle[sn][i];
			solveSideOnce(sol, bot, sLastIdx, i, sLastIdx, i - 1);
			int left = puzzle[i][0];
			solveSideOnce(sol, left, i - 1, 0, i, 0);
		}

		// check 4 sides. Do it in cycle until there will be no changes?
		// I added 2 tests for top side it gives good guarantee that this part of code is scalable
		while (!isSolved()) {
			for (int i = 1; i < sn; i++) {
				int top = puzzle[0][i];
				solveSide(sol, top, 0, i - 1, 0, i);
				int right = puzzle[i][sn];
				solveSide(sol, right, i, sLastIdx, i - 1, sLastIdx);
				int bot = puzzle[sn][i];
				solveSide(sol, bot, sLastIdx, i, sLastIdx, i - 1);
				int left = puzzle[i][0];
				solveSide(sol, left, i - 1, 0, i, 0);
			}
			// 0 can't be in the middle because it means a circle
			// middle can have 1 2 3 4
			solveMiddle(sn);
			solveCircles();
			solveDoublethinkTwoOnes(puzzle, sol);
			solveWithRotation(() -> solveDoublethinkAdvanced());
		}

		return solutionToString();
	}

	private static void backupSolution() {
		solPreviousCycle = new char[sol.length][];
		for (int i = 0; i < sol.length; i++) {
			solPreviousCycle[i] = Arrays.copyOf(sol[i], sol.length);
		}
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

	private static void solveDoublethinkTwoOnes(int[][] puzzle, char[][] solution) {
		// horizontal
		for (int i = 1; i < puzzle.length - 1; i++) {
			for (int j = 0; j < puzzle[0].length - 1; j++) {
				if (puzzle[i][j] == 1 && puzzle[i][j + 1] == 1) {
					if (j > 0) {
						solution[i - 1][j - 1] = '/';
						solution[i][j - 1] = '\\';
					}
					if (j + 1 < solution[0].length) {
						solution[i - 1][j + 1] = '\\';
						solution[i][j + 1] = '/';
					}
				}
			}
		}
		// vertical
		for (int i = 0; i < puzzle.length - 1; i++) {
			for (int j = 1; j < puzzle[0].length - 1; j++) {
				if (puzzle[i][j] == 1 && puzzle[i + 1][j] == 1) {
					if (i > 0) {
						solution[i - 1][j - 1] = '/';
						solution[i - 1][j] = '\\';
					}
					if (i + 1 < solution.length) {
						solution[i + 1][j - 1] = '\\';
						solution[i + 1][j] = '/';
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
				backupSolution();
				return false;
			}
		}
		return true;
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

	private static void solveSideOnce(char[][] solution, int sideNumber, int i1, int i2, int i3, int i4) {
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

	private static void solveSide(char[][] solution, int sideNumber, int i1, int i2, int i3, int i4) {
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
}
