import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Pivot {
    private int m; // amounts of constraints, basic variables
    private int n; // amounts of nonbasic variables
    private int[] N;
    private int[] B;
    private double[] b;
    private double[][] a;
    private double[] c;

    public Pivot(String input) throws IOException {
        ReadData(input);
    }
    
    public double getcurrentopt(){
        return c[0];
    }

    public void ReadData(String input) throws IOException {
        Scanner in = new Scanner(Paths.get(input));
        m = in.nextInt();
        n = in.nextInt();

        B = new int[m + 1];
        N = new int[n + 1];
        for (int i = 1; i <= m; i++) {
            B[i] = in.nextInt();
        }
        for (int i = 1; i <= n; i++) {
            N[i] = in.nextInt();
        }

        b = new double[m + 1];
        for (int i = 1; i <= m; i++)
            b[i] = in.nextDouble();

        a = new double[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] = in.nextDouble();
            }
        }

        c = new double[n + 1];
        for (int i = 0; i <= n; i++)
            c[i] = in.nextDouble();

    }

    // find the enterint variable, if no one is found(optimal), return -1
    public int Find_Entervar() {
        int index_record = Integer.MAX_VALUE;
        int record = 0;
        for (int i = 1; i <= n; i++) {
            if (c[i] > 0 && N[i] < index_record) {
                index_record = N[i];
                record = i;
            }
        }
        if (index_record == Integer.MAX_VALUE)
            return -1;
        else
            return record;
    }

    // find the enterint variable, if no one is found, return -1(unbounded)
    public int Find_Leavevar(int enter_colume) {
        assert (enter_colume > 0) : "Current solution is optimal!";
        int record = -1;
        double maxincre = Double.MAX_VALUE;

        for (int i = 1; i <= m; i++) {

            if (a[i][enter_colume] < 0) {

                double temp = b[i] / (-a[i][enter_colume]);
                if (temp < maxincre) {
                    record = i;
                    maxincre = temp;
                } else {
                    if (temp == maxincre && B[i] < B[record]) {
                        record = i;
                    }
                }
            }
        } // end for

        return record;
    }

    public void Exchange(int entervar, int leavevar) {

        // adjust the coefficient of a[leavevar][] and b[leavevar]
        double pivot = a[leavevar][entervar];
        for (int i = 1; i <= n; i++) {
            a[leavevar][i] = a[leavevar][i] * (-1 / pivot);
        }
        b[leavevar] = b[leavevar] * (-1 / pivot);
        a[leavevar][entervar] = 1 / pivot;

        // adjust the coefficient of a and b except for the a[leavevar][] and b[leavevar]
        for (int i = 1; i <= m; i++) {
            if(i != leavevar){
                double multiple=a[i][entervar];
                b[i]=b[i]+b[leavevar]*multiple;
                for(int j=1;j<=n;j++){
                    if(j!=entervar) a[i][j]=a[i][j]+a[leavevar][j]*multiple;
                }
                a[i][entervar]=a[leavevar][entervar]*multiple;
            }
           
            
        }
        // adjust the cefficient of c
        double multiple=c[entervar];
        c[0]=c[0]+b[leavevar]*multiple;
        
        for(int i=1;i<=n;i++){
            if(i!=entervar) c[i]=c[i]+a[leavevar][i]*multiple;
        }
        
        c[entervar]=a[leavevar][entervar]*multiple;
        
        
        //change the record of B and N 
        int enter_index=N[entervar];
        N[entervar]=B[leavevar];
        B[leavevar]=enter_index;
        

    }

    public void Output(int i) throws IOException {
        PrintWriter writer = null;
        File outputFile = new File("./output/Solution_" + i + ".txt");
        outputFile.getParentFile().mkdirs();
        writer = new PrintWriter(new FileWriter(outputFile));

        int entervar = Find_Entervar();

        if (entervar < 0) {
            writer.print("Current solution is optimal!");
            writer.close();
        } else { // entervar>=0
            int leavevar = Find_Leavevar(entervar);
            if (leavevar == -1) {
                writer.write("UNBOUNDED");
                writer.close();
            } else { // leavevar>0
                writer.println(N[entervar]);
                writer.println(B[leavevar]);

                double z2 = c[0] + b[leavevar] / (-a[leavevar][entervar]) * c[entervar];
                writer.println(z2);
                writer.close();
            }
        }

    }

    public static void main(String[] args) throws IOException {
//        for (int i = 1; i <= 5; i++) {
//            Pivot test = new Pivot("./testdata/assignmentParts1/part" + i + ".dict");
//            test.Output(i);
//        }
        Pivot test = new Pivot("./testdata/unitTests2/dict8");
        int enter=test.Find_Entervar();
        int leave=test.Find_Leavevar(enter);
        test.Exchange(enter, leave);
        System.out.println(Arrays.toString(test.a[3]));
        System.out.println(Arrays.toString(test.b));
        System.out.println(Arrays.toString(test.c));
        System.out.println(Arrays.toString(test.N));
        System.out.println(Arrays.toString(test.B));
        
    }
}
