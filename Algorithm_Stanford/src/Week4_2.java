import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

public class Week4_2 {

    public static void main(String[] args) throws IOException {
        HashSet<Long> set=new HashSet<Long>();
        
        //readData
        Scanner in=new Scanner(Paths.get("./data/2sum.txt"));
        while(in.hasNextLong()){
            set.add(in.nextLong());
        }
        
        int result=0;
        
        for(int t=-10000;t<=10000;t++){
//            System.out.println("t= "+t);
            for(Long ele:set){
                if(set.contains(t-ele)){
                    result++;
                    break;
                }
            }
        }
        
        System.out.println(result);
    }
}
