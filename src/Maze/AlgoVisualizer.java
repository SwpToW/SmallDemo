package Maze;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class AlgoVisualizer {
	private static int DELAY=20;
	private static int blockSide=8; //每个块
	private MazeData data; //数据
	private AlgoFrame frame;  //试图
	
	private static final int[][] d={{-1,0},{0,1},{1,0},{0,-1}};//方向偏移
  
    public AlgoVisualizer(String mazeFile){
    	data=new MazeData(mazeFile);
    	
    	int sceneHeight=data.N()*blockSide;
    	int sceneWidth=data.M()*blockSide;
    	
    	EventQueue.invokeLater(()->{
    		frame=new AlgoFrame("Maze",sceneWidth,sceneHeight);
    		
    		new Thread(()->{
    			run();
    		}).start();
    	});
    	
    }
    public void run(){
    	setData(-1,-1,false); //无效坐标
    	if(!go(data.getEntranceX(),data.getEntranceY()));// 迷宫入口
    	System.out.println("迷宫无解");
    	setData(-1,-1,false);
    }
	private boolean go(int x, int y) {//找到终点返回true
		if(!data.inArea(x, y))
			throw new IllegalArgumentException("wrong");
		
		data.visited[x][y]=true;
		data.path[x][y]=true;
		setData(x,y,true);
		//到达出口
		if(x==data.getExitX()&&y==data.getExitY())
			return true;
		for(int i=0;i<4;i++){
			int newX=x+d[i][0];
			int newY=y+d[i][1];
			if(data.inArea(newX, newY)&&
					data.getMaze(newX, newY)==MazeData.ROAD&&!data.visited[newX][newY])
				if(go(newX,newY))
					return true;
		}
		setData(x,y,false);
		return false;
		
	}
	private void setData(int x,int y,boolean isPath) {
		if(data.inArea(x, y)){
		data.path[x][y]=isPath;
		}
		frame.render(data);
		AlgoVisHelper.pause(DELAY);
		
	}
	public static void main(String[] args) {
		String mazeFile="src/maze.txt";
		AlgoVisualizer vis=new AlgoVisualizer(mazeFile);
	   
	}
    
}