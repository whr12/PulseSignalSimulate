package simulate;

import java.util.Random;

/**
 * 计算例程，完成程序所有的计算工作
 * @author whr
 *
 */
public class Calculate implements Runnable {

	volatile private Sim simulate;
		
	private int stepT;
	private double rate;
	private double signalcurrent;
	private int    signalwidth;
	private double inputRes;
	private double inputCap;
	
	private long simTimens;
	private long simTime;
	
	//计算参量
	private double V;
	private boolean hasSignal;
	private int widthTime;
	private double lamda;
	private Random rand;
	
	private SignalData<Double> data;
	
	//计数率计算参量
	private boolean detectPulse;
	private double cntRate;
	private double rm;      //均方值
	private double mean;    //电压平均值
	private double variance;//方差
	
	//误差参数，方差
	private double pulseerr;
	private double campbellerr;
	private double currenterr;
	
	//坎贝尔模式转换系数
	private double campa = 7041.9;
	private double campb = -517.71;
	
	//电流模式转换系数
	private double cura = 18800;
	private double curb = -15088;
	
	
	public Calculate(Sim s) {
		this.simulate = s;
		data = new SignalData<Double>(1024);
		rand = new Random();
		Init();
		
	}
	
	public void Init() {
		
		//输入参数
		stepT = Integer.valueOf(simulate.JTFminT.getText());						//ns
		rate  = Double.valueOf(simulate.JTFrate.getText());							//s-1
		signalcurrent = Double.valueOf(simulate.JTFsignalcurrent.getText());		//μA
		signalwidth = Integer.valueOf(simulate.JTFsignalwidth.getText());			//ns
		inputRes = Double.valueOf(simulate.JTFinputRes.getText());					//MΩ
		inputCap = Double.valueOf(simulate.JTFinputCap.getText());					//pC
		
		//计算量
		V = 0;
		rand.setSeed(System.currentTimeMillis());
		lamda = Lamda();
		hasSignal = false;
		widthTime = 0;
		
		//计时器
		simTimens = 0;
		simTime = 0;
		simulate.updateSimTime(0);
		
		data.clear();
		
		//计数率计算
		detectPulse = false;
		cntRate = 0;
		rm = 0;
		mean = 0;
		variance = 0;
		
		//误差计算
		pulseerr = 0;
		campbellerr = 0;
		currenterr = 0;
		
	}
	
	private double Lamda() {
		return rate*stepT/1000000000;
	}
	
	private int hasSignal() {
		if(hasSignal) {
			if(widthTime<signalwidth) {
				widthTime += stepT;
			}else {
				widthTime = 0;
				hasSignal = false;
			}
			return 1;
		}else {
			if(rand.nextDouble()<lamda) {
				hasSignal = true;
				return 1;
			}else
				return 0;
		}
	}
	
	public SignalData<Double> getData() {
		return data.copy();
	}
	
	public void reset() {
		Init();
	}
	
	@Override
	public void run() {
		//开始前的一些工作
		System.out.println("Thread start!!!");
		//初始化上次仿真数据
		data.clear();
		// 1个循环任务经过一个stepT
		while(true) {
//			System.out.println(System.currentTimeMillis());
			switch(simulate.getState()) {
				case Sim.IDLE: {
					break;
				}
				case Sim.PREPARE: {
					Init();
					simulate.setRun();
					break;
				}
				case Sim.RUN: {
					V = V+stepT/inputCap*(5*signalcurrent*hasSignal()-V/inputRes)/1000;
					simTimens++;
					if(simTimens%50==0) data.add(V);		//每50ns记录一次数据
					if(simTimens==1000000) {
						simTimens = 0;
						simTime++;
						simulate.updateSimTime(simTime);
						simulate.repaint(data);
						double pulsecnt = cntRate*1000000000/stepT;
						simulate.updatePulseRate(pulsecnt);
						double camp = campa*variance+campb;
						simulate.updateCampbellRate(camp);
//						simulate.updateCampbellRate(rm);
						double curr = mean*cura+curb;
						curr = curr/(1-curr*signalwidth/1000000000);
						simulate.updateCurrentRate(curr);
//						simulate.updateCurrentRate(mean);
						simulate.updateVariance(variance);
						
						//计算误差,用方差表示
						pulseerr = pulseerr*0.99+(pulsecnt-rate)*(pulsecnt-rate)*0.01;
						simulate.updatePulseerr(Math.sqrt(pulseerr));
						campbellerr = campbellerr*0.99+(camp-rate)*(camp-rate)*0.01;
						simulate.updateCampbellerr(Math.sqrt(campbellerr));
						currenterr = currenterr*0.99+(curr-rate)*(curr-rate)*0.01;
						simulate.updateCurrenterr(Math.sqrt(currenterr));
					}
					
					//计数率计算
					//脉冲模式
					cntRate = cntRate*0.99999999;
					if(!detectPulse&&V>4) {
						cntRate += 0.00000001;
						detectPulse = true;
					}
					if(V<3) detectPulse = false;
					//坎贝尔模式
					rm = rm*0.99999999+V*V*0.00000001;
					//电流模式
					mean = mean*0.99999999+V*0.00000001;
					//方差
					variance = variance*0.99999999+(V-mean)*(V-mean)*0.00000001;
					
					
					break;
				}
				case Sim.PAUSE: {
					break;
				}
				default: System.err.println("Wrong state!!!"); //...
			}
			
		}
	}	
	
}

