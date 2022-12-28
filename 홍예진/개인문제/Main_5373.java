import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
	static Cube[] cubes;

	public static char[] getColors(int x, int y, int z) {
		char[] colors = new char[6];
		if (y == 2)
			colors[0] = 'w'; // 윗면
		if (y == 0)
			colors[1] = 'y'; // 아랫면
		if (z == 2)
			colors[2] = 'r'; // 앞면
		if (z == 0)
			colors[3] = 'o'; // 뒷면
		if (x == 0)
			colors[4] = 'g'; // 왼쪽면
		if (x == 2)
			colors[5] = 'b'; // 오른쪽면
		return colors;
	}

	/*
	 * U평면을 기준으로 시계방향 회전과, D평면을 기준으로 반시계방향 회전은 같다. 각 평면에 대해서 회전 방향의 방법은 중복 제거시 3*2 =
	 * 6개가 된다.
	 */
	public static void rotateCube(Cube cube, String plane, String dir) {
		Point[] normalVector = cube.normalVector;
		Point point = cube.point;
		int x = 0;
		int y = 0;
		int z = 0;

		if (plane == "U") {
			if (dir == "+") {
				z = point.x;
				x = -point.z;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.z;
					v.z = v.x;
					v.x = -temp;
				}
			} else {
				z = -point.x;
				x = point.z;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.z;
					v.z = -v.x;
					v.x = temp;
				}
			}
		} else if (plane == "D") {
			if (dir == "+") {
				x = point.z;
				z = -point.x;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.x;
					v.x = v.z;
					v.z = -temp;
				}
			} else {
				x = -point.z;
				z = point.x;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.x;
					v.x = -v.z;
					v.z = temp;
				}
			}

		} else if (plane == "F") {
			if (dir == "+") {
				x = point.y;
				y = -point.x;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.x;
					v.x = v.y;
					v.y = -temp;
				}
			} else {
				x = -point.y;
				y = point.x;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.x;
					v.x = -v.y;
					v.y = temp;
				}
			}
		} else if (plane == "B") {
			if (dir == "+") {
				y = point.x;
				x = -point.y;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.y;
					v.y = v.x;
					v.x = -temp;
				}
			} else {
				y = -point.x;
				x = point.y;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.y;
					v.y = -v.x;
					v.x = temp;
				}
			}
		} else if (plane == "L") {
			if (dir == "+") {
				z = point.y;
				y = -point.z;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.z;
					v.z = v.y;
					v.y = -temp;
				}
			} else {
				z = -point.y;
				y = point.z;

				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.z;
					v.z = -v.y;
					v.y = temp;
				}
			}
		} else if (plane == "R") {
			if (dir == "+") {
				y = point.z;
				z = -point.y;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.y;
					v.y = v.z;
					v.z = -temp;
				}
			} else {
				y = -point.z;
				z = point.y;
				for (Point v : normalVector) {
					// 각 면에 대한 법선 벡터 v
					int temp = v.y;
					v.y = -v.z;
					v.z = temp;
				}
			}
		}

		point.x = x;
		point.y = y;
		point.z = z;
	}

	public static boolean inBound(int xyz, int[] bound) {
		return bound[0] <= xyz && xyz < bound[1];
	}

	public static ArrayList<Cube> getTarget(String plane) {

		ArrayList<Cube> target = new ArrayList<Cube>();
		int idx = "UDFBLR".indexOf(plane);
		int[][][] bounds = { 
				{ { 0, 3 }, { 2, 3 }, { 0, 3 } }, 
				{ { 0, 3 }, { 0, 1 }, { 0, 3 } },
				{ { 0, 3 }, { 0, 3 }, { 2, 3 } },
				{ { 0, 3 }, { 0, 3 }, { 0, 1 } },
				{ { 0, 1 }, { 0, 3 }, { 0, 3 } },
				{ { 0, 3 }, { 0, 3 }, { 2, 3 } }, };

		int[][] bound = bounds[idx];

		for (Cube cube : cubes) {
			Point p = cube.point;
			if (inBound(p.x, bound[0]) && inBound(p.y, bound[1]) && inBound(p.z, bound[2])) {
				target.add(cube);
			}
		}

		return target;
	}

	public static void print() {

		// 모든 큐브에서 y가 2인 값을 가져온다.
		ArrayList<Cube> list = new ArrayList<>();
		for (Cube cube : cubes) {
			if (cube.point.y == 2)
				list.add(cube);
		}

		// z좌표가 작은 순, 같다면 x좌표가 작은 순으로 정렬한다.
		Collections.sort(list,
				(c1, c2) -> c1.point.z == c2.point.z ? c1.point.x - c2.point.x : c1.point.z - c2.point.z);

		// 법선벡터가 {0, 1, 0}인 면의 색을 찾아 저장한다.
		ArrayList<Character> colorList = new ArrayList<>();
		for (Cube cube : list) {
			for (int i = 0; i < cube.normalVector.length; i++) {
				Point vector = cube.normalVector[i];
				if (vector.x == 0 && vector.y == 1 && vector.z == 0) {
					colorList.add(cube.colors[i]);
					break;
				}
			}
		}

		// 찾은 결과를 3*3으로 분리 출력한다.
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(colorList.get(i * 3 + j));
			}
			System.out.println();
		}
	}

	public static void rotate(String plane, String dir) {

		// 회전할 타겟 큐브조각을 선택한다.
		ArrayList<Cube> target = getTarget(plane);
		System.out.println(target);

		// 각 큐브롤 좌표, 색깔별로 회전시킨다.
		// 회전시킨다 = 각 면이 향하는 법선벡터를 변경시킨다.
		for (Cube cube : target) {
			// 각 큐브의 좌표를 변경
			rotateCube(cube, plane, dir);
		}

	}

	public static void solve(String[] actions) {
		for (String action : actions) {
			String plane = action.substring(0, 1);
			String dir = action.substring(1, 2);
			rotate(plane, dir);

		}
	}

	public static void init() {
		int num = 0;
		cubes = new Cube[27];
		for (int z = 0; z < 3; z++) {
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					char[] colors = getColors(x, y, z);
					cubes[num] = new Cube(new Point(x, y, z), colors);
					num++;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		while (T-- > 0) {
			int n = Integer.parseInt(br.readLine());
			String[] actions = br.readLine().split(" ");

			init();
			solve(actions);
//			print();

		}
	}

	static class Cube {
		Point point;
		char[] colors;
		Point[] normalVector;

		public Cube(Point point, char[] colors) {
			super();
			this.point = point;
			this.colors = colors;
			normalVector = new Point[] { new Point(0, 1, 0), new Point(0, -1, 0), new Point(0, 0, 1),
					new Point(0, 0, -1), new Point(-1, 0, 0), new Point(0, 0, 1) }; // 각 면 (위아래 앞뒤 왼오)의 법선 벡터
		}

		public String toString() {
			return "("+point.x + ", " + point.y + ", " + point.z + ")";
		}
	}

	static class Point {
		int x, y, z;

		public Point(int x, int y, int z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
		}

	}
}