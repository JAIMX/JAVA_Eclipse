package bendersexample;

import static bendersexample.Benders.FUZZ;
import ilog.cplex.IloCplex;
import java.util.HashSet;

/**
 * Solution is a container class to hold solutions to the problem.
 * @author Paul A. Rubin (rubin@msu.edu)
 */
public class Solution {
  public double cost;  // total cost
  public double[][] flows;  // flow volumes
  public HashSet<Integer> warehouses;  // warehouses used
  public IloCplex.CplexStatus status;  // status returned by CPLEX

  /**
   * Print the non-zero flows.
   */
  public final void printFlows() {
    for (int i = 0; i < flows.length; i++) {
      for (int j = 0; j < flows[0].length; j++) {
        if (flows[i][j] > FUZZ) {
          System.out.printf("\t%d -> %d: %f\n", i, j, flows[i][j]);
        }
      }
    }
  }
}
