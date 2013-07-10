/*
 * Name: Benjamin Edgar && Will Emmanuel
 * Email ID: bde2wd && wre9fz
 * Assignment: Homework 2
 * Lab Section: 103
 * No Other Collaborators
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Mp3Player {
	private static ArrayList<PlayList> playLists;
	private static int t;
	private static int d;
	private static boolean loop;
	private static String command;
	private static String commands = 
			"---------------------------------------------\n" +
					"              AVAILABLE COMMANDS: \n" +
					"new playlist                      load songs \n" +
					"add playlist                        add song \n" +
					"play playlist                      play song \n" +
					"print contents           print main playlist \n" +
					"print all playlists                     quit " ;
	private static String greeting = 
			"---------------------------------------------\n" +
					"--------------CS2110 MP3 Player--------------\n" +
					"---------------By Will and Ben---------------\n" ;

	/*
	 * Commands: new playlist - prompts the user to enter the playlist
	 * name, then creates the playlist; load songs - prompts the user to
	 * enter the name of the playlist to add to, and then the name of the song
	 * file, then loads the songs onto the given playlist; add song - prompts
	 * the user to enter a playlist and then an MP3 file name, creates a dummy
	 * song and adds it to the playlist; add playlist - prompts the user to
	 * enter the first playlist, and then the playlist to be added, then does
	 * the adding; play playlist - prompts the user to enter what playlist they
	 * want to play, asks if they want to play the whole song, if no, prompt for
	 * a number of seconds; play song - prompts the user to enter the MP3 file
	 * name, asks if they want to play the whole song, if no, asks how many
	 * seconds, then plays that mp3 file; print contents - prompt the user to
	 * enter what playlist they want to print, then prints the contents of that
	 * playlist; print main playlist - prints out just the titles of songs and
	 * playlists in the main playlist; print all playlists - prints out the name
	 * of all playlists stored in the MP3 player; quit - quits the
	 * application
	 */
	public static void start() {
		//Print out all commands for user
		System.out.println(greeting + commands);
		Scanner keys = new Scanner(System.in);
		//Initialize main playlist and arrayList playlist
		PlayList main = new PlayList("main");
		playLists = new ArrayList<PlayList>();
		playLists.add(main);
		t = 0;
		d = 0;
		loop = true;
		command = "";
		//While loop to continuously prompt the user for commands
		while ( loop )
		{
			if ( t != 2 && d == 0)
			{
				System.out.print(
						"---------------------------------------------\n" + 
						"Command: ");
				command = keys.nextLine().toLowerCase();
				t = 0;
			}
			if ( command.equals("") )
			{
				t = 1;
				d = 0;
			}

			// New Playlist Command
			if ( command.equals("new playlist") )
			{
				newPlayList(keys);
			}

			// Load Songs Command
			if ( command.equals("load songs") )
			{
				loadSongs(keys);
			}

			// Add Song Command
			if ( command.equals("add song") )
			{
				addSong(keys);
			}

			// Play Song Command
			if ( command.equals("play song") )
			{
				playSong(keys);
			}

			// Play PlayList command
			if ( command.equals("play playlist") )
			{
				playPlayList(keys);
			}

			// Print contents command
			if ( command.equals("print contents") )
			{
				printContents(keys); 
			}

			// Print all playlists command
			if ( command.equals("print all playlists") )
			{
				printAllPlaylists(keys);
			}

			// Print main playlist command
			if ( command.equals("print main playlist") )
			{
				t = 1;
				main.printContents();
			}

			// Add Playlist command
			if ( command.equals("add playlist") )
			{
				addPlayList(keys);
			}

			// Quit Application command
			if ( command.equals("quit") )
			{
				t = 1;
				loop = false;
			}

			if ( t == 0 )
			{
				commandNotRecognized();
			}
		}
	}
	//Command not recognized
	private static void commandNotRecognized() {
		System.out.println("Command not recognized");
		System.out.println(commands);
	}

	//Print All Playlists
	private static void printAllPlaylists(Scanner keys) {
		t = 1;
		System.out.println("All Playlists:");
		for ( PlayList p : playLists )
		{
			System.out.println(p.getName());
		}
	}

	//New Playlist
	private static void newPlayList(Scanner keys) {
		t = 1;
		System.out.print("Enter playlist name: ");
		String name = keys.nextLine();
		boolean exists = false; 
		for ( PlayList p : playLists ) {
			if ( p.getName().equals(name) ) {
				exists = true; 
			}
		}
		if ( !exists ) {
			playLists.add(new PlayList(name));
			System.out.println("Playlist '" + name + "' created");
		}
		else {
			System.out.println("Playlist '" + name + "' already exists");
		}
	}

	//Load songs
	private static void loadSongs(Scanner keys) {
		t = 1;
		System.out.print("Enter playlist name: ");
		String name = keys.nextLine();
		System.out.print("Enter filename: ");
		String filename = keys.nextLine();
		boolean exists = false;
		for ( PlayList p : playLists )
		{
			if ( p.getName().equals(name) )
			{
				exists = true;
				p.loadSongs(filename);
			}
		}
		if ( !exists )
		{
			System.out.println("Playlist does not exist");
		}
	}

	//Add song
	private static void addSong(Scanner keys) {
		t = 1;
		System.out.print("Enter playlist: ");
		String name = keys.nextLine();
		System.out.print("Enter filename: ");
		String filename = keys.nextLine();
		boolean exists = false;
		File mp3 = new File(filename);
		if ( mp3.exists() )
		{
			for ( PlayList p : playLists )
			{
				if ( p.getName().equals(name) )
				{
					exists = true;
					p.addSong(new Song(filename, "", filename));
					System.out.println("Song added successfully");
				}
			}
		}
		else
		{
			exists = true;
			System.out.println("File or playlist does not exist");
		}
		if ( !exists )
		{
			System.out.println("Playlist does not exist");
		}

	}

	//Add playlist
	private static void addPlayList(Scanner keys) {
		t = 1;
		System.out.print("Enter playlist to be added: ");
		String add = keys.nextLine();
		System.out.print("Enter destination playlist: ");
		String destination = keys.nextLine();
		boolean exists = false;
		for ( PlayList p : playLists )
		{
			if ( p.getName().equals(add) )
			{
				for ( PlayList p2 : playLists )
				{
					if ( p2.getName().equals(destination) )
					{
						p2.addPlayList(p);
						exists = true;
						System.out.println("Playlist added sucessfully");
					}
				}
			}
		}
		if ( !exists )
		{
			System.out.println("At least one playlist does not exist");
		}
	}

	//Play PlayList
	private static void playPlayList(Scanner keys) {
		t = 2;
		d = 1;
		command = "";
		System.out.print("Enter playlist name: ");
		String name = keys.nextLine();
		boolean exists = false;
		boolean yn = true;
		while ( yn )
		{
			System.out.print("Play the whole songs? (Y/N) : ");
			String answer = keys.nextLine().toLowerCase();
			for ( PlayList p : playLists )
			{
				if ( p.getName().equals(name) )
				{
					exists = true;
					if ( answer.equals("y") )
					{
						System.out.println("Now playing playlist '" 
								+ name + "'");
						p.play();
						yn = false;
					}
					else
						if ( answer.equals("n") )
						{
							System.out.print("How many seconds? ");
							int s = keys.nextInt();
							System.out.println("Now playing playlist '" 
									+ name + "'");
							p.play(s);
							yn = false;
						}
						else
						{
							System.out
							.println("Command not recognized");
						}
				}
			}
			if ( !exists )
			{
				System.out.println("Playlist does not exist");
				yn = false;
			}
		}
	}

	//Play song
	private static void playSong(Scanner keys) {
		t = 2;
		d = 1;
		command = "";
		System.out.print("Enter filename: ");
		String filename = keys.nextLine();
		File mp3 = new File(filename);
		boolean yn = true;
		if ( mp3.exists() )
		{
			Song dummy = new Song("dummy title ", "dummy artist ",
					filename);
			while ( yn )
			{
				System.out.print("Play the whole song? (Y/N) : ");
				String answer = keys.nextLine().toLowerCase();
				if ( answer.equals("y") )
				{
					dummy.play();
					yn = false;
				}
				else
					if ( answer.equals("n") )
					{
						System.out.print("How many seconds? ");
						int s = keys.nextInt();
						dummy.play(s);
						yn = false;
					}
					else
					{
						System.out.println("Command not recognized");
					}
			}

		}
		else
		{
			System.out.println("File does not exist");
		}
	}

	//Print contents
	private static void printContents(Scanner keys) {
		t = 1;
		System.out.print("Enter playlist: ");
		String name = keys.nextLine();
		boolean exists = false;
		for ( PlayList p : playLists )
		{
			if ( p.getName().equals(name) )
			{
				System.out.println("'" + name + "'" + " Contents: ");
				p.printContents();
				exists = true;
			}
		}
		if ( !exists )
		{
			System.out.println("Playlist does not exist");
		}
	}

	public static void main(String[] args) {
		//Starts MP3 Player
		start();
	}

}
