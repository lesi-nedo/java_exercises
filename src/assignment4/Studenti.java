/**
 * 
 */
package assignment4;

import java.util.Random;

/**
 * @author nedo1993
 *
 */
public class Studenti implements Runnable {
	private int id_stud;//identificatore associato allo studente
	private int a = 100;
	private int b = 1000;
	private int k;
	long sleep=(new Random().nextInt(b-a)+a);//per quanto tempo il thread si mette a dormire
	long rest=(new Random().nextInt(b-a)+a);
	private Tutor tutor;
	public Studenti(Tutor tutor, int id_stud, int k) {
		this.tutor=tutor;
		this.id_stud=id_stud;
		this.k=k;
	}
	@Override
	public void run() {
		while(this.k >0) {
			try {
				this.tutor.studs_ask(this.sleep, this.id_stud, null);
				Thread.sleep(rest);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.k--;
		}
	}

}
