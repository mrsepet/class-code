/**
* Name: William Emmanuel
* Computing ID: wre9fz
* Lab Section: 1111
* Date: 2/6/12
*/
import java.text.DecimalFormat;
import java.util.Scanner;
public class UnitConversion {
public static void main(String[] args){
	DecimalFormat formatter = new DecimalFormat("#0.0000");
	Scanner myKeyboard = new Scanner(System.in);
	System.out.print("Convert from English unit? ");
	String enunit;
	enunit = myKeyboard.nextLine();
	System.out.print("Convert to Metric unit? ");
	String meunit;
	meunit = myKeyboard.nextLine();
	if (((enunit.equals("ft") || enunit.equals("mi")) && (meunit.equals("m") || meunit.equals("km"))) || ((enunit.equals("oz") || enunit.equals("lb")) && (meunit.equals("g") || meunit.equals("kg"))) || ((enunit.equals("gal") || enunit.equals("pt") && meunit.equals("ml") || meunit.equals("l"))))
		;
	else
	{
		System.out.println("Invalid Conversion!");
		System.exit(0);
	}
	System.out.print("Amount? ");
	double amount;
	amount = myKeyboard.nextDouble();
	double converted = 0;
	if (enunit.equals("pt")){
		if (meunit.equals("ml")){
			//pt to ml
			converted = amount * 473.176473;
		}

		if (meunit.equals("l"))
		{
			//pt to l
			converted = amount * 0.473176473;
		}
	}	
	if (enunit.equals("gal")){
		if (meunit.equals("ml")){
			//pt to ml
			converted = amount * 3785.41178;
		}

		if (meunit.equals("l"))
		{
			//pt to l
			converted = amount * 3.78541178;
		}
	}	
	if (enunit.equals("ft")){
		if (meunit.equals("m")){
			//pt to ml
			converted = amount * 0.3048;
		}

		if (meunit.equals("km"))
		{
			//pt to l
			converted = amount * 0.0003048;
		}
	}	
	if (enunit.equals("mi")){
		if (meunit.equals("m")){
			//pt to ml
			converted = amount * 1609.344;
		}

		if (meunit.equals("km"))
		{
			//pt to l
			converted = amount * 1.609344;
		}
	}
	if (enunit.equals("oz")){
		if (meunit.equals("g")){
			//pt to ml
			converted = amount * 28.3495231;
		}

		if (meunit.equals("kg"))
		{
			//pt to l
			converted = amount * 0.0283495231;
		}
	}	
	if (enunit.equals("lb")){
		if (meunit.equals("g")){
			//pt to ml
			converted = amount * 453.59237;
		}

		if (meunit.equals("kg"))
		{
			//pt to l
			converted = amount * 0.45359237;
		}
	}
System.out.print(amount + " " + enunit + " = " + formatter.format(converted) + " " + meunit);
}
}
	
	