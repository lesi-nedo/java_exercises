/**
 * 
 */
package assignment4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author nedo1993
 *
 */
public class Tutor implements Runnable {
	private Object profs_obj;
	private Object tesisti_obj;
	private Object tutor_obj;
	private boolean stud_ok;//variabile serve per dire che un studente puo procedere poiche il computer dei tesisti e' occupato
	private Object studs_obj;
	private Laboratorio lab;
	private BlockingQueue<Computer> free_comp;
	private Computer curr_comp_stud;
	private Computer tesisti_free_comp;
	private Computer tesisti_busy_comp;
	private int profs_waiting;
	private int tesisti_waiting;
	private int studs_waiting;
	private int ith_comp;
	private boolean profs_cond;
	private boolean tutor_cond;// true se il tutor aspetta che il laboratorio abbia almeno un computer libero.
	public Tutor(int ith_comp) {
		this.ith_comp=ith_comp;
		this.profs_obj=new Object();
		this.tesisti_obj=new Object();
		this.studs_obj=new Object();
		this.lab=new Laboratorio();
		this.tesisti_free_comp=this.lab.get_ith(ith_comp);
		this.free_comp=new ArrayBlockingQueue<Computer>(Laboratorio.NUM_COMPS);
		for(int i=0; i<Laboratorio.NUM_COMPS; i++) {
			if(i!=ith_comp) {
				try {
					this.free_comp.put(lab.get_ith(i));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.profs_waiting=0;
		this.tesisti_waiting=0;
		this.studs_waiting=0;
		this.profs_cond=false;
		this.tutor_obj=new Object();
		this.curr_comp_stud=null;
		this.stud_ok=false;
	}
	public void profs_ask(long time_in_lab, int id) throws InterruptedException {
		synchronized(this.profs_obj) {
			do{
				this.profs_waiting++;
				this.profs_obj.wait();
				this.profs_waiting--;
			} while(this.profs_cond==true);
			this.profs_cond=true;
		}
		try {
			System.out.printf("Professore con l'id: %d ha acquisito tutto il laboratorio.\n",id);
			Thread.sleep(time_in_lab);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		synchronized(this.profs_obj) {
			this.profs_cond=false;
		}
		synchronized(this.tutor_obj) {
			if(this.tutor_cond==true) this.tutor_obj.notify();
		}
		System.out.printf("Professore con l'id: %d ha finito.\n", id);
		return;
	}
	public void tesisti_ask(long time_in_lab, int id) throws InterruptedException {
		synchronized(this.tesisti_obj) {
			do {
				this.tesisti_waiting++;
				if(this.profs_waiting >0) {
					synchronized(this.tutor_obj) {
						this.tutor_obj.notify();
					}
				}
				this.tesisti_obj.wait();
				this.tesisti_waiting--;
			} while(this.tesisti_free_comp==null  || this.profs_waiting >0);
			this.tesisti_busy_comp=this.tesisti_free_comp;
			this.tesisti_free_comp=null;
		}
		try {
			System.out.printf("Tesista con l'id %d ha acquistato il computer %d.\n", id, this.ith_comp);
			Thread.sleep(time_in_lab);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		synchronized(this.tesisti_obj) {
			this.tesisti_free_comp=this.tesisti_busy_comp;
			this.tesisti_busy_comp=null;
		}
		synchronized(this.tutor_obj) {
			this.tutor_obj.notify();
		}
		System.out.printf("Tesista con l'id: %d ha finito.\n", id);
		return;
	}
	public void studs_ask(long time_in_lab, int id, Computer comp) throws InterruptedException {
		
		synchronized(this.studs_obj) {
			do {
				this.studs_waiting++;
				if(this.profs_waiting >0) {
					synchronized(this.tutor_obj) {
						this.tutor_obj.notify();
					}
				}
				this.studs_obj.wait();
				this.studs_waiting--;
			} while(this.curr_comp_stud==null || this.profs_waiting >0 || (this.tesisti_waiting>0 && this.stud_ok==false));
			comp=this.curr_comp_stud;
			this.curr_comp_stud=null;
			this.stud_ok=false;
			synchronized(this.tutor_obj) {
				this.tutor_obj.notify();
			}
		}
		try {
			System.out.printf("Studente con l'id: %d ha acquistato il computer con l'id: %d.\n", id, comp.get_id());
			Thread.sleep(time_in_lab);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		this.free_comp.put(comp);
		System.out.printf("Studente con l'id: %d ha finito.\n", id);
		comp=null;
		if(this.tutor_cond == true) { 
			synchronized(this.tutor_obj) {
				this.tutor_obj.notify();
			}
		}
	}
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			synchronized(this.tutor_obj) {
				while(this.profs_waiting > 0) {
					while(this.free_comp.size() < Laboratorio.NUM_COMPS-1 || this.tesisti_busy_comp != null)
						try {
							this.tutor_cond=true;
							this.tutor_obj.wait();
							this.tutor_cond=false;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					synchronized(this.profs_obj) {
						this.profs_obj.notify();
					}
					try {
						this.tutor_cond=true;
						this.tutor_obj.wait();
						this.tutor_cond=false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				while(this.tesisti_waiting>0 && this.profs_waiting == 0) {
					while(this.tesisti_busy_comp!= null) {
						try {
							synchronized(this.studs_obj) {
								this.curr_comp_stud=this.free_comp.take();
								this.stud_ok=true;
								this.studs_obj.notify();
							}
							this.tutor_cond=true;
							this.tutor_obj.wait();
							this.tutor_cond=false;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					synchronized(this.tesisti_obj) {
						this.tesisti_obj.notify();
					}
					try {
						this.tutor_cond=true;
						this.tutor_obj.wait();
						this.tutor_cond=false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				while(this.studs_waiting >0 && this.tesisti_waiting == 0 && this.profs_waiting ==0) {
					try {
						synchronized(this.studs_obj) {
							if((this.curr_comp_stud=this.free_comp.poll(1l,TimeUnit.MILLISECONDS))==null) {
								if(this.tesisti_free_comp != null) {
									this.curr_comp_stud=this.tesisti_free_comp;
								} else {
									this.tutor_cond=true;
									this.tutor_obj.wait();
									this.tutor_cond=false;
							}
						}
							this.studs_obj.notify();
						}
						this.tutor_cond=true;
						this.tutor_obj.wait();
						this.tutor_cond=false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(this.free_comp.size()==0) break;
				}
			}
			
		}
	}
	
}