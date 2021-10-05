/**
 * 
 */
package eser3;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nedo1993
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Counter count2= new Counter();
		ReentrantLockCounter count2 = new ReentrantLockCounter();
//		ReentRWLockCounter count2=new ReentRWLockCounter();
//		ThreadPoolExecutor pool=(ThreadPoolExecutor)Executors.newCachedThreadPool();
//		ThreadPoolExecutor pool=(ThreadPoolExecutor)Executors.newFixedThreadPool(1);
//		ThreadPoolExecutor pool=(ThreadPoolExecutor)Executors.newFixedThreadPool(2);
		ThreadPoolExecutor pool=(ThreadPoolExecutor)Executors.newFixedThreadPool(4);

		final int num_cons = 20;
		final long start=System.currentTimeMillis();
		for(int i=0; i<num_cons;i++) {
			pool.execute(new Writer<ReentrantLockCounter>(count2));
		}
		for(int i=0; i<num_cons;i++) {
			pool.execute(new Reader<ReentrantLockCounter>(count2));
		}
		pool.shutdown();
		try {
			while(!pool.awaitTermination(10L, TimeUnit.MILLISECONDS));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Diff: %d\n", System.currentTimeMillis()-start);
	}

}
