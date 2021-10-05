/**
 * 
 */
package assignment2;

/**
 * @author nedo1993
 *
 */
public class Task implements Runnable {
	private int num_cliente=0;
	long dur=(long)(Math.random()*1000);
	/**
	 * 
	 */
	public Task(int num_cliente) {
		// TODO Auto-generated constructor stub
		this.num_cliente=num_cliente+1;
	}
	@Override public void run() {
		System.out.printf("Il cliente: %d sta per essere servito da thread: %d\n", this.num_cliente, Thread.currentThread().getId());
		try {
			Thread.sleep(this.dur);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Cliente: %d servito.\n", this.num_cliente);
	}
	/*
	 * @Effects: E' un getter per ottenere il numero di task.
	 * 
	 */
	public int getClient() {
		return this.num_cliente;
	}

}
