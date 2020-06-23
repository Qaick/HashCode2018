package eolymp;

import java.io.*;

public class MyPersistentDisjointSetUnion2
{
	private final static String WhSp = "\\s"; // whitespace
	private final static int MAXQ = 100_005, // p_array roots[] Queries
			MAXS = 10_000_000; // 1e8 limit for elements

	private static int nElements;
	private static int roots[] = new int[MAXQ]; // roots for trees
	private static int lch[] = new int[MAXS], rch[] = new int[MAXS], idxCounter;

	static int new_node(int idxLeft, int idxRight)
	{
		assert (idxCounter < MAXS);
		lch[idxCounter] = idxLeft;
		rch[idxCounter] = idxRight;
		return idxCounter++;
	}

	static int build(int n)
	{
		if (n == 1)
			return new_node(-1, -1);
		int m = n / 2;
		return new_node(build(m), build(n - m));
	}

	private static int get(int root, int n, int i)
	{
		if (n == 1)
			return lch[root];// returns value(weight) of vertex
		int m = n / 2;
		if (i < m)
			return get(lch[root], m, i);
		else
			return get(rch[root], n - m, i - m);
	}

	//v - vertex, n - deep lenght, i - needed vertex, x - value to set
	private static int set(int v, int n, int i, int x)
	{
		//path that I already have from previous tree
		if (i < 0 || i >= n)
			return v;
		//last deep element
		if (n == 1)
			return new_node(x, x);
		int m = n / 2;
		//recursive building tree to the needed to set vertext
		return new_node(set(lch[v], m, i, x), set(rch[v], n - m, i - m, x));
	}

	private static int find_set(int version, int element)
	{
		int c_i = get(version, nElements, element);
		if (c_i < 0)
			return element;
		return find_set(version, c_i);
	}

	private static int union_set(int v, int a, int b)
	{
		a = find_set(v, a);
		b = find_set(v, b);
		if (a != b)
		{
			int aa = get(v, nElements, a);
			int bb = get(v, nElements, b);
			//b>a
			if (aa > bb)
			{
				int tmp = a;
				a = b;
				b = tmp;
			}
			int ver = set(v, nElements, a, aa + bb);
			return set(ver, nElements, b, a);
		}
		else
		{
			return v;
		}
	}

	public static int log2(int x)
	{
		double v = Math.log(x) / Math.log(2) + 1e-10;
		System.out.println("log2 = " + v + ", " + (int) v);
		return (int) v;
	}

	public static void main(String... args)
	{
		int nCells = 8; // number of the cells in the table
		int nToStore = nCells*2-1; // number of cells needed to store table in binary tree
		int treeHeight = log2(nCells);
		int numberOfPaths = 0;

		int ans = 0,n=2, nSum = 0;
		for (int i = 18; i > 1; i--) {
			ans += i*n;
			System.out.println("n = " + n+", i = "+i);
			nSum+=n;
			n*=2;
		}
		System.out.println("ans = " + ans);
		System.out.println("full: " + 1.0*ans/nSum);

//		roots[0] = build(i);
//		for (int i = 2; i < 20; i++) {
//			System.out.println("i = "+i+" , real = "+idxCounter);
//			idxCounter = 0;
//		}

		long time = System.currentTimeMillis();
		StringBuilder answer = new StringBuilder();
		String line, ss[];
		//        int     an, // elements count
		int nRequests, // roots count
				a, // first element
				b, // second element
				version; // teporary for Version
		try
		{
			final BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
			ss = bufferedReader.readLine().split(WhSp);
			nElements = Integer.parseInt(ss[0]) + 1;
			nRequests = Integer.parseInt(ss[1]);
			roots[0] = build(nElements);
			for (int i = 1; i <= nRequests; ++i)
			{
				line = bufferedReader.readLine();
				ss = line.split(WhSp);
				version = Integer.parseInt(ss[1]);
				a = Integer.parseInt(ss[2]);
				b = Integer.parseInt(ss[3]);

				if (line.charAt(0) == '+')
				{
					roots[i] = union_set(roots[version], a, b);
				}
				else
				{
					int id_a = find_set(roots[version], a);
					int id_b = find_set(roots[version], b);
					answer.append((id_a == id_b) ? "YES" : "NO").append('\n');
				}
			}

			final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));
			bufferedWriter.write(answer.toString());
			bufferedWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		//System.out.println("time: " + (System.currentTimeMillis() - time));
		//System.out.println("cnt: " + cnt);
	}
}