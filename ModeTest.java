package simulate;

import java.io.*;
import java.util.Date;

/**
 * 测试用，探究测量模式间的区别和误差
 * @author whr
 *
 */
public class ModeTest implements Runnable {

	volatile private Sim sim;
	
	public ModeTest(Sim sim) {
		this.sim = sim;
	}
	
	@Override
	public void run(){
		try {
			long time = new Date().getTime();			
			File frate = new File("output/"+time+"/rate.txt");
			File fpulse = new File("output/"+time+"/pulseout.txt");
			File fcampbell = new File("output/"+time+"/campbellout.txt");
			File fcurrent = new File("output/"+time+"/currentout.txt");
			File fvariance = new File("output/"+time+"/varianceout.txt");
			frate.getParentFile().mkdirs();
			fpulse.getParentFile().mkdirs();
			fcampbell.getParentFile().mkdirs();
			fcurrent.getParentFile().mkdirs();
			fvariance.getParentFile().mkdirs();
			frate.createNewFile();
			fpulse.createNewFile();
			fcampbell.createNewFile();
			fcurrent.createNewFile();
			fvariance.createNewFile();
			PrintWriter rate = new PrintWriter(frate);
			PrintWriter pulseout = new PrintWriter(fpulse);
			PrintWriter campbellout = new PrintWriter(fcampbell);
			PrintWriter currentout = new PrintWriter(fcurrent);
			PrintWriter varianceout = new PrintWriter(fvariance);
			int testRate = 100;
			int incr = 100;
			
			while(testRate<=10000000) {
				//设置仿真计数率
				sim.JTFrate.setText(String.valueOf(testRate));
				
				sim.updateSimTime(0);
				rate.flush();
				pulseout.flush();
				campbellout.flush();
				currentout.flush();
				varianceout.flush();
				while(sim.getSimTime()!=0);
				
				//开始仿真
				sim.setState(Sim.PREPARE);
				while(sim.getSimTime()<1500) ;
				sim.setState(Sim.IDLE);
				
				//获取仿真计数数据
				//写入到文档
				rate.println(testRate);
				pulseout.println(testRate+"\t"+sim.getPulseRate()+"\t"+sim.getPulseerr());				
				campbellout.println(testRate+"\t"+sim.getCampbellRate()+"\t"+sim.getCampbellerr());
				currentout.println(testRate+"\t"+sim.getCurrentRate()+"\t"+sim.getCurrenterr());
				varianceout.println(testRate+"\t"+sim.getVariance());
			
				
				//改变计数率
				testRate += incr;
				if(testRate/incr>=10) incr *= 10;
			}
			
			rate.close();
			pulseout.close();
			campbellout.close();
			currentout.close();
			varianceout.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("end?");
		}
		
		sim.JTFminT.setEditable(true);
		sim.JTFrate.setEditable(true);
		sim.JTFsignalcurrent.setEditable(true);
		sim.JTFsignalwidth.setEditable(true);
		sim.JTFinputRes.setEditable(true);
		sim.JTFinputCap.setEditable(true);
		
		sim.JBstart.setEnabled(true);
		sim.JBterminate.setEnabled(true);
		sim.JBsave.setEnabled(true);
		sim.JBmodetest.setEnabled(true);
		
		sim.Init();
		
		System.out.println("Test Finished!!!");
		
	}

}
