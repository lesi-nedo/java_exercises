/**
 * 
 */
package assignment8;
import java.lang.reflect.Type;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nedo1993
 *
 */
public class GestoreMain {
	public static final int terminationDelay = 60000;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Gson gson=new Gson();
		ExecutorService pool = Executors.newCachedThreadPool();
		Map<String, AtomicInteger> result=new HashMap<String, AtomicInteger>(10);
		for(String mov: StaticValues.movimenti)
			result.put(mov, new AtomicInteger(0));
		final Type CONTE_TYPE=new TypeToken<Map<Integer, ConteCorrente>>() {}.getType();
		JsonReader reader = new JsonReader(new FileReader("conti_correnti.json"));
		Map<Integer, ConteCorrente> data=gson.fromJson(reader, CONTE_TYPE);
		data.forEach((k, v) -> {
			pool.execute(new ThreadTask(v, result));
		});
		reader.close();
		pool.shutdown();
		try {
			if (!pool.awaitTermination(terminationDelay, 
			TimeUnit.MILLISECONDS)) pool.shutdownNow();
		}
		catch (InterruptedException e) {pool.shutdownNow();}
		System.out.println("Server terminato!");
		System.out.println(result);
	}

}
