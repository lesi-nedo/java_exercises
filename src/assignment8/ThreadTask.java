/**
 * 
 */
package assignment8;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nedo1993
 *
 */
public class ThreadTask implements Runnable {
	private ConteCorrente acc;
	private Map<String, AtomicInteger> num_mov;
	private AtomicInteger val;
	public ThreadTask(ConteCorrente acc, Map<String, AtomicInteger> num_mov) {
		this.acc=acc;
		this.num_mov=num_mov;
	}
	public void run() {
		for(var entry : this.acc.getMovimenti().entrySet()) {
			this.val=num_mov.get(entry.getValue());
			this.val.incrementAndGet();
		}
	}
}
