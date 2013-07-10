/*
 * Name: Benjamin Edgar && Will Emmanuel
 * Email ID: bde2wd && wre9fz
 * Assignment: Homework 2
 * Lab Section: 103
 * No Other Collaborators
 */

import java.io.File;

import edu.virginia.cs2110.Mp3FilePlayer;

public class Song implements Comparable<Song>, Playable {
	// Fields
	private String artist;
	private String title;
	private int minutes;
	private int seconds;
	private String mp3File;

	// Sort
	@Override
	public int compareTo(Song item2) {
		int val = artist.compareTo(item2.artist);
		if ( val != 0 )
		{
			return val;
		}
		val = title.compareTo(item2.title);
		if ( val != 0 )
		{
			return val;
		}
		val = ((minutes * 60) + seconds)
				- ((item2.minutes * 60) + item2.seconds);
		return val;
	}

	// .Equals method
	@Override
	public boolean equals(Object o) {
		if ( !(o instanceof Song) )
		{
			return false;
		}
		Song song2 = (Song) o;
		return (this.title.equals(song2.title)
				&& this.artist.equals(song2.artist)
				&& (this.minutes == song2.minutes)
				&& (this.seconds == song2.seconds) && this.mp3File
					.equals(song2.mp3File));
	}

	// .toString Method
	@Override
	public String toString() {
		return "{Song: title=" + title + " artist=" + artist + "Length: "
				+ minutes + ":" + seconds + "}";
	}

	// Getters
	@Override
	public String getName() {
		return this.title;
	}

	public String getTitle() {
		return this.title;
	}

	public String getArtist() {
		return this.artist;
	}

	public int getMinutes() {
		return this.minutes;
	}

	public int getSeconds() {
		return this.seconds;
	}

	public String getMp3File() {
		return this.mp3File;
	}

	// Setters
	public void setTitle(String title) {
		this.title = title;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	// Constructors
	public Song(String title, String artist, String mp3File) {
		this.artist = artist;
		this.title = title;
		this.mp3File = mp3File;

	}

	public Song(String title, String artist, int minutes, int seconds,
			String mp3File) {
		this.artist = artist;
		this.title = title;
		while ( seconds >= 60 )
		{
			seconds = seconds % 60;
			minutes++;
		}
		this.minutes = minutes;
		this.seconds = seconds;
		this.mp3File = mp3File;
	}

	// Copy song
	public Song(Song s) {
		this.title = s.getTitle();
		this.artist = s.getArtist();
		this.minutes = s.getMinutes();
		this.seconds = s.getSeconds();
		this.mp3File = s.getMp3File();
	}

	// totalSeconds
	public int totalSeconds(Song s) {
		return ((s.getMinutes()) * 60) + s.getSeconds();
	}

	public int totalSeconds() {
		return (this.minutes * 60) + this.seconds;
	}

	@Override
	public int getPlayTimeSeconds() {
		return this.totalSeconds();
	}

	@Override
	public void play() {
		File myfile = new File(this.mp3File);
		if ( myfile.exists() )
		{
			Mp3FilePlayer mp3 = new Mp3FilePlayer(this.mp3File);
			// System.out.printf("Playing Song: artist=% - 20s title=%s\n",
			// artist, title);
			System.out.println("Now playing '" + this.title + "'");
			mp3.playAndBlock();
		}
		else
		{
			System.out.println(this.mp3File + " is not a valid location");
		}

	}

	@Override
	public void play(double s) {
		File myfile = new File(this.mp3File);
		if ( myfile.exists() )
		{
			Mp3FilePlayer mp3 = new Mp3FilePlayer(this.mp3File);
			// System.out.printf("Playing Song: artist=% - 20s title=%s\n",
			// artist, title);
			System.out.println("Now playing '" + this.title + "'");
			mp3.playAndBlock(s);
		}
		else
		{
			System.out.println(this.mp3File + " is not a valid location");
		}
	}
	// public static void main(String[] args) {
	// // Song test = new Song("Let it Be", "Beatles", 1, 05);
	// // System.out.println(test);
	// }

}
