package distanceUpdate;

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
    static double[][] fx;
    static long time;
    static Set<Route> rdSeq = new HashSet<Route>();
    static double penalty = 0;
    // test
    static final double penalty0 = 0;
    //static final double punishparameter = 0.6;
    static final double punishparameter = 0;
    static int nbSeq = 200;
    static double[] optLength;
    static boolean useShake = false;
    static double min_max;

    private static double penaltycaculate(double min_max) {    // min_max=min/max
        //return Math.pow(1.2,(0.9 - min_max)*2.25)-1;
        return (min_max >= 0.9) ? 0 : (0.9 - min_max) *punishparameter;
    }

    public static void main(String[] args) {
        readFile();
        iniRoute = new int[nbDots + 1];
        double optLength[][] = new double[nbSeq][2];
        localOptimum = new int[nbDots + 1];
        globalOptimum = new int[nbDots + 1];
        rdSeq = new HashSet<Route>();
        int optimalIdx = 0;
        double minFound = Double.MAX_VALUE;

        // nbSep times search
        for (int i = 0; i < nbSeq; i++) {

            while (!genInitSeq(iniRoute)) {
                ;
            }

            iterate(iniRoute);
            optLength[i][0] = minLength;
            optLength[i][1]=min_max;

            if (optLength[i][0] + penalty < minFound) {
                minFound = optLength[i][0] + penalty;
                optimalIdx = i;
                System.arraycopy(localOptimum, 0, globalOptimum, 0, localOptimum.length);
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
        System.out.println("The min/max is: " + optLength[optimalIdx][1]);
        
        Iterator ite = rdSeq.iterator();
        while (ite.hasNext()) {
            Route route = (Route) ite.next();
        }
    }

    private static void readFile() {
        ReadFile rf = new ReadFile();
        rf.main(null);
        distance = rf.distance;
        //System.out.println(distance.length);
        for (int i = 0; i < nbDots; i++) {
            for (int j = 0; j < nbDots; j++) {
                //System.out.print(distance[i][j] + "\t");
            }
            //System.out.println();
        }
    }

    private static boolean isConnected(int[] iniRoute) {
        // check if the iniRoute is a connected tour
        return true;
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
        minLength = Double.MAX_VALUE;
        int newRoute[] = new int[nbDots + 1];
        double maxDecre = 0;
        int localMinRoute[] = new int[nbDots + 1];

        // start the function iterate()
        System.arraycopy(route, 0, localMinRoute, 0, route.length);
        boolean updated;
        do {

            // Calculate maximal and minimal distance of initial route
            int i2 = 0;
            int n = 0;
            double lengthRoute[] = new double[nbVehicles];
            for (; n < nbVehicles - 1; n++) {
                do {
                    assert i2 < nbDots : "Wrong i2 route1";
                    int a = route[i2] - 1;
                    int b = route[i2 + 1] - 1;
                    lengthRoute[n] += distance[a][b];
                    i2++;
                } while (route[i2] <= nbCities);
            }
            for (; route[i2] != 1; i2++) {
                int a = route[i2] - 1;
                int b = route[i2 + 1] - 1;
                lengthRoute[n] += distance[a][b];
            }
            assert i2 == nbDots : i2 + " Wrong i2 route2";
            double min = lengthRoute[0];
            double max = lengthRoute[0];
            for (int m = 0; m < nbVehicles; m++) {
                min = min < lengthRoute[m] ? min : lengthRoute[m];
                max = max > lengthRoute[m] ? max : lengthRoute[m];
            }
           
            min_max=min/max;
            // maxDecre = (min / max >= 0.9) ? 0 : (0.9 - min / max) *punishparameter;
            // maxDecre=0;
            maxDecre = penaltycaculate(min / max);

            updated = false;

            for (int i = 1; i < nbDots - 1; i++) {
                for (int j = 0; i + j + 2 <= nbDots; j++) {

                    double incre = distance[route[j] - 1][route[j + i + 1] - 1]
                            + distance[route[j + 1] - 1][route[j + i + 2] - 1];
                    double decre = distance[route[j] - 1][route[j + 1] - 1]
                            + distance[route[j + i + 1] - 1][route[j + i + 2] - 1];

                    // construct newRoute
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

                    // Calculate maximal and minimal distance by lengthRoute
                    i2 = 0;
                    n = 0;
                    for (int i3 = 0; i3 < lengthRoute.length; i3++) {
                        lengthRoute[i3] = 0;
                    }
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
                    min = lengthRoute[0];
                    max = lengthRoute[0];
                    for (int m = 0; m < nbVehicles; m++) {
                        min = min < lengthRoute[m] ? min : lengthRoute[m];
                        max = max > lengthRoute[m] ? max : lengthRoute[m];
                    }

                    // judge if it is a better solution

                    // double temppenalty = (min / max >= 0.9) ? 0 : (0.9 - min
                    // / max) * punishparameter;
                    double temppenalty = penaltycaculate(min / max);

                    // double temppenalty =0;
                    // double temppenalty =1-min/max;
                    if (temppenalty + (incre - decre) < maxDecre) {
                        min_max=min/max;
                        updated = true;
                        penalty = temppenalty;
                        minLength = iniLength + (incre - decre);
                        // iniLength = minLength;
                        maxDecre = (incre - decre) + penalty;
                        System.arraycopy(newRoute, 0, localMinRoute, 0, route.length);

                        double totalLength = 0;
                        for (int i3 = 0; i3 < nbVehicles; i3++) {
                           // System.out.println("LengthRoute" + i3 + " = : " + lengthRoute[i3]);
                            totalLength += lengthRoute[i3];
                        }
                        //System.out.println(" total  = : " + totalLength);
                        //System.out.println(" total+penalty = : " + (totalLength + penalty));

                    }

                } // for j
            } // for i
            if (updated) {
                iniLength = minLength;
            }
            System.arraycopy(localMinRoute, 0, route, 0, route.length);
        } while (updated);

        minLength = iniLength; // if not updated for one time
        System.arraycopy(route, 0, localOptimum, 0, route.length);
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
    //
    // private static void printResults(String filename) {
    // // System.out.print(filename + "\n------------------------------\n");
    // System.out.printf("rum time(sec)= %-10.2f\n", time / 1000.0);
    // System.out.printf("min= %-10.2f\n", minLength);
    // System.out.println(nbDots);
    // for (int i = 0; i < nbDots; i++) {
    // for (int j = 0; j < nbDots; j++)
    // System.out.print((int) distance[i][j] + " ");
    // System.out.println();
    // }
    // }
}