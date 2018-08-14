package simulate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

public class Drawing extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5048613664967376898L;

//	private int stepT;
//	private int rate;
//	private int signalcurrent;
//	private int signalwidth;
//	private int inputRes;
//	private int inputCap;
	
	private int originX;
	private int originY;
	private int boundaryX;
	private int boundaryY;
	
	private int[] dataAxis;
	private double Yrate = 10;
	
	public Drawing(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		this.originX = 50;
		this.originY = height-50;
		this.boundaryX = width-100;
		this.boundaryY = height-100;
		this.setBorder(new TitledBorder("示波器"));
		this.setBackground(Color.white);
		
		dataAxis = new int[boundaryX];
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX()-originX;
				if(x>=0&&x<boundaryX) {
					String s = String.valueOf(dataAxis[x]/Yrate);
					setToolTipText(s);
				}
			}
		});
		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()>0) Yrate *= 2;
				else Yrate /= 2;
				repaint();
			}
			
		});
	}
	
	//计算数据再坐标上的像素位置，返回数据占据的像素宽度
	public int setPoint(SignalData<Double> data) {		
		if(data.size()==0) return 0;
		int Xrate = data.capacity/boundaryX;
		for(int i=0;i<boundaryX;i++) {
			int x = (int) (i*Xrate);
			if(x>=data.size()) return i;
			
			//有待利用迭代器优化
			this.dataAxis[i] = (int) (data.get(x)*Yrate);
		}
		return boundaryX;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		//画背景
		g.setColor(Color.white);
		g.fillRect(originX, originY-boundaryY, boundaryX, boundaryY);
		//画数据曲线
		g.setColor(Color.RED);
		for(int i=1;i<boundaryX;i++) {
			g.drawLine(originX+i-1, originY-dataAxis[i-1], originX+i, originY-dataAxis[i]);
		}
		//画坐标轴
		g.setColor(Color.black);
		g.drawLine(originX, originY, originX+boundaryX, originY);
		g.drawLine(originX, originY, originX, originY-boundaryY);
		//画箭头
		g.drawLine(originX+boundaryX, originY, originX+boundaryX-10, originY+10);
		g.drawLine(originX+boundaryX, originY, originX+boundaryX-10, originY-10);
		g.drawLine(originX, originY-boundaryY, originX-10, originY-boundaryY+10);
		g.drawLine(originX, originY-boundaryY, originX+10, originY-boundaryY+10);
	}
	
	public void repaint(SignalData<Double> data) {
		setPoint(data);
		repaint();
	}
	
	
	
}


