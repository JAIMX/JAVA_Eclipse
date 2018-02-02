package firstpackage;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

public class SubtourReversal {
	final static double MAXDISTANCE = Double.MAX_VALUE;
	static int nbCities = 53;
	static int nbVehicles = 4;
	static int nbDots = nbCities + nbVehicles - 1;
	static double[][] distance = new double[nbDots][nbDots];
	static int iniRoute[];
	static int localOptimum[];
	static int globalOptimum[];
	static double minLength;
	static double minLengthArr[];
	static double iniLength = 0;
	// static double iniLengthArr[] = new double[nbDots];
	static double[][] fx;
	static long time;
	static Set<Route> rdSeq = new HashSet<Route>();

	// static int nbSeq = 1;
	// static int nbSeq=2;
	// static int nbSeq=3;
	// static int nbSeq = 10;
	static int nbSeq = 999;
	// static int nbSeq = 25;
	// static int nbSeq = 30;
	// static int nbSeq = 50;
	// static int nbSeq = 100;
	// static int nbSeq=200;
	// static int nbSeq=300;
	// static int nbSeq=500;
	// static int nbSeq=600;
	// static int nbSeq=800;
	// static int nbSeq=1000;
	// static int nbSeq = 1200;
	// static int nbSeq = 1300;
	// static int nbSeq = 1400;
	// static int nbSeq = 1500;
	// static int nbSeq = 2000;
	// static int nbSeq = 3000;
	// static int nbSeq = 5000;
	// static int nbSeq = 6000;
	// static int nbSeq = 7000;
	// static int nbSeq = 8000;
	// static int nbSeq = 9000;
	// static int nbSeq = 10000;
	// static int fileSize = 4;
	// static int fileSize=5;
	// static int fileSize = 6;
	// static int fileSize = 7;
	// static int fileSize = 8;
	// static int fileSize=17;
	// static int fileSize=29;
	// static int fileSize = 48;
	static int fileSize = 53;
	// static int fileSize=76;
	// static int fileSize = 127;
	// static int fileSize=5;
	static double[] optLength;
	// static final int localOptimumRoute[][] = new int [nbSeq][nbDots + 1];
	static boolean useShake = false;

	public static void shakeRoute() {
		// shake localOptimum to iniRoute

		double maxEdge = 0;
		int left = 1;
		for (int i = 0; i < nbDots; i++) {
			double length = distance[localOptimum[i] - 1][localOptimum[i + 1] - 1];
			if (length > maxEdge) {
				maxEdge = length;
				left = i;
			}
		}
		System.out.printf("%f: (%d, %d) , ", maxEdge, localOptimum[left], localOptimum[left + 1]);
		if (left == 1) {
			left += 1;
		}
		// destroy left
		int pos = 1;
		do {
			pos = (int) (Math.random() * (nbDots - 1) + 1);
			System.out.print(pos + " , ");
		} while (pos == (left + 1));
		assert (localOptimum[pos] != 1) : "pos=" + pos;
		System.arraycopy(localOptimum, 0, iniRoute, 0, localOptimum.length);
		int temp = iniRoute[left];
		iniRoute[left] = iniRoute[pos];
		iniRoute[pos] = temp;
		System.out.printf("%d and %d swaped\n", localOptimum[left], localOptimum[pos]);
		iniLength = 0;
		for (int i = 0; i < nbDots; i++) {
			iniLength += distance[iniRoute[i] - 1][iniRoute[i + 1] - 1];
		}
		System.out.println("check ini " + iniLength + ": " + Arrays.toString(iniRoute));
		assert Checked(iniRoute, iniLength) : "Checked(localOptimum, optLength[i]) failed.";
	}

	public static void main(String[] args) {
		readFile();
		iniRoute = new int[nbDots + 1];
		double optLength[] = new double[nbSeq];
		localOptimum = new int[nbDots + 1];
		globalOptimum = new int[nbDots + 1];
		// final int localOptimumCopy[] = new int[nbDots + 1];
		final int localOptimumRoute[][] = new int[nbSeq][nbDots + 1];
		rdSeq = new HashSet<Route>();
		// double minFound = optLength[0];
		int optimalIdx = 0;
		double minFound = Double.MAX_VALUE;
		for (int i = 0; i < nbSeq; i++) {
			if (i == 0 || !useShake) {
				// iniLength = 0;
				while (!genInitSeq(iniRoute)) {
					;
				}
				// System.out.printf("%d: OK\n\n", i+1);
			} else if (i != 0 && useShake) {
				shakeRoute();
			} else {
				assert false : "should not reach here!";
			}
			iterate(iniRoute);
			optLength[i] = minLength;
			// localOptimumRoute[i] = localOptimum;
			// System.out.println(i + 1 + " local optimal length is " +
			// minLength);
			// System.out.println("The route is: " +
			// Arrays.toString(localOptimum));
			// assert Checked(localOptimum, optLength[i]) :
			// "Checked(localOptimum, optLength[i]) failed.";
			if (optLength[i] < minFound) {
				minFound = optLength[i];
				optimalIdx = i;
				// globalOptimum = localOptimum;
				System.arraycopy(localOptimum, 0, globalOptimum, 0, localOptimum.length);
				// System.out.println("The route is: " +
				// Arrays.toString(globalOptimum));
			}
		}
		System.out.println(optimalIdx + 1 + " The Min LENGTH found is " + minFound);
		System.out.println("The route is: " + Arrays.toString(globalOptimum));
		int i = 0;
		int n = 0;
		for (; n < nbVehicles; n++) {
			System.out.print("Subroute" + n + " is:" + globalOptimum[i]);
			i++;
			while (globalOptimum[i] <= nbCities && globalOptimum[i] != 1) {
				System.out.print(", " + globalOptimum[i]);
				i++;
			}
			// i++;
			System.out.println(", " + globalOptimum[i]);
		}
		i = 0;
		double subLength[] = new double[nbVehicles];
		double totalLength = 0;
		for (n = 0; n < nbVehicles; n++) {
			System.out.print("SubLength" + n + " = " + distance[globalOptimum[i] - 1][globalOptimum[i + 1] - 1]);
			subLength[n] = distance[globalOptimum[i] - 1][globalOptimum[i + 1] - 1];
			i++;
			while (globalOptimum[i] <= nbCities && globalOptimum[i] != 1) {
				System.out.print(" + " + distance[globalOptimum[i] - 1][globalOptimum[i + 1] - 1]);
				subLength[n] += distance[globalOptimum[i] - 1][globalOptimum[i + 1] - 1];
				i++;
			}
			// i++;
			// System.out.println(" + " + distance[globalOptimum[i] -
			// 1][globalOptimum[i + 1] - 1]);
			System.out.println();
			System.out.println(subLength[n]);
			totalLength += subLength[n];
		}
		System.out.println("TotalLength = " + totalLength);
		Iterator ite = rdSeq.iterator();
		while (ite.hasNext()) {
			Route route = (Route) ite.next();
		}
	}

	private static void readFile() {
		ReadFile rf = new ReadFile();
		rf.main(null);
		distance = rf.distance;
		System.out.println(distance.length);
		for (int i = 0; i < nbDots; i++) {
			for (int j = 0; j < nbDots; j++) {
				System.out.print(distance[i][j] + "\t");
			}
			System.out.println();
		}
	}

	private static int[] initialize() {
		int iniRoute[] = new int[nbDots + 1];
		System.out.print("Initial route is ");
		for (int i = 0; i < nbDots - 1; i++) {
			iniLength += distance[i][i + 1];
			System.out.print(i + 1 + "-");
			iniRoute[i] = i + 1;
		}
		iniLength += distance[nbDots - 1][0];
		System.out.println(nbDots + "-" + 1);
		System.out.println("InitialLength is " + iniLength);
		iniRoute[nbDots - 1] = nbDots;
		iniRoute[nbDots] = 1;
		return iniRoute;
	}

	private static boolean isConnected(int[] iniRoute) {
		// check if the iniRoute is a connected tour
		return true;
	}

	private static int[] randomIni() {
		// ÃƒÅ ÃƒÂ½Ãƒâ€”ÃƒÂ©
		int[] iniRoute = new int[nbDots + 1];
		int[] temp = new int[nbDots];
		Set<Integer> set = new HashSet<Integer>();// SetÃ‚Â´ÃƒÂ¦Ã‚Â·Ãƒâ€¦Ã‚Â·Ãƒâ€¡Ãƒâ€“ÃƒËœÃ‚Â¸Ã‚Â´Ãƒâ€Ã‚ÂªÃƒâ€¹ÃƒËœ
		for (int i = 0; i < nbDots; i++) {
			// temp.add(i);
			temp[i] = i + 1;
		}
		int index = 0;
		int it = temp[((int) (Math.random() * nbDots))];
		iniRoute[index] = it;
		set.add(it);
		index++;
		int count = 0;

		while (true) {
			if (set.size() == nbDots)// ÃƒË†ÃƒÂ§Ã‚Â¹ÃƒÂ»setÃƒâ€™Ãƒâ€˜Ãƒâ€šÃƒÂºÃ‚Â¡Ã‚Â£Ã‚Â±ÃƒÂ­ÃƒÅ
										// Ã‚Â¾Ãƒâ€™Ãƒâ€˜Ãƒâ€°ÃƒÂºÃ‚Â³Ãƒâ€°ÃƒÂÃƒÂªÃƒâ€¢ÃƒÂ»Ã‚ÂµÃƒâ€žroute
				break;
			it = temp[((int) (Math.random() * nbDots))];// Ãƒâ€¹ÃƒÂ¦Ã‚Â»ÃƒÂºÃƒâ€°ÃƒÂºÃ‚Â³Ãƒâ€°Ãƒâ€™Ã‚Â»Ã‚Â¸ÃƒÂ¶1Ã‚ÂµÃ‚Â½nbDotsÃ‚ÂµÃƒâ€žÃƒÅ
			if (set.contains(it)) {
				continue;
			} else if (distance[iniRoute[index - 1] - 1][it - 1] >= MAXDISTANCE) {// ÃƒË†ÃƒÂ§Ã‚Â¹ÃƒÂ»ÃƒÅ
				count++;
				continue;// Ã‚Â¼ÃƒÅ’ÃƒÂÃƒÂ¸Ãƒâ€°ÃƒÂºÃ‚Â³Ãƒâ€°Ãƒâ€¹ÃƒÂ¦Ã‚Â»ÃƒÂºÃƒÅ
							// ÃƒÂ½
			} else {
				iniRoute[index] = it;
				set.add(it);
				index++;
			}
			if (count > 999)
				randomIni();
		}
		iniRoute[index] = iniRoute[0];// Ãƒâ€”ÃƒÂ®Ã‚ÂºÃƒÂ³Ãƒâ€™Ã‚Â»Ã‚Â¸ÃƒÂ¶ÃƒÅ
		System.out.println("Initial route is ");
		for (int i = 0; i < nbDots; i++) {
			System.out.print(iniRoute[i] + "-");
			iniLength += distance[iniRoute[i] - 1][iniRoute[i + 1] - 1];
		}
		System.out.println(iniRoute[nbDots]);
		// iniLength += distance[ary[nbDots-1]-1][ary[nbDots]-1];
		System.out.println("InitialLength is " + iniLength);
		return iniRoute;
		// else return randomIni();
	}

	private static boolean genInitSeq(int[] iniRoute) {
		iniLength = 0;
		int length = iniRoute.length;
		// iniRoute[0] = 1;
		int[] candidate = new int[length - 1];
		for (int i = 0; i < candidate.length; i++)
			candidate[i] = i + 1;
		// System.out.println(Arrays.toString(candidate));
		Random rand = new Random();
		int indiniRoute = 0;
		int left = candidate.length;
		iniRoute[0] = 1;
		for (int i = 0; i < candidate.length - 1; i++) {
			int indCandidate;
			indCandidate = rand.nextInt(left - 1) + 1;
			// System.out.println(iniRoute[i-1]);
			iniRoute[++indiniRoute] = candidate[indCandidate];
			// if(distance[iniRoute[i] - 1][iniRoute[i + 1] - 1]>=999) return
			// false;
			for (int j = indCandidate; j < left - 1; j++)
				candidate[j] = candidate[j + 1];
			candidate[left - 1] = 0;
			left--;
			// System.out.println(Arrays.toString(candidate));
		}
		// iniRoute[length - 1] = iniRoute[0];
		iniRoute[length - 1] = 1;
		// if(distance[iniRoute[0]-1][iniRoute[1]-1]>=999) return false;
		for (int i = 0; i < nbDots; i++) {
			// System.out.print(iniRoute[i] + "-");
			if (distance[iniRoute[i] - 1][iniRoute[i + 1] - 1] >= MAXDISTANCE)
				return false;
			iniLength += distance[iniRoute[i] - 1][iniRoute[i + 1] - 1];
		}

		if (!isConnected(iniRoute)) {
			System.out.println("NOT CONNECTED!");
			return false;
		}
		Route r = new Route(iniRoute);
		if (!rdSeq.contains(r)) {
			rdSeq.add(r);
			// System.out.println("ADDED! size=" + rdSeq.size());
			return true;
		} else {
			// System.out.println("REPETITIONS!");
			return false;
		}

	}

	private static boolean Checked(int[] route, double toCompare) {
		boolean returnValue = true;
		// Check route first
		int minCityNb = 1, maxCityNb = route.length, nbDots = route.length - 1;
		Set<Integer> citySet = new HashSet<Integer>();
		for (int item : route) {
			if (item < minCityNb || item > maxCityNb) {
				System.out.print("|Wrong city number in route:" + item + "\t");
				returnValue = false;
				break;
			}
			citySet.add(item);
		}
		if (returnValue) {
			// System.out.print("|Correct City Numbers\t");
		}

		if (citySet.size() != nbDots) {
			System.out.print("|Wrong nbDots\t");
			returnValue = false;
		} else {
			// System.out.print("|Correct nbDots\t");
		}

		// Check length next
		double length = 0;
		for (int n = 0; n < nbDots; n++) {
			length += distance[route[n] - 1][route[n + 1] - 1];
		}
		// System.out.print("|The new length is " + length + " ADD\t");
		if (length == toCompare) {
			// System.out.println("|Length caculation is CORRECT!");
		} else {
			returnValue = false;
			System.out.println("|Length caculation is INcorrect!");
		}
		return returnValue;
		// return false;
	}

	private static void iterate(int[] route) {
		// minLength = iniLength;
		minLength = Double.MAX_VALUE;
		// System.out.println("minLength = " + minLength);
		int newRoute[] = new int[nbDots + 1];
		double iteLength[][] = new double[nbDots][nbDots];
		double maxDecre = 0;
		int localMinRoute[] = new int[nbDots + 1];
		;
		// System.out.println("before do!");
		do {
			// localMinRoute = new int[nbDots + 1];
			maxDecre = 0;
			for (int i = 1; i < nbDots - 1; i++) {
				// j = 0;
				for (int j = 0; i + j + 2 <= nbDots; j++) {
					// double lengthRoute1 = 0;
					// double lengthRoute2 = 0;
					double lengthRoute[] = new double[nbVehicles];
					double incre = distance[route[j] - 1][route[j + i + 1] - 1]
							+ distance[route[j + 1] - 1][route[j + i + 2] - 1];
					double decre = distance[route[j] - 1][route[j + 1] - 1]
							+ distance[route[j + i + 1] - 1][route[j + i + 2] - 1];
					iteLength[i][j] = iniLength + (incre - decre);

					if (incre - decre < maxDecre) {
						for (int m = 0; m < i; m++) {
							// System.out.print(route[j + m + 1] + "-");
						}

						int k;
						for (k = 0; k < j + 1; k++) {
							newRoute[k] = route[k];
							// System.out.print(newRoute[k] + "-");
						}
						int l = k;
						for (k = i + j + 1; k >= j + 1; k--) {
							newRoute[l] = route[k];
							l++;
						}
						for (k = i + j + 2; k < nbDots; k++) {
							newRoute[k] = route[k];
						}
						newRoute[k] = newRoute[0];
						// System.out.println(Arrays.toString(newRoute));
						int i2 = 0;
						int n = 0;
						for (; n < nbVehicles - 1; n++) {
							// System.out.print("route" + n + ": ");
							do {
								assert i2 < nbDots : "Wrong i2 route1";
								int a = newRoute[i2] - 1;
								int b = newRoute[i2 + 1] - 1;
								// System.out.print((a + 1) + ", ");
								lengthRoute[n] += distance[a][b];
								i2++;
							} while (newRoute[i2] <= nbCities);
							// System.out.println();
						}
						// System.out.print("route" + n + ": ");
						for (; newRoute[i2] != 1; i2++) {
							int a = newRoute[i2] - 1;
							int b = newRoute[i2 + 1] - 1;
							// System.out.print((a + 1) + ", ");
							lengthRoute[n] += distance[a][b];
						}
						// System.out.println();
						assert i2 == nbDots : i2 + " Wrong i2 route2";
						double min = lengthRoute[0];
						double max = lengthRoute[0];
						for (int m = 0; m < nbVehicles; m++) {
							min = min < lengthRoute[m] ? min : lengthRoute[m];
							max = max > lengthRoute[m] ? max : lengthRoute[m];
						}
						if (min / max > 0.9) {
							minLength = iteLength[i][j];
							maxDecre = incre - decre;
							System.arraycopy(newRoute, 0, localMinRoute, 0, route.length);
							double totalLength = 0;
							for (int i3 = 0; i3 < nbVehicles; i3++) {
								System.out.println("LengthRoute" + i3 + " = : " + lengthRoute[i3]);
								totalLength += lengthRoute[i3];
							}
							System.out.println(" total  = : " + totalLength);
						} else {
							// System.out.println("UNbalanced route!");
							assert route.length == nbDots + 1 : "Wrong Length!";
						}
					} // if
				} // for j
			} // for i
			iniLength = minLength;
			System.arraycopy(localMinRoute, 0, route, 0, route.length);
		} while (maxDecre < 0);
		// assert false:"HERE!line 492";
		if (maxDecre == 0) {
			minLength = iniLength;
		}
		// System.arraycopy(localMinRoute, 0, localOptimum, 0, route.length);
		System.arraycopy(localMinRoute, 0, localOptimum, 0, route.length);
	}

	// private static int[] permutate(int n) {
	// int i, j = 0;
	// int result[] = new int[n];
	// for (i = 0; i < n; i++) {
	// result[i] = i + 1;
	// System.out.print(result[i] + " ");
	// }
	// System.out.println("");
	// while (result[0] <= n) {
	// result[n - 1]++;
	// for (i = n - 1; i > 0; i--)
	// if (result[i] > n) {
	// result[i - 1]++;
	// result[i] = result[i] - n;
	// }
	// b: for (i = 0; i < n; i++) {
	// for (j = 0; j < n; j++)
	// if (result[i] == result[j] && i != j)
	// break b;
	// }
	// if (i == n && j == n) {
	// for (i = 0; i < n; i++)
	// System.out.print(result[i] + " ");
	// System.out.println("");
	// }
	// }
	// return result;
	// }

	private static void printResults(String filename) {
		// System.out.print(filename + "\n------------------------------\n");
		System.out.printf("rum time(sec)= %-10.2f\n", time / 1000.0);
		System.out.printf("min= %-10.2f\n", minLength);
		System.out.println(nbDots);
		for (int i = 0; i < nbDots; i++) {
			for (int j = 0; j < nbDots; j++)
				System.out.print((int) distance[i][j] + " ");
			System.out.println();
		}
	}
}