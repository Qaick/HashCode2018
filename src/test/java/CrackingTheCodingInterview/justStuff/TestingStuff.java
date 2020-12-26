package CrackingTheCodingInterview.justStuff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class TestingStuff {
	@Test
	void test3() {
		HashSet<Integer> integers = new HashSet<Integer>();
		integers.add(1);
		integers.add(3);
		integers.add(13);
		System.out.println(integers);
		for (int i = 2; i <= 20; i++) {
			System.out.println(i + " " + 1.0/i);
		}
	}

	@Test
	void test2() {
		System.out.println(Double.MAX_VALUE);
		System.out.println(new MessageFormat("Sensitivity to previous {0}")
				.format(new Object[] { "agree" }));
		ArrayList<Object> objects = new ArrayList<>();
		List<Object> collect = objects.stream().collect(Collectors.toList());
		System.out.println(collect.size());
//		objects.add("hello");
		objects.stream().forEach(el -> System.out.println(""));
	}

	@Test
	void test() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 64; i++) {
			sb.append((char)((i)%('z'-'a')+'a'));
		}
		System.out.println(sb.toString());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.txt".length());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa - Copy".length());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa - Copy (2)".length());

		StringBuilder helpPageSB1 = new StringBuilder();
		StringBuilder helpPageSB = new StringBuilder();
		String PYTHON_BUTTON_NOTE_HTML = "%sCode for this operation may be generated via the %s<b>Python</b> button.";
		helpPageSB1.append("<p>").append(String.format(PYTHON_BUTTON_NOTE_HTML, iconHTML("INFO") + "&nbsp;&nbsp;",
				iconHTML("GENERATE_CODE") + "&nbsp;")).append("</p>");

		helpPageSB.append("<p>");
		helpPageSB.append(iconHTML("INFO"));
		helpPageSB.append("&nbsp;&nbsp;Code for this operation may be generated via the ")
				.append(iconHTML("GENERATE_CODE")).append("&nbsp;<b>Python</b> button.</p>");

		Assertions.assertEquals(helpPageSB.toString(), helpPageSB1.toString());
	}

	private Object iconHTML(String info) {
		return "icon";
	}
}
