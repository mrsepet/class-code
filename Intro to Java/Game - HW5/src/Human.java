/**
 * Human.java
 * 
 * The player's character. It should have a location, a location to which it is
 * trying to go, a relative speed, and a number of bombs. You should create
 * appropriate methods for movement, changing the target location,
 * incrementing/decrementing bombs, and all appropriate constructors.
 * 
 * @Kevin Seitter, Will Emmanuel
 * @kps2wu, wre9fz
 * @Section 1111
 */
public class Human {
	public int x;
	public int y;
	public Human (){
		//initial starting
		x = 250;
		y = 250;
	}
	
	public void move(int newx, int newy){
	
			if (newx > x) x++;
			if (newx < x) x--;
			if (newy > y) y++;
			if (newy < y) y--;
		
	}
}
