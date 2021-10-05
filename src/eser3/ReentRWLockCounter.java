/**
 * 
 */
package eser3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author nedo1993
 *
 */
public class ReentRWLockCounter extends ReentrantReadWriteLock implements CounterInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int counter = 0;
	private int curr= 0;
	private Lock read= super.readLock();
	private Lock write=super.writeLock();
	public ReentRWLockCounter() {
		super();
	}
	public void increment() {
		write.lock();
		counter++;
		write.unlock();
	}
	public int get() {
		read.lock();
		curr=counter;
		read.unlock();
		return curr;
	}
}
