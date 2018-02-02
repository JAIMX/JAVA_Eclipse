package distanceUpdate;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearIntExpr;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class SubtourReversal {
    final static double MAXDISTANCE = 10000;
    static int nbCities = 76;
    static int nbVehicles = 1;
    static int nbNodes = 0; // initialize later in main()
    static double[][] distance = new double[nbNodes][nbNodes];
    static int iniRoute[];
    //={1,8,38,31,44,18,7,28,6,37,19,27,17,43,30,36,46,33,20,47,21,32,39,48,5,42,24,10,45,35,4,26,2,29,34,41,16,22,3,23,14,25,13,11,12,15,40,9,1};
    //={1,36,29,13,70,35,31,69,38,59,22,66,63,57,15,24,19,7,2,4,18,42,32,3,8,26,55,49,28,14,20,30,44,68,27,46,25,45,39,61,40,9,17,43,41,6,53,5,10,52,60,12,34,21,33,62,54,48,67,11,64,65,56,51,50,58,37,47,16,23,1};
    //1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 1};
    static int localOptimum[];
    static int stage1Opt[];
    static double minObjective;
    static double minLengthArr[];
    static double iniObjective = 0;
    static double[][] fx;
    static long time;
    static Set<Route> rdSeq = new HashSet<Route>();
    static double penalty = 0;
    // test
    static int nbStart = 1000;
    static double[] optLength;
    static boolean debugOn = false;
    static double min_max;
    static double alpha = 0.8;
    static final double penaltyWeight = 10000;
    static int times = 0; // used for MTZ_1.lp MTZ_2.lp ...
    static String errStr = "Terminated unexpectedly. Contact hongma@foxmail.com.";

    private static double penaltycaculate(double min_max) { // min_max=min/max
        // return Math.pow(1.2,(0.9 - min_max)*2.25)-1;
        return (min_max >= alpha) ? 0 : (alpha - min_max) * penaltyWeight;
    }

    static int[] MTZsolve(double[][] distance, int nbCities, int subtour[]) throws NumberFormatException, IOException {
        times++;
        double minLength = 0;
        fx = new double[nbCities][nbCities]; // decision variable x_ij
        int solTour[] = new int[nbCities]; // store final tour sequence
        try {
            IloCplex cplex = new IloCplex();
            IloIntVar[][] x = new IloIntVar[nbCities][nbCities];
            IloNumVar[] u = new IloNumVar[nbCities];

            // ---build model---//
            IloLinearNumExpr exprObj = cplex.linearNumExpr();
            for (int i = 0; i < nbCities; i++) {
                for (int j = 0; j < nbCities; j++) {
                    if (i != j)
                        x[i][j] = cplex.intVar(0, 1, "x" + i + "," + j);
                    else
                        x[i][j] = cplex.intVar(0, 0, "x" + i + "," + j);
                }
                exprObj.add(cplex.scalProd(x[i], distance[i]));
            }
            cplex.addMinimize(exprObj);

            // ---constraint 1 & 2---//
            for (int i = 0; i < nbCities; i++) {
                IloLinearIntExpr constraint2 = cplex.linearIntExpr();
                for (int j = 0; j < nbCities; j++)
                    constraint2.addTerm(1, x[j][i]);
                cplex.addEq(cplex.sum(x[i]), 1); // c1
                cplex.addEq(constraint2, 1); // c2
            }

            // ---constraint 3---//
            for (int i = 1; i < nbCities; i++)
                // u[i] = cplex.numVar(Double.NEGATIVE_INFINITY,
                // Double.POSITIVE_INFINITY, "u" + i);
                u[i] = cplex.numVar(1, nbCities - 1, "u" + i);

            for (int i = 1; i < nbCities; i++) {
                for (int j = i + 1; j < nbCities; j++) {
                    IloLinearNumExpr expr1 = cplex.linearNumExpr();
                    IloLinearNumExpr expr2 = cplex.linearNumExpr();

                    expr1.addTerm(1, u[i]);
                    expr1.addTerm(-1, u[j]);
                    expr1.addTerm(nbCities - 1, x[i][j]);

                    expr2.addTerm(1, u[j]);
                    expr2.addTerm(-1, u[i]);
                    expr2.addTerm(nbCities - 1, x[j][i]);

                    cplex.addLe(expr1, nbCities - 2);
                    cplex.addLe(expr2, nbCities - 2);
                }
            }

            // ---lp file out---//
            cplex.exportModel("./output/MTZ_" + times + ".lp");
            if (!debugOn)
                cplex.setOut(null);
            cplex.setParam(IloCplex.Param.WorkMem, 512);
            cplex.setParam(IloCplex.Param.Emphasis.Memory, true);
            // ---solve---//
            long start = System.currentTimeMillis();
            boolean success = cplex.solve();
            long end = System.currentTimeMillis();
            time = end - start;

            if (success) {
                minLength = cplex.getObjValue();
                System.out.printf("cplex.solved to %.4f\n", minLength);
                for (int i = 0; i < nbCities; i++)
                    fx[i] = cplex.getValues(x[i]);
                int cityIdx = 0;
                // output solution matrix
                if (debugOn) {
                    for (int i = 0; i < nbCities; i++) {
                        System.out.printf("%2d : ", i + 1);
                        boolean found = false;
                        for (int j = 0; j < nbCities; j++) {
                            System.out.printf(" %d ", (int) (fx[i][j] + 0.00001));
                            if ((int) (fx[i][j] + 0.00001) == 1) {
                                assert !found : "found two variables = 1 for i =" + i;
                                found = true;
                            } // if
                        } // for j
                        System.out.println();
                    } // for i
                      // System.out.printf("subtour: %s\n",
                      // Arrays.toString(subtour));
                    System.out.printf("Internal solution Idx: %d ", 1);

                    for (int i = 0; i < nbCities; i++) {
                        boolean found = false;
                        for (int j = 0; j < nbCities && !found; j++) {
                            if ((int) (fx[cityIdx][j] + 0.00001) == 1) {
                                System.out.printf(", %d", (j + 1));
                                cityIdx = j;
                                found = true;
                                break;
                            } // if
                        } // for j
                    } // for i
                    System.out.println();
                } // if debugOn

                // construct solution sequence in sulTour[]
                int solIdx = 0;
                cityIdx = 0;
                solTour[solIdx++] = subtour[0];
                // System.out.printf("%d ", subtour[0]);
                for (int i = 0; i < nbCities; i++) {
                    boolean found = false;
                    for (int j = 0; j < nbCities && !found; j++) {
                        if ((int) (fx[cityIdx][j] + 0.00001) == 1) {
                            // System.out.printf(", %d", subtour[j]);
                            if (i != nbCities - 1) {
                                // no need to store the last "1" in solTour
                                solTour[solIdx++] = subtour[j];
                                cityIdx = j;
                            }
                            found = true;
                            break;
                        }
                    }
                }
                // System.out.println("\n" + Arrays.toString(solTour));

            } else {
                System.out.printf("cplex.solve() fails. %s\n", errStr);
                System.exit(1);
            }
        } catch (IloException ex) {
            System.out.println(ex);
            System.out.printf("cplex exceptions caught. %s\n", errStr);
            System.exit(1);
        }
        // solTour[] returned to main()
        return solTour;
    }

    public static void main(String[] args) throws IOException {
        boolean defaultNbStart = true;
        boolean defaultAlpha = true;
        boolean defaultnbVehicles = true;
        for (String arg : args) {
            if (arg.startsWith("-restart=") || arg.startsWith("-restarts=")) {
                nbStart = Integer.parseInt(arg.split("=")[1]);
                defaultNbStart = false;
            }
            if (arg.startsWith("-alpha=")) {
                alpha = Double.parseDouble(arg.split("=")[1]);
                defaultAlpha = false;
            }
            if (arg.startsWith("-vehicle=") || arg.startsWith("-vehicles=")) {
                nbVehicles = Integer.parseInt(arg.split("=")[1]);
                defaultnbVehicles = false;
            }
            if (arg.startsWith("-debug") || arg.startsWith("-debug=on")) {
                debugOn = true;
            }
        }
        System.out.printf("DEBUG MODE IS %s.\n", (debugOn) ? "ON" : "OFF (default)");
        System.out.printf("nbStart %s to %d\n", (defaultNbStart) ? "defaults" : "overrides", nbStart);
        System.out.printf("alpha %s to %.2f\n", (defaultAlpha) ? "defaults" : "overrides", alpha);
        System.out.printf("nbVehicles %s to %d\n", (defaultnbVehicles) ? "defaults" : "overrides", nbVehicles);

        // add nbVehicles - 1 number of starting node
        nbNodes = nbCities + nbVehicles - 1;

        readFile();
        
        
        iniRoute = new int[nbNodes + 1];
        
        
        double optLength[][] = new double[nbStart][2];
        localOptimum = new int[nbNodes + 1];
        stage1Opt = new int[nbNodes + 1];
        rdSeq = new HashSet<Route>();
        int optimalIdx = 0;
        double minFound = Double.MAX_VALUE;

        System.out.println("--- Stage-1: LOCAL SEARCH OPTIMIZATION ---");
        // nbSep times search
        long start = System.nanoTime();
        for (int i = 0; i < nbStart; i++) {

//            while (!genInitSeq(iniRoute)) {
//                ;
//            }
            
            genInitSeq(iniRoute);
            iniObjective=0;
            for(int j=0;j<nbNodes;j++){
                iniObjective+=distance[iniRoute[j]-1][iniRoute[j+1]-1];
            }
            
            System.out.println(Arrays.toString(iniRoute));
            System.out.println("iniObjective=" +iniObjective);
            
            System.out.println(Arrays.toString(iniRoute));
            iterate_SA(iniRoute);
            optLength[i][0] = minObjective;
            optLength[i][1] = min_max;

            if (optLength[i][0] + penaltycaculate(optLength[i][1]) < minFound) {
                minFound = optLength[i][0] + penaltycaculate(optLength[i][1]);
                optimalIdx = i;
                System.arraycopy(localOptimum, 0, stage1Opt, 0, localOptimum.length);
                System.out.printf("#%d: found new best = %.4f\n", (optimalIdx + 1), minFound);
            }
            if ((i + 1) % 100 == 0) {
                System.out.print("#" + (i + 1) + "\t");
            }
            if (i != 0 && i % 1000 == 0) {
                System.out.println();
            }

        }
        long end = System.nanoTime();
        System.out.printf("\n%d iterations take %.3f seconds.", nbStart, (end - start) / 1e+9);

        System.out.printf("\nGlobal minimum objective found in #%d = %.4f\n", (optimalIdx + 1), minFound);
        System.out.println("Combined Stage-1 tour: " + Arrays.toString(stage1Opt));
        
        System.exit(0);

        System.out.println("--- Stage-2: MATH PROG OPTIMIZATION ---");
        int stage2Opt[] = new int[nbNodes + 1];
        stage2Opt[nbNodes] = 1;
        int stage2OptIdx = 0;
        BufferedWriter writer1 = null;
        BufferedWriter writer2 = null;
        start = System.nanoTime();
        try {
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());
            File logFile1 = new File("./output/Solution_" + timeLog + "-1.txt");
            File logFile2 = new File("./output/Solution_" + timeLog + "-2.txt");
            System.out.println("Writing Stage-1 solution to " + logFile1.getCanonicalPath());
            writer1 = new BufferedWriter(new FileWriter(logFile1));
            writer2 = new BufferedWriter(new FileWriter(logFile2));
            int i = 0;
            for (int j = 0; j < nbVehicles; j++) {
                ArrayList<Integer> tmpRoute = new ArrayList<Integer>();
                System.out.print("Subtour #" + j + ": " + ((stage1Opt[i] > nbCities) ? 1 : stage1Opt[i]));
                assert stage1Opt[i] == 1 || stage1Opt[i] > nbCities : "wrong" + stage1Opt[i];
                tmpRoute.add(1);
                i++;
                while (stage1Opt[i] <= nbCities && stage1Opt[i] != 1) {
                    System.out.printf(", %d", stage1Opt[i]);
                    tmpRoute.add(stage1Opt[i]);
                    i++;
                }
                // i++;
                System.out.println(", " + ((stage1Opt[i] > nbCities) ? 1 : stage1Opt[i]));
                String tmpStr = tmpRoute.toString();
                writer1.write(tmpStr.substring(1, tmpStr.length() - 1) + ", 1\n");
                int nbCitiesSub = tmpRoute.size();
                int subtour[] = new int[nbCitiesSub];
                for (int k = 0; k < nbCitiesSub; k++) {
                    subtour[k] = tmpRoute.get(k).intValue();
                }
                // System.out.println(Arrays.toString(subtour));

                double tmpDist[][] = new double[nbCitiesSub][nbCitiesSub];
                for (int k = 0; k < nbCitiesSub; k++)
                    for (int l = 0; l < nbCitiesSub; l++) {
                        int a = subtour[k];
                        int b = subtour[l];
                        tmpDist[k][l] = distance[a - 1][b - 1];
                    }
                // for (double[] tmp : tmpDist)
                // System.out.println(Arrays.toString(tmp));
                double tourLength = 0;
                for (int k = 0; k < nbCitiesSub - 1; k++) {
                    tourLength += tmpDist[k][k + 1];
                }
                tourLength += tmpDist[nbCitiesSub - 1][0];
                System.out.printf("Stage-1 tourLength #%d = %.4f\n", j, tourLength);
                System.out.println("re-optimizing...");
                int[] solTour = MTZsolve(tmpDist, nbCitiesSub, subtour);
                System.out.println(
                        "solTour solved to: " + Arrays.toString(solTour).substring(1, tmpStr.length() - 1) + ", 1");
                writer2.write(Arrays.toString(solTour).substring(1, tmpStr.length() - 1) + ", 1\n");
                // copy each solTour[] to the combined stage2Opt[]
                System.arraycopy(solTour, 0, stage2Opt, stage2OptIdx, solTour.length);
                stage2OptIdx += solTour.length;
            }
            System.out.println("Stage-2 solution written to " + logFile2.getCanonicalPath());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer1.close();
                writer2.close();
            } catch (Exception ex) {
                System.out.println(ex);
                System.out.printf("File IO exceptions caught. %s\n", errStr);
                System.exit(1);
            }
        }
        end = System.nanoTime();
        System.out.printf("Optimizing all %d subtours completes in %.3f seconds.\n", nbVehicles, (end - start) / 1e+9);
        // Final output
        System.out.println("Stage-1 Combined tour: " + Arrays.toString(stage1Opt));
        System.out.println("Stage-2 Combined tour: " + Arrays.toString(stage2Opt));
        int i = 0;
        double subLength[] = new double[nbVehicles];
        double totalLength = 0;
        double tempMin = Double.MAX_VALUE, tempMax = -1;
        for (int j = 0; j < nbVehicles; j++) {
            System.out.printf("Stage-2 subtour #%d length = %.4f", j, distance[stage2Opt[i] - 1][stage2Opt[i + 1] - 1]);
            // System.out.print("(" + stage2Opt[i] + "," + stage2Opt[i + 1] +
            // ")");
            subLength[j] = distance[stage2Opt[i] - 1][stage2Opt[i + 1] - 1];
            i++;
            while (stage2Opt[i] <= nbCities && stage2Opt[i] != 1) {
                System.out.printf(" + %.4f", distance[stage2Opt[i] - 1][stage2Opt[i + 1] - 1]);
                subLength[j] += distance[stage2Opt[i] - 1][stage2Opt[i + 1] - 1];
                i++;
            }
            tempMin = Math.min(tempMin, subLength[j]);
            tempMax = Math.max(tempMax, subLength[j]);
            System.out.printf("= %.4f\n", subLength[j]);
            totalLength += subLength[j];
        }
        System.out.printf("Final Stage-2 objective = %.4f\n", totalLength);
        System.out.printf("Stage-1 min/max ratio = %.4f\n", optLength[optimalIdx][1]);
        System.out.printf("Stage-2 min/max ratio = %.4f\n", tempMin / tempMax);

    }

    private static void readFile() {
        ReadFile rf = new ReadFile();
        rf.main(null);
        distance = rf.distance;
        // System.out.println(distance.length);
        for (int i = 0; i < nbNodes; i++) {
            for (int j = 0; j < nbNodes; j++) {
                // System.out.print(distance[i][j] + "\t");
            }
            // System.out.println();
        }
    }

    private static boolean isConnected(int[] iniRoute) {
        // check if the iniRoute is a connected tour
        return true;
    }

    private static boolean genInitSeq(int[] iniRoute) {
        iniObjective = 0;
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
        for (int i = 0; i < nbNodes; i++) {
            // System.out.print(iniRoute[i] + "-");
            if (distance[iniRoute[i] - 1][iniRoute[i + 1] - 1] >= MAXDISTANCE)
                return false;
            iniObjective += distance[iniRoute[i] - 1][iniRoute[i + 1] - 1];
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

    // check whether route and routeLen is consistent
    private static boolean Checked(int[] route, double routeLen) {
        double epsilon = 0.00001;
        boolean returnValue = true;
        // First check whether it is a valid route
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
        if (Math.abs(length - routeLen) < epsilon) {
            // System.out.println("|Length caculation is CORRECT!");
        } else {
            returnValue = false;
            System.out.println("|Length caculation is INcorrect!");
        }
        return returnValue;
        // return false;
    }

    private static void iterate_SA(int[] route) throws IOException {
        
//        PrintWriter writer = null;
//        File outputFile = new File("./output/Solution.txt");
//        outputFile.getParentFile().mkdirs();
//        writer = new PrintWriter(new FileWriter(outputFile));
        
        
        
        minObjective = iniObjective;
        //System.out.println("iniObjective = "+iniObjective);
        min_max = 0;
        int newRoute[] = new int[nbNodes + 1];

        double routedis = iniObjective;
        double routeminmax = 0;
        double newroutedis = 0;
        double newrouteminmax = 0;
        System.arraycopy(route, 0, localOptimum, 0, localOptimum.length);
        

        // Calculate maximal and minimal distance of initial route if nbVehicles!=1
        int i2 = 0;
        int n = 0;
        double lengthRoute[] = new double[nbVehicles];
        for (; n < nbVehicles - 1; n++) {
            do {
                assert i2 < nbNodes : "Wrong i2 route1";
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
        assert i2 == nbNodes : i2 + " Wrong i2 route2";
        double min = lengthRoute[0];
        double max = lengthRoute[0];
        for (int m = 0; m < nbVehicles; m++) {
            min = min < lengthRoute[m] ? min : lengthRoute[m];
            max = max > lengthRoute[m] ? max : lengthRoute[m];
        }
        
        if(nbVehicles==1){
            min_max=1;
            routeminmax=1;
        }else{
            min_max = min / max;
            routeminmax = min_max;
        }

        

        
        
        Double Temperature = 10000.0;


        do {
            int t = 0;
            int stopcondition1 = 0; // amount of trasformation of newroute to
                                    // route
            int stopcondition2 = 0; // amount of random generation for neighbors
                                    // of route

            do {
                t++;
                // randomly generate a valid neighbor of route
                int i = 0; // beginning of the subtour
                int j = 0; // end of the subtour
                boolean isvalid = false;
                Random random = new Random();
                while (!isvalid) {
                    isvalid = true;
                    i = random.nextInt(nbNodes - 2) + 1;
                    j = random.nextInt(nbNodes - i - 1) + i + 1;
                    if (i == 1 && j == nbNodes - 1)
                        isvalid = false;
                }
                
                //System.out.println(Arrays.toString(route));
                //System.out.println("i="+route[i]+" j="+route[j]);

                double incre = distance[route[i - 1] - 1][route[j] - 1] + distance[route[j + 1] - 1][route[i] - 1];
                double decre = distance[route[i - 1] - 1][route[i] - 1] + distance[route[j] - 1][route[j + 1] - 1];

                // construct newRoute
                int k;
                for (k = 0; k < i; k++) {
                    newRoute[k] = route[k];
                }
                int l = k;
                for (k = j; k >= i; k--) {
                    newRoute[l] = route[k];
                    l++;
                }
                for (k = j + 1; k <= nbNodes; k++) {
                    newRoute[k] = route[k];
                }
                
                //System.out.println(Arrays.toString(newRoute));

                // Calculate maximal and minimal distance by lengthRoute
                i2 = 0;
                n = 0;
                for (int i3 = 0; i3 < lengthRoute.length; i3++) {
                    lengthRoute[i3] = 0;
                }
                for (; n < nbVehicles - 1; n++) {
                    // System.out.print("route" + n + ": ");
                    do {
                        assert i2 < nbNodes : "Wrong i2 route1";
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
                assert i2 == nbNodes : i2 + " Wrong i2 route2";
                min = lengthRoute[0];
                max = lengthRoute[0];
                for (int m = 1; m < nbVehicles; m++) {
                    min = min < lengthRoute[m] ? min : lengthRoute[m];
                    max = max > lengthRoute[m] ? max : lengthRoute[m];
                }

                newroutedis = routedis + incre - decre;
                newrouteminmax = (nbVehicles==1)? 1:(min / max);

                // compare newroute with route
                double newroutepenalty = penaltycaculate(newrouteminmax);
                double routepenalty = penaltycaculate(routeminmax);

                if (routedis + routepenalty > newroutedis + newroutepenalty+0.00001) {
                    if (newroutedis + newroutepenalty+0.00001 < minObjective + penaltycaculate(min_max)) {
                        minObjective = newroutedis;
                        min_max = newrouteminmax;
                        System.arraycopy(newRoute, 0, localOptimum, 0, newRoute.length);
                        System.out.printf("#%d: found new best = %.8f\n", (t + 1), minObjective);
                        System.out.println("Temperature="+Temperature);
                    }
                    System.arraycopy(newRoute, 0, route, 0, newRoute.length);
                    routedis = newroutedis;
                    routeminmax = newrouteminmax;
                    stopcondition1++;
                    stopcondition2 = 0;
                } else {
                    double random0_1 = random.nextDouble();
                    double diff=routedis + routepenalty - (newroutedis + newroutepenalty);
                    double compare= Math.pow(Math.E,(diff) / Temperature);
                    
//                    System.out.println("eval(route)-eval(newroute)="+diff);
//                    System.out.println("Temperature="+Temperature);
//                    System.out.println("compare="+compare);
//                    System.out.println("random0-1="+random0_1);
                    
                    if (random0_1 <compare) {
                        System.arraycopy(newRoute, 0, route, 0, newRoute.length);
                        routedis = newroutedis;
                        routeminmax = newrouteminmax;
                        stopcondition1++;
                        stopcondition2 = 0;
                    } else {
                        stopcondition2++;
                    }
                }

            } while (stopcondition1 <= 100000 && stopcondition2 <= 3000);
            System.out.println("stopcondition1="+stopcondition1);
            System.out.println("stopcondition2="+stopcondition2);
            System.out.println();
            
            Temperature = Temperature / 1.2;

        } while (Temperature >= 0.01);
        
        //writer.close();

    }

    private static void iterate(int[] route) {
        minObjective = Double.MAX_VALUE;
        final boolean min_max_obj = false; // false: normal obj: minimize total
                                           // distance
        // true: obj: minimize the longest route

        int newRoute[] = new int[nbNodes + 1];
        double maxDecre = 0;
        int localMinRoute[] = new int[nbNodes + 1];

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
                    assert i2 < nbNodes : "Wrong i2 route1";
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
            assert i2 == nbNodes : i2 + " Wrong i2 route2";
            double min = lengthRoute[0];
            double max = lengthRoute[0];
            for (int m = 0; m < nbVehicles; m++) {
                min = min < lengthRoute[m] ? min : lengthRoute[m];
                max = max > lengthRoute[m] ? max : lengthRoute[m];
            }
            double oldMax = max;
            if (min_max_obj) {
                iniObjective = oldMax;
            }
            min_max = min / max;

            maxDecre = penaltycaculate(min / max);

            updated = false; // if updated: found a new local optimum
            // if not updated: stuck in the current local optimum

            for (int i = 1; i < nbNodes - 1; i++) {
                for (int j = 0; i + j + 2 <= nbNodes; j++) {
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
                    for (k = i + j + 2; k < nbNodes; k++) {
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
                            assert i2 < nbNodes : "Wrong i2 route1";
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
                    assert i2 == nbNodes : i2 + " Wrong i2 route2";
                    min = lengthRoute[0];
                    max = lengthRoute[0];
                    for (int m = 1; m < nbVehicles; m++) {
                        min = min < lengthRoute[m] ? min : lengthRoute[m];
                        max = max > lengthRoute[m] ? max : lengthRoute[m];
                    }

                    // judge if it is a better solution

                    double temppenalty = penaltycaculate(min / max);

                    double diff = (!min_max_obj) ? incre - decre : max - oldMax;
                    if (temppenalty + diff < maxDecre) {
                        updated = true;
                        min_max = min / max;
                        penalty = temppenalty;
                        minObjective = iniObjective + diff;
                        // iniLength = minLength;
                        maxDecre = diff + penalty;
                        System.arraycopy(newRoute, 0, localMinRoute, 0, route.length);

                        double totalLength = 0;
                        for (int i3 = 0; i3 < nbVehicles; i3++) {
                            // System.out.println("LengthRoute" + i3 + " = : " +
                            // lengthRoute[i3]);
                            totalLength += lengthRoute[i3];
                        }
                        // System.out.println(" total = : " + totalLength);
                        // System.out.println(" total+penalty = : " +
                        // (totalLength + penalty));

                    }

                } // for j
            } // for i
            if (updated) {
                iniObjective = minObjective;
            }
            System.arraycopy(localMinRoute, 0, route, 0, route.length);
        } while (updated);

        minObjective = iniObjective; // if not updated for one time
        System.arraycopy(route, 0, localOptimum, 0, route.length);
    }

}