import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Week2_4 {
	private int n;
	private double[][] distance;
	private ArrayList<HashMap<Long, Double>> optFunction1, optFunction2;

	public Week2_4(double[][] distance) throws IOException {

		this.distance = distance;
		n = distance.length;
	}

	private void opt() {
		// initialize to optFunction1
		optFunction1 = new ArrayList<HashMap<Long, Double>>();
		HashMap<Long, Double> temp = new HashMap<>();
		optFunction1.add(temp);
		for (int i = 1; i < n; i++) {
			temp = new HashMap<Long, Double>();
			temp.put((long) 0, distance[0][i]);
			optFunction1.add(temp);
		}

		// start the opt process
		for (int setSize = 1; setSize <= n - 2; setSize++) {
			System.out.println("setSize= "+setSize);
			optFunction2 = new ArrayList<HashMap<Long, Double>>();

			for (int i = 0; i < n; i++) {
				temp = new HashMap<>();
				optFunction2.add(temp);
			}

			// f(k,s)=min[dkj+f(j,s/j)]
			for (int j = 1; j <= n - 1; j++) {
				System.out.println("j= "+j);
				temp = optFunction1.get(j);

				Iterator iter = temp.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();

					Long keyy = (Long) key;
					Double vall = (Double) val;
					long newSet = (long) (keyy + Math.pow(2, j));

					// to get optFunction2
					for (int k = 1; k <= n - 1; k++) {
//						System.out.println("k= "+k);
						Long kbit = (long) Math.pow(2, k);
						if (((long) (kbit & newSet)) == 0) {
							if (optFunction2.get(k).containsKey(newSet)) {
								double min = Math.min(optFunction2.get(k).get(newSet), vall + distance[k][j]);
								optFunction2.get(k).put(newSet, min);
							} else {
								optFunction2.get(k).put(newSet, vall + distance[k][j]);
							}
						}
					}
				}

			}

			// after updating optFunction2
			optFunction1 = optFunction2;
		}

		// for(int i=0;i<n;i++){
		// System.out.println(optFunction1.get(i).keySet());
		// }

		double min = Double.MAX_VALUE;
		for (int j = 1; j < n; j++) {
			temp = optFunction1.get(j);

			Iterator iter = temp.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();

				Long keyy = (Long) key;
				Double vall = (Double) val;
				long newSet = (long) (keyy + Math.pow(2, j));

				min = Math.min(vall + distance[j][0], min);
			}
		}

		System.out.println(min);

	}

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(Paths.get("./data/tsp.txt"));
		// Scanner in = new Scanner(Paths.get("./data/test.txt"));
		int n = in.nextInt();
		double[] x = new double[n];
		double[] y = new double[n];

		for (int i = 0; i < n; i++) {
			x[i] = in.nextDouble();
			y[i] = in.nextDouble();
		}

		double[][] distance = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				distance[i][j] = Math.sqrt(Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));
			}
		}

		// Scanner in = new Scanner(Paths.get("./data/tsp6.txt"));
		// int n = in.nextInt();
		// double[][] dist = new double[n][n];
		//
		// for (int i = 0; i < n; i++) {
		// for (int j = 0; j < n; j++) {
		// dist[i][j] = in.nextDouble();
		// }
		// }
		//
		// in.close();

		Week2_4 test = new Week2_4(distance);
		// Week2_4 test = new Week2_4(dist);
		test.opt();

	}

}
