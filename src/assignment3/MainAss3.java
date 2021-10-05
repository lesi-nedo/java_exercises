/**
 * 
 */
package assignment3;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author nedo1993
 *
 */
public class MainAss3 {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner reader = new Scanner(System.in);
		int num_comp=20;
		int lower = 1;
		int ith;
		int upper=10;
		int k=(new Random().nextInt(upper-lower)+lower);
		int students=0;
		int thesis_wr=0;
		int professors=0;
		try {
			System.out.print("Please enter the number of students: \n");
			students=reader.nextInt();
			System.out.print("Please enter the number of thesis writers: \n");
			thesis_wr=reader.nextInt();
			System.out.print("Please enter the number of professors: \n");
			professors=reader.nextInt();
			if(students < 0 || thesis_wr < 0 || professors < 0) {
				throw new InputMismatchException();
			}
			ith=(new Random().nextInt(num_comp-1)+1);
			Tutor tutor=new Tutor(students, professors, thesis_wr, ith, k);
			Thread tutor_th=new Thread(tutor);
			tutor_th.start();
			for(int i=0;i<students; i++) {
				Studenti stud=new Studenti(tutor, i, k);
				Thread studs_th=new Thread(stud);
				studs_th.start();
			}
			for(int i=0; i<thesis_wr;i++) {
				Tesisti tesisti=new Tesisti(tutor, ith, i, k);
				Thread tesisti_th=new Thread(tesisti);
				tesisti_th.start();
			}
			for(int i=0; i<professors; i++) {
				Professori profs=new Professori(tutor,i, k);
				Thread profs_th=new Thread(profs);
				profs_th.start();
			}
			tutor_th.join();

		} catch(InputMismatchException e) {
			System.out.print("Wrong Input, Bye.\n");
			return;
		} catch(IllegalStateException e) {
			System.out.print("This should have never ever happened");
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			reader.close();	
		}
	}

}
