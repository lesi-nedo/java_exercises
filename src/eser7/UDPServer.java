/**
 * 
 */
package eser7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nedo1993
 *
 */
public class UDPServer {
	public static final int terminationDelay = 60000;
	public static final int PORT = 13;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		// TODO Auto-generated method stub
		try(DatagramSocket sock = new DatagramSocket(PORT)){
			sock.setSoTimeout(terminationDelay);
			while(!sock.isClosed()) {
				DatagramPacket req=new DatagramPacket(new byte[1024], 1024);
				sock.receive(req);
				executor.execute(new ServerTask(sock, req));
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.shutdown();
		try {
			if(!executor.awaitTermination(terminationDelay, TimeUnit.MILLISECONDS))
				executor.shutdownNow();
		}catch (InterruptedException e) 
		{
			executor.shutdownNow();
		}
		
	}

}
