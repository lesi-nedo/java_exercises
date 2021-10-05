package assignment3;

import java.util.Random;

public class Professori implements Runnable {
	private int id_profs;//identificatore associato allo studente
	private int a = 100;
	private int b = 1000;
	private int k;
	int sleep=(new Random().nextInt(b-a)+a);//per quanto tempo il thread si mette a dormire
	private Tutor tutor;
	public Professori(Tutor tutor, int id_profs, int k) {
		this.tutor=tutor;
		this.id_profs=id_profs;
		this.k=k;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		tutor.assToProf(this.id_profs, this.sleep, k);
	}

}
