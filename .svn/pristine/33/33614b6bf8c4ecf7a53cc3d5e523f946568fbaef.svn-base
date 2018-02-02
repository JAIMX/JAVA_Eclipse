package distanceUpdate;

import java.util.Arrays;

class Route {
	public int[] path;

	public Route(int[] path) {

		this.path = new int[path.length];
		for (int i = 0; i < path.length; i++) {
			this.path[i] = path[i];
		}
	}

	public boolean equals(Object obj) {
		System.out.println("here!Line 22");
		Route r1 = (Route) obj;
		for (int i = 0; i < r1.path.length; i++) {
			if (r1.path[i] != this.path[i])
				return false;
		}
		return true;
	}

	public int hashCode() {
		return Arrays.toString(this.path).hashCode();
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < path.length; i++) {
			s += path[i] + " ";
		}
		return s;
	}
}
