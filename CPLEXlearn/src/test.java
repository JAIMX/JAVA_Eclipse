import ilog.concert.IloColumn;
import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.Param.Preprocessing;

public class test {

	public static void main(String[] args) throws IloException {

		IloCplex master=new IloCplex();
		IloObjective obj = master.addMinimize();
		IloRange[] optConstraint = new IloRange[2];
		optConstraint[0] = master.addRange(0, Double.MAX_VALUE);
		optConstraint[1] = master.addRange(0, Double.MAX_VALUE);
		
		IloColumn tempColumn = master.column(obj, 1);
		for (int i = 0; i < 2; i++) {
			tempColumn = tempColumn.and(master.column(optConstraint[i], 1));
		}
		
		IloNumVar[] x=new IloNumVar[2];
		x[0] = master.numVar(tempColumn, 0, Double.MAX_VALUE);
		
		tempColumn = master.column(obj, 33);
		for (int i = 0; i < 2; i++) {
			tempColumn = tempColumn.and(master.column(optConstraint[i], 2));
		}
		
		x[1] = master.numVar(tempColumn, 0, Double.MAX_VALUE);
//		master.remove(x[1]);
		master.delete(x[1]);
		
		master.exportModel("temp.lp");
		
		
		
		
	}
}
