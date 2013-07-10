/**
 * Zombie.java
 * 
 * The class representing the Zombie object in the game. This class needs to
 * have some representation of current location, a reference to its target (aka
 * the Human) and a speed. You should create methods for checking to see if the
 * Zombie is colliding with another Zombie, checking to see if the Zombie is
 * close enough to eat the Human's brains, and movement.
 * 
 * @Kevin Seitter, Will Emmanuel
 * @kps2wu, wre9fz
 * @Section 1111
 */
public class Zombie {
	public int x;
	public int y;
	public Zombie (){
		//initial starting
		x = (int)(Math.random()*500);
		y = (int)(Math.random()*500);
	}
	
	public void move(int newx, int newy){

			if (newx > x) x++;
			if (newx < x) x--;
			if (newy > y) y++;
			if (newy < y) y--;

	}
}
