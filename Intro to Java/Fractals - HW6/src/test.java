import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.lrdev.turtle.Turtle;
public class test extends JPanel {
	public test() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JScrollPane(this));
		f.setSize(500, 500);
		f.setLocation(0, 0);
		f.setVisible(true);
	}
	protected void paintComponent(Graphics g) {
		Turtle turtle = new Turtle((Graphics2D) g, getBounds());
		turtle.setHeadingMode(Turtle.DEGREE);
		int recursiveDepth=5;
		recSquares(turtle,recursiveDepth,200.0,2,2);		
	}
	public void recursiveStep(Turtle turtle, double x, double y, int depth, int depthL, double heading)
	{
		double distance = 600/(Math.pow(depthL, 2));
		if (depth == 1){
			turtle = color(turtle);
			turtle.penDown();
			turtle.forward(distance);
			turtle.left(90);
			turtle.forward(distance);
			turtle.left(90);
			turtle.forward(distance);
			turtle.penUp();
		}
		else
		{
			depth = depth - 1;
			turtle.penDown();
			turtle.left(90);
			turtle.penUp();
			recursiveStep(turtle, turtle.xcor(), turtle.ycor(), depth, depthL, heading);
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
		test t= new test();
	}
}
