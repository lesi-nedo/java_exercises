/**
 * 
 */
package assignment3;

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
	int sleep=(new Random().nextInt(b-a)+a);//per quanto tempo il thread si mette a dormire
	private Tutor tutor;
	public Tesisti(Tutor tutor, int iThComp, int id_tesista, int k) {
		this.tutor=tutor;
		this.iThComp=iThComp;
		this.id_tesista=id_tesista;
		this.k=k;
	}
	@Override
	public void run() {
		this.tutor.assToTesista(this.id_tesista, this.sleep, this.k);
		// TODO Auto-generated method stub

	}

}
