import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * HW4Util.java
 * 
 * Contains the utility methods needed to calculate distances using 
 * GPS coordinates, bearings, and new coordinates.  Also contains
 * a method to create a Google Maps static image from a URL.
 * 
 * @author Mark Sherriff
 *
 */
public class HW4Util extends JPanel {

	// If you want to save the image to your project directory
	// with a random name, switch this to true
	private static final boolean SAVETODISK = false;
	
	// If you want the map image to display to the screen, 
	// set this to true
	private static final boolean DISPLAYONSCREEN = true;
	
	// Position of the image on the screen when it displays
	int XLOC = 100;
	int YLOC = 100;
	int LARGESTHEIGHT = 0;
	private static final long serialVersionUID = 1L;

	BufferedImage image = null;
	Dimension size = new Dimension();

	
	/**
	 * This method returns the distance between two GPS coordinate sets as a
	 * fraction of a mile.
	 * @param lat1 latitude of first coordinate
	 * @param lon1 longitude of first coordinate
	 * @param lat2 latitude of second coordinate
	 * @param lon2 longitude of second coordinate
	 * @return distance in miles
	 */
	public double distance(double lat1, double lon1, double lat2, double lon2) {

		double theta = lon1 - lon2;
		double dist = Math.sin(lat1 * Math.PI / 180.0)
				* Math.sin(lat2 * Math.PI / 180.0)
				+ Math.cos(lat1 * Math.PI / 180.0)
				* Math.cos(lat2 * Math.PI / 180.0)
				* Math.cos(theta * Math.PI / 180.0);
		dist = Math.acos(dist);
		dist = dist * 180.0 / Math.PI;
		dist = dist * 60 * 1.1515;

		return dist;
	}

	/**
	 * This method determines the radial bearing between two coordinates.
	 * @param lat1 latitude of first coordinate
	 * @param lon1 longitude of first coordinate
	 * @param lat2 latitude of second coordinate
	 * @param lon2 longitude of second coordinate
	 * @return radial bearing
	 */
	public double getBearing(double lat1, double lon1, double lat2, double lon2) {
		lat1 = lat1 * Math.PI / 180;
		lon1 = lon1 * Math.PI / 180;
		lat2 = lat2 * Math.PI / 180;
		lon2 = lon2 * Math.PI / 180;
		
		double dLon = lon2 - lon1;
		double y = Math.sin(dLon) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
				* Math.cos(lat2) * Math.cos(dLon);
		
		double bearing = (180.0/Math.PI)*Math.atan2(y, x);
		bearing = (bearing+180) % 360;
		return bearing;
	}

	/**
	 * This method finds a GPS coordinate given a start point, a bearing (direction),
	 * and a distance traveled
	 * @param lat1 latitude of first coordinate
	 * @param lon1 longitude of first coordinate
	 * @param brng radial bearing that you are traveling
	 * @param dist distance to travel in miles
	 * @return an array where index 0 is latitude and index 1 is longitude of found coordinate
	 */
	public double[] findCoordinate(double lat1, double lon1, double brng,
			double dist) {

		double R = 3963.1676;
		brng = brng * Math.PI / 180;

		dist = dist / R;

		lat1 = lat1 * Math.PI / 180;
		lon1 = lon1 * Math.PI / 180;

		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(dist)
				+ Math.cos(lat1) * Math.sin(dist) * Math.cos(brng));
		double lon2 = lon1
				+ Math.atan2(Math.sin(brng) * Math.sin(dist) * Math.cos(lat1),
						Math.cos(dist) - Math.sin(lat1) * Math.sin(lat2));
		lon2 = (lon2 + 3 * Math.PI) % (2 * Math.PI) - Math.PI; 

		lat2 = lat2 * 180 / Math.PI;
		lon2 = lon2 * 180 / Math.PI;

		double[] arr = { lat2, lon2 };
		return arr;

	}

	
	/**
	 * Pass a URL to an image here (like a Google map URL) to make the image
	 * appear on screen!  Technically it works with any image URL on the Internet.
	 * @param url web address of the image you want to show
	 */
	public void createImage(String url) {

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		if (DISPLAYONSCREEN) {
			try {
				image = ImageIO.read(new URL(url));
				size.setSize(image.getWidth(), image.getHeight());
			} catch (Exception e) {
				e.printStackTrace();
			}
			JFrame f = new JFrame();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.add(new JScrollPane(this));
			f.setSize(size.width + 50, size.height + 50);
			f.setLocation(XLOC, YLOC);
			XLOC += image.getWidth() + 50;
			if (image.getHeight() > LARGESTHEIGHT) {
				LARGESTHEIGHT = image.getHeight() + 75;
			}
			if (XLOC > dim.getWidth() - image.getWidth() - 50) {
				YLOC += LARGESTHEIGHT;
				XLOC = 100;
			}
			f.setVisible(true);
		}
		if (SAVETODISK) {

			Random randomGen = new Random();
			int rand = Math.abs(randomGen.nextInt(400000));
			String imageName = rand + ".png";

			File outputFile = new File(imageName);
			System.out.println("Saving to disk as: "
					+ outputFile.getAbsolutePath());
			try {
				ImageIO.write(image, "PNG", outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) {
		// Center image in this component.
		int x = (getWidth() - size.width) / 2;
		int y = (getHeight() - size.height) / 2;
		g.drawImage(image, x, y, this);
	}

}

