package com;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.Random;

/**
 * 20 tabs min
 * So the target is to have hosts notes placed 40 new lines between
 */
public class HostsMaze {
	static String ip = "127.0.0.1";
	static String[] s = new String[]{"www.youtube.com",
			"youtube.com",
			"twitch.tv",
			"www.twitch.tv"};

	public static void main(String[] args) {
		Random r = new Random();
		// big new line
		StringBuilder result = new StringBuilder();
		int n = 10_000; // 10_000 is good
		int max = 0;
		for (String line : s) {
			int r1 = r.nextInt(n);
			char[] firstNewLines = new char[80+ r1];
			Arrays.fill(firstNewLines, '\n');
			int r2 = r.nextInt(n);
			char[] tabs1 = new char[20+ r2];
			Arrays.fill(tabs1, '\t');
			int r3 = r.nextInt(n);
			char[] tabs2 = new char[20+ r3];
			Arrays.fill(tabs2, '\t');
			result.append(firstNewLines).append(tabs1).append(ip).append(tabs2).append(line);
			max = Math.max(r1 + r2 + r3, max);
		}
		char[] tabs2 = new char[100+r.nextInt(n)+max];
		Arrays.fill(tabs2, '\t');
		result.append(tabs2); // this is to prevent useful information to be placed in the end
		System.out.println(max);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(result.toString()), null);
	}
}
