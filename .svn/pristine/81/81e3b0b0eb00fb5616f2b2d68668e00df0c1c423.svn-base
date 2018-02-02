package distanceUpdate;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearIntExpr;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class exact_MTZ_double_final_by_MA {
	static int nbCities;

	static double minLength;
	static double[][] fx;
	static long time;
	
	static double optLength = 0;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//---read data---//
		String filename = null;
		String dir = "data/";
		String fileSize = "53";
		
		// tsp-lib-pr-test-data
//		int fileSize = 127; // 76, 107, 124, 136, 144, 152, 226
		
		filename = dir + "tsp" + fileSize + ".xls";
		
		double[][] distance = null;

		distance = readData(filename);
		System.out.println(Arrays.toString(distance[0]));
		
		MTZsolve(distance);
		
		//---results---//
		printResults(filename);
		
	}
	
	private static double[][] readData(String filename) {
		System.out.println("reading " + filename);
		double[][] distance = null;
		try {
			FileInputStream file = new FileInputStream(new File(filename));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet worksheet = workbook.getSheetAt(0);
			
			int nbLine = 0;
			//---read cities numbers---//
			HSSFRow row0 = worksheet.getRow(nbLine);
			HSSFCell cell0 = row0.getCell(0);
			nbCities = (int) cell0.getNumericCellValue();
			//---read distance matrix---//
			nbLine++;
			distance = new double[nbCities][nbCities];
			double tempvalue = 0;
			for (int i = 0; i < nbCities; i++) {
				HSSFRow row = worksheet.getRow(nbLine + i);
				for (int j = i; j < nbCities; j++) {
					HSSFCell cell = row.getCell(j);
					tempvalue = cell.getNumericCellValue();
					distance[i][j] = tempvalue;
					distance[j][i] = tempvalue;
				}
			}
			file.close();		
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return distance;	
	}	
	
	private static void checkOpt(int[] index,  double[][] distance) {
		int indA, indB = 0;
		for (int i = 0; i < nbCities - 1; i++) {
			indA = index[i];
			indB = index[i + 1];
			System.out.println(indA +" - "+indB + " : "+distance[indA][indB]);
			optLength += distance[indA][indB];
		}
		optLength += distance[indB][index[0]];
		System.out.println("optLength= " + optLength);
	}


	private static int[] readOpt(String optFile) throws NumberFormatException, IOException {
		int[] index = new int[nbCities];
		try {
			BufferedReader br = new BufferedReader(new FileReader(optFile));
			
			for (int i = 0; i < nbCities; i++) {
				index[i] = Integer.parseInt(br.readLine())-1;
//				System.out.println(index[i]);
			}
//			System.exit(0);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return index;
	}


	private static void printResults(String filename) {
		System.out.print(filename+"\n------------------------------\n");
		System.out.printf("rum time(sec)= %-10.2f\n",time / 1000.0);
		System.out.printf("min= %-10.2f\n", minLength);
		for (int i = 0; i < nbCities; i++) {
			System.out.printf("%2d : ", i+1);
			// output solution matrix
			boolean found = false;
			for (int j = 0; j < nbCities; j++) {
				System.out.print(((int)(fx[i][j]+0.00001)));
				if ((int)(fx[i][j]+0.00001) == 1) {
					assert !found: "found two variables = 1 for i =" + i;
					found = true;
				}
			}
			System.out.println();
		}
		System.out.printf("%d ", 1);
		int cityIdx = 0;
		for (int i = 0; i < nbCities; i++) {
			boolean found = false;
			for (int j = 0; j < nbCities && !found; j++) {
				if ((int)(fx[cityIdx][j]+0.00001) == 1) {
					System.out.printf(", %d", (j+1));
					cityIdx = j;
					found = true;
					break;
				}
			}
		}
		System.out.println();
	}


	static void MTZsolve(double[][] distance) throws NumberFormatException, IOException {
		fx = new double[nbCities][nbCities];
		try {
			IloCplex cplex = new IloCplex();
			IloIntVar[][] x = new IloIntVar[nbCities][nbCities];
			IloNumVar[] u = new IloNumVar[nbCities];
			
			//---build model---//
			IloLinearNumExpr exprObj = cplex.linearNumExpr();
			for (int i = 0; i < nbCities; i++) {
				for (int j = 0; j < nbCities; j++) {
					if (i != j)
						x[i][j] = cplex.intVar(0, 1, "x" + i + "," +j);
					else
						x[i][j] = cplex.intVar(0, 0, "x" + i + "," +j);
				}
				exprObj.add(cplex.scalProd(x[i], distance[i]));
			}
			cplex.addMinimize(exprObj);
			
			//---constraint 1 & 2---//
			for (int i = 0; i < nbCities; i++) {
				IloLinearIntExpr constraint2 = cplex.linearIntExpr();
				for (int j = 0; j < nbCities; j++)
					constraint2.addTerm(1, x[j][i]);
				cplex.addEq(cplex.sum(x[i]), 1); //c1
				cplex.addEq(constraint2, 1); //c2
			}
			
			
			//---constraint 3---//
			for (int i = 1; i < nbCities; i++)
//				u[i] = cplex.numVar(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "u" + i);
				u[i] = cplex.numVar(1, nbCities - 1, "u" + i);
			
			for (int i = 1; i < nbCities; i++) {
				for (int j = i + 1; j < nbCities; j++) {
					IloLinearNumExpr expr1 = cplex.linearNumExpr();
					IloLinearNumExpr expr2 = cplex.linearNumExpr();
					
					expr1.addTerm(1, u[i]);
					expr1.addTerm(-1, u[j]);
					expr1.addTerm(nbCities-1, x[i][j]);
					
					expr2.addTerm(1, u[j]);
					expr2.addTerm(-1, u[i]);
					expr2.addTerm(nbCities-1, x[j][i]);
					
					cplex.addLe(expr1, nbCities-2);
					cplex.addLe(expr2, nbCities-2);
				}
			}
					//---lp file out---//
			cplex.exportModel("MTZ.lp");
//			cplex.setOut(null);
			cplex.setParam(IloCplex.Param.WorkMem, 512);
			cplex.setParam(IloCplex.Param.Emphasis.Memory, true);
			//---solve---//
			long start = System.currentTimeMillis();
			boolean success = cplex.solve();
			long end = System.currentTimeMillis();
			time = end - start;
			
			if (success) {
				minLength = cplex.getObjValue();	
				for (int i = 0; i < nbCities; i++)
					fx[i] = cplex.getValues(x[i]);
			} else 
				System.out.println("cplex.solve() failed.");
			
		} catch (IloException ex) {
			System.out.println(ex);
		}
	}

}
