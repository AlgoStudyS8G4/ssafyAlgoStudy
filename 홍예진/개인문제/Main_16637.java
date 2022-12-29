import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static int n;
	static String input;
	static int max;

	public static int calculate(int num1, char op, int num2) {
		if (op == '+')
			return num1 + num2;
		if (op == '-')
			return num1 - num2;
		// op == '*'
		return num1 * num2;
	}

	public static int calculate(int left, int right) {
		int num1 = Character.getNumericValue(input.charAt(left));
		for (int i = left + 1; i < right; i += 2) {
			char op = input.charAt(i);
			int num2 = Character.getNumericValue(input.charAt(i + 1));
			num1 = calculate(num1, op, num2);
		}
		return num1;
	}

	public static void solve(int leftIdx, int num1, char op) {
		System.out.println(op+" "+num1);
		if (op == '$') {
			max = Math.max(max, num1);
			return;
		}

		// 3+8*7-9*2
		// (3)+8*7-9*2, (3+8)*7-9*2 ...
		// 3+(8)*7-9*2, 3+(8*7)-9*2
		// ...
		// (3+8)*(7-9)*2

		for (int rightIdx = leftIdx; rightIdx < n; rightIdx += 2) {
			char next_op = input.charAt(rightIdx + 1);
			int num2 = calculate(leftIdx, rightIdx); // 괄호친 값
			int next_num1 = calculate(num1, op, num2);

			solve(rightIdx + 2, next_num1, next_op);
		}
	}

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		input = br.readLine();

		// 갈이가 3이하일 때는 괄호에 상관없이 값이 똑같이 나온다.
		if (n <= 3) {
			System.out.println(input);
			return;
		}

		max = Integer.MIN_VALUE;
		input += "$";
		solve(0, 0, '+');

		System.out.println(max);
	}
}