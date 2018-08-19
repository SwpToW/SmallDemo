package ThunderGame;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Ball {
	 int x,y;//每个元素都以坐标为原点，r为半径的一个球
	 int r; //半斤
	 int ori;//方向 
	 int speed;//速度
   int type;//0代表子弹 1代表我方飞机

  public static Image image_fire=
	  new ImageIcon("fire.gif").getImage();//
  public static Image image_ef=
	  new ImageIcon("el_0.gif").getImage();//
  public static final int UP=4;    //方向，4向上，5向下  
  public static final int DOWN=5;
	
	public Ball(){
		
	}

	public Ball(int x, int y, int r, 
			int orientation,JPanel panel,int speed) {
		//super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.ori= ori;
		this.speed=speed;
	}
	
	public void draw(Graphics g){
    switch(type){
    case 0: g.drawImage(image_fire, x, y,
    		2*r, 2*r, null);break;
    case 1: g.drawImage(image_ef, x, y, 2*r, 2*r,
    		null);break;
    }
		
	}
	
	//碰撞检测
	public boolean isTouch(Ball ball){
	  int x1=x+r;
	  int y1=y+r;
      int x2=ball.x+ball.r;
      int y2=ball.y+ball.r;

    double s= Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    
		return s<=this.r+ball.r;
	}
	
     //上下移动
	public void move(){
		switch(ori){
    case UP:
      y--;
      break;
      
    case DOWN:
      y++;
      break;
		}
	}


}
