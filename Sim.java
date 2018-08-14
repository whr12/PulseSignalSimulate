package simulate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.util.concurrent.locks.*;

/**
 * 主界面显示
 * @author whr
 *
 */
public class Sim extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8325240110664711179L;
	
	public final static char IDLE = 0;
	public final static char RUN  = 1;
	public final static char PAUSE= 2;
	public final static char PREPARE = 3;
	private char state;
	
	private long simTime;
	
	JTextField jtf1 = new JTextField("时间间隔(ns):");
	JTextField jtf2 = new JTextField("计数率(/s):");
	JTextField jtf3 = new JTextField("信号电流(μA):");
	JTextField jtf4 = new JTextField("信号宽度(ns):");
	JTextField jtf5 = new JTextField("输入电阻(MΩ):");
	JTextField jtf6 = new JTextField("输入电容(pC):");
	
	JTextField jtf7 = new JTextField("仿真时间(ms):");
	
	JTextField JTFminT = new JTextField("10");
	JTextField JTFrate = new JTextField("10000");
	JTextField JTFsignalcurrent = new JTextField("50");
	JTextField JTFsignalwidth = new JTextField("200");
	JTextField JTFinputRes = new JTextField("1");
	JTextField JTFinputCap = new JTextField("10");
	
	JTextField JTFsimTime = new JTextField("0");
	
	ButtonGroup BGmode = new ButtonGroup();
	JRadioButton JBpulse = new JRadioButton("电压模式",true);
	JRadioButton JBcampbell = new JRadioButton("坎贝尔模式",false);
	JRadioButton JBcurrent = new JRadioButton("电流模式",false);
	
	JPanel JPmode = new JPanel();
	
	JButton JBstart = new JButton("开始");
	JButton JBterminate = new JButton("终止");
	JButton JBsave = new JButton("保存");
	
	JPanel JPresult = new JPanel();
	JTextField jtf8 = new JTextField("电压模式:");
	JTextField jtf9 = new JTextField("坎贝尔模式:");
	JTextField jtf10 = new JTextField("电流模式:");
	JTextField jtf11 = new JTextField("方差:");
	
	JTextField JTFpulse = new JTextField("0");
	JTextField JTFcampbell = new JTextField("0");
	JTextField JTFcurrent = new JTextField("0");
	JTextField JTFvariance = new JTextField("0");
	
	JTextField JTFpulseerr = new JTextField("0");
	JTextField JTFcampbellerr = new JTextField("0");
	JTextField JTFcurrenterr = new JTextField("0");
	
	Drawing Axis ;
	
	JButton JBmodetest = new JButton("模式测试");
	
	public Sim() {
		state = IDLE;
		
		simTime = 0;
		
		/**
		 * 添加组件
		 * 
		 */
		Border noBorder = new EmptyBorder(0,0,0,0);
		
		Font StaticFont = new Font("宋体",Font.BOLD,26);
		Font EditFont = new Font("宋体",Font.PLAIN,26);
		Font ButtonFont = new Font("宋体",Font.PLAIN,20);
		
		jtf1.setEditable(false);
		jtf2.setEditable(false);
		jtf3.setEditable(false);
		jtf4.setEditable(false);
		jtf5.setEditable(false);
		jtf6.setEditable(false);
		jtf7.setEditable(false);
			
		jtf1.setFont(StaticFont);
		jtf2.setFont(StaticFont);
		jtf3.setFont(StaticFont);
		jtf4.setFont(StaticFont);
		jtf5.setFont(StaticFont);
		jtf6.setFont(StaticFont);
		jtf7.setFont(StaticFont);
		
		jtf1.setBounds(0, 0, 200, 30);
		jtf2.setBounds(0, 30, 200, 30);
		jtf3.setBounds(0, 60, 200, 30);
		jtf4.setBounds(0, 90, 200, 30);
		jtf5.setBounds(0, 120, 200, 30);
		jtf6.setBounds(0, 150, 200, 30);
		jtf7.setBounds(0, 180, 200, 30);
		
		jtf1.setBorder(noBorder);
		jtf2.setBorder(noBorder);
		jtf3.setBorder(noBorder);
		jtf4.setBorder(noBorder);
		jtf5.setBorder(noBorder);
		jtf6.setBorder(noBorder);
		jtf7.setBorder(noBorder);
		
		JTFminT.setFont(EditFont);
		JTFrate.setFont(EditFont);
		JTFsignalcurrent.setFont(EditFont);
		JTFsignalwidth.setFont(EditFont);
		JTFinputRes.setFont(EditFont);
		JTFinputCap.setFont(EditFont);
		
		JTFminT.setBackground(Color.white);
		JTFrate.setBackground(Color.white);
		JTFsignalcurrent.setBackground(Color.white);
		JTFsignalwidth.setBackground(Color.white);
		JTFinputRes.setBackground(Color.white);
		JTFinputCap.setBackground(Color.white);
		
		JTFminT.setHorizontalAlignment(JTextField.RIGHT);
		JTFrate.setHorizontalAlignment(JTextField.RIGHT);
		JTFsignalcurrent.setHorizontalAlignment(JTextField.RIGHT);
		JTFsignalwidth.setHorizontalAlignment(JTextField.RIGHT);
		JTFinputRes.setHorizontalAlignment(JTextField.RIGHT);
		JTFinputCap.setHorizontalAlignment(JTextField.RIGHT);
		
		JTFminT.setBounds(250, 0, 200, 30);
		JTFrate.setBounds(250, 30, 200, 30);
		JTFsignalcurrent.setBounds(250, 60, 200, 30);
		JTFsignalwidth.setBounds(250, 90, 200, 30);
		JTFinputRes.setBounds(250, 120, 200, 30);
		JTFinputCap.setBounds(250, 150, 200, 30);
		
		JTFsimTime.setFont(EditFont);
		JTFsimTime.setBounds(250, 180, 200, 30);
		JTFsimTime.setEditable(false);
		JTFsimTime.setBackground(Color.white);
		JTFsimTime.setHorizontalAlignment(JTextField.RIGHT);
		
		
		BGmode.add(JBpulse);
		BGmode.add(JBcampbell);
		BGmode.add(JBcurrent);
		
		JBpulse.setFont(ButtonFont);
		JBcampbell.setFont(ButtonFont);
		JBcurrent.setFont(ButtonFont);
		
		JPmode.add(JBpulse);
		JPmode.add(JBcampbell);
		JPmode.add(JBcurrent);
		
		TitledBorder TBmode = new TitledBorder("测量模式:");
		TBmode.setTitleFont(new Font("宋体",Font.BOLD,26));
		JPmode.setBorder(TBmode);
		JPmode.setBounds(50,240,200,150);
		JPmode.setLayout(new GridLayout(3,1));
		
		JBstart.setFont(ButtonFont);
		JBterminate.setFont(ButtonFont);
		JBsave.setFont(ButtonFont);
		
		JBstart.setBounds(50, 420, 100, 50);
		JBterminate.setBounds(200, 420, 100, 50);
		JBsave.setBounds(200,510,100,50);
		
		Axis = new Drawing(600,10,1000,900);
		add(Axis);
		
		TitledBorder TBresult = new TitledBorder("测量结果:");
		TBresult.setTitleFont(new Font("宋体",Font.BOLD,26));
		JPresult.setBorder(TBresult);
		JPresult.setBounds(50, 600, 540, 200);
		JPresult.setLayout(new GridLayout(4,3));
		JPresult.add(jtf8);
		JPresult.add(JTFpulse);
		JPresult.add(JTFpulseerr);
		JPresult.add(jtf9);
		JPresult.add(JTFcampbell);
		JPresult.add(JTFcampbellerr);
		JPresult.add(jtf10);
		JPresult.add(JTFcurrent);
		JPresult.add(JTFcurrenterr);
		JPresult.add(jtf11);
		JPresult.add(JTFvariance);
		
		jtf8.setFont(EditFont);
		jtf9.setFont(EditFont);
		jtf10.setFont(EditFont);
		jtf11.setFont(EditFont);
		jtf8.setEditable(false);
		jtf9.setEditable(false);
		jtf10.setEditable(false);
		jtf11.setEditable(false);
		jtf8.setBorder(noBorder);
		jtf9.setBorder(noBorder);
		jtf10.setBorder(noBorder);
		jtf11.setBorder(noBorder);
		
		JTFpulse.setFont(EditFont);
		JTFcampbell.setFont(EditFont);
		JTFcurrent.setFont(EditFont);
		JTFvariance.setFont(EditFont);
		JTFpulse.setBackground(Color.white);
		JTFcampbell.setBackground(Color.white);
		JTFcurrent.setBackground(Color.white);
		JTFvariance.setBackground(Color.white);
		JTFpulse.setEditable(false);
		JTFcampbell.setEditable(false);
		JTFcurrent.setEditable(false);
		JTFvariance.setEditable(false);
		JTFpulse.setHorizontalAlignment(JTextField.RIGHT);
		JTFcampbell.setHorizontalAlignment(JTextField.RIGHT);
		JTFcurrent.setHorizontalAlignment(JTextField.RIGHT);
		JTFvariance.setHorizontalAlignment(JTextField.RIGHT);
		
		JTFpulseerr.setFont(EditFont);
		JTFcampbellerr.setFont(EditFont);
		JTFcurrenterr.setFont(EditFont);
		JTFpulseerr.setBackground(Color.white);
		JTFcampbellerr.setBackground(Color.white);
		JTFcurrenterr.setBackground(Color.white);
		JTFpulseerr.setEditable(false);
		JTFcampbellerr.setEditable(false);
		JTFcurrenterr.setEditable(false);
		JTFpulseerr.setHorizontalAlignment(JTextField.RIGHT);
		JTFcampbellerr.setHorizontalAlignment(JTextField.RIGHT);
		JTFcurrenterr.setHorizontalAlignment(JTextField.RIGHT);
		
		JBmodetest.setFont(ButtonFont);
		JBmodetest.setBounds(50, 900, 150, 50);
		
		//添加事件响应
		/**
		 * 开始按键响应，按下开始按键，将根据当前设置数据开始仿真
		 */
		JBstart.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				switch(state) {
				case IDLE:{
					state = PREPARE;
					JBstart.setText("暂停");
					break;
				}
				case RUN:{
					state = PAUSE;
					JBstart.setText("继续");
					break;
				}
				case PAUSE:
					state = RUN;
					JBstart.setText("暂停");
					break;
				}
				
				JTFminT.setEditable(false);
				JTFrate.setEditable(false);
				JTFsignalcurrent.setEditable(false);
				JTFsignalwidth.setEditable(false);
				JTFinputRes.setEditable(false);
				JTFinputCap.setEditable(false);
			}
			
		});
		
		/**
		 * 终止按键事件响应事件，终止仿真线程，允许改变参数值
		 */
		JBterminate.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				JBstart.setText("开始");
				state = IDLE;
				JTFminT.setEditable(true);
				JTFrate.setEditable(true);
				JTFsignalcurrent.setEditable(true);
				JTFsignalwidth.setEditable(true);
				JTFinputRes.setEditable(true);
				JTFinputCap.setEditable(true);
			}
			
		});
		
		/**
		 * 编辑框事件响应
		 */
		JTFrate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(JTFrate.getText());
			}
			
		});
		
		JBmodetest.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				JTFminT.setEditable(false);
				JTFrate.setEditable(false);
				JTFsignalcurrent.setEditable(false);
				JTFsignalwidth.setEditable(false);
				JTFinputRes.setEditable(false);
				JTFinputCap.setEditable(false);
				
				JBstart.setEnabled(false);
				JBterminate.setEnabled(false);
				JBsave.setEnabled(false);
				JBmodetest.setEnabled(false);

				Thread thread = new Thread(new ModeTest(Sim.this));
				thread.start();
				
			}
			
		});
		
		add(jtf1);
		add(jtf2);
		add(jtf3);
		add(jtf4);
		add(jtf5);
		add(jtf6);
		add(jtf7);
		add(JTFminT);
		add(JTFrate);
		add(JTFsignalcurrent);
		add(JTFsignalwidth);
		add(JTFinputRes);
		add(JTFinputCap);
		add(JTFsimTime);
		add(JPmode);
		add(JBstart);
		add(JBterminate);
		add(JBsave);
		add(JPresult);
		add(JBmodetest);
		/*******************************************************************/
		
		/**
		 * Test Code
		 */
		
		/******************************************************************/
		Init();
	}
	
	//初始化工作
	public void Init() {
		state = IDLE;
	}
	
	//更新显示
	public void updateSimTime() {
		JTFsimTime.setText(String.valueOf(simTime));
	}
	public void updateSimTime(int time) {
		this.simTime = time;
		JTFsimTime.setText(String.valueOf(time));
	}
	public void updateSimTime(long time) {
		this.simTime = time;
		JTFsimTime.setText(String.valueOf(time));		
	}
	
	public void updatePulseRate(double rate) {
		JTFpulse.setText(String.format("%.4f",rate));
	}
	
	public void updateCampbellRate(double rate) {
		JTFcampbell.setText(String.format("%.4f",rate));
	}
	
	public void updateCurrentRate(double rate) {
		JTFcurrent.setText(String.format("%.4f",rate));
	}
	
	public void updateVariance(double variance) {
		JTFvariance.setText(String.format("%.4f", variance));
	}
	
	public String getPulseRate() {
		return JTFpulse.getText();
	}
	
	public String getCampbellRate() {
		return JTFcampbell.getText();
	}
	
	public String getCurrentRate() {
		return JTFcurrent.getText();
	}
	
	public String getVariance() {
		return JTFvariance.getText();
	}
	
	public void updatePulseerr(double err) {
		JTFpulseerr.setText(String.format("%.4f", err));
	}
	
	public String getPulseerr() {
		return JTFpulseerr.getText();
	}
	
	public void updateCampbellerr(double err) {
		JTFcampbellerr.setText(String.format("%.4f",err));
	}
	
	public String getCampbellerr() {
		return JTFcampbellerr.getText();
	}
	
	public void updateCurrenterr(double err) {
		JTFcurrenterr.setText(String.format("%.4f", err));
	}
	
	public String getCurrenterr() {
		return JTFcurrenterr.getText();
	}
	
	public char getState() {
		return state;
	}
	
	public void setRun() {
		state = RUN;
	}
	
	public void setState(char SimState) {
		state = SimState;
	}
	
	public void setSimTime(long time) {
		this.simTime = time;
	}
	
	public long getSimTime() {
		return this.simTime;
	}
	
	public void repaint(SignalData<Double> data) {
		this.Axis.repaint(data);
	}
	
	
	
	

	
}
