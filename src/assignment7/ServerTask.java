/**
 * 
 */
package assignment7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author nedo1993
 *
 */
public class ServerTask implements Runnable {
	private DatagramSocket sock;//il riferimento al socket utilizzato dal server per accertare i clienti.
	private DatagramPacket received_pack; // il pacchetto ricevuto e passato ad un thread.
	private boolean send;//true se si puo inviare una risposta al cliente false altrimenti.
	int randomDel = ThreadLocalRandom.current().nextInt(100, 500 + 1);
	public ServerTask(DatagramSocket sock, byte[] data, int length, InetAddress addrs, int port, boolean send) {
		this.received_pack=new DatagramPacket(data, length, addrs, port);
		this.sock=sock;
		this.send=send;
	}
	public void run() {
		try {
			if(this.send) {
					Thread.sleep(randomDel);
					this.sock.send(this.received_pack);
					System.out.printf("%s:%d> %s ACTION: delayed %d\n", this.received_pack.getAddress().getHostAddress(), this.received_pack.getPort(), new String(this.received_pack.getData(), 0, this.received_pack.getLength(), "US-ASCII"), 
							this.randomDel);
	
				
			} else {
				System.out.printf("%s:%d> %s ACTION: not sent\n", this.received_pack.getAddress().getHostAddress(), this.received_pack.getPort(), new String(this.received_pack.getData(), 0, this.received_pack.getLength(), "US-ASCII"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
