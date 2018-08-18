package RnndomMaze;


import java.awt.*;
import javax.swing.*;

import Maze.AlgoVisHelper;

public class AlgoFrame extends JFrame{

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);

        setResizable(false);
        pack();    

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public AlgoFrame(String title){

        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    // TODO: 设置自己的数据
    private MazeData data;
    public void render(MazeData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            // 双缓存
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            // 抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);
            
            int w=canvasWidth/data.M(); //
            int h=canvasHeight/data.N();
            // 具体绘制
            //遍历每个位点
            for(int i=0;i<data.N();i++){
            	for(int j=0;j<data.M();j++){
            		if(data.inMist[i][j])//位点在迷雾中，画笔设置为黑色
            			AlgoVisHelper.setColor(g2d, AlgoVisHelper.Black);
            		else if(data.maze[i][j]==MazeData.WALL) //位点时墙，画笔设为蓝色
            			AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
            		else
            			AlgoVisHelper.setColor(g2d, AlgoVisHelper.White); //位点是路，画笔设为白色
            		AlgoVisHelper.fillRectangle(g2d, j*w, i*h, w, h); //绘制每一个位点
            	}
            }
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}