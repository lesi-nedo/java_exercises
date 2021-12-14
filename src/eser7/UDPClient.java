/**
 * 
 */
package eser7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author nedo1993
 *
 */
public class UDPClient {
	public static final int PORT=13;
	public static final int timeout=15000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (DatagramSocket sock=new DatagramSocket(0)){
			InetAddress host=InetAddress.getByName("localhost");
			byte[] buf=new byte[1024];
			byte[] req="PING".getBytes();
			DatagramPacket request=new DatagramPacket(req, req.length, host, PORT);
			DatagramPacket response=new DatagramPacket(buf, 1024);
			sock.send(request);
			sock.setSoTimeout(timeout);
			sock.receive(response);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
