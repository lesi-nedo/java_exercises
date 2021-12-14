/**
 * 
 */
package assignment5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nedo1993
 *
 */
public class MainAss5 {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String path;
		LinkedList<Path> queue=new LinkedList<Path>();
		AtomicInteger curr_num_elemnts=new AtomicInteger(0);
		Lock lock_queue=new ReentrantLock();
		Lock lock_readers=new ReentrantLock();
		Condition queue_cond=lock_queue.newCondition();
		Thread[] consumatori;
		int k=0;
		Path path_arg;
		BufferedReader ob = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Inserire il nome della Directory.\n");
		path=ob.readLine();
		path_arg=Paths.get(path);
		System.out.print("Inserire il numero dei consumatori.\n");
		k=Integer.parseInt(ob.readLine());
		consumatori= new Thread[k];
		if(!Files.isDirectory(path_arg)) {
			Files.createDirectory(path_arg);
		}
		System.setProperty("user.dir", path_arg.toAbsolutePath().toString());
		Produttore prod=new Produttore(queue, lock_queue, path_arg.toAbsolutePath(), curr_num_elemnts, queue_cond);
		Thread prod_th=new Thread(prod);
		prod_th.start();
		System.out.println(curr_num_elemnts);
		for(int i=0; i<k; i++) {
			Consumatore cons= new Consumatore(queue, lock_queue, curr_num_elemnts, queue_cond, lock_readers);
			consumatori[i]=new Thread(cons);
			consumatori[i].start();
		}
		prod_th.join();
		for(int i=0; i<k; i++) {
			consumatori[i].interrupt();
		}
		for(int i=0; i<k; i++) {
			consumatori[i].join();
		}
		System.out.print("FINISH\n");

	}

}
