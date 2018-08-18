package Maze;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData {
	private int entranceX,entranceY;
	private int exitX,exitY;
	
	public static final char ROAD=' ';
	public static final char WALL='#';
	
	public boolean[][] path; //这个点是否路径
	
	public boolean[][] visited; //表示是否访问过
	
    private char[][] maze; //二维矩阵
    private int N,M;// 行，列
    public MazeData(String filename){
    	if(filename==null)
    		throw new IllegalArgumentException("filename wrong");
    	Scanner scanner=null;
    	try{
    		File file=new File(filename);
    		System.out.println(file);
    		if(!file.exists())
    			throw new IllegalArgumentException("wrong");
    		
    		FileInputStream fis=new FileInputStream(file);
    		scanner=new Scanner(new BufferedInputStream(fis),"UTF-8");
    		
    		String nmline=scanner.nextLine();
    		String[] nm=nmline.trim().split("\\s+");
    		N=Integer.parseInt(nm[0]);
    		M=Integer.parseInt(nm[1]);
    		
    		maze=new char[N][M];
    		visited =new boolean[N][M];
    		path=new boolean[N][M];
    		for(int i=0;i<N;i++){
    			String line=scanner.nextLine();
    			if(line.length()!=M)
    				throw new IllegalArgumentException(" file wrong");
    			for(int j=0;j<M;j++){
    				maze[i][j]=line.charAt(j);
    			}
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}finally{
    		if(scanner!=null)
    			scanner.close();
    	}
    	entranceX=1; //入口坐标
    	entranceY=0;
    	exitX=N-2;  //出口坐标
    	exitY=M-1;
    }
    
    public int N(){return N;}
    public int M(){return M;}
    public int getEntranceX(){return entranceX;}
    public int getEntranceY(){return entranceY;}
    public int getExitX(){return exitX;}
    public int getExitY(){return exitY;}
    
    public char getMaze(int i,int j){
    	if(!inArea(i,j))
    		throw new IllegalArgumentException("i or j worong");
        return maze[i][j];
    	
    }
    //判断坐标是否在界面中
    public boolean inArea(int x,int y){
    	return x>=0&&x<N&&y>=0&&y<M;
    }
    public void print(){
    	
    }
	
	
}
