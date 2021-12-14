/**
 * 
 */
package assignment8;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author nedo1993
 *
 */
public class ConteCorrente {
	/*
	 * Overview: Classe che rappresenta il conto corrente. I movimento sono memorizzati in HashMap 
	 * poiche si evita due transazione che hanno lo stesso tempo d'esecuzione (ho assunto che .
	 */
	private String nome;
	private String cognome;
	private Map<Long, String> movimenti;
	Iterator<Map.Entry<Long, String>> it;
	public ConteCorrente(String nome, String cognome) {
		this.nome=nome;
		this.cognome=cognome;
		this.movimenti=new HashMap<Long, String>();
	}
	/*
	 * Requires: causale uguale a un valore presi dal@Override
            public boolean hasNext() {
                return iter.hasNext();
            }l'array salvato nella classe StaticValues
	 * Throws: IlligalArgumentException
	 * Effects: aggiunge una transazione alla collezione.
	 */
	public void add_trans(Long time, String causale) throws IllegalArgumentException {
		int hashed=causale.hashCode()%StaticValues.HASHING_VAL;
		int ind=hashed%StaticValues.NUM_MOV;
		hashed=ind<0?ind+StaticValues.NUM_MOV:ind;
		if(!StaticValues.movimenti[hashed].equals(causale)) {
			System.out.println(StaticValues.movimenti[hashed]+causale);
			throw new IllegalArgumentException();
		}
		this.movimenti.put(time, causale);
	}
	public String getNome() {
		return this.nome;
	}
	public String getCognome() {
		return this.cognome;
	}
	public Map<Long, String> getMovimenti() {
		return this.movimenti;
	}
	@Override
	public String toString() {
		return "{\nnome: " + this.nome + "\ncognome: " + this.cognome +"\nmovimenti: " + this.movimenti.toString()+"\n}"; 
	}
}
