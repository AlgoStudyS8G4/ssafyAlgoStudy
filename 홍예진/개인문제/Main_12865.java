import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
* 무게를 넘지않는 조합찾기 
**/
public class Main {
	static int N, K, minW;
	static Stuff[] stuffs;
	static int ans;

	public static void solve(int emptyWeight, int idx, int totalValue) {
		if (N == idx || emptyWeight < stuffs[idx].weight) {
			ans = Math.max(ans, totalValue);
			return;
		}

		for (int i = idx; i < N; i++) {
			solve(emptyWeight - stuffs[i].weight, i + 1, totalValue + stuffs[i].value);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		minW = Integer.MAX_VALUE;
		ans = 0;
		stuffs = new Stuff[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int w = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			stuffs[i] = new Stuff(w, v);
		}

		Arrays.sort(stuffs, (s1, s2) -> s2.weight - s1.weight); // 무거운 순 정렬
		solve(K, 0, 0);
		
		System.out.println(ans);
	}

	static class Stuff {
		int weight, value;

		public Stuff(int weight, int value) {
			super();
			this.weight = weight;
			this.value = value;
		}
	}
}
