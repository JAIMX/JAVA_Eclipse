package mTSP_GA;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

//public class Graph extends Frame implements WindowListener {
//	static double[] x;
//	static double[] y;
//	private static final int DEFAULT_WIDTH = 500;
//	private static final int DEFAULT_HEIGHT = 500;
//	ArrayList<Individual> firstFront;
//
//	public Graph(ArrayList<Individual> firstFront, String title) {
//		super(title);
//		this.firstFront = firstFront;
//		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//		setVisible(true);
//		this.addWindowListener(this);
//	}
//
//	public void windowClosing(WindowEvent e) {
//		System.out.println("windowClosing() method");
//		System.exit(0);
//	}
//
//	public void windowClosed(WindowEvent e) {
//	}
//
//	public void windowActivated(WindowEvent e) {
//	}
//
//	public void windowDeactivated(WindowEvent e) {
//	}
//
//	public void windowIconified(WindowEvent e) {
//		System.out.println("windowIconified() method");
//	}
//
//	public void windowDeiconified(WindowEvent e) {
//		System.out.println("windowDeiconified() method");
//	}
//
//	public void windowOpened(WindowEvent e) {
//		System.out.println("windowOpened() method");
//	}
//
//	public void paint(Graphics g) {
//
//		System.out.println(firstFront.size());
//
//		for (int i = 0; i < firstFront.size(); i++) {
//			double x = firstFront.get(i).getFitness1();
//			double y = firstFront.get(i).getFitness2();
//			if (x <= 50 && y <= 50) {
//				g.drawOval((int) x, (int) y , 1, 1);
//			}
//
//		}
//
//	}
//
//	public Dimension getPreferredSize() {
//		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//	}
//
//}

//public class Graph extends JFrame {
//	static double[] x;
//	static double[] y;
//	private static final int DEFAULT_WIDTH = 500;
//	private static final int DEFAULT_HEIGHT = 500;
//	ArrayList<Individual> firstFront;
//	private Graphics graph;
//	
//	public Graph(ArrayList<Individual> firstFront){
//	    this.firstFront=firstFront;
//	    JFrame frame=new JFrame();
//	    
//	    try{
//	        Thread.sleep(500);
//	    }catch(Exception e){
//	        e.printStackTrace();
//	    }
//	    
//	    graph=this.getGraphics();
//	    paintComponent(graph);
//	    
//	    frame.setTitle("Test");
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    frame.setVisible(true);
//	}
//	
//	public void paintComponent(Graphics g){
//	    
//	    g.drawOval(50, 50, 10, 10);
//	    
//        for (int i = 0; i < firstFront.size(); i++) {
//            double x = firstFront.get(i).getFitness1();
//            double y = firstFront.get(i).getFitness2();
//            if (x <= 50 && y <= 50) {
//                g.drawOval((int) x, (int) y, 1, 1);
//            }
//
//        }
//	}
//	
//	
//}

public class Graph {
    static ArrayList<Individual> firstFront;
    static double[] x;
    static double[] y;

    public Graph(ArrayList<Individual> firstFront) {
    	this.firstFront=firstFront;

        EventQueue.invokeLater(() -> {
            JFrame frame = new DrawFrame();
            frame.setTitle("DrawTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    
//    public static void main(String[] args)
//    {
//       EventQueue.invokeLater(() ->
//          {
//             JFrame frame = new DrawFrame();
//             frame.setTitle("DrawTest");
//             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//             frame.setVisible(true);
//          });
//    }
//    public static void main(String[] args) {
//        Graph g=new Graph();
//    }
}

/**
 * A frame that contains a panel with drawings
 */
class DrawFrame extends JFrame {
    public DrawFrame() {
        add(new DrawComponent());
        pack();
    }
}

/**
 * A component that displays rectangles and ellipses.
 */

class DrawComponent extends JComponent
{
   private static final int DEFAULT_WIDTH = 800;
   private static final int DEFAULT_HEIGHT = 500;
   private static final double[]x=Graph.x;
   private static final double[]y=Graph.y;
   

   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      

//      for(int i=0;i<Graph.firstFront.size();i++){
//    	  double x=Graph.firstFront.get(i).getFitness2();
//    	  double y=Graph.firstFront.get(i).getFitness1();
//    	  
//          Ellipse2D.Double node=new Ellipse2D.Double(x, y, 2, 2);
//          g2.draw(node);
//      }
      
      int size=Graph.firstFront.size();
      int index=(int) (Math.random()*size);
      int[] part1=Graph.firstFront.get(index).getchromosome1();
      int[] part2=Graph.firstFront.get(index).getchromosome2();
      
      for(int i=0;i<Graph.x.length;i++){
    	  x[i]=x[i]/4;
    	  y[i]=y[i]/4;
      }
      
      //draw the depot(x/10,y/10)
    Ellipse2D.Double node=new Ellipse2D.Double(x[0], y[0], 5, 5);
    g2.draw(node);
    
    
    int lIndex=0;
    int rIndex=part2[0]-1;
    for(int i=0;i<part2.length;i++){
    	
    	for(int j=lIndex;j<=rIndex;j++){
    	    node=new Ellipse2D.Double(x[part1[j]], y[part1[j]], 1, 1);
    	    g2.draw(node);
    	    if(j!=rIndex){
    	    	g2.draw(new Line2D.Double(x[part1[j]],y[part1[j]],x[part1[j+1]] , y[part1[j+1]]));
    	    }
    	    g2.draw(new Line2D.Double(x[0], y[0],x[part1[lIndex]], y[part1[lIndex]]));
    	    g2.draw(new Line2D.Double(x[0],y[0],x[part1[rIndex]] , y[part1[rIndex]]));    
    	}
    	
    	if(i<part2.length-1){
        	lIndex=rIndex+1;
        	rIndex=rIndex+part2[i+1];
    	}
    }    
      

   }
   
   public Dimension getPreferredSize() { return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); }
}
