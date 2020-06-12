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
        in.nextLine(); // jump to next line because I'm still on 'n' line
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

    static void solveBigClosedCircles(char[][] solution) {
        // transform solution into vertex array, union set. Transformation will take most of the time then.
        int arrN = solution.length + 1;
        int[] arr = new int[arrN * arrN];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[0].length; j++) {
                // 3 scenario here: empty, right, left
                int leftTop = i * arrN + j;
                int rightTop = leftTop + 1;
                int leftBot = (i + 1) * arrN + j;
                int rightBot = leftBot + 1;
                if (solution[i][j] == '/') {
                    connect(arr, rightTop, leftBot);
                } else if (solution[i][j] == '\\') {
                    connect(arr, leftTop, rightBot);
                } // I'm not interested in empty cells. Valuable information for me are connections.
            }
        }
        // iterate over empty cells, requesting connection between diagonal vertexes
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[0].length; j++) {
                if (solution[i][j] == '.') {
                    int topLeft = i * solution.length + j;
                    int topRight = topLeft + 1;
                    int botLeft = (i + 1) * solution.length + j;
                    int botRight = botLeft + 1;
                    if (areConnected(arr, topLeft, botRight)) solution[i][j] = '/';
                    if (areConnected(arr, topRight, botLeft)) solution[i][j] = '\\';
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

    static String solve(int[][] puzzle) {
        int sSize = puzzle.length - 1; // solution size
        int sLastIdx = sSize - 1; // solution last index
        char[][] solution = new char[sSize][sSize];
        for (char[] aChar : solution) {
            Arrays.fill(aChar, '.');
        }
        // check 4 corners. If corner contain 1 or 0 I know the solve
        if (puzzle[0][0] != -1) solution[0][0] = puzzle[0][0] == 1 ? '\\' : '/';
        if (puzzle[0][sSize] != -1) solution[0][sSize - 1] = puzzle[0][sSize] == 1 ? '/' : '\\';
        if (puzzle[sSize][sSize] != -1) solution[sSize - 1][sSize - 1] = puzzle[sSize][sSize] == 1 ? '\\' : '/';
        if (puzzle[sSize][0] != -1) solution[sSize - 1][0] = puzzle[sSize][0] == 1 ? '/' : '\\';

        for (int i = 1; i < sSize; i++) {
            int top = puzzle[0][i];
            solveSideOnce(solution, top, 0, i - 1, 0, i);
            int right = puzzle[i][sSize];
            solveSideOnce(solution, right, i, sLastIdx, i - 1, sLastIdx);
            int bot = puzzle[sSize][i];
            solveSideOnce(solution, bot, sLastIdx, i, sLastIdx, i - 1);
            int left = puzzle[i][0];
            solveSideOnce(solution, left, i - 1, 0, i, 0);
        }

        // check 4 sides. Do it in cycle until there will be no changes?
        // I added 2 tests for top side it gives good guarantee that this part of code is scalable
        while (!isSolved(solution)) {
            for (int i = 1; i < sSize; i++) {
                int top = puzzle[0][i];
                solveSideLoop(solution, top, 0, i - 1, 0, i);
                int right = puzzle[i][sSize];
                solveSideLoop(solution, right, i, sLastIdx, i - 1, sLastIdx);
                int bot = puzzle[sSize][i];
                solveSideLoop(solution, bot, sLastIdx, i, sLastIdx, i - 1);
                int left = puzzle[i][0];
                solveSideLoop(solution, left, i - 1, 0, i, 0);
            }
            // handle middle part
            // 0 can't be in the middle because it means a circle
            // middle can have 1 2 3 4
            solveMiddleLoop(puzzle, sSize, solution);
            // у меня появилась идея что все эти / и \ можно закодировать автоматически
            // по парности проверять. В теории это должно много проблем и лишнего кода упростить

            // result should not contain circles
            solveCirclesLoop(sSize, solution);
            solveBigClosedCircles(solution);
        }

        // collect to string
        StringBuilder sb = new StringBuilder();
        for (char[] aChar : solution) {
            for (char c : aChar) {
                sb.append(c);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private static void solveMiddleLoop(int[][] puzzle, int sSize, char[][] solution) {
        for (int i = 1; i < sSize; i++) {
            for (int j = 1; j < sSize; j++) {
                // continue if all the squares are filled
                if (solution[i][j] != '.' && solution[i][j - 1] != '.' && solution[i - 1][j] != '.'
                        && solution[i - 1][j - 1] != '.') continue;
                // TODO faster main 4: n*n-4n
                if (puzzle[i][j] == 4) { // draw around
                    solution[i - 1][j - 1] = '\\';
                    solution[i - 1][j] = '/';
                    solution[i][j - 1] = '/';
                    solution[i][j] = '\\';
                } else if (puzzle[i][j] == 3) {
                    solveOneThree(solution, i, j, '/', '\\');
                } else if (puzzle[i][j] == 2) {
                    // if you know half you can find out the second half
                    // \/
                    // ..
                    if (solution[i - 1][j - 1] == '\\' && solution[i - 1][j] == '/') {
                        solution[i][j - 1] = '\\';
                        solution[i][j] = '/';
                    } else if (solution[i - 1][j - 1] == '/' && solution[i - 1][j] == '\\') {
                        solution[i][j - 1] = '/';
                        solution[i][j] = '\\';
                    } else
                        // .\
                        // ./
                        if (solution[i - 1][j] == '\\' && solution[i][j] == '/') {
                            solution[i][j - 1] = '/';
                            solution[i - 1][j - 1] = '\\';
                        } else if (solution[i - 1][j] == '/' && solution[i][j] == '\\') {
                            solution[i][j - 1] = '\\';
                            solution[i - 1][j - 1] = '/';
                        } else
                            // . .
                            // \/
                            if (solution[i][j] == '\\' && solution[i][j - 1] == '/') {
                                solution[i - 1][j - 1] = '/';
                                solution[i - 1][j] = '\\';
                            } else if (solution[i][j] == '/' && solution[i][j - 1] == '\\') {
                                solution[i - 1][j - 1] = '\\';
                                solution[i - 1][j] = '/';
                            } else
                                // \.
                                // /.
                                if (solution[i][j - 1] == '/' && solution[i - 1][j - 1] == '\\') {
                                    solution[i - 1][j] = '\\';
                                    solution[i][j] = '/';
                                } else if (solution[i][j - 1] == '\\' && solution[i - 1][j - 1] == '/') {
                                    solution[i - 1][j] = '/';
                                    solution[i][j] = '\\';
                                } else
                                    // ./
                                    // /.
                                    if (solution[i][j - 1] == '/' && solution[i - 1][j] == '/') {
                                        solution[i - 1][j - 1] = '/';
                                        solution[i][j] = '/';
                                    } else if (solution[i][j - 1] == '\\' && solution[i - 1][j] == '\\') {
                                        solution[i - 1][j - 1] = '\\';
                                        solution[i][j] = '\\';
                                    } else
                                        // \.
                                        // .\
                                        if (solution[i - 1][j - 1] == '\\' && solution[i][j] == '\\') {
                                            solution[i - 1][j] = '\\';
                                            solution[i][j - 1] = '\\';
                                        } else if (solution[i - 1][j - 1] == '/' && solution[i][j] == '/') {
                                            solution[i - 1][j] = '/';
                                            solution[i][j - 1] = '/';
                                        }
                } else if (puzzle[i][j] == 1) {
                    solveOneThree(solution, i, j, '\\', '/');
                }
            }
        }
    }

    // TODO faster circle: n+n+n*n*(2..4)
    // if I'll remove checks that exceeds the field and rewrite
    private static void solveCirclesLoop(int sSize, char[][] solution) {
        for (int i = 0; i < sSize; i++) {
            for (int j = 0; j < sSize; j++) {
                // continue if all the squares are filled
//                if (puzzle[i][j] != '.' && solution[i][j - 1] != '.' && solution[i - 1][j] != '.'
//                        && solution[i - 1][j - 1] != '.') continue;
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

    // TODO faster remember last not solved point: it will make very small improvement
    private static boolean isSolved(char[][] solution) {
        if (loopsCounterForTests != -1) {
            return loopsCounterForTests-- <= 0;
        }
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

    // for first time. Do for sure changes.
    private static void solveSideOnce(char[][] solution, int sideNumber, int i1, int i2, int i3, int i4) {
        if (sideNumber == -1) return; // cannot be solved
        if (solution[i1][i2] != '.' && solution[i3][i4] != '.') return; // solved
        if (sideNumber == 0) {
            solution[i1][i2] = '\\';
            solution[i3][i4] = '/';
        } else if (sideNumber == 2) {
            solution[i1][i2] = '/';
            solution[i3][i4] = '\\';
        }
    }

    // for all next times. Situation could appear
    private static void solveSideLoop(char[][] solution, int sideNumber, int i1, int i2, int i3, int i4) {
        if (sideNumber == -1) return; // cannot be solved
        if (solution[i1][i2] != '.' && solution[i3][i4] != '.') return; // solved
        if (sideNumber == 1) {
            if (solution[i1][i2] == '.' && solution[i3][i4] != '.') { // хоть одна стоит
                solution[i1][i2] = solution[i3][i4] == '/' ? '/' : '\\';
            } else if (solution[i1][i2] != '.' && solution[i3][i4] == '.') {
                solution[i3][i4] = solution[i1][i2] == '/' ? '/' : '\\';
            }
        }
    }
}

// TODO faster solved -1:
// TODO faster, when I solve by hand I don't go over all the cells. I do the change and solve from that point until there will be no differences, like a wave: HUGE
