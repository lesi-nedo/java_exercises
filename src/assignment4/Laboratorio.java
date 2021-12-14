/**
 * 
 */
package assignment4;

/**
 * @author nedo1993
 *
 */
public class Laboratorio {
	public static final int NUM_COMPS=20;
	private Computer[] comps=new Computer[Laboratorio.NUM_COMPS];
	public Laboratorio() {
		for(int i=0; i<Laboratorio.NUM_COMPS; i++) {
			this.comps[i]=new Computer(i);
		}
	}
	public Computer get_ith(int i) {
		return comps[i];
	}

}