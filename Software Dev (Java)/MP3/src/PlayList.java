/*
 * Name: Benjamin Edgar && Will Emmanuel
 * Email ID: bde2wd && wre9fz
 * Assignment: Homework 2
 * Lab Section: 103
 * No Other Collaborators
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayList implements Playable {
	// Fields
	private String name;
	private ArrayList<Playable> playableList;

	// Constructors
	public PlayList() {
		this.name = "Untitled";
		playableList = new ArrayList<Playable>();
	}

	public PlayList(String newName) {
		this.name = newName;
		playableList = new ArrayList<Playable>();
	}

	// toString
	@Override
	public String toString() {
		String s = "[ " + this.name + ": ";
		for ( Playable p : playableList )
		{
			s += p.toString() + ", ";
		}
		s += " ]";
		return s;
	}

	public void printContents() {
		for ( Playable p : playableList )
		{
			System.out.println(p.getName());
		}
	}

	// Getters/Setters
	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public ArrayList<Playable> getPlayableList() {
		return playableList;
	}

	@Override
	public int getPlayTimeSeconds() {
		int seconds = 0;
		for ( Playable p : playableList )
		{
			seconds += p.getPlayTimeSeconds();
		}
		return seconds;
	}

	public void setPlayableList(ArrayList<Playable> play) {
		this.playableList = play;
	}

	// public Song getSong(int index) {
	// if ((playableList.size() - 1) >= index && index >= 0) {
	// return playableList.get(index);
	// }
	// else {
	// return null;
	// }
	// }
	public Playable remove(int i) {
		Playable p = null;
		try
		{
			p = playableList.get(i);
			playableList.remove(i);
		}
		catch ( Exception e )
		{
			return null;
		}
		return p;
	}

	public Playable get(int i) {
		Playable p = null;
		try
		{
			p = playableList.get(i);
		}
		catch ( Exception e )
		{
			return null;
		}
		return p;
	}

	public boolean addSong(Song s) {
		try
		{
			playableList.add(s);
		}
		catch ( Exception e )
		{
			return false;
		}
		return true;
	}

	public boolean addPlayList(PlayList pl) {
		if ( !pl.getName().equals(this.name) )
		{
			if ( !playableList.contains(pl) )
			{
				playableList.add(pl);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			System.out.println("Cannot copy a playlist to itself");
			return false;
		}
	}

	// public Song removeSong(int index) {
	// if ((playableList.size() - 1) >= index && index >= 0) {
	// return playableList.remove(index);
	// }
	// else {
	// return null;
	// }
	// }
	// public Song removeSong(Song s) {
	// if (playableList.contains(s)) {
	// while (playableList.contains(s)) {
	// playableList.remove(s);
	// }
	// return s;
	// }
	// else {
	// return null;
	// }
	// }
	public boolean clear() {
		playableList.clear();
		return true;
	}

	public int size() {
		return playableList.size();
	}

	@Override
	public void play() {
		for ( Playable p : playableList )
		{
			p.play();
		}
	}

	@Override
	public void play(double s) {
		for ( Playable p : playableList )
		{
			p.play(s);
		}
	}

	public String totalPlayTime() {
		int h = 0;
		int m = 0;
		int s = 0;
		for ( Playable p : this.playableList )
		{
			// m = m + s1.getMinutes();
			// s = s + s1.getSeconds();
			s += p.getPlayTimeSeconds();
		}
		h = m / 60;
		m = (m % 60) + (s / 60);
		s = s % 60;
		if ( h == 0 )
		{
			if ( m >= 10 )
			{
				if ( s >= 10 )
				{
					return m + ":" + s;
				}
				else
				{
					return m + ":" + "0" + s;
				}
			}
			else
			{
				if ( s >= 10 )
				{
					return "0" + m + ":" + s;
				}
				else
				{
					return "0" + m + ":" + "0" + s;
				}
			}
		}
		else
		{
			if ( h >= 10 )
			{
				if ( m >= 10 )
				{
					if ( s >= 10 )
					{
						return h + ":" + m + ":" + s;
					}
					else
					{
						return h + ":" + m + ":" + "0" + s;
					}
				}
				else
				{
					if ( s >= 10 )
					{
						return h + ":" + "0" + m + ":" + s;
					}
					else
					{
						return h + ":" + "0" + m + ":" + "0" + s;
					}
				}
			}
			else
			{
				if ( m >= 10 )
				{
					if ( s >= 10 )
					{
						return "0" + h + ":" + m + ":" + s;
					}
					else
					{
						return "0" + h + ":" + m + ":" + "0" + s;
					}
				}
				else
				{
					if ( s >= 10 )
					{
						return "0" + h + ":" + "0" + m + ":" + s;
					}
					else
					{
						return "0" + h + ":" + "0" + m + ":" + "0" + s;
					}
				}
			}
		}
	}

	// public void sortByArtist() {
	// Collections.sort(playableList);
	// }
	public boolean loadSongs(String fileName) {

		File file = new File(fileName);
		Scanner fileReader = null;
		Song s = null;
		String song = "";
		String artist = "";
		String mp3 = "";
		String line = "";
		int minutes = 0;
		int seconds = 0;
		try
		{
			fileReader = new Scanner(file);
		}
		catch ( FileNotFoundException e )
		{
			System.out.println("Song file not found");
			return false;
		}
		try
		{
			while ( fileReader.hasNext() )
			{
				song = check(fileReader);

				artist = check(fileReader);

				line = check(fileReader);

				minutes = Integer
						.parseInt(line.substring(0, line.indexOf(':')));

				seconds = Integer
						.parseInt(line.substring(line.indexOf(':') + 1));

				mp3 = check(fileReader);

				s = new Song(song, artist, minutes, seconds, mp3);

				playableList.add(s);
			}
			System.out.println("Songs added sucessfully");
		}
		catch ( Exception e )
		{
			System.out.println("Improper song file formatting");
			return false;
		}

		// do the check for comments!!!!!

		return true;
	}

	public String check(Scanner reader) {
		String line = "";
		int index = 0;
		while ( reader.hasNext() && line.equals("") )
		{
			line = reader.nextLine().trim();
			while ( line.isEmpty() && reader.hasNext() )
			{
				line = reader.nextLine().trim();
			}
			if ( line.contains("//") )
			{
				index = line.indexOf("//");
				if ( index != 0 )
				{
					while ( line.charAt(index - 1) == ' ' )
					{
						line = line.substring(0, index - 1);
						line.trim();
						index = line.length();
					}
					if ( line.charAt(index - 1) != ' ' )
					{
						line = line.substring(0, index);
						line.trim();
					}
				}
				else
				{
					line = "";
				}
			}
		}
		return line;
	}

	@Override
	public boolean equals(Object o) {
		if ( o instanceof PlayList )
		{
			PlayList p = (PlayList) o;
			if ( this.getName().equals(p.getName()) )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	public static void main(String[] args) {
		//System.out.println("hey");
	}
}