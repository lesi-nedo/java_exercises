/**
 * 
 */
package assignment2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nedo1993
 *
 */
public class Ufficio {
	/*
	 * Overview: La classe che modella il ufficio postale con le due stanze
	 * 
	 * AF: f(max_pers, total_pers) --> Ufficio Postale
	 * 
	 * IR: total_pers,max_pers>0 && total_pers,max_pers<Integer.MAX_VALUE
	 *  */
	private final int sportelli = 4;
	private ExecutorService pool;
	private BlockingQueue<Runnable> queue;
	private boolean res;
	private Task task;
	/**
	 * @param: max_pers,total_pers
	 * @Requires: max_pers,total_pers>0 && max_pers,total_pers<Integer.MAX_VALUE
	 * @Effects: Inizializza la classe
	 * @Throws: IllegalArgumentException
	 */
	public Ufficio(int max_pers, int total_pers) throws IllegalArgumentException {
		// TODO Auto-generated constructor stub
		if(max_pers < 0 || max_pers > Integer.MAX_VALUE || total_pers < 0 || total_pers > Integer.MAX_VALUE){
			throw new IllegalArgumentException("Wrong Input.");
		}
		this.pool=new ThreadPoolExecutor(this.sportelli, this.sportelli,60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(max_pers));
		this.queue = new ArrayBlockingQueue<Runnable>(total_pers);
		for(int i=0; i<total_pers; i++) {
			this.res=queue.offer(new Task(i));
			assert this.res==true;
		}
	}
	/*
	 * @Effects: Esegue il codice che modella l'assignment 02.
	 */
	public void exec() {
		while((this.task=(Task) queue.poll()) !=null){
			try {
				this.pool.execute(this.task);
			} catch(RejectedExecutionException e) {
//				System.out.printf("Not enough space for task: %d.\n", this.task.getClient());
				this.res=queue.offer(this.task);
				assert this.res==true;
			}
		};
		pool.shutdown();
		try {
			while(!pool.awaitTermination(10L, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("All Clients have beeen served.\n");
	}
}
