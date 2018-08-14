package simulate;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		Sim simulate = new Sim();
		jf.add(simulate);
		jf.setSize(1800, 1200);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		Calculate processor = new Calculate(simulate);
		processor.run();
	}
	
}
