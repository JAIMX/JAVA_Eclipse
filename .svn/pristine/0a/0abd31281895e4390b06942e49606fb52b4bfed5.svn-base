import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Pivot_all {

    double optvalue;
    int step;
    Pivot model;
    
    public Pivot_all(String input) throws IOException{
        model=new Pivot(input);
        optvalue=Double.MIN_VALUE;
        step=0;
    }
    
    // get the optimal objvalue(maximal), if unbounded return -1 else return 1;
    public int getopt(){
        
        optvalue=model.getcurrentopt();
        step=0;
        
        int entervar=model.Find_Entervar();
        // start the pivot process
        while(entervar>0){
            int leavevar=model.Find_Leavevar(entervar);
            if(leavevar<0) return -1;    //unbounded
            
            model.Exchange(entervar, leavevar);
            step++;
            optvalue=model.getcurrentopt();
            
            entervar=model.Find_Entervar();
        }
        
        return 1;
    }
    
    public void Output(int i,int ifbounded) throws IOException {
        PrintWriter writer = null;
        File outputFile = new File("./output/Solution_" + i + ".txt");
        outputFile.getParentFile().mkdirs();
        writer = new PrintWriter(new FileWriter(outputFile));
        
        if(ifbounded<0){
            writer.print("UNBOUNDED");
            writer.close();
        } else {
            writer.println(optvalue);
            writer.println(step);
            writer.close();
        }
        

    }
    
    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 10; i++) {
          Pivot_all test = new Pivot_all("./testdata/unitTests2/dict" + i );
          test.Output(i,  test.getopt());
      }
    }
    
        
}
