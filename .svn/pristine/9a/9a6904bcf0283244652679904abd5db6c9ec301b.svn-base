import java.util.Arrays;
import java.util.Stack;

import ilog.concert.*;
import ilog.cplex.*;

public class sample {

    public static void main(String[] args) {
        try {
            IloCplex cplex = new IloCplex();
            // IloNumVar[] x = new IloNumVar[3];

            // build model
            IloLinearNumExpr exprObj = cplex.linearNumExpr();
            // for (int i = 0; i < 3; i++) {
            // x[i] = cplex.numVar(0, Double.MAX_VALUE, "x" + (i + 1));
            // }

            double[] lb = { -Double.MAX_VALUE, 0.0, 0, -Double.MAX_VALUE };
            double[] ub = { 0, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE };
            String[] varname = { "x1", "x2", "x3", "x4" };
            IloNumVar[] x = cplex.numVarArray(4, lb, ub, varname);

            int[] parameter = { 2, 3, -5, 0 };
            exprObj.add(cplex.scalProd(parameter, x));
            cplex.addMinimize(exprObj);

            // constraints
            IloLinearNumExpr expr1 = cplex.linearNumExpr();
            IloLinearNumExpr expr2 = cplex.linearNumExpr();
            IloLinearNumExpr expr3 = cplex.linearNumExpr();

            expr1.addTerm(1, x[0]);
            expr1.addTerm(1, x[1]);
            expr1.addTerm(-1, x[2]);
            expr1.addTerm(1, x[3]);

            expr2.addTerm(2, x[0]);
            expr2.addTerm(0, x[1]);
            expr2.addTerm(1, x[2]);
            expr2.addTerm(0, x[3]);

            expr3.addTerm(0, x[0]);
            expr3.addTerm(1, x[1]);
            expr3.addTerm(1, x[2]);
            expr3.addTerm(1, x[3]);

            Stack<IloRange> constraint = new Stack<IloRange>();
            constraint.push(cplex.addGe(expr1, 5));
            constraint.push(cplex.addLe(expr2, 4));
            constraint.push(cplex.addEq(expr3, 6));
            // constraint.push(cplex.addLe(expr2, 30));
            // cplex.addLe(x[0], 40);
            // cplex.remove(constraint[0]);
            // cplex.remove(constraint.pop());
            // cplex.remove(constraint.pop());

            // lp file out
            cplex.exportModel("sample.lp");

            // solve
            boolean success = cplex.solve();
            if (success) {
                cplex.output().println("Solution status = " + cplex.getStatus());
                cplex.output().println("Solution value  = " + cplex.getObjValue());
                double xx[] = cplex.getValues(x);
                double reducedcost[] = cplex.getReducedCosts(x);
                for (int i = 0; i < 4; i++) {
                    System.out.print(x[i] + " = " + xx[i]);
                    System.out.println("    Reduced cost = " + reducedcost[i]);
                }

            } else {
                System.out.println("cplex.solve() failed.");
            }

        } catch (IloException ex) {
            System.out.println(ex);
        }
    }
}
