package com.interview;

import java.util.concurrent.atomic.AtomicBoolean;

public class VolatileCrashTest {
	/*volatile*/ private static boolean btExit = false; //попробуйте убрать volatile
//	private AtomicBoolean btExit2 = new AtomicBoolean(false); // has volatile inside

	public static void main(String[] args) {
		Runnable gui = () -> {
			try {
				Thread.sleep(2000); // типа пользователь общается с GUI и выходит из игры
			} catch (InterruptedException ignored) { }
			btExit = true; // поток game не видит это изменение без volatile
			System.out.println("gui thread finished");
		};
		new Thread(gui).start();
		System.out.println("gui thread started");

		Runnable game = () -> {
			while (!btExit) { } // без volatile этот цикл крутится бесконечно
			System.out.println("game thread finished");
		};
		new Thread(game).start();
		System.out.println("game thread started");
	}
}
