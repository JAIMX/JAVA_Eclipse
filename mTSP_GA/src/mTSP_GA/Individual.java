package mTSP_GA;

import java.util.Arrays;

public class Individual {

    private int[] chromosome1;
    private int[] chromosome2;
    private double fitness1=-1;
    private double fitness2=-1;
    static double[][] distance;
    private int populationIndex;
    private double crowdDistance;
    
    public Individual(int[] chromosome1, int[] chromosome2, double fitness){
    	this.chromosome1=new int[chromosome1.length];
    	this.chromosome2=new int[chromosome2.length];
        System.arraycopy(chromosome1, 0, this.chromosome1, 0, chromosome1.length);
        System.arraycopy(chromosome2, 0, this.chromosome2, 0, chromosome2.length);
        avoidCrossover();
        if(fitness<0){
            calFitness();
        }
    }
    
    public void avoidCrossover(){
    	int lIndex=0;
    	int rIndex=chromosome2[0]-1;
    	
    	for(int i=0;i<chromosome2.length;i++){
    		// Create the subTour
    		int[] subTour=new int[chromosome2[i]+2];
    		subTour[0]=0;
    		subTour[subTour.length-1]=0;
    		for(int j=1;j<subTour.length-1;j++){
    			subTour[j]=chromosome1[lIndex-1+j];
    		}
    		
//    		System.out.println("Initial subTour "+(i+1)+" is "+Arrays.toString(subTour));
    		
    		//Check the subTour
    		for(int l=1;l<subTour.length-2;l++){
    			for(int r=l+1;r<subTour.length-1;r++){
    				if((distance[subTour[l-1]][subTour[l]]+distance[subTour[r]][subTour[r+1]])>(distance[subTour[l-1]][subTour[r]]+distance[subTour[l]][subTour[r+1]])){
    					int[] temp=new int[r-l+1];
    					for(int index=0;index<temp.length;index++){
    						temp[index]=subTour[index+l];
    					}
    					for(int index=l;index<=r;index++){
    						subTour[index]=temp[r-index];
    					}
    				}
    			}
    		}
    		
//    		System.out.println("Updated subTour "+(i+1)+" is "+Arrays.toString(subTour));
    		
    		//change chromosome1 with subTour
    		for(int index=1;index<=subTour.length-2;index++){
    			chromosome1[lIndex-1+index]=subTour[index];
    		}
    		
//    		System.out.println("The new chromosome1 is "+ Arrays.toString(chromosome1));
    		
    		if(i<chromosome2.length-1){
        		lIndex=rIndex+1;
        		rIndex=rIndex+chromosome2[i+1];
    		}

    	}
    	
    	
    }
    
    public static void main(String[] args) {
		int[] part1={1,2,3,4,5,6};
		int[] part2={3,3};
//		Individual.distance=new Double[][]{{Double.MAX_VALUE,4,7,2,3}{4,Double.MAX_VALUE,9,1,5}{7,9,Double.MAX_VALUE,4,6}
//		{2,1,4,Double.MAX_VALUE,8}{3,5,6,8,Double.MAX_VALUE}};
		
		Individual.distance=new double[7][7];
		for(int i=0;i<7;i++){
			for(int j=i;j<7;j++){
				if(i!=j){
					Individual.distance[i][j]=Math.random();
					Individual.distance[j][i]=Individual.distance[i][j];
				}else Individual.distance[i][j]=Double.MAX_VALUE;

			}
		}
		
		Individual test=new Individual(part1,part2,1);
		
		
	}
    
    public void calFitness(){
        fitness1=0;
        double[] subDistance=new double[chromosome2.length];
        
        int index=0;
        double currentDistance=0;
        for(int i=0;i<chromosome2.length;i++){
            
            fitness1+=distance[0][chromosome1[index]];
            for(int j=0;j<chromosome2[i]-1;j++){
                fitness1+=distance[chromosome1[index]][chromosome1[index+1]];
                index++;
            }
            fitness1+=distance[chromosome1[index]][0];
            subDistance[i]=fitness1-currentDistance;
            currentDistance=fitness1;
            
            index++;
        }
        
        //calculate fitness2
        double average=fitness1/chromosome2.length;
        fitness2=0;
        for(int i=0;i<chromosome2.length;i++){
            fitness2+=Math.pow(subDistance[i]-average, 2);
        }
        fitness2=fitness2/chromosome2.length;
        //fitness2=1/fitness2;
        fitness2=10000000/fitness2;
        

        //calculate fitness1
        fitness1=1000000/fitness1;
        
        //System.out.println("fitness1="+fitness1);
        
        return ;
    }
    
    public double getFitness1(){
        //System.out.println("fitness1="+fitness1);
    	return fitness1;
    }
    
    
    public double getFitness2(){
    	return fitness2;
    }
    
    public void setPopulationIndex(int index){
    	populationIndex=index;
    }
    
    public int getPopulationIndex(){
    	return populationIndex;
    }
    
    public double getcrowdDistance(){
    	return crowdDistance;
    }
    
    public void setcrowdDistance(double distance){
    	crowdDistance=distance;
    }
    
    public String toString(){
    	String string="";
    	for(int i=0;i<chromosome1.length;i++){
    		string+=" "+chromosome1[i];
    	}
    	string+="|";
    	for(int i=0;i<chromosome2.length;i++){
    		string+=" "+chromosome2[i];
    	}
    	return string;
    	
    }
    
    public int[] getchromosome1(){
    	return chromosome1;
    }
    
    public int[] getchromosome2(){
    	return chromosome2;
    }
    
    
}
