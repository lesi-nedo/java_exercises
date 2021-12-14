/**
 * 
 */
package assignment5;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author nedo1993
 *
 */
public class Produttore implements Runnable{
	private LinkedList<Path> queue;
	private Lock lock_queue;
	private Path path;
	private AtomicInteger curr_num_elemnts;
	private Condition queue_cond;
	public Produttore(LinkedList<Path> queue, Lock lock_queue, Path path, AtomicInteger curr_num_elemnts, Condition queue_cond) {
		this.lock_queue=lock_queue;
		this.queue=queue;
		this.path=path;
		this.curr_num_elemnts=curr_num_elemnts;
		this.queue_cond=queue_cond;
	}
	private void helper(Path path_rec, DirectoryStream<Path> dirs) throws InterruptedException, IOException {
		lock_queue.lock();
		try {
			while(this.curr_num_elemnts.get()>=Integer.MAX_VALUE) {
				queue_cond.await();
			}
			queue.offer(path_rec);
			if(this.curr_num_elemnts.get()==0)
				queue_cond.signalAll();
			this.curr_num_elemnts.incrementAndGet();
		} finally {
			lock_queue.unlock();
		}
		dirs=Files.newDirectoryStream(path_rec);
		for(Path file: dirs) {
			if(file.toFile().isDirectory()) {
				helper(file, null);
			}
		}
		
	}
	public void run() {
		try {
			this.helper(this.path, null);
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
