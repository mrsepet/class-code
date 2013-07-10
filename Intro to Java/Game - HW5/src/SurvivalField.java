import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * SurvivalField.java
 * 
 * The SurvivalField is the field of play for the game. It passes messages
 * between the Human and the Zombies. It also picks up the commands from the
 * mouse and does the appropriate action. This is the class that will have the
 * main method to start the game.
 * 
 * @Kevin Seitter, Will Emmanuel
 * @kps2wu, wre9fz
 * @Section 1111
 */
public class SurvivalField {
	float mouseX;
	float mouseY;
	
	int cycleHumanMove;
	int cycleZombieMove;
	int cycleBomb;
	int cycleNewZombie;
	int cycleScore;
	
	int playerSpeed = 10;
	int zombieSpeed = 20;
	int bombAdditionSpeed = 100000;
	int zombieAdditionSpeed = 5000;
	int scoringSpeed = 100; //cycles
	
	static int bombs=3;
	int bombRadius = 50; //pixels
	int collisionThresholdRadius = 8; //pixels
	int initialZombies = 4;
	int score = 0;
	int zombieScore = 0;
	static boolean gameOver = false;
	static boolean finished = false;
	
	private Human player;
	private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	
	private SimpleCanvas canvas;
	private InfoFrame output;
	
	



	//========== CONSTRUCTOR ==========//
	public SurvivalField() {
		canvas = new SimpleCanvas(500, 500, this);
		output = new InfoFrame(this);
		
		player = new Human();
		output.println("New Plater Created! You start with " + bombs + " bombs!");
		for (int i=0; i<initialZombies; i++) {
			zombies.add(new Zombie());
		}
	}

	
	//========== MOUSE STUFF ==========//
	public void mouseAction(float x, float y, int button) throws Exception {
		mouseX = x;
		mouseY = y;
		if (button == -1) {
			
		}

		if (button == 1) {
			detonateBomb();
		}

		if (button == 3) {
			
		}

	}

	//========= BOMBS ==========//
	public void detonateBomb() throws Exception {
		if (bombs>0) {
			for (int i=0; i<zombies.size(); i++){
				double distance = Math.sqrt(Math.pow(zombies.get(i).x - player.x, 2) + Math.pow(zombies.get(i).y - player.y, 2));
				if (distance < bombRadius) {zombies.remove(i); zombieScore++; i--;}
			}
			bombs--;
			
			File bombFile = new File("Explosion.wav");
			AudioInputStream bombSound = AudioSystem.getAudioInputStream(bombFile);
			Clip bombClip = AudioSystem.getClip();
			bombClip.open(bombSound);
			bombClip.start();
			
			output.println("BOOM! You dropped a bomb! You now have " +bombs + " bombs!");
		}
		else output.println("Out of bombs!");
		
	}
	
	public boolean willCollide(int zombieNumber) throws Exception {
		boolean collisionIminent = false;
		for (int i=0; i<zombies.size(); i++){
			if (i != zombieNumber) {
				double distance = Math.sqrt(Math.pow(zombies.get(i).x - zombies.get(zombieNumber).x, 2) + Math.pow(zombies.get(i).y - zombies.get(zombieNumber).y, 2));
				if (distance<collisionThresholdRadius) {
					collisionIminent = true;
					if (zombies.get(i).x > zombies.get(zombieNumber).x) {zombies.get(i).x++; zombies.get(zombieNumber).x--;}
					if (zombies.get(i).x < zombies.get(zombieNumber).x) {zombies.get(zombieNumber).x++; zombies.get(i).x--;}
					if (zombies.get(i).y > zombies.get(zombieNumber).y) {zombies.get(i).y++; zombies.get(zombieNumber).y--;}
					if (zombies.get(i).y < zombies.get(zombieNumber).y) {zombies.get(zombieNumber).y++; zombies.get(i).y--;}
					
					File gruntFile = new File("Grunts.wav");
					AudioInputStream gruntSound = AudioSystem.getAudioInputStream(gruntFile);
					Clip gruntClip = AudioSystem.getClip();
					gruntClip.open(gruntSound);
					gruntClip.start();
				}
			}
		}
		return collisionIminent;
	}
	
	public boolean checkEatBrains(int zombieNumber) {
		boolean collisionIminent = false;
		for (int i=0; i<zombies.size(); i++){
			double distance = Math.sqrt(Math.pow(zombies.get(i).x - player.x, 2) + Math.pow(zombies.get(i).y - player.y, 2));
			if (distance<collisionThresholdRadius) {
				collisionIminent = true;
			}
		}
		gameOver = collisionIminent;
		return collisionIminent;
	}

	/**
	 * This is the main drawing function that is automatically called whenever
	 * the canvas is ready to be redrawn. The 'elapsedTime' argument is the
	 * time, in seconds, since the last time this function was called.
	 * 
	 * Other things this method should do: - draw the zombies and the human on
	 * the screen - tell the zombies and human to move - check to see if the
	 * game is over after they move - halt the game if the game is over - update
	 * the score for every cycle that the user is alive - add a new zombie every
	 * 5000 cycles - add a new bomb every 100000 cycles
	 * 
	 * 
	 */
	public void draw(Graphics2D g, float elapsedTime) throws Exception {
		if (!gameOver){
			cycleHumanMove++;
			cycleZombieMove++;
			cycleBomb++;
			cycleNewZombie++;
			cycleScore++;
		}
		else if (!finished) endOfGame();
		
		if (cycleHumanMove==playerSpeed) {
			player.move((int)mouseX, (int)mouseY);
			cycleHumanMove=0;}
		if (cycleZombieMove==zombieSpeed) {
			for (int i=0; i<zombies.size(); i++){
				if (!willCollide(i)) zombies.get(i).move(player.x, player.y);
				checkEatBrains(i);
			}
			cycleZombieMove=0;
		}
		if (cycleBomb==bombAdditionSpeed) {bombs++; cycleBomb=0;}
		if (cycleNewZombie==zombieAdditionSpeed) {zombies.add(new Zombie()); cycleNewZombie=0;}
		if (cycleScore==scoringSpeed) {score++; cycleScore=0;}

		canvas.drawDot(g, player.x, player.y, Color.green);
		for (int i=0; i<zombies.size(); i++){
			canvas.drawDot(g, zombies.get(i).x, zombies.get(i).y, Color.red);
			
		canvas.drawText(g, "Score: "+score, 20, 25, 25, Color.cyan);
		canvas.drawText(g, "Zombies killed: "+zombieScore, 20, 200, 25, Color.yellow);
		if(gameOver) {canvas.drawText(g, "GAME", 100, 100, 180, Color.red);canvas.drawText(g, "OVER", 100, 105, 300, Color.red);}
		}
	}
	
	
	public void endOfGame() throws Exception {
		finished = true;
		output.println("NOMNOMNOMNOMNOMNOM");
		output.println("");
		output.println("Game over.");
		output.println("Score: " + score);
		output.println("Zombies Killed: " + zombieScore);
		
		File oooohFile = new File("Ooooh.wav");
		AudioInputStream oooohSound = AudioSystem.getAudioInputStream(oooohFile);
		Clip oooohClip = AudioSystem.getClip();
		oooohClip.open(oooohSound);
		oooohClip.start();
	}

	
	//========= MAIN METHOD =========//
	public static void main(String[] args) throws Exception {
		
		SurvivalField simulator = new SurvivalField();
		simulator.play();
		
	}

	
	//========== START THE GAME ==========//
	public void play() {
		canvas.setupAndDisplay();
	}
}
