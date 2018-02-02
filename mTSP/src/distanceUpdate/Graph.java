package distanceUpdate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Arrays;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Graph extends Frame implements WindowListener {
	final static double phi = (Math.sqrt(5) - 1) / 2;
	final static int width = ReadFile.width;
	final static int height = ReadFile.height;
	final static int nbCities = ReadFile.nbCities;
	final static int nbDots = ReadFile.nbDots;
	final static int x[] = new int[nbDots];
	final static int y[] = new int[nbDots];
	private static int companyXY[] = new int[2];
	private static int landfillXY[] = new int[2];
	int screenMaxLat = ReadFile.screenMaxLat + 30;
	// System.arraycopy();

	String s = "";

	public Graph(String title) {
		super(title);
		setSize(width, height);
		setVisible(true);
	}

	public Graph() {
		// super(title);
		setSize(width, height);
		setVisible(true);

	}

	public static void main(String args[]) {
		String file = "./data/longitude&latitude.xls";
		ReadFile rf = new ReadFile();
		rf.readFile(file);
		System.arraycopy(rf.x, 0, x, 0, nbCities);
		System.arraycopy(rf.y, 0, y, 0, nbCities);
		for (int i = nbCities; i < nbDots; i++) {
			x[i] = rf.x[0];
			y[i] = rf.y[0];
		}
		companyXY[0] = (int)Math.round(rf.company[0]);
		companyXY[1] = (int)Math.round(rf.company[1]);
		landfillXY[0] = (int)Math.round(rf.landfill[0]);
		landfillXY[1] = (int)Math.round(rf.landfill[1]);
		Graph g = new Graph("mTSP");
		// Graph g = new Graph();
		g.addWindowListener(g);
		// FileResource res = new FileResource("att48.tsp");
		// int i = 0;
		// for (String line : res.lines()) {
		// //System.out.println(line);
		// int firstBlankIdx = line.indexOf(" ");
		// int secondBlankIdx = line.indexOf(" ",firstBlankIdx + 1);
		// x[i] = Integer.parseInt(line.substring(firstBlankIdx +
		// 1,secondBlankIdx));
		// y[i] = Integer.parseInt(line.substring(secondBlankIdx + 1));
		// System.out.println(x[i] + " " + y[i]);
		// i++ ;
		// }
	}

	public void windowClosing(WindowEvent e) {
		System.out.println("windowClosing() method");
		System.exit(0);
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
		System.out.println("windowIconified() method");
	}

	public void windowDeiconified(WindowEvent e) {
		System.out.println("windowDeiconified() method");
	}

	public void windowOpened(WindowEvent e) {
		System.out.println("windowOpened() method");
	}

	public void paint(Graphics g) {
		// g.setColor(Color.BLACK);
		Random r = new Random();
		// int[] x = new int[nbDots];
		// int[] y = new int[nbDots];
		int[] trip = new int[nbDots];
//		int[] trip = { 1, 19, 32, 33, 35, 30, 22, 31, 34, 23, 24, 28, 27, 26, 20, 25, 29, 21, 52, 51, 16, 18, 17, 13,
//				14, 15, 53, 3, 5, 4, 8, 11, 10, 39, 48, 47, 49, 50, 45, 44, 46, 43, 36, 42, 40, 41, 38, 37, 9, 12, 7, 6,
//				2 };
		//int trip[] = new int[nbDots];
//		SubtourReversal sr = new  SubtourReversal();
//		sr.main(null);
		SubtourReversal.main(null);
		System.arraycopy(SubtourReversal.globalOptimum, 0, trip, 0, nbDots);
	    //System.out.println(Arrays.toString(trip));
		int tripLength = trip.length;
		long[][] distance = new long[nbDots][nbDots];

		// x[0] = r.nextInt(width - 1);
		// y[0] = r.nextInt(height - 1);
		// g.drawOval(7, 30, 1, 1);
		// g.drawOval(width - 10, height - 10, 1, 1);
		g.drawOval(x[0], screenMaxLat - y[0], 5, 5);
		//g.drawOval(companyXY[0], companyXY[1], 5, 5);
		g.drawOval(landfillXY[0], screenMaxLat - landfillXY[1], 5, 5);
		// trip[0] = 1;
		//System.out.println(x[0] + " " + y[0]);
		int i;
		for (i = 1; i < nbDots; i++) {
			// x[i] = r.nextInt(width - 13) + 7;
			// y[i] = r.nextInt(height - 40) + 30;
			g.drawOval(x[i], screenMaxLat - y[i], 1, 1);
			//System.out.println(x[i] + " " + y[i]);
		}
		for (i = 1; i < nbDots; i++) {
			g.drawLine(x[trip[i - 1] - 1], screenMaxLat - y[trip[i - 1] - 1], x[trip[i] - 1], screenMaxLat - y[trip[i] - 1]);
		}
		// y[trip[0] - 1]);
		g.drawLine(x[trip[i - 1] - 1], screenMaxLat - y[trip[i - 1] - 1], x[trip[0] - 1], screenMaxLat - y[trip[0] - 1]);
	
	}
}
