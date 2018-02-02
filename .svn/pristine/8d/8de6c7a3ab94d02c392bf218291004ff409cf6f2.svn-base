package bendersexample;

import ilog.concert.IloException;
import java.util.Random;

/**
 * BendersExample solves a small fixed-charge transportation problem.
 * The purpose is to demonstrate how to code Benders decomposition using CPLEX.
 *
 * @author Paul A. Rubin (rubin@msu.edu)
 * @version 2.5 (updated to work with CPLEX 12.7 and later)
 */
public final class BendersExample {
  /* Configure model dimensions here */
  private static final int WAREHOUSES = 15;    // number of warehouses
  private static final int CUSTOMERS = 400;    // number of customers
  private static final long SEED = 72612L;     // random number seed
  private static final double CAPMULT = 2.5;   // mean capacity multiplier
  private static final double FIXCOST = 50;    // mean fixed cost

  /**
   * Constructor.
   */
  private BendersExample() { }

  /**
   * @param args the command line arguments
   */
  public static void main(final String[] args) {
    /*
     * seed the random number generator
     */
    Random rng = new Random(SEED);
    /*
     * generate problem parameters randomly
     */
    double[] demand = new double[CUSTOMERS];  // demands
    double totalDemand = 0;
    for (int i = 0; i < CUSTOMERS; i++) {
      demand[i] = rng.nextDouble();
      totalDemand += demand[i];
    }
    double meanCapacity = CAPMULT * totalDemand / WAREHOUSES;
      // mean capacity of any one warehouse
    double[] capacity = new double[WAREHOUSES];  // capacity of each warehouse
    double[] fixedCost = new double[WAREHOUSES]; // fixed cost of each warehouse
    for (int i = 0; i < WAREHOUSES; i++) {
      capacity[i] = meanCapacity * 2 * rng.nextDouble();
      fixedCost[i] = 2 * FIXCOST * rng.nextDouble();
    }
    double[][] flowCost = new double[WAREHOUSES][CUSTOMERS];
      // unit flow cost from any possible warehouse to any customer
    for (int i = 0; i < WAREHOUSES; i++) {
      for (int j = 0; j < CUSTOMERS; j++) {
        flowCost[i][j] = rng.nextDouble();
      }
    }
    /*
     * Build a single MIP model and solve it, as a benchmark.
     */
    long start;
    long end;
    try {
      start = System.currentTimeMillis();
      System.out.println("\n================================="
                         + "\n== Solving the usual MIP model =="
                         + "\n=================================");
      SingleModel model = new SingleModel(WAREHOUSES, CUSTOMERS, capacity,
                                          demand, fixedCost, flowCost);
      Solution s = model.solve();
      end = System.currentTimeMillis();
      System.out.println("\n***\nThe unified model's solution has total cost "
                         + String.format("%10.5f", s.cost)
                         + ".\nWarehouses used: "
                         + s.warehouses.toString());
      System.out.println("Flows:");
      s.printFlows();
      System.out.println("\n*** Elapsed time = "
                         + (end - start) + " ms. ***\n");
    } catch (IloException ex) {
      System.err.println("\n!!!Unable to solve the unified model:\n"
                         + ex.getMessage() + "\n!!!");
      System.exit(1);
    }
    /*
     * Build and solve a model using Benders decomposition.
     */
    try {
      start = System.currentTimeMillis();
      System.out.println("\n======================================="
                         + "\n== Solving via Benders decomposition =="
                         + "\n=======================================");
      Benders model2 = new Benders(WAREHOUSES, CUSTOMERS, capacity,
                                   demand, fixedCost, flowCost);
      Solution s = model2.solve();
      end = System.currentTimeMillis();
      System.out.println("\n***\nThe Benders model's solution has total cost "
                         + String.format("%10.5f", s.cost)
                         + ".\nWarehouses used: "
                         + s.warehouses.toString());
      System.out.println("Flows:");
      s.printFlows();
      System.out.println("\n*** Elapsed time = "
                         + (end - start) + " ms. ***\n");
    } catch (IloException ex) {
      System.err.println("\n!!!Unable to solve the Benders model:\n"
                         + ex.getMessage() + "\n!!!");
      System.exit(2);
    }
    /*
     * Build and solve a model using aggressive Benders decomposition.
     */
    try {
      start = System.currentTimeMillis();
      System.out.println("\n=============================================="
                         + "===="
                         + "\n== Solving via aggressive Benders decomposition"
                         + " =="
                         + "\n=============================================="
                         + "====");
      AggressiveBenders model3 =
        new AggressiveBenders(WAREHOUSES, CUSTOMERS, capacity,
                              demand, fixedCost, flowCost);
      Solution s = model3.solve();
      end = System.currentTimeMillis();
      System.out.println("\n***\nThe aggressive Benders model's solution "
                         + "has total cost "
                         + String.format("%10.5f", s.cost)
                         + ".\nWarehouses used: "
                         + s.warehouses.toString());
      System.out.println("Flows:");
      s.printFlows();
      System.out.println("\n*** Elapsed time = "
                         + (end - start) + " ms. ***");
    } catch (IloException ex) {
      System.err.println("\n!!!Unable to solve the aggressive"
                         + " Benders model:\n"
                         + ex.getMessage() + "\n!!!");
      System.exit(2);
    }
  }
}
