
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Data {

	private class demandPair {
		private int s;
		private int t;
		private int demandQuantity;

	}

	HashMap<String, Integer> cityIndex;
	HashMap<String, Integer> truckIndex;
	int numberOfCities;
	int numberOfTrucks;
	ArrayList<demandPair> demandPairs;
	int numberOfDemandPair;
	double xx[];
	double yy[];
	double[][] length;
	double[] openTime;
	double[] closeTime;
	double[] arrivalTime;
	double[] processingTime;
	int M;
	double fixedCost;
	double transportationCost;
	int legLimit;
	double distanceLimit;
	double averageSpeed;
	double drivingTimePerDay;
	int[] truckCapacity;
	int[] truckStartNode;

	int T ;
	int[][] b;
	static double e = Math.pow(10, -2);
	boolean[][] connect;

	public class Edge {
		int start, end;
		double length;
		int setIndex;
		// hat A=1
		// AT=2
		// AO=3
		// AD=4
		int u;
		int v;
		int t1, t2;
	}

	ArrayList<Edge> edgeSet;
	ArrayList<ArrayList<Integer>> distance;
	ArrayList<ArrayList<Integer>> distanceReverse;
	int numOfEdge1, numOfEdge2, numOfEdge3, numOfEdge4;
	int numOfEdge12, numOfy, numOfx, numOfconstraint;

	double[] c, f, bb;
	double[][] A, B;

	public Data() {
		cityIndex = new HashMap<String, Integer>();
		truckIndex = new HashMap<String, Integer>();
		demandPairs = new ArrayList<demandPair>();
	}

	public void readData(String filename) throws IOException {

		Scanner in = new Scanner(Paths.get(filename));

		String temp = new String();
		// ---read "cities"---//
		temp = in.nextLine();
		assert (temp.substring(0, 3) == "City") : "Wrong cities";
		int lIndex = temp.indexOf("'", 0);
		int rIndex = temp.indexOf("'", lIndex + 1);
		int citiesIndex = 0;

		while (lIndex >= 0) {
			String city = temp.substring(lIndex + 1, rIndex);
			cityIndex.put(city, citiesIndex);
			citiesIndex++;
			lIndex = temp.indexOf("'", rIndex + 1);
			rIndex = temp.indexOf("'", lIndex + 1);
		}
		numberOfCities = cityIndex.size();

		// ---read "demandQuantity"---//
		temp = in.nextLine();
		assert (temp.substring(0, 5) == "demand") : "Wrong demandQuantity";
		temp = in.nextLine();

		while (!temp.equals("}")) {
			demandPair pair = new demandPair();

			lIndex = temp.indexOf("'", 0);
			rIndex = temp.indexOf("'", lIndex + 1);
			String city = temp.substring(lIndex + 1, rIndex);
			pair.s = cityIndex.get(city);
			// System.out.println("s="+pair.s);

			lIndex = temp.indexOf("'", rIndex + 1);
			rIndex = temp.indexOf("'", lIndex + 1);
			city = temp.substring(lIndex + 1, rIndex);
			pair.t = cityIndex.get(city);
			// System.out.println("t="+pair.t);

			int a = temp.lastIndexOf(": ");
			int demand = Integer.parseInt(temp.substring(a + 2, temp.length()));
			pair.demandQuantity = demand;
			demandPairs.add(pair);
			// System.out.println("demand="+pair.demandQuantity);

			temp = in.nextLine();
		}

		numberOfDemandPair = demandPairs.size();

		temp = in.nextLine();
		assert (temp.substring(0, 4) == "coord") : "Wrong coordinate";
		// ---read "coordinate"---//
		xx = new double[numberOfCities];
		yy = new double[numberOfCities];
		temp = in.nextLine();

		while (!temp.equals("}")) {
			lIndex = temp.indexOf("'", 0);
			rIndex = temp.indexOf("'", lIndex + 1);
			int city = Integer.parseInt(temp.substring(lIndex + 1, rIndex));

			lIndex = temp.indexOf("(", rIndex + 1);
			rIndex = temp.indexOf(",", lIndex + 1);
			xx[city] = Double.valueOf(temp.substring(lIndex + 1, rIndex));
			yy[city] = Double.valueOf(temp.substring(rIndex + 1, temp.length() - 1));
			// System.out.println(city);
			// System.out.println("x="+x[city]);
			// System.out.println("y="+y[city]);

			temp = in.nextLine();
		}

		length = new double[numberOfCities][numberOfCities];

		// Calculate distance
		for (int i = 0; i < numberOfCities; i++) {
			for (int j = i; j < numberOfCities; j++) {
				if (i == j) {
					length[i][j] = 0;
				} else {
					length[i][j] = Math.sqrt(Math.pow((xx[i] - xx[j]), 2) + Math.pow((yy[i] - yy[j]), 2));
					length[j][i] = length[i][j];
				}
			}
		}

		temp = in.nextLine();
		assert (temp.substring(0, 3) == "open") : "Wrong openTime";
		openTime = new double[numberOfCities];

		// ---read "openTime"---//
		lIndex = temp.indexOf("'", 0);
		rIndex = temp.indexOf("'", lIndex + 1);

		while (lIndex >= 0) {
			int city = cityIndex.get(temp.substring(lIndex + 1, rIndex));
			lIndex = temp.indexOf(":", rIndex + 1);
			rIndex = temp.indexOf(",", lIndex + 1);

			if (rIndex < 0) {
				openTime[city] = Double.valueOf(temp.substring(lIndex + 1, temp.length() - 1));
				break;
			} else {
				openTime[city] = Double.valueOf(temp.substring(lIndex + 1, rIndex));
			}

			lIndex = temp.indexOf("'", rIndex);
			rIndex = temp.indexOf("'", lIndex + 1);
		}

		temp = in.nextLine();
		assert (temp.substring(0, 4) == "close") : "Wrong closeTime";
		closeTime = new double[numberOfCities];

		// ---read "closeTime"---//
		lIndex = temp.indexOf("'", 0);
		rIndex = temp.indexOf("'", lIndex + 1);

		while (lIndex >= 0) {
			int city = cityIndex.get(temp.substring(lIndex + 1, rIndex));
			lIndex = temp.indexOf(":", rIndex + 1);
			rIndex = temp.indexOf(",", lIndex + 1);

			if (rIndex < 0) {
				closeTime[city] = Double.valueOf(temp.substring(lIndex + 1, temp.length() - 1));
				break;
			} else {
				closeTime[city] = Double.valueOf(temp.substring(lIndex + 1, rIndex));
			}

			lIndex = temp.indexOf("'", rIndex);
			rIndex = temp.indexOf("'", lIndex + 1);
		}

		temp = in.nextLine();
		assert (temp.substring(0, 6) == "arrival") : "Wrong arrivalTime";
		arrivalTime = new double[numberOfCities];

		// ---read "arrivalTime"---//
		lIndex = temp.indexOf("'", 0);
		rIndex = temp.indexOf("'", lIndex + 1);

		while (lIndex >= 0) {
			int city = cityIndex.get(temp.substring(lIndex + 1, rIndex));
			lIndex = temp.indexOf(":", rIndex + 1);
			rIndex = temp.indexOf(",", lIndex + 1);

			if (rIndex < 0) {
				arrivalTime[city] = Double.valueOf(temp.substring(lIndex + 1, temp.length() - 1));
				break;
			} else {
				arrivalTime[city] = Double.valueOf(temp.substring(lIndex + 1, rIndex));
			}

			lIndex = temp.indexOf("'", rIndex);
			rIndex = temp.indexOf("'", lIndex + 1);
		}
		T=(int) arrivalTime[0];

		temp = in.nextLine();
		assert (temp.substring(0, 6) == "process") : "Wrong processingTime";
		processingTime = new double[numberOfCities];

		// ---read "processingTime"---//
		lIndex = temp.indexOf("'", 0);
		rIndex = temp.indexOf("'", lIndex + 1);

		while (lIndex >= 0) {
			int city = cityIndex.get(temp.substring(lIndex + 1, rIndex));
			lIndex = temp.indexOf(":", rIndex + 1);
			rIndex = temp.indexOf(",", lIndex + 1);

			if (rIndex < 0) {
				processingTime[city] = Double.valueOf(temp.substring(lIndex + 1, temp.length() - 1));
				break;
			} else {
				processingTime[city] = Double.valueOf(temp.substring(lIndex + 1, rIndex));
			}

			lIndex = temp.indexOf("'", rIndex);
			rIndex = temp.indexOf("'", lIndex + 1);
		}

		// ---read single parameters---//
		// M
		temp = in.nextLine();
		lIndex = temp.indexOf("=", 0);
		M = Integer.parseInt(temp.substring(lIndex + 1, temp.length()));
		// fixedCost
		temp = in.nextLine();
		temp = in.nextLine();
		lIndex = temp.indexOf("=", 0);
		fixedCost = Double.valueOf(temp.substring(lIndex + 1, temp.length()));
		// transprotationCost
		temp = in.nextLine();
		lIndex = temp.indexOf("=", 0);
		transportationCost = Double.valueOf(temp.substring(lIndex + 1, temp.length()));
		// max number of legs per truck
		temp = in.nextLine();
		lIndex = temp.indexOf("=", 0);
		legLimit = Integer.parseInt(temp.substring(lIndex + 1, temp.length()));
		// max distance by a truck
		temp = in.nextLine();
		lIndex = temp.indexOf("=", 0);
		distanceLimit = Double.valueOf(temp.substring(lIndex + 1, temp.length()));
		// average speed
		temp = in.nextLine();
		lIndex = temp.indexOf("=", 0);
		averageSpeed = Double.valueOf(temp.substring(lIndex + 1, temp.length()));
		// driving time per day
		temp = in.nextLine();
		lIndex = temp.indexOf("=", 0);
		drivingTimePerDay = Double.valueOf(temp.substring(lIndex + 1, temp.length()));

		// ---read "trucks"---//
		temp = in.nextLine();
		assert (temp.substring(0, 5) == "trucks") : "Wrong trucks";
		temp = in.nextLine();
		int trucksIndex = 0;

		while (!temp.equals("}")) {
			lIndex = temp.indexOf("'", 0);
			rIndex = temp.indexOf("'", lIndex + 1);
			while (lIndex >= 0) {
				String truck = temp.substring(lIndex + 1, rIndex);
				truckIndex.put(truck, trucksIndex);
				// System.out.println(truck.toString());
				trucksIndex++;

				lIndex = temp.indexOf("'", rIndex + 1);
				rIndex = temp.indexOf("'", lIndex + 1);
			}
			temp = in.nextLine();
		}
		// System.out.println(truckIndex.size());
		numberOfTrucks = truckIndex.size();

		// ---read "truckCapacity"---//
		temp = in.nextLine();
		assert (temp.substring(0, 4) == "truck") : "Wrong trucksCapacity";
		truckCapacity = new int[numberOfTrucks];

		while (!temp.equals("}")) {
			lIndex = temp.indexOf("'", 0);
			rIndex = temp.indexOf("'", lIndex + 1);
			int index1 = temp.indexOf(":", rIndex + 1);
			int index2 = temp.indexOf(",", rIndex + 1);

			while (lIndex >= 0) {
				int capacity = Integer.valueOf(temp.substring(index1 + 1, index2));
				truckCapacity[truckIndex.get(temp.substring(lIndex + 1, rIndex))] = capacity;

				lIndex = temp.indexOf("'", index2 + 1);
				if (lIndex >= 0) {
					rIndex = temp.indexOf("'", lIndex + 1);
					index1 = temp.indexOf(":", rIndex + 1);
					index2 = temp.indexOf(",", rIndex + 1);
				}

			}
			temp = in.nextLine();
		}
		// ---read "truckStartNode"---//
		truckStartNode = new int[numberOfTrucks];

		temp = in.nextLine();
		assert (temp.substring(0, 4) == "truck") : "Wrong truckStartNode";
		temp = in.nextLine();

		while (!temp.equals("}")) {
			lIndex = temp.indexOf("'", 0);
			rIndex = temp.indexOf("'", lIndex + 1);
			int index1 = temp.indexOf(":", rIndex + 1);
			int index2 = temp.indexOf(",", rIndex + 1);

			while (lIndex >= 0) {

				int truckIndexTemp = truckIndex.get(temp.substring(lIndex + 1, rIndex));
				int cityIndexTemp = cityIndex.get(temp.substring(index1 + 1, index2));
				truckStartNode[truckIndexTemp] = cityIndexTemp;

				lIndex = temp.indexOf("'", index2 + 1);
				if (lIndex >= 0) {
					rIndex = temp.indexOf("'", lIndex + 1);
					index1 = temp.indexOf(":", rIndex + 1);
					index2 = temp.indexOf(",", rIndex + 1);
				}

			}
			temp = in.nextLine();
		}

	}

	/**
	 * Vst index:00,01,...,0T;n-1 0,n-1 1,...,n-1 T [0,n(T+1)-1] O
	 * index:n0,n1,...,n(n-1) [n(T+1),n(T+2)-1] D index:n+1 0,n+1 1,...,n+1(n-1)
	 * [n(T+2), n(T+3)-1]
	 */
	public void graphTransfer() {

		connect = new boolean[numberOfCities * (T + 3)][numberOfCities * (T + 3)];

		// calculate distance

		edgeSet = new ArrayList<Edge>();
		distance = new ArrayList<ArrayList<Integer>>();
		distanceReverse = new ArrayList<ArrayList<Integer>>();

		// only record Vst and O
		for (int i = 0; i < numberOfCities * (T + 2); i++) {
			ArrayList<Integer> templist = new ArrayList<Integer>();
			distance.add(templist);

		}

		for (int i = 0; i < numberOfCities * (T + 3); i++) {
			ArrayList<Integer> templist2 = new ArrayList<Integer>();
			distanceReverse.add(templist2);
		}

		// add hat A
		for (int i = 0; i < numberOfCities; i++) {
			for (int j = 0; j < numberOfCities; j++) {
				if (i != j) {
					double time = length[i][j] / averageSpeed;
					int timeLength = (int) Math.ceil(time);

					int t = timeLength;
					int nodeIndex1 = i * (T + 1);
					int nodeIndex2 = j * (T + 1) + timeLength;

					while (t <= T) {
						Edge edge = new Edge();
						edge.start = nodeIndex1;
						edge.end = nodeIndex2;
						edge.length = length[i][j];
						edge.setIndex = 1;
						edge.u = i;
						edge.v = j;
						edge.t1 = t - timeLength;
						edge.t2 = t;
						edgeSet.add(edge);
						distance.get(nodeIndex1).add(edgeSet.size() - 1);
						distanceReverse.get(nodeIndex2).add(edgeSet.size() - 1);
						connect[nodeIndex1][nodeIndex2] = true;

						t++;
						nodeIndex1++;
						nodeIndex2++;
					}
				}
			}
		}
		numOfEdge1 = edgeSet.size();

		// add AT
		for (int node = 0; node < numberOfCities; node++) {
			for (int t = 0; t < T; t++) {
				Edge edge = new Edge();
				int nodeIndex = node * (T + 1) + t;
				edge.start = nodeIndex;
				edge.end = nodeIndex + 1;
				edge.length = 0;
				edge.setIndex = 2;
				edge.u = node;
				edge.v = node;
				edge.t1 = t;
				edge.t2 = t + 1;
				edgeSet.add(edge);

				distance.get(nodeIndex).add(edgeSet.size() - 1);
				distanceReverse.get(nodeIndex + 1).add(edgeSet.size() - 1);
				connect[nodeIndex][nodeIndex + 1] = true;
			}
		}

		numOfEdge2 = edgeSet.size() - numOfEdge1;

		// add AO:(Ok,n0)
		for (int o = 0; o < numberOfCities; o++) {
			int oIndex = numberOfCities * (T + 1) + o;

			// for (int node = 0; node < numberOfCities; node++) {
			// int nodeIndex = node * (T + 1);
			// Edge edge = new Edge();
			// edge.start = oIndex;
			// edge.end = nodeIndex;
			// edge.length = length[o][node];
			// edge.setIndex = 3;
			// edge.u = o;
			// edge.v = node;
			// edge.t1 = -1;
			// edge.t2 = 0;
			//
			// edgeSet.add(edge);
			// distance.get(oIndex).add(edgeSet.size() - 1);
			// distanceReverse.get(edge.end).add(edgeSet.size() - 1);
			// connect[oIndex][nodeIndex] = true;
			// }

			int nodeIndex = o * (T + 1);
			Edge edge = new Edge();
			edge.start = oIndex;
			edge.end = nodeIndex;
			edge.length = 0;
			edge.setIndex = 3;
			edge.u = o;
			edge.v = o;
			edge.t1 = -1;
			edge.t2 = 0;
			edgeSet.add(edge);
			distance.get(oIndex).add(edgeSet.size() - 1);
			connect[oIndex][nodeIndex] = true;
			distanceReverse.get(edge.end).add(edgeSet.size() - 1);
		}

		numOfEdge3 = edgeSet.size() - numOfEdge1 - numOfEdge2;

		// add AD:(nT,Dk)
		for (int node = 0; node < numberOfCities; node++) {
			int nodeIndex = node * (T + 1) + T;
//			for (int d = 0; d < numberOfCities; d++) {
//				int dIndex = numberOfCities * (T + 2) + d;
//				Edge edge = new Edge();
//				edge.start = nodeIndex;
//				edge.end = dIndex;
//				edge.length = length[node][d];
//				edge.setIndex = 4;
//				edge.u = node;
//				edge.v = d;
//				edge.t1 = T;
//				edge.t2 = -1;
//				edgeSet.add(edge);
//				distance.get(nodeIndex).add(edgeSet.size() - 1);
//				distanceReverse.get(dIndex).add(edgeSet.size() - 1);
//				connect[nodeIndex][dIndex] = true;
//
//			}
			
			int dIndex = numberOfCities * (T + 2) + node;
			Edge edge = new Edge();
			edge.end = dIndex;
			edge.start=nodeIndex;
			edge.length = 0;
			edge.setIndex = 4;
			edge.u = node;
			edge.v = node;
			edge.t1 = T;
			edge.t2 = -2;
			edgeSet.add(edge);
			distance.get(nodeIndex).add(edgeSet.size()-1);
			distanceReverse.get(dIndex).add(edgeSet.size()-1);
			connect[nodeIndex][dIndex] = true;
		}

		numOfEdge4 = edgeSet.size() - numOfEdge1 - numOfEdge2 - numOfEdge3;

		// set b
		b = new int[numberOfCities * (T + 1)][numberOfDemandPair];
		for (int p = 0; p < numberOfDemandPair; p++) {
			demandPair pair = demandPairs.get(p);
			b[pair.s * (T + 1)][p] = pair.demandQuantity;
			b[pair.t * (T + 1) + T][p] = -pair.demandQuantity;
		}

	}

	public void matrixGenerator() {
		numOfEdge12 = numOfEdge1 + numOfEdge2;
		numOfy = (numOfEdge12) * numberOfDemandPair;
		numOfx = edgeSet.size() * numberOfTrucks;

		c = new double[numOfy];
		int index = 0;
		for (int p = 0; p < numberOfDemandPair; p++) {
			for (int e = 0; e < numOfEdge12; e++) {
				c[index] = transportationCost * edgeSet.get(e).length;
				index++;
			}
		}

		// for(int e=0;e<numOfEdge12;e++) {
		// for(int p=0;p<numberOfDemandPair;p++) {
		// System.out.print(c[p*numOfEdge12+e]+" ");
		// }
		// System.out.println();
		// }

		f = new double[numOfx];
		double constant = fixedCost / (averageSpeed * drivingTimePerDay);
		index = 0;
		for (int k = 0; k < numberOfTrucks; k++) {
			for (int e = 0; e < edgeSet.size(); e++) {
				f[index] = constant * edgeSet.get(e).length;
				index++;
			}
		}

		// for (int e = 0; e < edgeSet.size(); e++) {
		// for (int k = 0; k < numberOfTrucks; k++) {
		// System.out.print(f[k * edgeSet.size() + e] + " ");
		// }
		// System.out.println();
		// }

		numOfconstraint = numberOfCities * (T + 1) * numberOfDemandPair * 2 + numOfEdge1 + 7 * numberOfTrucks+2*numberOfTrucks*numberOfCities*(T+1);
		A = new double[numOfconstraint][numOfy];
		B = new double[numOfconstraint][numOfx];
		bb = new double[numOfconstraint];

		int row = 0;
		System.out.println("start to generate matrix!");
		
		///-----constraint 1------///
		for(int k=0;k<numberOfTrucks;k++) {
			for(int i=0;i<numberOfCities*(T+1);i++){
				
				for(int edgeIndex:distance.get(i)) {
					B[row][k * edgeSet.size() + edgeIndex]=1;
				}
				
				for(int edgeIndex:distanceReverse.get(i)) {
					B[row][k * edgeSet.size() + edgeIndex]=-1;
				}
				
				row++;
				
			}
		}
		
		for(int k=0;k<numberOfTrucks;k++) {
			for(int i=0;i<numberOfCities*(T+1);i++){
				
				for(int edgeIndex:distance.get(i)) {
					B[row][k * edgeSet.size() + edgeIndex]=-1;
				}
				
				for(int edgeIndex:distanceReverse.get(i)) {
					B[row][k * edgeSet.size() + edgeIndex]=1;
				}
				
				row++;
				
			}
		}
		
		///-----constraint 2------///
		for(int k=0;k<numberOfTrucks;k++) {
			int ok=numberOfCities*(T+1)+truckStartNode[k];
			
			for(int edgeIndex:distance.get(ok)) {
				B[row][k * edgeSet.size() + edgeIndex]=-1;
			}
			bb[row]=-1;
			row++;
		}
		
		
		///-----constraint 3------///
		for(int k=0;k<numberOfTrucks;k++) {
			int ok=numberOfCities*(T+1)+truckStartNode[k];
			
			for(int ok2=numberOfCities*(T+1);ok2<numberOfCities*(T+2);ok2++) {
				if(ok2!=ok) {
					for(int edgeIndex:distance.get(ok2)) {
						B[row][k * edgeSet.size() + edgeIndex]=1;
					}
				}
			}
			row++;
		}
		
		for(int k=0;k<numberOfTrucks;k++) {
			int ok=numberOfCities*(T+1)+truckStartNode[k];
			
			for(int ok2=numberOfCities*(T+1);ok2<numberOfCities*(T+2);ok2++) {
				if(ok2!=ok) {
					for(int edgeIndex:distance.get(ok2)) {
						B[row][k * edgeSet.size() + edgeIndex]=-1;
					}
				}
			}
			row++;
		}
		
		
		///-----constraint 4------///
		for(int k=0;k<numberOfTrucks;k++) {
			int dk=numberOfCities*(T+2)+truckStartNode[k];
			
			for(int dk2=numberOfCities*(T+2);dk2<numberOfCities*(T+3);dk2++) {
				if(dk2!=dk) {
					for(int edgeIndex:distanceReverse.get(dk2)) {
						B[row][k * edgeSet.size() + edgeIndex]=1;
					}
				}
			}
			row++;
		}
		
		for(int k=0;k<numberOfTrucks;k++) {
			int dk=numberOfCities*(T+2)+truckStartNode[k];
			
			for(int dk2=numberOfCities*(T+2);dk2<numberOfCities*(T+3);dk2++) {
				if(dk2!=dk) {
					for(int edgeIndex:distanceReverse.get(dk2)) {
						B[row][k * edgeSet.size() + edgeIndex]=-1;
					}
				}
			}
			row++;
		}
		
		
		
		
		
		
		for (int k = 0; k < numberOfTrucks; k++) {

			for (int e = 0; e < edgeSet.size(); e++) {
				if (edgeSet.get(e).setIndex == 1) {
					B[row][k * edgeSet.size() + e] = -1;
				}
			}

			bb[row] = -legLimit;
			row++;
		}


		for (int k = 0; k < numberOfTrucks; k++) {

			for (int e = 0; e < edgeSet.size(); e++) {
				B[row][k * edgeSet.size() + e] = -edgeSet.get(e).length;
			}

			bb[row] = -distanceLimit;
			row++;
		}
		
		for (int p = 0; p < numberOfDemandPair; p++) {
			for (int i = 0; i < numberOfCities * (T + 1); i++) {

				for (int e = 0; e < distance.get(i).size(); e++) {
					if (edgeSet.get(distance.get(i).get(e)).setIndex < 3) {
						A[row][numOfEdge12 * p + distance.get(i).get(e)] = 1;
					}
				}

				for (int e = 0; e < distanceReverse.get(i).size(); e++) {
					if (edgeSet.get(distanceReverse.get(i).get(e)).setIndex < 3) {
						A[row][numOfEdge12 * p + distanceReverse.get(i).get(e)] = -1;
					}
				}

				bb[row] = b[i][p];
				row++;

			}
		}

		// System.out.println("node 1 point to: ");
		//
		// for(int edgeIndex:distance.get(1)) {
		// System.out.print(edgeSet.get(edgeIndex).end+" "+edgeIndex+" ");
		// }
		//
		// System.out.println();
		// System.out.println("node 1 point from: ");
		// for(int edgeIndex:distanceReverse.get(1)) {
		// System.out.print(edgeSet.get(edgeIndex).start+"
		// "+edgeSet.get(edgeIndex).setIndex+" "+edgeIndex+" ");
		// }
		//
		//
		// System.out.println();
		// int temp = 0;
		// for (int p = 0; p < numberOfDemandPair; p++) {
		// for (int e = 0; e < numOfEdge12; e++) {
		// System.out.print(A[1][temp] + " ");
		// temp++;
		// }
		// System.out.println();
		// }
		// System.out.println();
		// temp = 0;
		// for (int p = 0; p < numberOfDemandPair; p++) {
		// for (int e = 0; e < numOfEdge12; e++) {
		// System.out.print(A[222][temp] + " ");
		// temp++;
		// }
		// System.out.println();
		// }

		for (int p = 0; p < numberOfDemandPair; p++) {
			for (int i = 0; i < numberOfCities * (T + 1); i++) {

				for (int e = 0; e < distance.get(i).size(); e++) {
					if (edgeSet.get(distance.get(i).get(e)).setIndex < 3) {
						A[row][numOfEdge12 * p + distance.get(i).get(e)] = -1;
					}
				}

				for (int e = 0; e < distanceReverse.get(i).size(); e++) {
					if (edgeSet.get(distanceReverse.get(i).get(e)).setIndex < 3) {
						A[row][numOfEdge12 * p + distanceReverse.get(i).get(e)] = 1;
					}
				}

				bb[row] = -b[i][p];
				row++;

			}

		}

		for (int e = 0; e < numOfEdge1; e++) {

			for (int p = 0; p < numberOfDemandPair; p++) {
				A[row][p * numOfEdge12 + e] = -1;
			}

			for (int k = 0; k < numberOfTrucks; k++) {
				B[row][k * edgeSet.size() + e] = truckCapacity[k];
			}

			// if (e == 1) {
			// int temp = 0;
			// for (int k = 0; k < numberOfTrucks; k++) {
			// for (int ee = 0; ee < edgeSet.size(); ee++) {
			// System.out.print(B[row][temp] + " ");
			// temp++;
			// }
			// System.out.println();
			// }
			// }

			// if (e == 3) {
			// int temp = 0;
			// for (int p = 0; p < numberOfDemandPair; p++) {
			// for (int ee = 0; ee < numOfEdge12; ee++) {
			// System.out.print(A[row][temp] + " ");
			// temp++;
			// }
			// System.out.println();
			// }
			// }

			row++;
		}



		// for (int k = 0; k < numberOfTrucks; k++) {
		//
		// for (int e = 0; e < edgeSet.size(); e++) {
		// System.out.print(B[row - 2][k * edgeSet.size() + e] + " ");
		// }
		// System.out.println();
		// }

		System.out.println("The number of x= " + numOfx);
		System.out.println("The number of y= " + numOfy);
		System.out.println("The number of trucks= " + numberOfTrucks);
		System.out.println("The number of demand= " + numberOfDemandPair);
		System.out.println("The number of edges= " + edgeSet.size());
		System.out.println("The number of edge12= " + numOfEdge12);
		System.out.println("The number of constraints= " + A.length);

	}

	public double[] generateInitialx() {
		double[] initialx = new double[numOfx];

		for (int k = 0; k < numberOfTrucks; k++) {
			int startNode = numberOfCities * (T + 1) + truckStartNode[k];
			int endNode = startNode + numberOfCities;

			for (int edgeIndex : distance.get(startNode)) {
				if (edgeSet.get(edgeIndex).end == truckStartNode[k] * (T + 1)) {
					initialx[k * edgeSet.size() + edgeIndex] = 1;
					break;
				}
			}

			int currentNode = truckStartNode[k] * (T + 1);
			for (int t = 0; t < T; t++) {
				for (int edgeIndex : distance.get(currentNode)) {
					if (edgeSet.get(edgeIndex).setIndex == 2) {
						initialx[k * edgeSet.size() + edgeIndex] = 1;
						break;
					}
				}
				currentNode++;
			}

			for (int edgeIndex : distance.get(currentNode)) {
				if (edgeSet.get(edgeIndex).end == endNode) {
					initialx[k * edgeSet.size() + edgeIndex] = 1;
					break;
				}
			}

		}

		// System.out.println("for truck #1: ");
		// for(int e=0;e<edgeSet.size();e++) {
		// if(initialx[1*edgeSet.size()+e]==1) {
		// System.out.println(edgeSet.get(e).start+"->"+edgeSet.get(e).end+"->");
		// }
		// }

		return initialx;
	}

	// public double[] getc() {
	// return c;
	// }
	//
	// public double[] getf() {
	// return f;
	// }
	//
	// public double[] getbb() {
	// return bb;
	// }
	//
	// public double[][] getA() {
	// return A;
	// }
	//
	// public double[][] getB() {
	// return B;
	// }
	//
	// public int getNumOfx() {
	// return numOfx;
	// }
	//
	// public int getNumOfy() {
	// return numOfy;
	// }
	//
	// public int getNumOfTruck() {
	// return numberOfTrucks;
	// }
	//
	// public ArrayList<Edge> getEdgeSet() {
	// return edgeSet;
	// }
	//
	// public int getT() {
	// return T;
	// }
	//
	// public int getNumOfCity() {
	// return numberOfCities;
	// }
	//
	// public ArrayList<ArrayList<Integer>> getDistance() {
	// return distance;
	// }
	//
	// public ArrayList<ArrayList<Integer>> getDistanceReverse() {
	// return distanceReverse;
	// }
	//
	// public int[] getTruckStartNode() {
	// return truckStartNode;
	// }

	public static void main(String[] args) throws IOException {
		Data data = new Data();
		data.readData("out2.txt");
		data.graphTransfer();
		data.matrixGenerator();
		// data.generateInitialx();
	}
}
