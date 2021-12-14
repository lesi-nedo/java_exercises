/**
 * 
 */
package assignment7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author nedo1993
 *
 */
public class PINGERServer {
	public static final int TERMINATION=60000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadPoolExecutor executor=(ThreadPoolExecutor)Executors.newCachedThreadPool();
		int received_ping=0;
		int received_refused=0;
		// TODO Auto-generated method stub
		if(args.length==1) {
			Integer port=Integer.parseInt(args[0]);
			try(DatagramSocket serverSocket=new DatagramSocket(port);) {
			serverSocket.setSoTimeout(TERMINATION);
			byte[] buffer=new byte[22];
			int length=22;
			DatagramPacket receivedPacket = new DatagramPacket(buffer, length);
			while(true) {
				serverSocket.receive(receivedPacket);
				received_ping++;
				if(((int)(received_ping*0.25))>received_refused) {
					received_refused++;
					executor.execute(new ServerTask(serverSocket, receivedPacket.getData(), receivedPacket.getLength(), receivedPacket.getAddress(), receivedPacket.getPort(), false));
				} else {
					executor.execute(new ServerTask(serverSocket, receivedPacket.getData(), receivedPacket.getLength(), receivedPacket.getAddress(), receivedPacket.getPort(), true));
				}
			}
			} catch(SocketTimeoutException e) {
				System.out.print("Timeout has expired bye.\n");
				return;
			} catch (NumberFormatException ex) {
				System.out.print("ERR -arg 1\n");
				return;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} else if(args.length > 1) {
			System.out.print("ERR -arg 1\n");
			return;
		} else {
			System.out.println("Usage: java PingServer port");
		}
	}

}
