import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class Week2_1 {
	
	private long numInversion;
	private ArrayList<Integer> array;
	private ArrayList<Integer> mergeArray;
	
	
	public Week2_1(ArrayList<Integer> array){
		numInversion=0;
		this.array=new ArrayList<Integer>();
		for(int i=0;i<array.size();i++){
			this.array.add(array.get(i));
		}
	}
	
	public void mergeSort(int leftIndex,int rightIndex){
		if(leftIndex==rightIndex){
			return;
		}else{
			

			
			int cut=(rightIndex-leftIndex+1)/2;
			
//			System.out.println("leftIndex="+leftIndex);
//			System.out.println("rightIndex="+rightIndex);
//			System.out.println("cut="+cut);
//			System.out.println();
			
			mergeSort(leftIndex, cut+leftIndex-1);
			mergeSort(leftIndex+cut, rightIndex);
			mergeArray=new ArrayList<Integer>();
			int i=leftIndex;
			int j=cut+leftIndex;
			int leftIndex2=cut+leftIndex-1;
			

			
//			System.out.println("i="+i);
//			System.out.println("j="+j);
//			System.out.println("the size of array="+array.size());
			
			while(i<=leftIndex2&&j<=rightIndex){
				if(array.get(i)<=array.get(j)){
					mergeArray.add(array.get(i));
					i++;
				}else{
					mergeArray.add(array.get(j));
					
					System.out.println("leftIndex2="+leftIndex2);
					System.out.println("leftIndex="+leftIndex);
					System.out.println("rightIndex="+rightIndex);
					System.out.println("i="+i);
					System.out.println("j="+j);
					System.out.println("sum+= "+(leftIndex2-i+1));
					System.out.println();
					
					numInversion+=leftIndex2-i+1;
					j++;
				}
			}
			
			if(i<=leftIndex2){
				for(int temp=i;temp<=leftIndex2;temp++){
					mergeArray.add(array.get(temp));
				}
			}
			
			if(j<=rightIndex){
				for(int temp=j;temp<=rightIndex;temp++){
					mergeArray.add(array.get(temp));
				}
			}
			
			for(int temp=leftIndex;temp<=rightIndex;temp++){
				array.set(temp, mergeArray.get(0));
				mergeArray.remove(0);
			}
			
			
			
			
		}
	}
	
	public long getInversion(){
		return numInversion;
	}

	public static void main(String[] args) throws IOException {
//		int[] temp={1,2,5,7,3,4,2,4};
		ArrayList<Integer> array=new ArrayList<Integer>();
//		for(int i=0;i<temp.length;i++){
//			array.add(temp[i]);
//		}
		
		Scanner in=new Scanner(Paths.get("./data/IntegerArray.txt"));
		int temp;
		
		while(in.hasNextInt()){
			temp=in.nextInt();
			array.add(temp);
		}
		Week2_1 test=new Week2_1(array);
		test.mergeSort(0, array.size()-1);
		System.out.println(test.getInversion());
	}
}
