/**
 * 
 */
package eser6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author nedo1993
 *
 */
public class Server {

	public final static int PORT=5959;
	public final static String FILE = "src/eser6/some_name.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ServerSocket server = new ServerSocket(PORT)){
			while(true) {
				String output;
				try(Socket connection = server.accept();
						BufferedReader reader = new BufferedReader(new FileReader(FILE));
						PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
						) {
					while((output=reader.readLine())!=null) {
						out.println(output);
					}
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

}
