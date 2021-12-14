/**
 * 
 */
package assignment7;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * @author nedo1993
 *
 */
public class PINGClient {
	public static final int TIMEOUT=1000;
	public static final int NUM_REQ=10;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length==2) {
			String host_name=args[0];
			int lost=0;
			Long RTT=0L;
			int received=0;
			int sended=0;
			Long min_trip=0L;
			Long max=0L;
			long[] all_time_trip=new long[10];
			int i=0;
			Long time1=0L;
			Long time2=0L;
			float avg=0;
			try(DatagramSocket clientSocket=new DatagramSocket()) {
				clientSocket.setSoTimeout(TIMEOUT);
				InetAddress address=InetAddress.getByName(host_name);
				int port_num=Integer.parseInt(args[1]);
				while(sended < NUM_REQ) {
					String data="PING " + String.valueOf(sended)+" "+Long.toString(System.currentTimeMillis())+" ";
					byte[] buffer=data.getBytes("US-ASCII");
					DatagramPacket pack=new DatagramPacket(buffer, buffer.length, address, port_num);
					time1=System.nanoTime();
					clientSocket.send(pack);
					try {
						clientSocket.receive(pack);
						time2=System.nanoTime();
						RTT=(time2-time1)/1000000;
						if(min_trip==0) min_trip=RTT;
						if(RTT<min_trip) min_trip=RTT;
						if(RTT>max) max=RTT;
						all_time_trip[i]=RTT;
						i++;
						System.out.printf("%s RTT: %d\n", data, RTT);
						received++;
					}catch(SocketTimeoutException e) {
						System.out.printf("%s RTT: *\n", data);
						lost++;
					}
					sended++;
				}
			} catch(NumberFormatException e) {
				System.out.println("ERR - arg 2");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int j=0; j<i; j++) {
				avg+=all_time_trip[j];
			}
			avg=avg/NUM_REQ;
			System.out.println("---- PING Statistics ----");
			System.out.printf("10 packets transmitted, %d packets received, %.2f%% packet loss\n", received, (((float)lost)/NUM_REQ)*100);
			System.out.printf("round-trip (ms) min/avg/max = %d/%.2f/%d\n", min_trip, avg, max);
		} else if(args.length==0) {
			System.out.println("Usage: java PingClient hostname port");
		} else {
			System.out.println("ERR - arg 2");
		}
	}

}
