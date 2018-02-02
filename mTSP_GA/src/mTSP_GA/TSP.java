package mTSP_GA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.Scanner;

public class TSP {

    public static int maxGenerations=100;
    public static int numCity;
    public static int numSalesmen;
    public static int populationSize;
    public static double mutationRate;
    public static double crossoverRate;
    public static int elitismCount;
    public static int tournametSize;
    
    
    

    public static void main(String[] args) throws IOException {

        // get the fixed parameters
        Scanner in = new Scanner(System.in);
        System.out.println("Please input the population size.");

        populationSize = in.nextInt();
//        in.nextLine();
//        System.out.println("Please input the filename.");
//        String filename;
//        filename = in.nextLine();
        System.out.println("Please input the number of cities and number of salesmen.");
        numCity = in.nextInt();
        numSalesmen = in.nextInt();

        System.out.println("// ---Parameters---//");
        System.out.println("maxGenerations: " + maxGenerations);
        //System.out.println("filename: " + filename);
        System.out.println("number of cities=" + numCity);
        System.out.println("number of salesmen=" + numSalesmen);

        // read data
        ReadData data = new ReadData("./data/att48.tsp", 48, "ATT");
        //ReadData data = new ReadData("./data/eil76.tsp", 76, "EUC");
        //ReadData data = new ReadData("./data/bier127.tsp",127, "EUC");
        // ReadData test = new ReadData("./data/att48.tsp", 48, "ATT");
        // ReadData test = new ReadData("./data/tsp29.txt", 29, "NORMAL");
        double[][] distance = data.read();
        Individual.distance=distance;
        
     
        // Initial GA
        GeneticAlgorithm ga = new GeneticAlgorithm(populationSize, 0.1, 0.6);

        // Initialize population
        Population population = ga.initPopulation(numCity-1,numSalesmen); //city0 is the depot, so the first part of individual is from 1 to numCity-1
        

//        // Initial GA
//        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.9, 2, 5);
//
//        // Initialize population
//        Population population = ga.initPopulation(cities.length);

//        //select parents test
//        ArrayList<Individual>test=new ArrayList<Individual>(); 
//        test=ga.selectParent(population);
//        
//        for(Individual ele:test){
//        	System.out.println(ele.toString());
//        }
//        
//        System.out.println("the size of population before is "+population.getPopulation().size());
//        ga.crossoverPopulation(test, population);
//        ga.mutatePopulation(population);
//        System.out.println("the size of population after is "+population.getPopulation().size());
        

		// Keep track of current generation
        
//        File file=new File("Fitness.txt");
//        BufferedWriter out=new BufferedWriter(new FileWriter(file));
        
  
		int generation = 1;
		ArrayList<Individual> firstFront = new ArrayList<Individual>(); ;
		// Start evolution loop
		while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
			
            //select parent
	        ArrayList<Individual> parent=new ArrayList<Individual>(); 
	        parent=ga.selectParent(population);
	        
//	        System.out.println("selectParent Done!!!");
	        
	        //add Patterns
	        ga.addPattern(population, 1);
	        
//	        System.out.println("addPattern Done!!!");
	        
			// Apply crossover
			ga.crossoverPopulation(parent, population);
			
//	        System.out.println("crossoverPopulation Done!!!");


			// Apply mutation
			ga.mutatePopulation(population);
			
//			System.out.println("mutatePopulation Done!!!");

			// Evaluate population
			ga.selectPopulation(population);
			
//			System.out.println("selectPopulation Done!!!");

			// Increment the current generation
			System.out.println("generation "+generation+" have done!");
//			System.out.println("The first front index is "+ population.getFirstFrontIndex());
//			System.out.println("Now the amount of pattern is "+population.getPatternSet().size());
			generation++;
			
			firstFront=new ArrayList<Individual>();
			for(int i=0;i<population.getFirstFrontIndex();i++){
				firstFront.add(population.getPopulation().get(i));
//				System.out.println(firstFront.get(firstFront.size()-1).toString());
//				System.out.println(firstFront.get(firstFront.size()-1).getFitness1()+" "+firstFront.get(firstFront.size()-1).getFitness2());
			}


			
//			if(generation%1==0){
//				System.out.println("Gneration "+generation+"'s first front is");	
//				for(int i=0;i<population.getFirstFrontIndex();i++){
//					firstFront.add(population.getPopulation().get(i));
//					System.out.println(firstFront.get(firstFront.size()-1).getFitness1()+" "+firstFront.get(firstFront.size()-1).getFitness2());
//				}
//				System.out.println("Fitness1:");
//				for(int i=0;i<firstFront.size();i++){
//				    System.out.println(firstFront.get(i).getFitness1());
//				}
//				System.out.println();
//				System.out.println("Fitness2:");
//                for(int i=0;i<firstFront.size();i++){
//                    System.out.println(firstFront.get(i).getFitness2());
//                }
//			}
			
//		    if(generation==maxGenerations){
//		          Graph graph=new Graph("mTSP",500,500,firstFront);
//		          graph.addWindowListener(graph);
//		    }

			
//			System.out.println(firstFront.size());

			
		}
		
        Graph graph=new Graph(firstFront);
        
        
    }
}
