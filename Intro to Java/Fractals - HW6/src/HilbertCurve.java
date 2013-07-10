import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import com.lrdev.turtle.Turtle;

public class HilbertCurve extends JPanel {
	static int depth; 
	//String recursiveDepth = JOptionPane.showInputDialog("Input your recursive depth.");
	//depth = Integer.parseInt(recursiveDepth);
	double distance;
	
	public HilbertCurve(int depth) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JScrollPane(this));
		f.setSize(675, 675);
		f.setLocation(0, 0);
		f.setVisible(true);
	}
	protected void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 1500, 1500);
		Turtle turtle = new Turtle((Graphics2D) g, getBounds());
		turtle.penup();
		turtle.changeColor(Color.BLUE);
		turtle.setHeadingMode(Turtle.DEGREE);
		turtle.setheading(0);
		turtle.setxy(-300.0,-300.0);
		int depthUse = depth+1;
		turtle.penDown();
		if(depth == 1) distance = 550;
		else if (depth == 2) distance = 200;
		else if (depth == 3) distance = 80;
		else if (depth == 4) distance = 40;
		else if (depth == 5) distance = 19;
		else if (depth == 6) distance = 9.5;
		else if (depth == 7) distance = 4.7;
		else if (depth == 8) distance = 2.4;
		else if (depth == 9) distance = 1.2;
		else distance = .6;
		recursiveStepA(turtle, 0, 0, depthUse, depthUse, 0, distance);	
	}
	public void recursiveStepA(Turtle turtle, double x, double y, int depth, int depthL, double heading, double distance)
	{
		
		if (depth == 1){
			color(turtle);
			return;
		}
		else
		{
			turtle.left(90);
			recursiveStepB(turtle, turtle.xcor(), turtle.ycor(), depth-1, depthL, -turtle.getHeading(), distance);
			turtle.forward(distance);
			turtle.right(90);
			recursiveStepA(turtle, turtle.xcor(), turtle.ycor(), depth-1, depthL, turtle.getHeading(), distance);
			turtle.forward(distance);                             
			recursiveStepA(turtle, turtle.xcor(), turtle.ycor(), depth-1, depthL, turtle.getHeading(), distance);
			turtle.right(90);
			turtle.forward(distance); 
			recursiveStepB(turtle, turtle.xcor(), turtle.ycor(), depth-1, depthL, -turtle.getHeading(), distance);
			turtle.left(90);
		}
	}
	public void recursiveStepB(Turtle turtle, double x, double y, int depth, int depthL, double heading, double distance){
		if (depth == 1){
			return;
		}
		else
		{
			turtle.right(90);
			recursiveStepA(turtle, turtle.xcor(), turtle.ycor(), depth-1, depthL, -turtle.getHeading(), distance);
			turtle.forward(distance);
			turtle.left(90);
			recursiveStepB(turtle, turtle.xcor(), turtle.ycor(), depth-1, depthL, turtle.getHeading(), distance);
			turtle.forward(distance);                             
			recursiveStepB(turtle, turtle.xcor(), turtle.ycor(), depth-1, depthL, turtle.getHeading(), distance);
			turtle.left(90);
			turtle.forward(distance); 
			recursiveStepA(turtle, turtle.xcor(), turtle.ycor(), depth-1, depthL, -turtle.getHeading(), distance);
			turtle.right(90);
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
		Scanner keys = new Scanner(System.in);
		System.out.println("Depth: ");
		depth = Integer.parseInt(keys.nextLine());
		HilbertCurve test = new HilbertCurve(depth);
	}
	
}
