package com.guods.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hanoi {

	private static int count = 0;
	
	public static void main(String args[]) throws IOException {
		int n;
		BufferedReader buf;
		buf = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("请输入盘数：");
		n = Integer.parseInt(buf.readLine());
		Hanoi hanoi = new Hanoi();
		hanoi.move(n, 'A', 'B', 'C');
		System.out.println(count);
	}

	public void move(int n, char a, char b, char c) {
		if (n == 1) {
			System.out.println("盘 " + n + " 由 " + a + " 移至 " + c);
			count++;
		}else {
			move(n - 1, a, c, b);
			System.out.println("盘 " + n + " 由 " + a + " 移至 " + c);
			count++;
			move(n - 1, b, a, c);
		}
	}
}
