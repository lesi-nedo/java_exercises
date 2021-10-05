/**
 * 
 */
package eser3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nedo1993
 *
 */
public class ReentrantLockCounter extends ReentrantLock implements CounterInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int counter = 0;
	private int curr= 0;
	public ReentrantLockCounter() {
		super();
	}
	public void increment() {
		super.lock();
		counter++;
		super.unlock();
	}
	public int get() {
		super.lock();
		curr=counter;
		super.unlock();
		return curr;
	}
}
