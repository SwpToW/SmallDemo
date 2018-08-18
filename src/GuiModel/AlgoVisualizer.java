package GuiModel;

import java.awt.EventQueue;
import java.util.Arrays;

public class AlgoVisualizer {
    private int[] money;
    private AlgoFrame frame;  //视图
    private static int DELAY=40;
    
    public AlgoVisualizer(int sceneWidth,int sceneHeight){
       
    	money=new int[100];
    	for(int i=0;i<money.length;i++)
    		money[i]=100; //每个人初始的时候都是一百块钱，表现在高度上
    	
    	//初始化视图
    	EventQueue.invokeLater(()->{
    		frame=new AlgoFrame("welcome", sceneWidth, sceneHeight);
    		
    		//鼠标键盘监听器
    		
    		new Thread(()->{
    			run();
    		}).start();
        });
    }
    //动画逻辑
    private void run(){
    	while(true){
    		Arrays.sort(money);
    		frame.render(money);
    		AlgoVisHelper.pause(DELAY); //画面停留
    		for(int k=0;k<50;k++){
    		for(int i=0;i<money.length;i++){
    			//if(money[i]>0){
    				int j=(int)(Math.random()*money.length);//随机以下另外一个得到前的
    				money[i]-=1;
    				money[j]+=1;
    			}
    		 }
    		}
    	}
   // }
    //
    
    public static void main(String[] args) {
		int sceneWidth=1000;
		int sceneHeight=800;
		
		AlgoVisualizer visualizer=new AlgoVisualizer(sceneWidth, sceneHeight);
	}
}
