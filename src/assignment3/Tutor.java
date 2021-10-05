/**
 * 
 */
package assignment3;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nedo1993
 *
 */
public class Tutor implements Runnable {
	/*
	 * Overview: Simula il comportamento di un tutor
	 */
	private boolean am_in;//serve per gestire l'attesa del tutor
	private Integer curr_ID=0;
	private int lower = 100;
	private int upper=500;
	private long pause=(new Random().nextInt(upper-lower)+lower);
	private int total_comp;
	private Lock lock_wait;//il lock serve per inizializzare un variabile di condizione.
	private Condition cond_wait;//serve per fare aspettare il tutor che tutti i computer si liberano.
	private int ithComp;//i-esimo computer che devono usare i tesisti
	private int left; //il totale di volte che tutti gli utenti devono usare ancora il laboratorio
	private Lock[] locks_tesisti;//lock per aspettare il proprio turno senza fare attesa attiva.
	private Lock[] locks_profs;
	private Lock[] locks_stud;
	private Lock[] ready_tesisti;
	private Lock[] ready_profs;
	private Lock[] ready_stud;
	private Condition[] cond_tesisti;//sono necesserie per aspettare su una condizione
	private Condition[] cond_profs;
	private Condition[] cond_stud;
	private boolean[] bool_tesisti;//sono necessarie per uscire dal while 
	private boolean[] bool_profs;
	private boolean[] bool_stud;
	private BlockingQueue<Lock> free_comp;
	private BlockingQueue<Integer> queue_profs;
	private BlockingQueue<Integer> queue_tesisti;
	private BlockingQueue<Integer> queue_stud;
	private Laboratorio lab;
	public Tutor(int num_stud, int num_prof, int num_thes_wr, int ithComp, int k) {
		this.lab=new Laboratorio();
		this.left=k*(num_stud+num_prof+num_thes_wr);
		this.lock_wait=new ReentrantLock();
		this.cond_wait=lock_wait.newCondition();
		this.ithComp=ithComp;
		this.total_comp=this.lab.getNumComp();
		this.am_in=false;
		this.queue_profs=new ArrayBlockingQueue<Integer>(num_prof);
		this.queue_tesisti=new ArrayBlockingQueue<Integer>(num_thes_wr);
		this.queue_stud=new ArrayBlockingQueue<Integer>(num_stud);
		this.free_comp= new ArrayBlockingQueue<Lock>(lab.getNumComp());
		this.locks_tesisti=new ReentrantLock[num_thes_wr];
		this.locks_profs=new ReentrantLock[num_prof];
		this.locks_stud=new ReentrantLock[num_stud];
		this.bool_tesisti=new boolean[num_thes_wr];
		this.bool_profs=new boolean[num_prof];
		this.bool_stud=new boolean[num_stud];
		this.cond_tesisti=new Condition[num_thes_wr];
		this.cond_profs=new Condition[num_prof];
		this.cond_stud=new Condition[num_stud];
		this.ready_tesisti=new ReentrantLock[num_thes_wr];
		this.ready_profs=new ReentrantLock[num_prof];
		this.ready_stud=new ReentrantLock[num_stud];
		init_locks(num_thes_wr, this.locks_tesisti);
		init_locks(num_prof, this.locks_profs);
		init_locks(num_stud, this.locks_stud);
		init_locks(num_prof, this.ready_profs);
		init_locks(num_thes_wr, this.ready_tesisti);
		init_locks(num_stud, this.ready_stud);
		init_cond(num_thes_wr, this.cond_tesisti, this.locks_tesisti);
		init_cond(num_stud, this.cond_stud, this.locks_stud);
		init_cond(num_prof, this.cond_profs, this.locks_profs);
		for(int i=0;i<this.lab.getNumComp(); i++)
			try {
				this.free_comp.put(this.lab.getIthLock(i));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}
	private void decrementLeft() {
	(this.left)--;
	}
	private void init_cond(int size, Condition[] cond, Lock[] locks) {
		for(int i=0;i<size;i++)
			cond[i]=locks[i].newCondition();
	}
	private void init_locks(int size, Lock[] locks) {
		for(int i=0;i<size;i++)
			locks[i]=new ReentrantLock();
	}
	/*
	 * il metodo viene chiamata per avvisare il tutor che un generico utente vuole usare un computer.
	 * la mia idea e' che il tutor preleva da una queue bloccante l'utente e lo si avvisa del fatto che gli e' stato assegnato un computer
	 * attraverso l'array di bool. Un importanza fondamentale gioca l'id del utente.
	 */
	private void assToAss(Lock[] locks, BlockingQueue<Integer> queue, boolean[] bool, Condition[] cond, Lock[] ready, long in_lab, int id) {
		locks[id].lock();
		try {
			queue.put(id);
			while(bool[id]==false) {
				cond[id].await();
			}
			assert ready[id] != null;
			ready[id].lock();
			Thread.sleep(in_lab);
			this.free_comp.put(ready[id]);
			ready[id].unlock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			locks[id].unlock();
			ready[id]=null;
			bool[id]=false;
			if(this.am_in==true)
				System.out.print("utente avvisa il tutor che ha finito.\n");
				this.lock_wait.lock();
				this.cond_wait.signal();
				this.lock_wait.unlock();
		}
	}
	public void assToTesista(int id_tes, long in_lab, int total_acces_tesisti) {
		while(total_acces_tesisti >0) {
			System.out.printf("Il Tesista: %d chiede l'utilizzo del computer\n", id_tes);
			assToAss(this.locks_tesisti, queue_tesisti, this.bool_tesisti, this.cond_tesisti, ready_tesisti, in_lab, id_tes);
			System.out.printf("Il Tesista: %d ha finito\n", id_tes);
			total_acces_tesisti--;
			try {
				Thread.sleep(this.pause);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void assToProf(int id_prof, long in_lab, int total_acces_profs) {
		while(total_acces_profs>0) {
			System.out.printf("Il Professore: %d chiede il laboratorio\n", id_prof);
			assToAss(this.locks_profs, queue_profs, this.bool_profs, this.cond_profs, ready_profs, in_lab, id_prof);
			System.out.printf("Il Professore: %d ha finito\n", id_prof);
			total_acces_profs--;
		}
		try {
			Thread.sleep(this.pause);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void assToStud(int id_stud, long in_lab, int total_acces_stud) {
		while(total_acces_stud>0) {
			System.out.printf("Lo studente: %d chiede una postazione\n", id_stud);
			assToAss(this.locks_stud, queue_stud, this.bool_stud, this.cond_stud, ready_stud, in_lab, id_stud);
			System.out.printf("Lo Studente: %d ha finito\n", id_stud);
			total_acces_stud--;
		}
		try {
			Thread.sleep(this.pause);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void exec_helper(Lock[] locks, BlockingQueue<Integer> queue, boolean[] bool, Condition[] cond, Lock[] ready, int ith_lock, Integer curr_id) {
		locks[curr_id].lock();
		try {
			//se si passa 0 come valore del parametro ith allora si usa i-esimo computer.
			if(ith_lock!=0) {
				ready[curr_id]=this.lab.getIthLock(ith_lock);
				while(free_comp.removeIf(filter-> filter.equals(ready[curr_id]))!=true);
			} else {
				ready[curr_id]=this.free_comp.take();
			}
			bool[curr_id]=true;
			cond[curr_id].signal();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			locks[curr_id].unlock();
		}
	}
	/*
	 * Si loopa fino a che i thread non hanno consumato tutte gli accessi.
	 * Si aspetta che tutti i thread rilasciano i computer.
	 */
	public void run() {
		while(this.left>0) {
			try {
				this.lock_wait.lock();
				while((this.curr_ID =this.queue_profs.poll())!= null) {
					this.am_in=true;
					while(this.free_comp.size()!=this.total_comp) {
						this.cond_wait.await();
					}
					System.out.print("Il tutor assegna tutto il laboratorio al professore.\n");
					this.exec_helper(this.locks_profs, this.queue_profs, this.bool_profs, this.cond_profs, this.ready_profs, 0, this.curr_ID);
					this.decrementLeft();
				}
				this.am_in=false;
				this.lock_wait.unlock();
				while((this.curr_ID =this.queue_tesisti.poll())!= null) {
					this.exec_helper(this.locks_tesisti, this.queue_tesisti, this.bool_tesisti, this.cond_tesisti, this.ready_tesisti, this.ithComp, this.curr_ID);
					this.decrementLeft();
					if(this.queue_profs.peek()!=null) {
						break;
					}
				}
				while((this.curr_ID =this.queue_stud.poll())!= null) {
					this.exec_helper(this.locks_stud, this.queue_stud, this.bool_stud, this.cond_stud, this.ready_stud, 0, this.curr_ID);
					this.decrementLeft();
					if(this.queue_profs.peek()!=null || this.queue_tesisti.peek() != null)
						break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
	}
}
