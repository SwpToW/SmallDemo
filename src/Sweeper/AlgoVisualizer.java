package Sweeper;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public class AlgoVisualizer {

	private static int DELAY = 5;
	private static int blockSide = 32;
	private static final int block=20;// 长宽给定，也可修改
	private static final int minenumber=50;//雷的数目锁定
     
	//数据与视图
	private MineData data; 
	private AlgoFrame frame;
    //传入长宽和雷的数量
	public AlgoVisualizer(int N, int M, int mineNumber) {

		data = new MineData(N, M, mineNumber);
		//计算长宽
		int sceneWidth = M * blockSide;
		int sceneHeight = N * blockSide;

		EventQueue.invokeLater(() -> {
			//初始化视图
			frame = new AlgoFrame("Mine Sweeper", sceneWidth, sceneHeight);
			//添加鼠标监听
			frame.addMouseListener(new AlgoMouseListener());
			new Thread(() -> {
				run();
			}).start();
		});
	}

	private void run() {
        //刷新数据
		setData(false, -1, -1);
	}

	private void setData(boolean isleftClicked, int x, int y) {
		if (data.inArea(x, y)) {
			if (isleftClicked) {//是左键点击的话
				if (data.isMine(x, y)) {
					// 本来应该游戏结束
					data.open[x][y] = true;
				} else
					data.open(x, y);//如果不是雷区，打开周边
			} else//右键点击标志旗帜
				data.flags[x][y] = !data.flags[x][y];//这里取反，如果开始有标志则取消，没有则标志
		}
		frame.render(data);
		AlgoVisHelper.pause(DELAY);
	}

	private class AlgoMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent event) {

			// 重定位坐标
			event.translatePoint(-(int) (frame.getBounds().width - frame.getCanvasWidth()),
					-(int) (frame.getBounds().height - frame.getCanvasHeight()));
			Point pos = event.getPoint();//得到屏幕上点击的点

			int w = frame.getCanvasWidth() / data.M();
			int h = frame.getCanvasHeight() / data.N();

			int x = pos.y / h;
			int y = pos.x / w;

			if (SwingUtilities.isLeftMouseButton(event))
				//如果左键点击，
				setData(true, x, y);
			else
				setData(false, x, y);
		}
	}

	public static void main(String[] args) {


		AlgoVisualizer vis = new AlgoVisualizer(block, block, minenumber);

	}
}