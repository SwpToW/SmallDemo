package JFrame;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

//自定义的框架类
public class AlgoFrame extends JFrame {
	private int canvasWidth;
	private int canvasHeight;
    public AlgoFrame(String title,int canvasWidth,int canvasHeight){
    	super(title);
    	this.canvasHeight=canvasHeight;
    	this.canvasWidth=canvasWidth;
    	
    	AlgoCanvas canvas=new AlgoCanvas();
    	setContentPane(canvas);//设置当前面板
    	pack();// 布局整理
    	this.setResizable(false);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setVisible(true);
    }
    public AlgoFrame(String title){
    	this(title,1024,768);
    }
    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}
    //JPanel 
    private class AlgoCanvas extends JPanel{
        
    	public AlgoCanvas(){
    		super(true);
    	}
    	public void paintComponent(Graphics g){
    		super.paintComponent(g);
    		Graphics2D g2d=(Graphics2D)g; //换一下画笔
    		RenderingHints hints=new RenderingHints(RenderingHints.KEY_ANTIALIASING
    				                               ,RenderingHints.VALUE_ANTIALIAS_ON);
    		g2d.addRenderingHints(hints);
    		
    		
            
    	}
    	@Override
    	public Dimension getPreferredSize(){
    		return new Dimension(canvasWidth, canvasHeight);
    	}
    }
}
