import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

import edu.princeton.cs.algs4.Stopwatch;
import ilog.concert.*;
import ilog.cplex.*;

public class Branch_and_Bound_MIP {
    /*
     * Maximize Z=c*x 
     *      s.t a*x<=b 
     *          x>=0 
     *          x_int is integer 
     * (first numof_int variables are integers)
     */

    
    private class Node implements Comparable<Node>{
        int divpoint;
        double divpointvalue;
        Stack<IloRange> st;
        double opt;

        
        public Node(int divpoint,double divpointvalue,Stack<IloRange> st,double opt){
            this.divpoint=divpoint;
            this.divpointvalue=divpointvalue;
            this.st=st;
            this.opt=opt;
        }
        
        public int compareTo(Node that){
            if(this.opt<that.opt) return -1;else{
                if(this.opt==that.opt)return 0;else{
                    return 1;
                }
            }
        }
    }
    

    double c[];
    IloNumVar[] x;
    Stack<IloRange> st;
    IloCplex cplex0;
    double[][] a;
    double[] b;
    int numofx_int;
    int numofx_num;
    int numofst;
    int numofx;
    static double eps=1e-10;
    double LB=-Double.MAX_VALUE;
    PriorityQueue<Node> record;
    IloLinearNumExpr exprObj;
    double[] bestxRecord;
    

    public Branch_and_Bound_MIP(String fileName) throws IOException, IloException {
        try {
            readData();

            // build the initial model
            cplex0 = new IloCplex();
            x= cplex0.numVarArray(numofx, 0, Double.MAX_VALUE);

            exprObj = cplex0.linearNumExpr();
            for (int i = 0; i < numofx; i++) {
                exprObj.addTerm(c[i], x[i]);
            }
            
            cplex0.addMaximize(exprObj);

            // add constraints
            st=new Stack<IloRange>();
            for (int i = 0; i < numofst; i++) {
                IloLinearNumExpr expr = cplex0.linearNumExpr();
                for (int j = 0; j < numofx; j++) {
                    expr.addTerm(a[i][j], x[j]);
                }
                st.push(cplex0.addLe(expr, b[i]));
                //System.out.println(st.get(st.size()-1));

            }

            //cplex0.remove(st.pop());
            //cplex0.exportModel("sample0.lp");
            
            
            // solve
            record=new PriorityQueue<Node>();
            boolean success = cplex0.solve();
            if (success) {
                //System.out.println(success);
                double[] xx = cplex0.getValues(x);
                int first_non_int=ifallint(xx);
                
                if(first_non_int<0) {
                    cplex0.output().println("Solution status = " + cplex0.getStatus());
                    cplex0.output().println("Solution value  = " + cplex0.getObjValue());
                    double[] xxx = cplex0.getValues(x);
                    for (int i = 0; i < numofx; i++) {
                      System.out.printf("x[%d] = ", i + 1);
                      System.out.println(xxx[i]); 
                    }
                    }else{
                
                    @SuppressWarnings("unchecked")
                    Stack<IloRange> temp=(Stack<IloRange>) st.clone();
                    Node initial=new Node(first_non_int,xx[first_non_int],temp,cplex0.getObjValue());
                    record.add(initial);
                }
                

            } else {
                System.out.println("cplex0.solve() failed.");
            }

        } catch (IloException ex) {
            System.out.println(ex);
        }
    }
    
    
    // if int-variables are all integers, return -1 if it is true;
    public int ifallint(double[] xx){   
        boolean allint=true;
        int first_non_int=0;
        for(int i=0;i<numofx_int;i++){
            if(xx[i]-(double)((int)xx[i]) > eps){
                allint=false;
                first_non_int=i;
                break;
            }
        }
        if(allint) return -1;else{
            return first_non_int;
        }
    }

    public void readData() throws java.io.IOException {
        Scanner in = new Scanner(Paths.get("spring garden tools.txt"));
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
    
    public void Search() throws  IloException{
        bestxRecord=new double[numofx];
        while(!record.isEmpty()){
            // construct a new model
            Node temp=record.poll();
            IloCplex tempmodel=new IloCplex();
            exprObj = tempmodel.linearNumExpr();
            IloNumVar[] x=tempmodel.numVarArray(numofx, 0, Double.MAX_VALUE);
            for (int i = 0; i < numofx; i++) {
                exprObj.addTerm(c[i], x[i]);
            }
            tempmodel.addMaximize(exprObj);
            
            for(int i=0;i<temp.st.size();i++){
                //tempmodel.add(temp.st.get(i));
                //System.out.println(temp.st.get(i).getExpr());
                //tempmodel.addCut(temp.st.get(i));
                tempmodel.addLe(temp.st.get(i).getExpr(), temp.st.get(i).getUB());
            }
            

            
//            System.out.println(tempmodel.getModel()); 
//            System.out.println("model's divdedVar is  x"+temp.divpoint+"="+temp.divpointvalue);
//            System.out.println("Currently the queue's size is "+ record.size());
            
            assert(tempmodel.solve()): " the tempmodel cannot be solved";
            
            double b1=Math.floor(temp.divpointvalue);
            double b2=Math.ceil(temp.divpointvalue);
            
            boolean ifadd1=true;
            boolean ifadd2=true;
            
            IloLinearNumExpr addexpr = tempmodel.linearNumExpr();
            addexpr.addTerm(1, x[temp.divpoint]);
            
            Stack<IloRange> st1=(Stack<IloRange>) temp.st.clone();
            Stack<IloRange> st2=(Stack<IloRange>) temp.st.clone();
            
            
          //build and test 1st submodel
            st1.push(tempmodel.addLe(addexpr, b1));
            
            boolean success=tempmodel.solve();
            double obj1=0;
            
            double[] x1=new double[x.length];
            int first_non_int1=0;
            
            if(!success) ifadd1=false; else{
                obj1=tempmodel.getObjValue();
                if(obj1<LB)ifadd1=false;else{
                    x1 = tempmodel.getValues(x);
                    first_non_int1=ifallint(x1);
                    if(first_non_int1<0){
                        ifadd1=false;
                        if(LB<obj1){
                            System.arraycopy(x1,0,bestxRecord,0,x1.length);
                            System.out.println("A new best solution found.");
                            System.out.println(Arrays.toString(x1));
                            System.out.println("Current optimal objvalue is "+obj1);
                            LB=obj1;
                        }
                    }
                }
            }
            
                 //build a Node for 1st submodel
            if(ifadd1){ 
                Node subnode1=new Node(first_non_int1,x1[first_non_int1],st1,obj1);
                record.add(subnode1);
                }
            
            
            // build and test 2nd submodel and add
            
            IloLinearNumExpr addexpr2 = tempmodel.linearNumExpr();
            addexpr2.addTerm(-1, x[temp.divpoint]);
            tempmodel.remove(st1.peek());
            st2.push(tempmodel.addLe(addexpr2, -b2));
            
            success=tempmodel.solve();
            double obj2=0;
            
            double[] x2=new double[x.length];
            int first_non_int2=0;;
            
            if(!success) ifadd2=false; else{
                obj2=tempmodel.getObjValue();
                if(obj2<LB)ifadd2=false;else{
                    x2 = tempmodel.getValues(x);
                    first_non_int2=ifallint(x2);
                    if(first_non_int2<0){
                        ifadd2=false;
                        if(LB<obj2){
                            System.out.println("A new best solution found.");
                            System.out.println(Arrays.toString(x2));
                            System.out.println("Current optimal objvalue is "+obj2);
                            LB=obj2;
                        }
                    }
                }
            }
            
            //build a Node for 1st submodel and add
            if(ifadd2){
                Node subnode2=new Node(first_non_int2,x2[first_non_int2],st2,obj2);
                record.add(subnode2);
                }
            
//            System.out.println("ifadd1="+ifadd1);
//            System.out.println("ifadd2="+ifadd2);
            

        }
        
        Output();
    }

    public void Output(){
        if(LB==-Double.MAX_VALUE){
            System.out.println("We can't find a optimal solution");
        }else{
            System.out.println("The optimal solution is "+Arrays.toString(bestxRecord));
            System.out.println("The maximal value is "+LB);
        }
    }
    public static void main(String[] args) throws IOException, IloException {
        Stopwatch timer = new Stopwatch();
        
        Branch_and_Bound_MIP test = new Branch_and_Bound_MIP("test.txt");
       // System.out.println(test.record.elementAt(0).divpoint);
        test.Search();
        
        double time = timer.elapsedTime();
        System.out.println("time = " + time);

    }

}
