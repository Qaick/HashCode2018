import java.util.Arrays;
import java.util.Scanner;

/**
 * Главное замечание, мои тесты проходят либо за 100ms + ~20 либо больше 2 секунд
 * что наталкивает на мысль что проблема не в медленной работе а в бесконечном цыкле
 * Я взять образец задачи с википедии и понял что закрытый цыкл посложнее может быть!!!
 * TODO нахождение ситуаций АА и простановка троек и единиц
 */
public class Problem547 {
	static int loopsCounterForTests = -1;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt() + 1;
		in.nextLine();
		String[] strings = new String[n];
		for (int i = 0; i < n; i++) {
			strings[i] = in.nextLine();
		}
		System.out.println(solve2(strings));
	}

	static String solve2(String[] strings) {
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

	private static void solveBigClosedCircles(char[][] solution) {
		int arrN = solution.length + 1;
		int[] arr = new int[arrN * arrN];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution[0].length; j++) {
				int leftTop = i * arrN + j;
				int leftBot = (i + 1) * arrN + j;
				if (solution[i][j] == '/') {
					connect(arr, leftTop + 1, leftBot);
				} else if (solution[i][j] == '\\') {
					connect(arr, leftTop, leftBot + 1);
				}
			}
		}
		// iterate over empty cells, requesting connection between diagonal vertexes
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution[0].length; j++) {
				if (solution[i][j] == '.') {
					int topLeft = i * arrN + j;
					int botLeft = (i + 1) * arrN + j;
					if (areConnected(arr, topLeft, botLeft + 1)) solution[i][j] = '/';
					if (areConnected(arr, topLeft + 1, botLeft)) solution[i][j] = '\\';
				}
			}
		}
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
	static int pn, sn;

	static String solve(int[][] _puzzle) {
		puzzle = _puzzle;
		pn = puzzle.length;
		sn = pn - 1;
		int sLastIdx = sn - 1; // solution last index
		sol = new char[sn][sn];
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
		while (!isSolved(sol)) {
			for (int i = 1; i < sn; i++) {
				int top = puzzle[0][i];
				solveSideLoop(sol, top, 0, i - 1, 0, i);
				int right = puzzle[i][sn];
				solveSideLoop(sol, right, i, sLastIdx, i - 1, sLastIdx);
				int bot = puzzle[sn][i];
				solveSideLoop(sol, bot, sLastIdx, i, sLastIdx, i - 1);
				int left = puzzle[i][0];
				solveSideLoop(sol, left, i - 1, 0, i, 0);
			}
			// handle middle part
			// 0 can't be in the middle because it means a circle
			// middle can have 1 2 3 4
			solveMiddleLoop(sn);
			// у меня появилась идея что все эти / и \ можно закодировать автоматически
			// по парности проверять. В теории это должно много проблем и лишнего кода упростить

			// result should not contain circles
			solveCirclesLoop(sn, sol);
			solveBigClosedCircles(sol);
			solveDoublethink(puzzle, sol);
			solveDoublethinkAdvancedFull();
		}

		// collect to string
		StringBuilder sb = new StringBuilder();
		for (char[] aChar : sol) {
			for (char c : aChar) {
				sb.append(c);
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	private static void solveDoublethinkAdvanced() {
		// 1 on the wall
		for (int i = 1, j = 0; i < puzzle.length - 1; i++) {
			if (puzzle[i][j] == 1 && sol[i - 1][j] == '.' && sol[i][j] == '.') {
				bazzinga(i, j + 1);
			}
		}
		// 2 one side empty another full
		for (int i = 1; i < puzzle.length - 1; i++) {
			for (int j = 1; j < puzzle.length - 1; j++) {
				if (puzzle[i][j] == 2 && sol[i - 1][j] == '.' && sol[i][j] == '.' && sol[i - 1][j - 1] != '.' && sol[i][j - 1] != '.') {
					bazzinga(i, j + 1);
				}
			}
		}
	}

	private static void solveDoublethinkAdvancedFull() {
		for (int i = 0; i < 4; i++) {
			solveDoublethinkAdvanced();
			rotate();
		}
	}

	private static void bazzinga(int i, int j) {
		try {
			if (puzzle[i][j] == 1) {
				sol[i - 1][j] = '\\';
				sol[i][j] = '/';
			} else if (puzzle[i][j] == 2) {
				// TODO check state of the cells and do something
				bazzinga(i, j + 1);
			} else if (puzzle[i][j] == 3) {
				sol[i - 1][j] = '/';
				sol[i][j] = '\\';
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	static void rotate() {
		int n = puzzle.length;
		int[][] arr2 = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				arr2[j][n - i - 1] = puzzle[i][j];
			}
		}
		puzzle = arr2;
		sol = rotate(sol);
	}

	static char[][] rotate(char[][] arr) {
		int n = arr.length;
		char[][] arr2 = new char[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				arr2[j][n - i - 1] = arr[i][j] == '.' ? '.' : arr[i][j] == '/' ? '\\' : '/';
			}
		}
		return arr2;
	}

	private static void solveDoublethink(int[][] puzzle, char[][] solution) {
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

	private static void solveMiddleLoop(int sSize) {
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
					solveOneThree(sol, i, j, '/', '\\');
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
					solveOneThree(sol, i, j, '\\', '/');
				}
			}
		}
	}

	// TODO faster circle: n+n+n*n*(2..4)
	private static void solveCirclesLoop(int sSize, char[][] solution) {
		for (int i = 0; i < sSize; i++) {
			for (int j = 0; j < sSize; j++) {
				if (i - 1 >= 0) {
					// bottom right
					if (j - 1 >= 0) {
						// left, left top, top
						if (solution[i][j - 1] == '\\' && solution[i - 1][j - 1] == '/' && solution[i - 1][j] == '\\') {
							solution[i][j] = '\\';
						}
					}
					// bottom left
					if (j + 1 < sSize) {
						if (solution[i - 1][j] == '/' && solution[i - 1][j + 1] == '\\' && solution[i][j + 1] == '/') {
							solution[i][j] = '/';
						}
					}
				}
				if (i + 1 < sSize) {
					// right top
					if (j - 1 >= 0) {
						// left, left bottom, bottom
						if (solution[i][j - 1] == '/' && solution[i + 1][j - 1] == '\\' && solution[i + 1][j] == '/') {
							solution[i][j] = '/';
						}
					}
					// left top
					if (j + 1 < sSize) {
						// bottom, right bottom, right
						if (solution[i + 1][j] == '\\' && solution[i + 1][j + 1] == '/' && solution[i][j + 1] == '\\') {
							solution[i][j] = '\\';
						}
					}
				}
			}
		}
	}

	private static boolean isSolved(char[][] solution) {
		if (loopsCounterForTests != -1) return loopsCounterForTests-- <= 0;
		for (char[] chars : solution) {
			for (char aChar : chars) {
				if (aChar == '.') return false;
			}
		}
		return true;
	}

	private static void solveOneThree(char[][] solution, int i, int j, char a, char b) {
		if (solution[i][j] == a) {
			solution[i - 1][j - 1] = b;
			solution[i - 1][j] = a;
			solution[i][j - 1] = a;
		} else if (solution[i - 1][j] == b) {
			solution[i - 1][j - 1] = b;
			solution[i][j - 1] = a;
			solution[i][j] = b;
		} else if (solution[i][j - 1] == b) {
			solution[i - 1][j - 1] = b;
			solution[i - 1][j] = a;
			solution[i][j] = b;
		} else if (solution[i - 1][j - 1] == a) {
			solution[i - 1][j] = a;
			solution[i][j - 1] = a;
			solution[i][j] = b;
		}
		// inverted
		if (solution[i - 1][j - 1] == b &&
				solution[i - 1][j] == a &&
				solution[i][j - 1] == a) {
			solution[i][j] = a;
		} else if (
				solution[i - 1][j - 1] == b &&
						solution[i][j - 1] == a &&
						solution[i][j] == b) {
			solution[i - 1][j] = b;
		} else if (
				solution[i - 1][j - 1] == b &&
						solution[i - 1][j] == a &&
						solution[i][j] == b) {
			solution[i][j - 1] = b;
		} else if (
				solution[i - 1][j] == a &&
						solution[i][j - 1] == a &&
						solution[i][j] == b) {
			solution[i - 1][j - 1] = a;
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

	private static void solveSideLoop(char[][] solution, int sideNumber, int i1, int i2, int i3, int i4) {
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
