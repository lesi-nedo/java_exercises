/**
 * 
 */
package eser3;

/**
 * @author nedo1993
 *
 */
public class Reader<T extends CounterInterface> implements Runnable {
	private T count;
	public Reader(T count) {
		this.count=count;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.printf("Result: %d\n", count.get());
	}		

}
