import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.Font;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * SimpleCanvas.java
 * 
 * The SimpleCanvas class contains the drawing methods needed to manage the
 * SurvivalField. Many of the methods in this class will not be called by the
 * programmer - instead, they will be automatically called when something
 * occurs. For instance, mouseClicked is called when someone clicks on the
 * SurvivalField.
 * 
 * @author Jason Lawrence and Mark Sherriff
 * 
 * Updated/Expanded by:
 * @Kevin Seitter, Will Emmanuel
 * @kps2wu, wre9fz
 * @Section 1111
 */
public class SimpleCanvas extends JPanel implements MouseListener,
		MouseMotionListener {

	// width and height of the window
	int width;
	int height;

	// lastTime that the screen was refreshed
	long lastTime;

	// a link back to the SurvivalField for updating it
	SurvivalField simulator;

	/**
	 * Constructor for the SimpleCanvas
	 * 
	 * @param width_
	 *            width of the window
	 * @param height_
	 *            height of the window
	 * @param simulator_
	 *            link back to the SurvivalField
	 */
	public SimpleCanvas(int width_, int height_, SurvivalField simulator_) {
		width = width_;
		height = height_;
		simulator = simulator_;
		lastTime = -1L;
	}

	/**
	 * Called to start the game
	 */
	public void setupAndDisplay() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JScrollPane(this));
		f.setSize(width, height);
		f.setLocation(200, 200);
		f.setVisible(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * This method is NEVER called by the programmer. This method is called
	 * whenever the screen refreshes. Consequently, it calls the draw() method
	 * in SurvivalField, telling it to update its components.
	 */
	protected void paintComponent(Graphics g) {
		boolean first = (lastTime == -1L);
		long elapsedTime = System.nanoTime() - lastTime;
		lastTime = System.nanoTime();
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		
		try {
			simulator.draw((Graphics2D) g, (first ? 0.0f
					: (float) elapsedTime / 1e9f));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
		}

	/**
	 * drawDot does just what it says - it puts a dot on the screen.
	 * 
	 * @param g
	 *            Graphics engine passed from paintComponent() into
	 *            SurivalField.draw()
	 * @param x
	 *            x coordinate of dot
	 * @param y
	 *            y coordinate of dot
	 * @param color
	 *            Color instance of color of dot
	 */
	public void drawDot(Graphics2D g, double x, double y, Color color) {
		g.setColor(color);
		g.fillOval((int) (x + 0.5f), (int) (y + 0.5f), 8, 8);
	}
	

	public void drawText(Graphics2D g, String text, int size_, int x_, int y_, Color color_) {
		g.setFont(new Font("SanSerif", Font.BOLD, size_));
	    g.setColor(color_);
	    g.drawString(text, x_, y_);
	}
	
	/**
	 * Whenever the mouse is moved on the SurvivalField, this method gets
	 * called.
	 */
	public void mouseMoved(MouseEvent e) {
		try {
			simulator.mouseAction((float) e.getX(), (float) e.getY(), -1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Whenever the mouse is clicked on the SurvivalField, this method gets
	 * called.
	 */
	public void mouseClicked(MouseEvent e) {
		try {
			simulator
					.mouseAction((float) e.getX(), (float) e.getY(), e.getButton());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Whenever the mouse enters the SurvivalField, this method gets called.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Whenever the mouse leaves the SurvivalField, this method gets called.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Whenever the mouse is pressed (yes, it's just barely different than
	 * clicked) on the SurvivalField, this method gets called.
	 */
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Whenever the mouse press is released on the SurvivalField, this method
	 * gets called.
	 */
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Whenever the mouse clicked and dragged on the SurvivalField, this method
	 * gets called.
	 */
	public void mouseDragged(MouseEvent arg0) {
	}

}
