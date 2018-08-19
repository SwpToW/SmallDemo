package ThunderGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BallJPanel extends JPanel implements MouseMotionListener,MouseListener{
     int x,y;
    
     private List<Ball> allBalls;//我方飞机集合
     private List<Ball> allSnows;//敌方飞机
     boolean isFire;//�ж��Ƿ���
     Image hero_img=new ImageIcon("hero.gif").getImage();
     //构造面板
     public BallJPanel(){
    	 x=200;
    	 y=400;
    	 
    	 allBalls=new ArrayList<Ball>(); 
    	 allSnows=new ArrayList<Ball>();
    	 //添加鼠标监听
    	 this.addMouseMotionListener(this);
    	 this.addMouseListener(this);

    	
     }
     //��
    public void paint(Graphics g) {
    	super.paint(g);
    
    	URL imgURL=getClass().getResource("back.jpg");
    	ImageIcon icon=new ImageIcon(imgURL);
    	g.drawImage(icon.getImage(), 0, 0, 400, 600, this); 	
    	g.drawImage(hero_img, x,y,50,50,null);
    	
    	for(int i=0;i<allBalls.size();i++){
    		Ball b=allBalls.get(i);
    		
    		b.draw(g);
    	}	
    	
    	for(int i=0;i<allSnows.size();i++){
    		Ball b=allSnows.get(i);
    		
    		b.draw(g);
    	}  	
  	
    }

    public void st(){
    	new Thread(){
    		public void run(){
    			int count=0;
    			while(true){
    				count++;
    				if(count>=Integer.MAX_VALUE){
    					count=0;
    				}
    				//不断生成敌机
    				if(count%60==0){
    			
    					Ball b=new Ball();
    					b.x=(int)(Math.random()*400);
    					b.y=-40;
    					b.r=20;
    					b.ori=5;
    					b.type=1;
    					allSnows.add(b);					
    				}
    				//如果敌机越过屏幕，从集合里移除
    				for(int i=0;i<allSnows.size();i++){
    					Ball b=allSnows.get(i);
    					b.move();
    					if(b.y>BallJPanel.this.getHeight()){
    						allSnows.remove(i);
    					}
    				}
                    //如果开火生成子弹
    				if(isFire && count%15==0 ){
    					Ball b=new Ball();
    					b.x=x;
    					b.y=y;
    					b.r=20;
    					b.ori=4;
    					b.type=0;
    	         		allBalls.add(b);			
    				}
    				//如果子弹越过屏幕，移除子弹
    				for(int i=0;i<allBalls.size();i++){
    					Ball b=allBalls.get(i);
    					b.move();
    					if(b.y<=0){
    						allBalls.remove(i);
    					}
    				}
    				
    				for(int i=0;i<allBalls.size();i++){
    					for(int j=0;j<allSnows.size();j++){
    						//两者碰撞，都移除
    						if(i<allBalls.size() && j<allSnows.size() 
    								&& allBalls.get(i).isTouch(allSnows.get(j))){
    							allBalls.remove(i);
    							allSnows.remove(j);
    							
    						}
    					}
    				}
    		          
    				try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
    				repaint();
    			}
    		}
    	}.start();
    }
    
   //�϶�
	public void mouseDragged(MouseEvent e) {
		x=e.getX();
		y=e.getY();
		
		repaint();
		
	}
  //移动事件
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
		System.out.println(x+" "+y);
		repaint();
		
	}
	
	public void mousePressed(MouseEvent e) {
		isFire=true;// 鼠标按下开火
		
	}
	  //�ſ���� 
	public void mouseReleased(MouseEvent e) {
		isFire=false;  //鼠标松开不开火
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
     
}
