/**
 * 
 */
package assignment4;

/**
 * @author nedo1993
 *
 */
public class Computer {
	private int id_comp;
	private boolean is_free;
	public Computer(int id_comp) {
		this.id_comp=id_comp;
		this.is_free=true;
	}
	public int get_id() {
		return this.id_comp;
	}
	public boolean get_status() {
		return this.is_free;
	}
	public void set_to_busy() {
		this.is_free=false;
	}
	public void set_to_free() {
		this.is_free=true;
	}
}
