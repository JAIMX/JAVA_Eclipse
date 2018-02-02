import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

public class MaxIndependentSet {

	public static void main(String[] args) throws IOException {
		Scanner in=new Scanner(Paths.get("./data/mwis.txt"));
		int n=in.nextInt();
		int[] function=new int[n];
		int[] weight=new int[n];
		for(int i=0;i<n;i++ ) {
			weight[i]=in.nextInt();
		}
		
		function[0]=weight[0];
		function[1]=Math.max(weight[0], weight[1]);
		for(int i=2;i<n;i++) {
			if(function[i-1]>=function[i-2]+weight[i]) {
				function[i]=function[i-1];
			}else {
				function[i]=function[i-2]+weight[i];
			}
			
		}
		
		HashSet<Integer> set=new HashSet<Integer>();
		int index=n-1;
		while(index>1) {
			if(function[index-1]>=function[index-2]+weight[index]) {
				index--;
			}else {
				set.add(index);
				index=index-2;
			}
		}
		
		if(index==0) {
			set.add(0);
		}else {
			if(weight[0]>weight[1]) {
				set.add(0);
			}else set.add(1);
		}
		
		int[] test= {0, 1, 2, 3, 16, 116, 516,996};
		for(int i=0;i<test.length;i++) {
			if(set.contains(test[i])) {
				System.out.print(1);
			}else System.out.print(0);
		}
		
		
	}
}
