package eser3;

 public class Writer<T extends CounterInterface> implements Runnable {
	private T count;
	public Writer(T count) {
		this.count=count;
	}
	@Override 	public void run() {
		count.increment();
	}
}
