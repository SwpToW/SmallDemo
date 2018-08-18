package Sweeper;

public class MineData {

	public static final String blockImageURL = "resources/block.png";
	public static final String flagImageURL = "resources/flag.png";
	public static final String mineImageURL = "resources/mine.png";

	public static String numberImageURL(int num) {
		if (num < 0 || num > 8)
			throw new IllegalArgumentException("No such a number image!");
		return "resources/" + num + ".png";
	}

	private int N, M; //行列
	private boolean[][] mines;  //坐标是否雷区
	private int[][] numbers;// 坐标周围有多少雷
	public boolean[][] open;  //坐标是否已经打开
	public boolean[][] flags; // 是否被玩家标记成旗帜

	public MineData(int N, int M, int mineNumber) {

		if (N <= 0 || M <= 0)
			throw new IllegalArgumentException("Mine sweeper size is invalid!");

		if (mineNumber < 0 || mineNumber > N * M)
			throw new IllegalArgumentException("Mine number is larger than the size of mine sweeper board!");

		this.N = N;
		this.M = M;
       //数据初始化
		mines = new boolean[N][M];
		open = new boolean[N][M];
		flags = new boolean[N][M];
		numbers = new int[N][M];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++) {
				mines[i][j] = false;
				open[i][j] = false;
				flags[i][j] = false;
				numbers[i][j] = 0; // 初始化周边雷区数目0
			}
        // 根据雷的数目生成随机雷区，用到了knuth算法
		generateMines(mineNumber);
		
		// 生成雷区后对numbers[][]数组修改
		caculateNumbers();
	}

	// 计算每一个格子周围有多少个雷
	private void caculateNumbers() {
		//遍历每个节点
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (mines[i][j])// 如果本身就是雷，点开方块游戏结束，这里设置为无效值
					numbers[i][j] = -1;
				numbers[i][j] = 0;
				//遍历周边八个节点
				for (int ii = i - 1; ii <= i + 1; ii++)
					for (int jj = j - 1; jj <= j + 1; jj++)
						if (inArea(ii, jj) && mines[ii][jj])
							numbers[i][j]++;
			}
		}

	}

	private void generateMines(int mineNumber) {
		// 这样设置可能会有重复雷区
		/*
		 * for(int i=0;i<mineNumber;i++){ int x=(int)Math.random()*N; int
		 * y=(int)Math.random()*M; mines[x][y]=true; }
		 * 
		 * for (int i = 0; i < mineNumber; i++) { while (true) { int x = (int)
		 * (Math.random() * N); int y = (int) (Math.random() * M); if
		 * (!mines[x][y]) { mines[x][y] = true; break; } } }
		 */
		//首先将雷区摆在地图的前一部分
		for (int i = 0; i < mineNumber; i++) {
			int x = i / M;
			int y = i % M;
			mines[x][y] = true;
		}

		// knuth洗牌算法 從54張牌等概率的拿出一張放到第一個位置
		// 之後從剩下的53 53 52。。。等概率拿出放到相應位置
		// 在一個空間操作就是要有交換，這裏從前往後遍歷
		for (int i = 0; i < N * M; i++) {
			// 顺序映射出遍历的坐标(x,y)
			int iX = i / M;
			int iY = i % M;
			// 隨即從剩下的个数里挑选一个出来
			int ranNumber = (int) (Math.random() * (N * M - i) + i);
			// 通过随机数ranNumber 来随机映射出挑选出来的坐标
			int ranX = ranNumber / M;
			int ranY = ranNumber % M;

			swap(iX, iY, ranX, ranY);// 交换位置
		}

	}

	private void swap(int x1, int x2, int y1, int y2) {
		boolean t = mines[x1][y1];
		mines[x1][y1] = mines[x2][y2];
		mines[x2][y2] = t;

	}

	public int N() {
		return N;
	}

	public int M() {
		return M;
	}

	public boolean isMine(int x, int y) {
		if (!inArea(x, y))
			throw new IllegalArgumentException("Out of index in isMine function!");
		return mines[x][y];
	}
    //得到周边雷区数目
	public int getNumber(int x, int y) {
		if (!inArea(x, y))
			throw new IllegalArgumentException("index is wrong");
		return numbers[x][y];
	}

	public boolean inArea(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}

	// 點擊一個坐標，打開一片區域，flood_fill算法。
	//滴入一滴墨水向周围扩散遇到阻碍停止
	public void open(int x, int y) {
		if (!inArea(x, y))
			throw new IllegalArgumentException("index wrong");
		if (isMine(x, y))
			throw new IllegalArgumentException("mine can not open");
		//首先打开传入的点
		open[x][y] = true;
		//如果周边有雷，停止扩散
		if (numbers[x][y] > 0)
			return;
		for (int i = x - 1; i <= x + 1; i++)
			for (int j = y - 1; j <= y + 1; j++)
				//遍历周边八个点，如果有效并且未打开并且非雷区就打开
				if (inArea(i, j) && !open[i][j] && !mines[i][j])
					open(i, j);//继续遍历
	}

}