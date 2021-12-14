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
public class Consumatore implements Runnable {
	private LinkedList<Path> queue;
	private AtomicInteger curr_num_elemnts;
	private Lock queue_lock;
	private Lock read_lock;
	private Condition queue_cond;
	private Path path;
	private DirectoryStream<Path> files;
	public Consumatore(LinkedList<Path> queue, Lock queue_lock, AtomicInteger curr_num_elemnts, Condition queue_cond, Lock read_lock) {
		this.queue=queue;
		this.curr_num_elemnts=curr_num_elemnts;
		this.queue_lock=queue_lock;
		this.read_lock=read_lock;
		this.queue_cond=queue_cond;
		this.path=null;
		this.files=null;
	}
	public void run() {
		while(true) {
			this.queue_lock.lock();
			try {
				if(Thread.currentThread().isInterrupted())
					break;
				while(this.curr_num_elemnts.get()==0)
					try {
						this.queue_cond.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.printf("Thread: %d has fineshed.--\n", Thread.currentThread().getId());
						return;
					}
			} finally {
				this.queue_lock.unlock();
			}
			try {
				this.read_lock.lock();
				this.path=this.queue.poll();
			} finally {
				this.read_lock.unlock();
			}
			assert this.path!=null;
			try {
				this.queue_lock.lock();
				if(this.curr_num_elemnts.get()>=Integer.MAX_VALUE)
					this.queue_cond.signal();
				this.curr_num_elemnts.decrementAndGet();
			} finally {
				this.queue_lock.unlock();
			}
			try {
				this.files=Files.newDirectoryStream(this.path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Path file: files) {
				if(file.toFile().isFile())
					System.out.println(file.getFileName());
			}
		}
		System.out.printf("Thread: %d has fineshed.\n", Thread.currentThread().getId());
	}
	
}
