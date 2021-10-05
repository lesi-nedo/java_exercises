/**
 * 
 */
package eser2;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author nedo1993
 *
 */
public class Power implements Callable<Double> {
	private double n =0;
	private int exp =0;
	public Power(double n, int exp) {
		this.n=n;
		this.exp=exp;
	}
	@Override public Double call() {
		System.out.printf("Esecuzione %f^%d in %d\n", this.n, this.exp, Thread.currentThread().getId());
		return Math.pow(this.n,  this.exp);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=2;
		double result =0;
		ExecutorService pool = Executors.newCachedThreadPool();
		ArrayList<Future<Double>> res= new ArrayList<Future<Double>>();
		for(int i=2; i<51; i++) {
			res.add(pool.submit(new Power(n, i)));
		}
		pool.shutdown();
		for(int i=0; i<res.size(); i++) {
			try {
				result+=res.get(i).get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			while(!pool.awaitTermination(10L, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Result: %f\n", result);
	}

}
