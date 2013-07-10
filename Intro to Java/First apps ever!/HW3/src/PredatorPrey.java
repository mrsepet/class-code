/**
* Name: William Emmanuel
* Computing ID: wre9fz
* Lab Section: 1111
* Date: 2/11/12
*/
import java.util.Scanner;
import java.text.DecimalFormat;
public class PredatorPrey {
	public static void main(String[] args) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		Scanner keys = new Scanner(System.in);
		System.out.print("Prey Birth Rate: ");
		double birth = keys.nextDouble();
		System.out.print("Rate of Predation: ");
		double predrate = keys.nextDouble();
		System.out.print("Predator Birth Rate without food: ");
		double birthwof = keys.nextDouble();
		System.out.print("Predator Birth Rate with food: ");
		double birthwf = keys.nextDouble();
		System.out.print("Initial Prey Population: ");
		double preypop = keys.nextDouble();
		System.out.print("Initial Predator Population: ");
		double predpop = keys.nextDouble();
		System.out.print("Number of Periods: ");
		int periods = keys.nextInt();
		double oldprey;
		double oldpred;
		System.out.println("Period  |  Prey  |  Predator");
		System.out.println("----------------------------");
		System.out.println("0          " + formatter.format(preypop) + "      " + formatter.format(predpop));
		for (int i=1; i<periods; i++){
			oldprey = preypop; 
			oldpred = predpop;
			preypop = oldprey*(1+birth-predrate*oldpred);
			predpop = oldpred * (1 - birthwof + birthwf * oldprey);
			System.out.println(i + "          " + formatter.format(preypop) + "      " + formatter.format(predpop));
		}
	}
}
