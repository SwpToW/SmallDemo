package PI;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
	private static int DELAY=50;
	private int insideCircle=0;
    private Circle circle;        // 数据
    private LinkedList<Point> points;
    private AlgoFrame frame;    // 视图
    private int N;

    public AlgoVisualizer(int sceneWidth, int sceneHeight,int N){

        // 初始化数据
    	this.N=N;
    	circle=new Circle(sceneWidth/2,sceneHeight/2,sceneHeight/2);
        points=new LinkedList<Point>();

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("PI", sceneWidth, sceneHeight);
         
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){

        for(int i=0;i<N;i++){
        	if(i%100==0){
        	frame.render(circle, points);
        	AlgoVisHelper.pause(DELAY);
        	if(points.size()!=0){
        	int circleArea=insideCircle;
        	int squareArea=points.size();
        	double PI=4*(double)circleArea/squareArea;
        	System.out.println(PI);
        	}
        	}
        	int x=(int)(Math.random()*frame.getCanvasWidth());
        	int y=(int)(Math.random()*frame.getCanvasHeight());
        	Point p=new Point(x,y);
        	points.add(p);
        	if(circle.contains(p))
        		insideCircle++;
        }
    }



    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N=50000;

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight,N);
    }
}
