/**
 * 
 */
package eser1;
import java.util.Calendar;
/**
 * @author nedo1993
 *
 */
public class DatePrinter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Calendar rightNow = Calendar.getInstance(Locale.ITALY);
		while(true) {
			System.out.println(Calendar.getInstance().getTime());
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}