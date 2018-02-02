import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Week1_3 {
	
	private int n;
	private PriorityQueue<Job> queue1,queue2;
	private long completionTime1,completionTime2;

	private class Job{
		int weight;
		int length;
	}
	
 

	
	public void readData(String filename) throws IOException {
		Scanner in=new Scanner(Paths.get(filename));
		n=in.nextInt();
		
		Comparator<Job> Sort1;  
		Sort1 = new Comparator<Job>() {  
		  
		    @Override  
		    public int compare(Job o1, Job o2) {  
		        // TODO Auto-generated method stub  
		        int x1=o1.length-o1.weight;
		        int x2=o2.length-o2.weight;
		        
		        if(x1!=x2) {
		        	return x1-x2;
		        }else {
		        	return o2.weight-o1.weight;
		        }
		    }  
		};
		
		Comparator<Job> Sort2;  
		Sort2 = new Comparator<Job>() {  
		  
		    @Override  
		    public int compare(Job o1, Job o2) {  
		        // TODO Auto-generated method stub  
		    	double x1=(double)o1.weight/(double)o1.length;
		    	double x2=(double)o2.weight/(double)o2.length;
		    	double difference=x2-x1;
		    	if(difference<0)return -1;
		    	if(difference>0) return 1;
		    	return 0;
		    }  
		};
		
		queue1=new PriorityQueue<Job>(1,Sort1);
		queue2=new PriorityQueue<Job>(1,Sort2);
		
		Job job=new Job();
		int weight=0;
		int length=0;
		
		for(int i=0;i<n;i++) {
			weight=in.nextInt();
			length=in.nextInt();
			job=new Job();
			job.weight=weight;
			job.length=length;
			queue1.add(job);
			queue2.add(job);
		}
		
		
	}
	
	
	public void greedy() {
		completionTime1=0;
		completionTime2=0;
		long sumTime1=0;
		long sumTime2=0;
		
		for(int i=0;i<n;i++) {
			Job job1=queue1.poll();
			Job job2=queue2.poll();
			System.out.println(job2.weight+" "+job2.length);
			
			sumTime1+=job1.length;
			sumTime2+=job2.length;
			
			completionTime1+=sumTime1*job1.weight;
			completionTime2+=sumTime2*job2.weight;
			
		}
		
		System.out.println("1st compleation time = "+completionTime1);
		System.out.println("2nd compleation time = "+completionTime2);
	}
	
	public static void main(String[] args) throws IOException {
		Week1_3 test=new Week1_3();
//		test.readData("./data/test.txt");
		test.readData("./data/jobs.txt");
		test.greedy();
	}
}
