import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int H, W;
	static int[] world;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		world = new int[W + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < W; i++) {
			world[i] = Integer.parseInt(st.nextToken());
		}
		world[W] = world[W - 1];

		int left = 0;
		int sum = 0;
		while (left < W - 1) {
			if (world[left] < world[left + 1]) {
				left++;
				continue;
			}

			int right = left + 1;
			while (right < W && world[right] < world[left]) {
				right++;
			}

			int min = Math.min(world[left], world[right]);
			for(int idx = left+1; idx < right; idx++) {
				sum += (min - world[idx]);
			}
			
			left = right;
		}

		System.out.println(sum);
	}
}