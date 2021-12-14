/**
 * 
 */
package eser5;

import java.io.FileNotFoundException;

/**
 * @author nedo1993
 *
 */
public class MainEser5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Piece p = new Piece("/home/nedo1993", "Dam.txt", "suka.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
