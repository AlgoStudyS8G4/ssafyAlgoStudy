import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int R, C;
	static char[][] lake;
	static Point[] swans;
	static int[][] delta = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };

	public static boolean inBoundary(int i, int j) {
		return 0 <= i && i < R && 0 <= j && j < C;
	}

	public static boolean isWaterContact(int i, int j) {
		for (int[] d : delta) {
			int ni = i + d[0];
			int nj = j + d[1];
			if (inBoundary(ni, nj) && lake[ni][nj] == '.')
				return true;
		}
		return false;
	}

	public static Queue<Point> getPoints() {
		Queue<Point> q = new LinkedList<>();

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (lake[i][j] == 'X' && isWaterContact(i, j)) {
					q.add(new Point(i, j));
				}
			}
		}

		return q;
	}

	public static boolean isConnect(int start) {
		int end = (start+1)%2;
		
		Queue<Point> q = new LinkedList<>();
		q.add(swans[start]);
		lake[swans[start].i][swans[start].j] = '0';

		while (!q.isEmpty()) {
			Point p = q.poll();
		
			for (int[] d : delta) {
				int ni = p.i + d[0];
				int nj = p.j + d[1];
				if (ni == swans[end].i && nj == swans[end].j)
					return true;

				if (inBoundary(ni, nj)) {

					if (lake[ni][nj] == '.') {
						q.add(new Point(ni, nj));
						lake[ni][nj] = (char) (start);
					}
				}

			}
		}

		return false;
	}
	
	// p위치가 X에서 .으로 바뀌는 순간, 인근의 0과 1이 이어지는 셈
	public static boolean checkConnect(Point p) {
		boolean zero = false;
		boolean one = false;
		for (int[] d : delta) {
			int ni = p.i + d[0];
			int nj = p.j + d[1];

			if (inBoundary(ni, nj)) {
				if(lake[ni][nj] == '0')
					zero = true;
				if(lake[ni][nj] == '1')
					one = true;
			}
		}
		return zero && one;
	}

	
	public static void solve() {
		int count = 0;
		Queue<Point> q = getPoints();
		while (!isConnect(0)) {
			count++;
			int qSize = q.size();
			while (qSize-- > 0) {
				Point p = q.poll();
				lake[p.i][p.j] = '.';
				
				for (int[] d : delta) {
					int ni = p.i + d[0];
					int nj = p.j + d[1];

					if (inBoundary(ni, nj) && lake[ni][nj] == 'X') {
						q.add(new Point(ni, nj));
					}
				}
			}
		}
		System.out.println(count);
	}

	static class Point {
		int i, j;

		public Point(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		lake = new char[R][C];
		swans = new Point[2];
		int sIdx = 0;

		for (int i = 0; i < R; i++) {
			lake[i] = br.readLine().toCharArray();
			for (int j = 0; j < C; j++) {
				if (lake[i][j] == 'L')
					swans[sIdx++] = new Point(i, j);
			}
		}

		solve();
	}
}