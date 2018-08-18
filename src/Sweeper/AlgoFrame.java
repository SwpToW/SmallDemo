package Sweeper;

import java.awt.*;
import javax.swing.*;

public class AlgoFrame extends JFrame {

	private int canvasWidth;
	private int canvasHeight;

	public AlgoFrame(String title, int canvasWidth, int canvasHeight) {

		super(title);

		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;

		AlgoCanvas canvas = new AlgoCanvas();
		setContentPane(canvas);
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		setVisible(true);
	}

	public AlgoFrame(String title) {

		this(title, 1024, 768);
	}

	public int getCanvasWidth() {
		return canvasWidth;
	}

	public int getCanvasHeight() {
		return canvasHeight;
	}

	// data
	private MineData data;

	public void render(MineData data) {
		this.data = data;
		repaint();
	}

	private class AlgoCanvas extends JPanel {

		public AlgoCanvas() {
			// 双缓存
			super(true);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;

			// 抗锯齿
			RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.addRenderingHints(hints);

			// 具体绘制
			int w = canvasWidth / data.M();
			int h = canvasHeight / data.N();

			for (int i = 0; i < data.N(); i++)
				for (int j = 0; j < data.M(); j++) {
					if (data.open[i][j]) {
						if (data.isMine(i, j))
							//打开并且是雷区，绘制雷
							AlgoVisHelper.putImage(g2d, j * w, i * h, MineData.mineImageURL);
						else
							//不是雷区并且打开给出周围雷区有几个雷
							AlgoVisHelper.putImage(g2d, j * w, i * h, MineData.numberImageURL(data.getNumber(i, j)));
					} else {
						if (data.flags[i][j])
							//没有打开但被标志旗帜画出旗帜
							AlgoVisHelper.putImage(g2d, j * w, i * h, MineData.flagImageURL);
						else
							//否则给出石块
							AlgoVisHelper.putImage(g2d, j * w, i * h, MineData.blockImageURL);
					}

				}
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(canvasWidth, canvasHeight);
		}
	}
}