/**
 * 
 */
package eser1;

import java.util.Calendar;

/**
 * @author nedo1993
 *
 */
public class DatePrinterRunnable implements Runnable{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatePrinterRunnable newTh = new DatePrinterRunnable();
		Thread startTh = new Thread(newTh);
		startTh.start();
		System.out.println(Thread.currentThread().getName());

	}
	public void run() {
		int now = 0;
		while(true) {
			System.out.println(Calendar.getInstance().getTime());
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(now>5) break;
			now++;
		}
	}

}
