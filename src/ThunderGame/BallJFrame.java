package ThunderGame;

import javax.swing.JFrame;

public class BallJFrame extends JFrame {
	BallJPanel panel;//int i;
	public BallJFrame(){
		panel=new BallJPanel();
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    public void showMe(){
    	this.setSize(400, 600);
    	this.setTitle("雷电大战");
    	
    	this.setVisible(true);
    	panel.st();
    }
	public static void main(String[] args) {
		BallJFrame frame=new BallJFrame();
		frame.showMe();
	}
}
