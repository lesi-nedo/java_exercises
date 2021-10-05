/**
 * 
 */
package eser2;
import java.util.concurrent.*;


/**
 * @author nedo1993
 *
 */
public class SalaStazione implements Runnable {
		private int currTask=0;
		public SalaStazione(int currTask) {
			this.currTask=currTask;
		}
		@Override public void run() {
			System.out.printf("Viaggiatore %d: sto acquistando un biglietto.\n", currTask);
			try {
				Thread.sleep((long)(Math.random()*1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.printf("Viaggiatore %d: ho acquistato il biglietto.\n", currTask);
		}
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		final int totalTask = 50;
		ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10);
		ExecutorService pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS, queue);
		for(int i=0;i<totalTask; i++) {
			try {
				pool.execute(new SalaStazione(i+1));
			} catch(RejectedExecutionException e) {
				System.out.printf("Traveler no. %d: sala esaurita.\n", i+1);
			}
			Thread.sleep(50);
			if(i+1==totalTask) {
				pool.shutdown();
				while(!pool.awaitTermination(10L, TimeUnit.SECONDS));
				System.out.print("Terminated.\n");
			}
		}
	}
}
