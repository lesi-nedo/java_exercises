/**
 * 
 */
package eser7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author nedo1993
 *
 */
public class ServerTask implements Runnable {
	private DatagramSocket sock;
	private DatagramPacket received;
	private byte[] resp;
	private DatagramPacket resp_packet;
	public ServerTask(DatagramSocket sock, DatagramPacket received){
		this.sock=sock;
		this.received=received;
	}
	public void run() {
		try {
			if(new String(received.getData(),0, received.getLength(), "US-ASCII").equals("PING")) {
				System.out.println(resp);
				this.resp="PONG".getBytes("US-ASCII");
				this.resp_packet=new DatagramPacket(this.resp, resp.length, received.getAddress(), received.getPort());
				this.sock.send(resp_packet);
			}
		} catch (IOException | RuntimeException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
