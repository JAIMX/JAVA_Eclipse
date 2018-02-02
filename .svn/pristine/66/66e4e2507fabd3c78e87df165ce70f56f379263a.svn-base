import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Week3_1 {

	private int[] array;
	private int n;
	private long numComparation;
	
	public Week3_1(int[] array){
		n=array.length;
		this.array=new int[n];
		System.arraycopy(array, 0, this.array, 0, n);
		numComparation=0;
	}
	
	public void QuickSort1(int lIndex,int rIndex){
		numComparation+=rIndex-lIndex;
		
//		System.out.println("lIndex="+lIndex);
//		System.out.println("rIndex="+rIndex);
//		System.out.println(array[lIndex]+" "+array[rIndex]);
		
		if(lIndex==rIndex) return;
		
		int pivote=array[lIndex];
		int i=lIndex+1;
		int temp=0;
		for(int j=lIndex+1;j<=rIndex;j++){
			if(array[j]<pivote){
				temp=array[i];
				array[i]=array[j];
				array[j]=temp;
				
//				System.out.println("swap "+array[i]+" && "+array[j]);
//				System.out.println(Arrays.toString(array));
				
				i++;
			}
		}
		
		temp=array[i-1];
		array[i-1]=array[lIndex];
		array[lIndex]=temp;
//		System.out.println("swap "+array[i-1]+" && "+array[lIndex]);
//		System.out.println(Arrays.toString(array));
		
		if(lIndex<=i-2) QuickSort1(lIndex, i-2);
		if(rIndex>=i) QuickSort1(i, rIndex);
		
	}
	
	public void QuickSort2(int lIndex,int rIndex){
		numComparation+=rIndex-lIndex;
		
//		System.out.println("lIndex="+lIndex);
//		System.out.println("rIndex="+rIndex);
//		System.out.println(array[lIndex]+" "+array[rIndex]);
		
		if(lIndex==rIndex) return;
		
		int temp=array[lIndex];
		array[lIndex]=array[rIndex];
		array[rIndex]=temp;
		
//		System.out.println("swap "+array[lIndex]+" && "+array[rIndex]);
//		System.out.println(Arrays.toString(array));
		
		int pivote=array[lIndex];
		int i=lIndex+1;
		for(int j=lIndex+1;j<=rIndex;j++){
			if(array[j]<pivote){
				temp=array[i];
				array[i]=array[j];
				array[j]=temp;
				
//				System.out.println("swap "+array[i]+" && "+array[j]);
//				System.out.println(Arrays.toString(array));
				
				i++;
			}
		}
		
		temp=array[i-1];
		array[i-1]=array[lIndex];
		array[lIndex]=temp;
		
//		System.out.println("swap "+array[i-1]+" && "+array[lIndex]);
//		System.out.println(Arrays.toString(array));
		
		if(lIndex<=i-2) QuickSort2(lIndex, i-2);
		if(rIndex>=i) QuickSort2(i, rIndex);
		
	}
	
	public int findMid(int l,int m,int r){
		int a=array[l];
		int b=array[m];
		int c=array[r];
		int mid=0;
		
		int max=Math.max(a, b);
		int min=Math.min(a, b);
		
		if(c>=max){
			mid=max;
		}else{
			if(c>=min){
				mid=c;
			}else mid=min;
		}
		
		if(mid==a)return l;
		if(mid==b)return m;
		if(mid==c)return r;
		return 0;
		
		
	}
	
	public void QuickSort3(int lIndex,int rIndex){
		numComparation+=rIndex-lIndex;
		
//		System.out.println("lIndex="+lIndex);
//		System.out.println("rIndex="+rIndex);
//		System.out.println(array[lIndex]+" "+array[rIndex]);
		
		if(lIndex==rIndex) return;
		int temp=0;
		int pivoteIndex=0;
		
		if(rIndex-lIndex>1){
			int mid=(rIndex+lIndex)/2;
			pivoteIndex=findMid(lIndex,mid,rIndex);
			
			temp=array[lIndex];
			array[lIndex]=array[pivoteIndex];
			array[pivoteIndex]=temp;
		}
		

		
//		System.out.println("swap "+array[lIndex]+" && "+array[pivoteIndex]);
//		System.out.println(Arrays.toString(array));

		int pivote=array[lIndex];
		int i=lIndex+1;
		for(int j=lIndex+1;j<=rIndex;j++){
			if(array[j]<pivote){
				temp=array[i];
				array[i]=array[j];
				array[j]=temp;
				
//				System.out.println("swap "+array[i]+" && "+array[j]);
//				System.out.println(Arrays.toString(array));
				
				i++;
			}
		}
		
		temp=array[i-1];
		array[i-1]=array[lIndex];
		array[lIndex]=temp;
		
//		System.out.println("swap "+array[i-1]+" && "+array[lIndex]);
//		System.out.println(Arrays.toString(array));
		
		if(lIndex<=i-2) QuickSort3(lIndex, i-2);
		if(rIndex>=i) QuickSort3(i, rIndex);
		
	}
	
	
	
	public void Output(){
		System.out.println(Arrays.toString(array));
		System.out.println(numComparation);
	}
	
	public static void main(String[] args) throws IOException {
//		int[] array={5,4,3,2,1};
//		int[] array={2,3,4,1,5};
		
		Scanner in=new Scanner(Paths.get("./data/QuickSort.txt"));
		int[] array=new int[10000];
		
		for(int i=0;i<10000;i++){
			array[i]=in.nextInt();
		}
		
		Week3_1 test=new Week3_1(array);
		test.QuickSort2(0, array.length-1);
		test.Output();
		
	}
}
