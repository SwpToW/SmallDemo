package RnndomMaze;

public class MazeData {
	public static final char ROAD=' ';
	public static final char WALL='#';
	
	private int N,M; //长宽 ，奇数
	public char[][] maze; //二维矩阵
	
    public boolean[][] inMist;  //迷雾
	
	private int entranceX,entranceY; //入口及出口
	private int exitX,exitY;
	
    public boolean[][] visited; //是否访问过的标记
	
	public MazeData(int N,int M){
		//给定数组有要判断是不是奇数
		if(N%2==0||M%2==0)
			throw new IllegalArgumentException("N or M %2");
		this.M=M;
		this.N=N;
		maze=new char[N][M];
		visited =new boolean[N][M]; //初始化默认false
		inMist=new boolean[N][M]; //初始化默认在迷雾中true
		//初始化二维迷宫，，横纵坐标都为奇数时初始化为road
		for(int i=0;i<N;i++){
		     
			for(int j=0;j<M;j++){
				if(i%2==1&&j%2==1)
					maze[i][j]=ROAD;
				else
					maze[i][j]=WALL;
				visited[i][j]=false;  //初始化时没有被访问过的
				inMist[i][j]=true; //初始化在迷雾中
			}
			entranceX=1; //入口坐标
	    	entranceY=0;
	    	exitX=N-2;  //出口坐标
	    	exitY=M-1;
	    	
	    	maze[entranceX][entranceY]=ROAD;//设置好出口入口
	    	maze[exitX][exitY]=ROAD;
		}
	}
	
	public int N(){return N;}
	public int M(){return M;}
    public int getEntranceX(){return entranceX;}
    public int getEntranceY(){return entranceY;}
    public int getExitX(){return exitX;}
    public int getExitY(){return exitY;}
    
    public boolean inArea(int x,int y){
    	return x>=0&&x<N&&y>=0&&y<M;
    }
    //将给定坐标的迷雾打开
    public void openMist(int x,int y){
    	if(!inArea(x, y))
    		throw new IllegalArgumentException("wrong");
    	for(int i=x-1;i<x+1;i++)     
    		for(int j=y-1;j<y+1;j++)
    			//打开以此坐标为中心边上八个位点的迷雾
    			if(inArea(x, y))
    				inMist[i][j]=false;
    }

}
