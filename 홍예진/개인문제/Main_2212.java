import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, K;
	static int[] point;

	public static void solve() {
		// 각 집중국은 센서의 수신 가능 영역을 조절할 수 있다.
		// 최대 K개 집중국의 수신 가능 영역 길이 합의 최솟값 출력.
		// N개의 센서가 적어도 하나의 집중국과는 통신이 가능해야한다.
		
		
	}
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		point = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			point[i] = Integer.parseInt(st.nextToken());
		}
		
		solve();
	}

}