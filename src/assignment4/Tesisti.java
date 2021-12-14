/**
 * 
 */
package assignment4;

import java.util.Random;

/**
 * @author nedo1993
 *
 */
public class Tesisti implements Runnable {
	private int id_tesista;//identificatore associato al tesista
	int iThComp;//l'iesimo computer che il tesista deve usare
	private int k;
	private int a = 100;
	private int b = 1000;
	long sleep=(new Random().nextInt(b-a)+a);//per quanto tempo il thread si mette a dormire
	long rest=(new Random().nextInt(b-a)+a);
	private Tutor tutor;
	public Tesisti(Tutor tutor, int iThComp, int id_tesista, int k) {
		this.tutor=tutor;
		this.iThComp=iThComp;
		this.id_tesista=id_tesista;
		this.k=k;
	}
	@Override
	public void run() {
		while(this.k >0) {
			try {
				this.tutor.tesisti_ask(this.sleep, this.id_tesista);
				Thread.sleep(rest);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.k--;
		}

	}

}
