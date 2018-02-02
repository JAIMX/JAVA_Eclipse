package mTSP_GA;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Test extends JPanel implements ActionListener{
    JButton b1,b2;
    String message="请点击按钮";
    int row=50,col=50;

    public Test(){
        //按钮1
        b1=new JButton("First Button");
        b1.setActionCommand("first");
        b1.addActionListener(this);
        //按钮2
        b2=new JButton("Second Button");
        b2.setActionCommand("second");
        b2.addActionListener(this);

        add(b1);
        add(b2);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("first")){
            message="第一个按钮被按下!";
            row=50;
            col=50;
        }else if(e.getActionCommand().equals("second")){
            message="第二个按钮被按下!";
            row=50;
            col=150;
        }
        repaint();   //注意这一行
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);  //注意这一行
        g.drawString(message, col, row);
    }

    public static void main(String args[]){
        JFrame jf=new JFrame("按钮测试!");
        jf.getContentPane().add(new Test());
        jf.setSize(300,200);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
