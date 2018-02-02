package distanceUpdate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadFile {
    final static double MAXDISTANCE = 10000;
    static int nbCities = SubtourReversal.nbCities;
    static int nbVehicles = SubtourReversal.nbVehicles;
    final static int nbNodes = nbCities + nbVehicles - 1;
    final static double phi = (Math.sqrt(5) - 1) / 2;
    final static int width = 1000;
    final static int height = (int) Math.round(width * phi);
    static double[][] distance = new double[nbNodes][nbNodes];
    public static double[] lat = new double[nbCities];
    public static double[] lon = new double[nbCities];
    public static int[] x = new int[nbCities];
    public static int[] y = new int[nbCities];
    static double company[] = { 116.289518, 28.036692 };
    static double landfill[] = { 116.261663, 28.02839 };
    static int screenMinLon = 7;
    static int screenMaxLon = width - 10;
    static int ScreenIntervalLon = screenMaxLon - screenMinLon;
    static int screenMinLat = 30;
    static int screenMaxLat = height - 10;
    static int ScreenIntervalLat = screenMaxLat - screenMinLat;

    public static void main(String args[]) {
	String file = "./data/pr76-test.xls";
	readFile(file);
    }

    public static void readFile(String filename) {
	try {
	    FileInputStream file = new FileInputStream(new File(filename));
	    HSSFWorkbook workbook = new HSSFWorkbook(file);
	    HSSFSheet worksheet = workbook.getSheet("longitude&latitude");
	    int nbLine = 0;
	    // ---read cities numbers---//
	    HSSFRow row0 = worksheet.getRow(nbLine);
	    HSSFCell cell0 = row0.getCell(0);
	    // nbCities = (int) cell0.getNumericCellValue();
	    System.out.printf("reading EXCEL. nbCities = %d\n", nbCities);
	    // ---read distance matrix---//
	    nbLine++;
	    double latitudevalue = 0;
	    double longitudevalue = 0;
	    int i;
	    nbLine++;
	    double maxLat = 0;
	    double minLat = 90;
	    double maxLon = 0;
	    double minLon = 180;
	    for (i = 0; i < nbCities; i++) {
		HSSFRow row = worksheet.getRow(nbLine + i);
		HSSFCell latitude = row.getCell(1);
		latitudevalue = latitude.getNumericCellValue();
		HSSFCell longitude = row.getCell(2);
		longitudevalue = longitude.getNumericCellValue();
		lat[i] = latitudevalue;
		if (lat[i] > maxLat) maxLat = lat[i];
		if (lat[i] < minLat) minLat = lat[i];
		lon[i] = longitudevalue;
		if (lon[i] > maxLon) maxLon = lon[i];
		if (lon[i] < minLon) minLon = lon[i];

	    }
	    double intervalLat = maxLat - minLat;
	    double intervalLon = maxLon - minLon;
	    // System.out.print(minLat + " " + maxLat + " " + minLon + " " +
	    // maxLon + "\n");
	    int m = 0;
	    for (; m < nbCities; m++) {
		int n = m;
		for (; n < nbCities; n++) {
		    distance[m][n] = Math.sqrt(Math.pow(lat[m] - lat[n], 2) + Math.pow(lon[m] - lon[n], 2));
		    distance[n][m] = distance[m][n];
		    // System.out.print(distance[m][n] + "\t");
		}
		for (n = nbCities; n < nbNodes; n++) {
		    distance[m][n] = distance[m][0];
		    distance[n][m] = distance[m][0];
		}
	    }
	    for (; m < nbNodes; m++) {
		distance[0][m] = MAXDISTANCE;
		distance[m][0] = MAXDISTANCE;
		// distance[i][i] = 0;
		for (int n = m + 1; n < nbNodes; n++) {
		    distance[m][n] = MAXDISTANCE;
		    distance[n][m] = MAXDISTANCE;
		}
	    }
	    for (int k = 0; k < nbNodes; k++) {
		for (int l = 0; l < nbNodes; l++) {
		    // System.out.print(distance[k][l] + " ");
		}
		// System.out.println();
	    }
	    // g.drawOval(7, 30, 1, 1);
	    // g.drawOval(width - 10, height - 10, 1, 1);

	    company[0] = screenMinLon + (company[0] - minLon) * ScreenIntervalLon / intervalLon;
	    company[1] = screenMinLat + (company[1] - minLat) * ScreenIntervalLat / intervalLat;
	    landfill[0] = screenMinLon + (landfill[0] - minLon) * ScreenIntervalLon / intervalLon;
	    landfill[1] = screenMinLat + (landfill[1] - minLat) * ScreenIntervalLat / intervalLat;
	    // System.out.println(Arrays.toString(company));
	    for (int m1 = 0; m1 < nbCities; m1++) {
		// System.out.print(m1 + "\t");
		// System.out.print(lon[m1] + "\t");
		lon[m1] = screenMinLon + (lon[m1] - minLon) * ScreenIntervalLon / intervalLon;
		x[m1] = (int) Math.round(lon[m1]);
		// System.out.print(lon[m1] + "\t");
		// System.out.print(x[m1] + "\t");

		// System.out.print(lat[m1] + "\t");
		lat[m1] = screenMinLat + (lat[m1] - minLat) * ScreenIntervalLat / intervalLat;
		y[m1] = (int) Math.round(lat[m1]);
		// System.out.print(lat[m1] + "\t");
		// System.out.print(y[m1] + "\t");
		// System.out.println();
	    }
	    file.close();
	} catch (FileNotFoundException ex) {
	    ex.printStackTrace();
	    System.out.print("FileNotFoundException!");
	    System.exit(1);
	} catch (IOException ex) {
	    ex.printStackTrace();
	    System.out.print("IOException!");
	    System.exit(1);
	}
    }
}
