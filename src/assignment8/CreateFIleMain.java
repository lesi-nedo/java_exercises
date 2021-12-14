/**
 * 
 */
package assignment8;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * @author nedo1993
 *
 */
public class CreateFIleMain {
	public static final String file_name="/conti_correnti.json";
	public static final String SOURCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static SecureRandom secureRnd = new SecureRandom();
	public static int num_clients=30;
	public static int num_mov=70;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		FileChannel out = FileChannel.open(Paths.get("conti_correnti.json"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		HashMap<Integer, ConteCorrente> all_acc=new HashMap<Integer, ConteCorrente>();
		for(int i=0; i<num_clients; i++) {
			System.out.println("Creo un cliente.");
			all_acc.put(i, new ConteCorrente(randomString(5), randomString(10)));
			ConteCorrente acc=all_acc.get(i);
			for(int j=0; j<num_mov; j++) {
				System.out.println("Aggiungo le transazione.");
				int ind=ThreadLocalRandom.current().nextInt()%StaticValues.NUM_MOV;
				acc.add_trans(System.currentTimeMillis(), StaticValues.movimenti[ind<0?ind+StaticValues.NUM_MOV:ind]);
				Thread.sleep(4);
			}
		}
		String json=gson.toJson(all_acc);
		ByteBuffer buff=ByteBuffer.allocateDirect(json.length());
		buff.put(json.getBytes());
		buff.flip();
		while(buff.hasRemaining())
			out.write(buff);
		buff.clear();
		out.close();
	}
	public static String randomString(int length) {
	    StringBuilder sb = new StringBuilder(length);
	    for (int i = 0; i < length; i++)
	      sb.append(SOURCE.charAt(secureRnd.nextInt(SOURCE.length())));
	    return sb.toString();
	  }

}
