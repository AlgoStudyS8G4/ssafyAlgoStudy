import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int R, C;
	static char[][] lake;
	static boolean[][] visited;
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
				if (visited[i][j])
					continue;
				if (lake[i][j] == 'X' && isWaterContact(i, j)) {
					q.add(new Point(i, j));
					visited[i][j] = true;
				}
			}
		}

		return q;
	}

	public static boolean isConnect() {

		boolean[][] visited = new boolean[R][C];
		visited[swans[0].i][swans[0].j] = true;
		Queue<Point> q = new LinkedList<>();
		q.add(swans[0]);

		while (!q.isEmpty()) {
			Point p = q.poll();
			for (int[] d : delta) {
				int ni = p.i + d[0];
				int nj = p.j + d[1];
				if (ni == swans[1].i && nj == swans[1].j)
					return true;

				if (inBoundary(ni, nj)) {

					if (!visited[ni][nj] && lake[ni][nj] == '.') {
						q.add(new Point(ni, nj));
						visited[ni][nj] = true;
					}
				}

			}
		}

		return false;
	}

	public static void solve() {
		int count = 0;
		Queue<Point> q = getPoints();
		while (!isConnect()) {
			count++;
			int qSize = q.size();
			while (qSize-- > 0) {
				Point p = q.poll();
				visited[p.i][p.j] = true;
				lake[p.i][p.j] = '.';
				for (int[] d : delta) {
					int ni = p.i + d[0];
					int nj = p.j + d[1];

					if (inBoundary(ni, nj) && !visited[ni][nj] && lake[ni][nj] == 'X') {
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
		visited = new boolean[R][C];
		swans = new Point[2];
		int sIdx = 0;

		for (int i = 0; i < R; i++) {
			lake[i] = br.readLine().toCharArray();
			for (int j = 0; j < C; j++) {
				if (lake[i][j] == '.')
					visited[i][j] = true;
				else if (lake[i][j] == 'L')
					swans[sIdx++] = new Point(i, j);
			}
		}

		solve();
	}
}