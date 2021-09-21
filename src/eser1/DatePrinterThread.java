/**
 * 
 */
package eser1;

import java.util.Calendar;

/**
 * @author nedo1993
 *
 */
public class DatePrinterThread extends Thread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatePrinterThread newTh = new DatePrinterThread();
		newTh.start();
		System.out.println(Thread.currentThread().getName());

	}
	@Override
	public void run() {
		while(true) {
			System.out.println(Calendar.getInstance().getTime());
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
