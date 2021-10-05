/**
 * 
 */
package assignment3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nedo1993
 *
 */
public class Laboratorio {
	/*
	 * Overview: La classe che modella il Laboratorio con 20 computers
	 * I computer sono modellati come un array di Lock per mettere un accesso escclusivo per ogni
	 * computer. Ad ogni cella di array e' associato una Condition per permettere agli thread di attendere senza fare attesa attiva.
	 * 
	 */

	/**
	 * 
	 */
	private final int num_comp=20;
	private Lock[] computers;
	/*
	 * Effects:Inizializza l'array contenete le Condition.
	 */
	public Laboratorio() {
		computers=new ReentrantLock[num_comp];
		for(int i=0; i<num_comp;i++)
			computers[i]=new ReentrantLock();
	}
	/*
	 * Effects: restituisce l'iesimo lock associato all'iesimo computer
	 */
	public Lock getIthLock(int i) {
		return  computers[i];
	}
	public int getNumComp() {
		return this.num_comp;
	}
}
