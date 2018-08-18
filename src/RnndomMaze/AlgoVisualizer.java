package RnndomMaze;

import java.awt.EventQueue;
import java.util.Stack;

public class AlgoVisualizer {
	private static int DELAY=5; //渲染延时
	private static int blockSide = 8;// 墙的边长

	private MazeData data; // 数据
	private AlgoFrame frame; // 视图
	private static final int[][] d={{-1,0},{0,1},{1,0},{0,-1}};// 四个方向的便宜量
	

	public AlgoVisualizer(int N, int M) {
		// 数据
		data = new MazeData(N, M);  
		int secneHeight = data.N() * blockSide; //求出画布的宽高。 位点的边长已经确定
		int secneWidth = data.M() * blockSide;
		// 视图初始化

		EventQueue.invokeLater(() -> {
			frame = new AlgoFrame("Random Maze", secneWidth, secneHeight);
			new Thread(() -> {
				run();
			}).start();
		});

	}
    //动画逻辑
	private void run() {
       setData(-1,-1);  //因为没有打破强，所以传入无效的参数
       
      // go(data.getEntranceX(),data.getEntranceY()+1); 
       //非递归算法 ,随机队列 ，广度优先遍历
       RandomQueue<Position> queue=new RandomQueue<Position>();
       Position first=new Position(data.getEntranceX(), data.getEntranceY()+1);
       queue.add(first); //入队操作
       //访问位点迷雾消失
       data.visited[first.getX()][first.getY()]=true; 
       data.openMist(first.getX(), first.getY());
       
       while(!queue.isEmpty()){
    	   Position curPos=queue.remove(); //出队操作
    	   //四个方向都要进行遍历
    	   for(int i=0;i<4;i++){
    			int newX=curPos.getX()+d[i][0]*2; //每两个节点隔着一个墙，所以要偏移2位
    			int newY=curPos.getY()+d[i][1]*2;
    			if(data.inArea(newX, newY)&&!data.visited[newX][newY]&&data.maze[newX][newY]==MazeData.ROAD){
    				queue.add(new Position(newX, newY)); //遍历的新的位点都入栈
    		   //访问过迷雾消失
    			data.visited[newX][newY]=true;
    			data.openMist(newX,newY);
    			//刷新渲染
    			setData(curPos.getX()+d[i][0],curPos.getY()+d[i][1]);
    			}
    				
    	   }
       }
       setData(-1,-1);
	}
	/*private void go(int x, int y) {
		if(!data.inArea(x,y))
			throw new IllegalArgumentException(" x,y wrong index");
		data.visited[x][y]=true;//访问过设为true
		for(int i=0;i<4;i++){
			int newX=x+d[i][0]*2; //每两个节点隔着一个墙，所以要偏移2位
			int newY=y+d[i][1]*2;
			if(data.inArea(newX, newY)&&!data.visited[newX][newY])
				setData(x+d[i][0],y+d[i][1]); //强的位置
				go(newX,newY);
		}
		
	}*/
	 //渲染函数
	private void setData(int x,int y) {
		if(data.inArea(x, y))
			data.maze[x][y]=MazeData.ROAD; //打破强
		frame.render(data);
		AlgoVisHelper.pause(DELAY);

	}

	public static void main(String[] args) {
        AlgoVisualizer vis=new AlgoVisualizer(101, 101);
		
	}

}
