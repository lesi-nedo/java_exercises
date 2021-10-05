/**
 * 
 */
package assignment3;

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
	int sleep=(new Random().nextInt(b-a)+a);//per quanto tempo il thread si mette a dormire
	private Tutor tutor;
	public Studenti(Tutor tutor, int id_stud, int k) {
		this.tutor=tutor;
		this.id_stud=id_stud;
		this.k=k;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.tutor.assToStud(this.id_stud, this.sleep, k);
	}

}
