/**
 * 
 */
package assignment1;

/**
 * @author nedo1993
 *
 */
public class MainClass implements Runnable {
	private double accuracy;
	//Il tempo massimo e' in secondi
	final long timeout;
	/**
	 * @param args
	 */
	private MainClass(double accuracy, final long timeout) {
		this.accuracy=accuracy;
		this.timeout=timeout;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length!=2) {
			System.out.print("First parameter accuracy grade, second timeout");
		}
		MainClass cl;
		try {
			cl = new MainClass(Double.parseDouble(args[0]), Math.abs(Long.parseLong(args[1])));
		} catch(NumberFormatException e) {
			System.out.print("Wrong type.\n");
			return;
		}
		Thread newTh = new Thread(cl);
		newTh.start();
		try {
			newTh.join(cl.timeout*1000);
			newTh.interrupt();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run() {
		long toDiv=3;
		double pi=4;
		while(true) {
			pi= (toDiv-1)%4==0?pi+(((double) 4)/toDiv): pi-(((double) 4)/toDiv);
			if(Thread.currentThread().isInterrupted() || this.accuracy > Math.abs(pi-Math.PI)) { 
				System.out.print("PI: " + pi + "\n");
				return;
			}
			toDiv+=2;
		}
	}
}

