/**
* Name: Will Emmanuel
* Computing IDs: wre9fz	
* Lab Section: 1111
* Date: 3/19/12
*/
import java.util.Scanner;
import java.io.File;
import java.text.DecimalFormat;
public class SearchAndRescue {
public static int GetNumLines (File file) throws Exception{
		Scanner fileReader = new Scanner(file);
		int l=0;
		while (fileReader.hasNextLine()){
			l++;
			fileReader.nextLine();
		}
		fileReader.close();
		return l;
	}
public static void main(String[] args) throws Exception{
		//Decimal Formatter
		DecimalFormat formatter = new DecimalFormat("#0.0000");
		HW4Util util = new HW4Util();
		//Initial input of file
		Scanner keys = new Scanner(System.in);
		System.out.println("--- Hike Analysis ---");
		System.out.print("File: ");
		File file = new File(keys.nextLine());
		//Get number of entries and create arrays
		int entries = GetNumLines(file);
		String[][] gpsStr = new String[entries][];
		Double[][] gpsDbl = new Double[entries][2];
		Integer[][] time = new Integer[entries][7];
		//Split up file and load into arrays
		Scanner fileReader = new Scanner(file);
		for(int i = 0; fileReader.hasNextLine(); i++) {
			String line = fileReader.nextLine();
			//Lat. and long. storing
			gpsStr[i] = line.split(",");
			gpsDbl[i][0] = Double.parseDouble(gpsStr[i][0]);
			gpsDbl[i][1] = Double.parseDouble(gpsStr[i][1]);
			//Time stamp storing
			String tline = gpsStr[i][2];
			time[i][0] = (tline.charAt(0)-48)*1000+(tline.charAt(1)-48)*100+(tline.charAt(2)-48)*10+(tline.charAt(3)-48);
			time[i][1] = (tline.charAt(5)-48)*10+tline.charAt(6)-48;
			time[i][2] = (tline.charAt(8)-48)*10+tline.charAt(9)-48;
			time[i][3] = (tline.charAt(11)-48)*10+tline.charAt(12)-48;
			time[i][4] = (tline.charAt(14)-48)*10+tline.charAt(15)-48;
			time[i][5] = (tline.charAt(17)-48)*10+tline.charAt(18)-48;
			time[i][6] = time[i][5]+(60*time[i][4])+(60*60*time[i][3]);
		}
		//Find total distance traveled
		double distance = 0;
		for (int i=1; i < entries; i++){
			distance = distance + util.distance(gpsDbl[i-1][0], gpsDbl[i-1][1], gpsDbl[i][0], gpsDbl[i][1]);
		}
		System.out.println("Total distance travelled: " + formatter.format(distance) + " miles");
		//Find total time traveled
		int timed = time[entries-1][6] - time[0][6];
		final double SecTime = timed;
		int hr = (timed)/(60*60); 
		timed = timed - (hr*60*60); 
		int min = (timed)/(60); 
		timed = timed - (min*60);
		int sec = (timed);
		System.out.println("Total time travelled: " + hr + ":" + min + ":" + sec);
		//Avg minutes between markers
		double BetweenTime=0;
		for (int i = 1; i < entries; i++){
			BetweenTime = BetweenTime + time[i][6] - time[i-1][6];
		}
		double avg = (BetweenTime / entries)/60;
		System.out.println("Avg minutes between GPS markers: " + formatter.format(avg));
		//Avg distance in 1 minute
		double minDist = distance / (SecTime/60);
		System.out.println("Avg distance traveled in 1 min: " + formatter.format(minDist*5280) + " ft");
		//1 Hour Perimeter
		double OnePerimeter = minDist * 60; 
		double TwoPerimeter = minDist * 120;
		System.out.println("1 hour search perimeter: " + formatter.format(OnePerimeter) + " mi");
		System.out.println("2 hour search perimeter: " + formatter.format(TwoPerimeter) + " mi");
		//Google maps trail
		String url = "http://maps.googleapis.com/maps/api/staticmap?zoom=13&size=1000x500&maptype=roadmap";
		String markers = ""; 
		int MarkerFraction = entries / 15;
		for (int i = MarkerFraction; i <= entries; i += MarkerFraction){
			double lati = gpsDbl[i][0];
			double longi = gpsDbl[i][1];
			double disti = 0;
			for (int j=1; j < i; j++){
				disti = disti + util.distance(gpsDbl[j-1][0], gpsDbl[j-1][1], gpsDbl[j][0], gpsDbl[j][1]);
			}
			int distin = (int)disti;
			String add = "&markers=color:green|label:"+distin+"|"+lati+","+longi;
			markers += add; 
		}
		//Google maps perimeter
		double lati = gpsDbl[entries-1][0];
		double longi = gpsDbl[entries-1][1];
		for (double i=0; i!=360; i+=45){
			double[] temp = util.findCoordinate(lati, longi, i, OnePerimeter);
			String add = "&markers=color:red|label:S|"+temp[0]+","+temp[1];
			markers += add; 
		}
		for (double i=0; i!=360; i+=45){
			double[] temp = util.findCoordinate(lati, longi, i, TwoPerimeter);
			String add = "&markers=color:red|label:S|"+temp[0]+","+temp[1];
			markers += add; 
		}
		//Most probable location
		double brg = util.getBearing(gpsDbl[entries-1][0], gpsDbl[entries-1][1], gpsDbl[entries-2][0], gpsDbl[entries-2][1]);
		double[] temp = util.findCoordinate(gpsDbl[entries-1][0], gpsDbl[entries-1][1], brg, OnePerimeter);
		String add = "&markers=color:blue|label:P|"+temp[0]+","+temp[1];
		markers += add; 
		url = url + markers + "&sensor=false";
		System.out.println(url);
		util.createImage(url);
		
		
		
		//BONUS BONUS BONUS BONUS BONUS BONUS BONUS BONUS BONUS BOUNS
		//Calculate average deviation of bearing from point to point
		url = "http://maps.googleapis.com/maps/api/staticmap?zoom=13&size=1000x500&maptype=roadmap";
		markers = ""; 
		MarkerFraction = entries / 15;
		for (int i = MarkerFraction; i <= entries; i += MarkerFraction){
			double lati2 = gpsDbl[i][0];
			double longi2 = gpsDbl[i][1];
			double disti = 0;
			for (int j=1; j < i; j++){
				disti = disti + util.distance(gpsDbl[j-1][0], gpsDbl[j-1][1], gpsDbl[j][0], gpsDbl[j][1]);
			}
			int distin = (int)disti;
			add = "&markers=color:green|label:"+distin+"|"+lati2+","+longi2;
			markers += add; 
		}
		double sum=0;
		double dev=0;
		int devent=0; 
		for (int i=2; i<entries; i++){
			dev = util.getBearing(gpsDbl[i-1][0], gpsDbl[i-1][1], gpsDbl[i][0], gpsDbl[i][1]) - util.getBearing(gpsDbl[i-2][0], gpsDbl[i-2][1], gpsDbl[i-1][0], gpsDbl[i-1][1]);
			sum = dev + sum;
			devent++;
		}
		double devavg = sum / devent;
		lati = gpsDbl[entries-1][0];
		longi = gpsDbl[entries-1][1];
		double newlati = gpsDbl[entries-1][0];
		double newlongi = gpsDbl[entries-1][1];
		for (double i=0; i!=7; i++){
			double[] temp2 = util.findCoordinate(newlati, newlongi, (i+1)*(devavg), OnePerimeter/3);
			newlati = temp2[0];
			newlongi = temp2[1];
			add = "&markers=color:red|label:S|"+temp2[0]+","+temp2[1];
			markers += add; 
		}
		newlati = gpsDbl[entries-1][0];
		newlongi = gpsDbl[entries-1][1];
		for (double i=0; i!=7; i++){
			double[] temp2 = util.findCoordinate(newlati, newlongi, (90-(i+1)*devavg), OnePerimeter/3);
			newlati = temp2[0];
			newlongi = temp2[1];
			add = "&markers=color:red|label:S|"+temp2[0]+","+temp2[1];
			markers += add; 
		}
		//Most probable location
		brg = util.getBearing(gpsDbl[entries-1][0], gpsDbl[entries-1][1], gpsDbl[entries-2][0], gpsDbl[entries-2][1]);
		temp = util.findCoordinate(gpsDbl[entries-1][0], gpsDbl[entries-1][1], brg, OnePerimeter);
		add = "&markers=color:blue|label:P|"+temp[0]+","+temp[1];
		markers += add; 
		url = url + markers + "&sensor=false";
		System.out.println("BONUS: "+url);
		System.out.println("BONUS EXPLANATION: The perimeter is based on the average deviation between every GPS point. Each search point represents 20 minutes of possible distance travelled. Every three points represents an extra hour search perimeter.");
		util.createImage(url);
		//END OF BONUS
		
		
		//Close fileReader
		fileReader.close();
}
}

