package bendersexample;

import ilog.concert.IloConstraint;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloLinearNumExprIterator;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.CutManagement;
import java.util.HashMap;

/**
 * AggressiveBenders creates master and subproblems and solves the fixed-charge
 * transportation problem using Benders decomposition. It is more aggressive
 * than the Benders class in that it looks to generate cuts at every node,
 * not just when an integer-feasible incumbent is found. It does this by
 * rounding fractional solutions to the master problem.
 *
 * The master problem (MIP) selects the warehouses to use.
 *
 * The subproblem (LP) determines flows from warehouses to customers.
 *
 * @author Paul A. Rubin <rubin@msu.edu>
 */
public class AggressiveBenders extends Benders {

  /**
   * Constructor.
   * @param nW number of potential warehouses
   * @param nC number of customers
   * @param capacity warehouse capacities
   * @param demand customer demands
   * @param fixed fixed costs to use warehouses
   * @param unitCost unit flow costs
   * @throws IloException if something makes CPLEX unhappy
   */
  public AggressiveBenders(final int nW, final int nC, final double[] capacity,
                           final double[] demand, final double[] fixed,
                           final double[][] unitCost)
         throws IloException {
    super(nW, nC, capacity, demand, fixed, unitCost);
    // attach a user cut callback to the master
    master.use(new BendersCutCallback());
  }

  /**
   * BendersCutCallback implements a user cut callback that rounds the
   * warehouse selection decisions from the current master problem node
   * solution, uses them to set the subproblem constraints, and tries
   * to solve the subproblem.
   *
   * Optimality and feasibility cuts are generated exactly as in the Benders
   * class (lazy constraint callback). Before adding the cuts, though, they
   * are checked to verify that the (fractional) node solution violates them,
   * and are added only if it does.
   */
  private class BendersCutCallback extends IloCplex.UserCutCallback {

    @Override
    protected void main() throws IloException {
      HashMap<IloNumVar, Double> msol = new HashMap<IloNumVar, Double>();
        // maps master variables to their values in the node solution
      double zMaster = getValue(flowCost);  // get master flow cost estimate
      msol.put(flowCost, zMaster);
      // which warehouses does the proposed master solution use?
      double[] x = getValues(use);
      // set the supply constraint right-hand sides in the subproblem
      for (int i = 0; i < nWarehouses; i++) {
        msol.put(use[i], x[i]);
        cSupply[i].setUB((x[i] >= ROUNDUP) ? capacity[i] : 0);
      }
      // solve the subproblem
      sub.solve();
      IloCplex.Status status = sub.getStatus();
      IloNumExpr expr = master.numExpr();
      if (status == IloCplex.Status.Infeasible) {
        // subproblem is infeasible -- add a feasibility cut
        // first step: get a Farkas certificate, corresponding to a dual ray
        // along which the dual is unbounded
        IloConstraint[] constraints =
          new IloConstraint[nWarehouses + nCustomers];
        double[] coefficients = new double[nWarehouses + nCustomers];
        sub.dualFarkas(constraints, coefficients);
        double temp = 0;  // sum of cut terms not involving primal variables
        // process all elements of the Farkas certificate
        for (int i = 0; i < constraints.length; i++) {
          IloConstraint c = constraints[i];
          expr = master.sum(expr, master.prod(coefficients[i], rhs.get(c)));
        }
        // generate a feasibility cut
        IloRange r = master.le(master.sum(temp, expr), 0);
        //test the cut against the current solution
        IloLinearNumExpr rexpr = (IloLinearNumExpr) r.getExpr();
        IloLinearNumExprIterator it = rexpr.linearIterator();
        double lhs = 0;
        double rhs = r.getUB();
        while (it.hasNext()) {
          IloNumVar v = it.nextNumVar();
          lhs += it.getValue() * msol.get(v);
        }
        // if a violation occurs, add a feasibility cut
        if (lhs > rhs + FUZZ) {
          System.out.println("!!! Adding user feasibility cut: " + r);
          add(r, CutManagement.UseCutPurge);
        }
      } else if (status == IloCplex.Status.Optimal) {
        if (zMaster < sub.getObjValue() - FUZZ) {
          // the master problem surrogate variable underestimates the actual
          // flow cost -- add an optimality cut
          double[] lambda = sub.getDuals(cDemand);
          double[] mu = sub.getDuals(cSupply);
          // compute the scalar product of the RHS of the demand constraints
          // with the duals for those constraints
          for (int j = 0; j < nCustomers; j++) {
            expr = master.sum(expr, master.prod(lambda[j],
                              rhs.get(cDemand[j])));
          }
          // repeat for the supply constraints
          for (int i = 0; i < nWarehouses; i++) {
            expr = master.sum(expr, master.prod(mu[i], rhs.get(cSupply[i])));
          }
          // generate an optimality cut
          IloRange r = master.le(master.diff(expr, flowCost), 0);
          //test the cut against the current solution
          IloLinearNumExpr rexpr = (IloLinearNumExpr) r.getExpr();
          IloLinearNumExprIterator it = rexpr.linearIterator();
          double lhs = 0;
          double rhs = r.getUB();
          while (it.hasNext()) {
            IloNumVar v = it.nextNumVar();
            lhs += it.getValue() * msol.get(v);
          }
          // if a violation occurs, add an optimality cut
          if (lhs - rhs > FUZZ) {
            System.out.println("!!! Adding user optimality cut: " + r);
            add(r, CutManagement.UseCutPurge);
          }
        }
      } else {
        // unexpected status -- report but do nothing
        System.err.println("!!! Unexpected subproblem solution status: "
                           + status);
      }
    }
  }
}
