import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import com.lrdev.turtle.Turtle;

public class LevyCurve extends JPanel {
	
	String recursiveDepth = JOptionPane.showInputDialog("Input your recursive depth.");
	int depth = Integer.parseInt(recursiveDepth);
	double width1 = 50*depth;
	double height1 = 50*depth;
	int width = (int) width1;
	int height = (int) height1;
	
	public LevyCurve()
	{
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JScrollPane(this));
		f.setSize(width, height);
		f.setLocation(300, 0);
		f.setVisible(true);
		
	}
	
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		Turtle turtle = new Turtle((Graphics2D) g, getBounds());
		turtle.setHeadingMode(Turtle.DEGREE);
		turtle.changeColor(Color.BLUE);
		turtle.setheading(45);
		turtle.penup();
		turtle.setxy(-10*depth, -5*depth);
		double x = 300;
		double y = 0;
		double heading = 45;
		int depthUse = depth;
		recursiveStep(turtle, x, y, depthUse, depthUse, heading);
	}
	
	public void recursiveStep(Turtle turtle, double x, double y, int depth, int depthL, double heading)
	{
		double distance = 600/(Math.pow(depthL, 2));
		if (depth == 1){
			turtle = color(turtle);
			turtle.penDown();
			turtle.forward(distance);
			turtle.penUp();
		}
		else
		{
			depth = depth - 1;
			turtle.penDown();
			turtle.right(45);
			recursiveStep(turtle, turtle.xcor(), turtle.ycor(), depth, depthL, heading);
			turtle.left(45);
			turtle.left(45);
			recursiveStep(turtle, turtle.xcor(), turtle.ycor(), depth, depthL, heading);
			turtle.right(45);
			turtle.penUp();
		}
	}
	
	public Turtle color(Turtle turtle)
	{
		if (turtle.getCurrentColor() == Color.BLUE)
		{
			turtle.changeColor(Color.CYAN);
			return turtle;
		}
		if (turtle.getCurrentColor() == Color.CYAN)
		{
			turtle.changeColor(Color.GREEN);
			return turtle;
		}
		if (turtle.getCurrentColor() == Color.GREEN)
		{
			turtle.changeColor(Color.YELLOW);
			return turtle;
		}
		if (turtle.getCurrentColor() == Color.YELLOW)
		{
			turtle.changeColor(Color.ORANGE);
			return turtle;
		}
		if (turtle.getCurrentColor() == Color.ORANGE)
		{
			turtle.changeColor(Color.RED);
			return turtle;
		}
		if (turtle.getCurrentColor() == Color.RED)
		{
			turtle.changeColor(Color.BLUE);
			return turtle;
		}
		return turtle;
	}
	
	
	public static void main(String[] args) {
		LevyCurve test = new LevyCurve();
	}
	
}
