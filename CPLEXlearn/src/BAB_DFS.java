import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import edu.princeton.cs.algs4.Stopwatch;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

public class BAB_DFS {

    /*
     * Maximize Z=c*x s.t a*x<=b x>=0 x_int is integer (first numof_int
     * variables are integers)
     */

    double c[];
    IloNumVar[] x;
    Stack<IloRange> st;
    IloCplex cplex;
    double[][] a;
    double[] b;
    int numofx_int;
    int numofx_num;
    int numofst;
    int numofx;
    static double eps = 1e-5;
    double LB = -Double.MAX_VALUE;
    IloLinearNumExpr exprObj;
    double[] bestxRecord;
    double obj;
    int count;

    public BAB_DFS(String fileName) throws IloException, IOException {

        count = 0;
        readData(fileName);
        bestxRecord = new double[numofx];

        // build the initial model
        cplex = new IloCplex();
        x = cplex.numVarArray(numofx, 0, Double.MAX_VALUE);

        exprObj = cplex.linearNumExpr();
        for (int i = 0; i < numofx; i++) {
            exprObj.addTerm(c[i], x[i]);
        }

        cplex.addMaximize(exprObj);

        // add constraints
        // st=new Stack<IloRange>();
        for (int i = 0; i < numofst; i++) {
            IloLinearNumExpr expr = cplex.linearNumExpr();
            for (int j = 0; j < numofx; j++) {
                expr.addTerm(a[i][j], x[j]);
            }
            // st.push(cplex0.addLe(expr, b[i]));
            cplex.addLe(expr, b[i]);
            // System.out.println(st.get(st.size()-1));

        }

        // solve
        cplex.setOut(null);
        if (cplex.solve()) {
            DFS();
        }
        Output();

    }

    public void Output() {
        if (LB == -Double.MAX_VALUE) {
            System.out.println("We can't find a optimal solution");
        } else {
            if(Checkvalid()){
                System.out.println("The optimal solution is " + Arrays.toString(bestxRecord));
                System.out.println("The maximal value is " + LB);
            }else System.out.println("The answer is invalid");

        }
    }
    
    public boolean Checkvalid(){
        double sum=0;
        for(int i=0;i<numofx;i++){
            sum+=c[i]*bestxRecord[i];
        }
        if(sum!=LB) {
            System.out.println("wrong type 1");
            return false;
        }
        
        for(int constraint=0;constraint<numofst;constraint++){
            sum=0;
            for(int i=0;i<numofx;i++){
                sum+=a[constraint][i]*bestxRecord[i];
            }
            if (sum>b[constraint]) {
                System.out.println("wrong type 2");
                System.out.println("Current anwser is "+Arrays.toString(bestxRecord));
                System.out.println("Breaked constraints is NO."+ constraint);
                System.out.println("sum= "+sum);
                System.out.println("b[constraint]= "+b[constraint]);
                return false;
            }
            
        }
        return true;
    }
    

    public void DFS() throws IloException {

        IloRange record1,record2;
        double obj1 = 0, obj2 = 0;
        IloLinearNumExpr addexpr;
        boolean addnode1 = true;
        boolean addnode2 = true;
        
        //System.out.println(cplex.getModel());

        cplex.solve();
        double[] xx = cplex.getValues(x);
        int first_non_int = ifallint(xx);
        obj = cplex.getObjValue();

//        if (first_non_int >= 0) {
//            System.out.println("model's divdedVar is  x" + (first_non_int + 1) + "=" + xx[first_non_int]);
//            System.out.println("Current solution is " + Arrays.toString(xx));
//
//        }

        if (first_non_int < 0) {
            if (LB < obj) {
                System.arraycopy(xx, 0, bestxRecord, 0, xx.length);
                System.out.println("A new best solution found.");
                System.out.println(Arrays.toString(xx));
                System.out.println("Current optimal objvalue is " + obj);
                LB = obj;

//                count++;
//                cplex.exportModel("./outputmodel/" + count + ".lp");
            }
        } else {

            if (LB < obj) {

                // solve the two branch models
                double b1 = Math.floor(xx[first_non_int]);
                double b2 = Math.ceil(xx[first_non_int]);


                // left-branch model
                addexpr = cplex.linearNumExpr();
                addexpr.addTerm(1, x[first_non_int]);
                record1 = cplex.addLe(addexpr, b1);
                if (!cplex.solve())
                    addnode1 = false;
                else {
                    obj1 = cplex.getObjValue();
                    if (obj1 <= LB)
                        addnode1 = false;
                }

                // if(addnode1){
                // System.out.println("left-branch model is "+
                // cplex.getModel());
                // }

                cplex.remove(record1);

                // right-branch model
                record2 = cplex.addGe(addexpr, b2);
                if (!cplex.solve())
                    addnode2 = false;
                else {
                    obj2 = cplex.getObjValue();
                    if (obj2 <= LB)
                        addnode2 = false;
                }

                // if(addnode2){
                // System.out.println("right-branch model is "+
                // cplex.getModel());
                // }

                cplex.remove(record2);

//                System.out.println("addnode1=" + addnode1);
//                System.out.println("addnode2=" + addnode2);
//                System.out.println("obj1= "+obj1);
//                System.out.println("obj2= "+ obj2);

                // discuss all situations
                if ((addnode1 && addnode2 && obj1 > obj2) ) {
                    record1=cplex.addLe(addexpr, b1);
                    DFS();
                    cplex.remove(record1);
//                    System.out.println("record1="+record1.toString());
//                    System.out.println("Show the right branck works in the right way!!!!!");
//                    System.out.println("The model is "+ cplex.getModel());
                    
                    record2=cplex.addGe(addexpr, b2);

                    DFS();
                    cplex.remove(record2);
                    
                }else{
                    if(addnode1 && addnode2 && obj1 <= obj2){
                        record2=cplex.addGe(addexpr, b2);
                        DFS();
                        cplex.remove(record2);
                        
                        record1=cplex.addLe(addexpr, b1);
                        DFS();
                        cplex.remove(record1);
                    }else{
                        if(addnode1 && !addnode2){
                            record1=cplex.addLe(addexpr, b1);
                            DFS();
                            cplex.remove(record1);
                        }else{
                            if(!addnode1 && addnode2){
                                record2=cplex.addGe(addexpr, b2);
                                DFS();
                                cplex.remove(record2);
                            }
                        }
                    }
                }


            }

        }

    }

    public void readData(String fileName) throws java.io.IOException {
        Scanner in = new Scanner(Paths.get(fileName));
        numofx_int = in.nextInt(); // amount of integer variables
        numofx_num = in.nextInt(); // amount of double variables
        numofst = in.nextInt(); // amount of constraints
        numofx = numofx_int + numofx_num;

        c = new double[numofx];
        for (int i = 0; i < numofx; i++) {
            c[i] = in.nextDouble();
        }

        a = new double[numofst][numofx];
        for (int i = 0; i < numofst; i++) {
            for (int j = 0; j < numofx; j++) {
                a[i][j] = in.nextDouble();
            }
        }

        b = new double[numofst];
        for (int i = 0; i < numofst; i++) {
            b[i] = in.nextDouble();
        }

    }

    // if int-variables are all integers, return -1 if it is true;
    public int ifallint(double[] xx) {
        boolean allint = true;
        int first_non_int = 0;
        for (int i = 0; i < numofx_int; i++) {
            if (xx[i] - (int) xx[i] > eps && (int) xx[i] + 1 - xx[i] > eps) {// xx[i]-(double)((int)xx[i])
                                                                             // >
                                                                             // eps){
                allint = false;
                first_non_int = i;
                break;
            }
        }
        if (allint)
            return -1;
        else {
            return first_non_int;
        }
    }

    public static void main(String[] args) throws IloException, IOException {
        Stopwatch timer = new Stopwatch();

        BAB_DFS test = new BAB_DFS("spring garden tools.txt");
        //BAB_DFS test = new BAB_DFS("test.txt");

        double time = timer.elapsedTime();
        System.out.println("time = " + time);
    }
}
