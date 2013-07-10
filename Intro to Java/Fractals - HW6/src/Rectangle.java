import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.lrdev.turtle.Turtle;

/**
 * Rectangle.java
 * A simple class to show how to use the turtle to draw shapes
 * in Java.
 * 
 * @author Jason Lawrence and Mark Sherriff
 *
 */
public class Rectangle extends JPanel {

	/**
	 * Constructor for Rectangle
	 */
	public Rectangle() {
		
		// Create a JFrame - a window that will appear on your screen
		JFrame f = new JFrame();
		
		// Tells the program to quit if you close the window
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Puts your drawing into the window (the JFrame)
		f.add(new JScrollPane(this));
		
		// Changes the size of the window on the screen
		f.setSize(600, 600);
		
		// Changes where the window will appear on your screen
		f.setLocation(200, 200);
		
		// Makes the window appear
		f.setVisible(true);
	}
	
	private void drawSmallRectangle(Turtle turtle, int x, int y) {
		// move the turtle to a starting position at the bottom
		turtle.penup();
		turtle.setXY(x,y);
		
		// draw a simple rectangle that is 100x50 pixels
		turtle.pendown();
		turtle.forward(100);
		turtle.left(90);
		turtle.forward(50);
		turtle.left(90);
		turtle.forward(100);
		turtle.left(90);
		turtle.forward(50);
		turtle.penup();
	}
	
	private void drawBlueRectangle(Turtle turtle, int x, int y) {
		// move the turtle somewhere else
		turtle.setXY(x, y);
		turtle.setheading(0);
		
		// draw a thicker, blue rectangle
		turtle.changeColor(Color.BLUE);
		turtle.changeWidth(5);
		turtle.pendown();
		turtle.forward(60);
		turtle.left(90);
		turtle.forward(120);
		turtle.left(90);
		turtle.forward(60);
		turtle.left(90);
		turtle.forward(120);
		turtle.penup();
	}
	
	private void drawRedBorder(Turtle turtle, int x, int y) {
		// move the turtle again, to the lower-left corner
		turtle.setXY(x, y);
		turtle.setheading(0);
		
		// draw a red border around the window
		turtle.changeColor(Color.RED);
		turtle.changeWidth(10);
		turtle.pendown();
		turtle.forward(500);
		turtle.left(90);
		turtle.forward(500);
		turtle.left(90);
		turtle.forward(500);
		turtle.left(90);
		turtle.forward(500);
		turtle.penup();
	}

	/**
	 * This is the method that is auto-called when the window is 
	 * loaded and drawn.  So, put your drawing code in here!
	 * 
	 * Notice we're calling various methods here to draw each
	 * of the rectangles.  When you're drawing your images, kick
	 * them off here!
	 */
	protected void paintComponent(Graphics g) {
		Turtle turtle = new Turtle((Graphics2D) g, getBounds());
		turtle.setHeadingMode(Turtle.DEGREE);
		
		drawSmallRectangle(turtle, 0, -200);
		
		drawBlueRectangle(turtle, -200, -100);
		
		drawRedBorder(turtle, -250, -250);
	}

	/**
	 * main is called when the program starts and will create
	 * a new instance of this class, which calls the constructor,
	 * which in turn kicks off the drawing
	 */
	public static void main(String[] args) {
		Rectangle test = new Rectangle();
	}
}
