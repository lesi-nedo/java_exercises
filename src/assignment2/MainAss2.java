/**
 * 
 */
package assignment2;
import java.util.Scanner;
/**
 * @author nedo1993
 *
 */
public class MainAss2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Ufficio uff;
		int num_clients=0;
		int capa_room=0; //capicita della seconda stanza;
		Scanner scan = new Scanner(System.in);
		System.out.print("Number of Clients: ");
		try {
			num_clients = scan.nextInt();
			System.out.print("Capacity of the second room: ");
			capa_room=scan.nextInt();
		} catch(Exception e) {
			System.out.print("\nWrong Input, bye.\n");
			return;
		} finally {
			scan.close();
		}
		try {
			uff=new Ufficio(capa_room, num_clients);
			uff.exec();
		} catch(IllegalArgumentException e) {
			System.out.print("\nWrong Input, bye.\n");
			return;
		} finally {
			scan.close();
		}
	}

}
