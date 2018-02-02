import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.Stopwatch;
import ilog.concert.*;
import ilog.cplex.*;

public class Solve {
    int numCity;
    double[][] distance;
    double[][] currentx;
    final int maxrounds;
    List<List<Integer>> subtour;
    int[] optRoute;
    boolean opt;


    public Solve(int numCity, double[][] distance, int maxrounds) {
        
        this.numCity = numCity;
        this.distance = distance;
        this.maxrounds = maxrounds;
        optRoute=new int[numCity];
    }

    public void TeachingPaper_Solve() throws IloException {
        
        Stopwatch timer = new Stopwatch();
        
        IloCplex cplex = new IloCplex();
        IloIntVar[][] x = new IloIntVar[numCity][numCity];
        IloNumVar[] u = new IloNumVar[numCity];

        // ---build model---//
        IloLinearNumExpr exprObj = cplex.linearNumExpr();
        for (int i = 0; i < numCity; i++) {
            for (int j = 0; j < numCity; j++) {
                if (i != j)
                    x[i][j] = cplex.intVar(0, 1, "x" + i + "," + j);
                else
                    x[i][j] = cplex.intVar(0, 0, "x" + i + "," + j);
            }
            exprObj.add(cplex.scalProd(x[i], distance[i]));
        }
        cplex.addMinimize(exprObj);

        // ---constraint 1 & 2---//
        for (int i = 0; i < numCity; i++) {
            IloLinearIntExpr constraint2 = cplex.linearIntExpr();
            for (int j = 0; j < numCity; j++)
                constraint2.addTerm(1, x[j][i]);
            cplex.addEq(cplex.sum(x[i]), 1); // c1
            cplex.addEq(constraint2, 1); // c2
        }
        
        

        int k = 1;
        opt=false;

        // ---start the algorithm step2---//
        while (k <= maxrounds) {

            cplex.exportModel("MTZ"+k+".lp");
            cplex.setOut(null);
            //cplex.setParam(IloCplex.IntParam.EachCutLim,0);
            boolean feasible = cplex.solve();

            System.out.println("In the "+k+" st round, the optValue is "+cplex.getObjValue());
            double time = timer.elapsedTime();
            System.out.println("Now the time                    = " + time);
            
            if (feasible) {
                currentx = new double[numCity][numCity];
                for (int i = 0; i < numCity; i++) {
                    currentx[i] = cplex.getValues(x[i]);
                }

                // part1: find all the subtours from currentx
                subtour = findSubtour(currentx);
                
                System.out.println("There are "+subtour.size()+" subtours");
                // part2: add the subtour constraints
                if(subtour.size()==1){
                    opt=true;
                    for(int i=0;i<numCity;i++){
                        optRoute[i]=subtour.get(0).get(i);
                    }
                    System.out.println("The model is optimal now!");
                    break;
                }else{
                    // add the constraints
                    for(int i=0;i<subtour.size();i++){
                        IloLinearNumExpr expr = cplex.linearNumExpr();
                        for(int j=0;j<subtour.get(i).size();j++){
                            for(int m=0;m<subtour.get(i).size();m++){
                                expr.addTerm(1, x[subtour.get(i).get(j)][subtour.get(i).get(m)]);
                            }
                        }
                        
//                        System.out.println("The current expr is "+expr.toString());
                        
                        cplex.addLe(expr, subtour.get(i).size()-1);
                    }
                }
                
                k++;
                
                

            } else {
                System.out.println("The  model is unfesible and it may be wrongly built up!");
            }
        }
        
        // ---step3:add the arc constraints---//
        if(!opt){
            
            for (int i = 1; i < numCity; i++)
                u[i] = cplex.numVar(1, numCity - 1, "u" + i);
            
            for (int i = 1; i < numCity; i++) {
                for (int j = i + 1; j < numCity; j++) {
                    IloLinearNumExpr expr1 = cplex.linearNumExpr();
                    IloLinearNumExpr expr2 = cplex.linearNumExpr();
                    
                    expr1.addTerm(1, u[i]);
                    expr1.addTerm(-1, u[j]);
                    expr1.addTerm(numCity-1, x[i][j]);
                    
                    expr2.addTerm(1, u[j]);
                    expr2.addTerm(-1, u[i]);
                    expr2.addTerm(numCity-1, x[j][i]);
                    
                    cplex.addLe(expr1, numCity-2);
                    cplex.addLe(expr2, numCity-2);
                }
            }
            
            boolean feasible=cplex.solve();
            
            if(feasible){
                currentx = new double[numCity][numCity];
                for (int i = 0; i < numCity; i++) {
                    currentx[i] = cplex.getValues(x[i]);
                }
            }
            
            optRoute[0]=0;
            for(int i=1;i<numCity;i++){
                for(int j=0;j<numCity;j++){
                    if(currentx[optRoute[i-1]][j]==1){
                        optRoute[i]=j;
                        break;
                    }
                }
            }
            
            cplex.exportModel("finalModel.lp");
            
        }
        

        
        System.out.println("The optimal objValue is "+cplex.getObjValue());
        System.out.println("The optimal route is "+Arrays.toString(optRoute));

    }

    public List<List<Integer>> findSubtour(double[][] x) {
        
        List<List<Integer>> record = new ArrayList<>();
        List<Integer> list = new ArrayList<Integer>();

        boolean[] chosen = new boolean[numCity];
        int currentCity = 0;
        int count = 1;
        chosen[0] = true;
        list.add(0);

        while (count <numCity) {
            int next = -1;
            for (int i = 0; i < numCity; i++) {
                if (x[currentCity][i] > 0) {
                    next = i;
                    break;
                }
            }
            
            if (next == list.get(0)) {
                record.add(list);
                list = new ArrayList<Integer>();

                for (int i = 0; i < numCity; i++) {
                    if (!chosen[i]) {
                        currentCity = i;
                        count++;
                        chosen[i] = true;
                        list.add(i);
                        break;
                    }
                }
            } else {
                chosen[next] = true;
                count++;
                list.add(next);
                currentCity = next;
            }
        }
        
        record.add(list);

        return record;

    }

    public static void main(String[] args) throws IOException, IloException {
        Stopwatch timer = new Stopwatch();
        
        //ReadData data = new ReadData("./data/tsp29.txt", 29, "NORMAL");
        //ReadData data = new ReadData("./data/tsp6.txt", 6, "NORMAL");
        ReadData data = new ReadData("./data/eil76.tsp", 76, "EUC");
        //ReadData data = new ReadData("./data/bier127.tsp", 127, "EUC");
        //ReadData data = new ReadData("./data/att48.tsp", 48, "ATT");
        //ReadData data = new ReadData("./data/a280.tsp", 280, "EUC");
        //ReadData data = new ReadData("./data/fl417.tsp", 417, "EUC");
        double[][] distance = data.read();
        Solve test = new Solve(76, distance,3);
        test.TeachingPaper_Solve();
        
        double time = timer.elapsedTime();
        System.out.println("Total time                    = " + time);
        
    }

}
