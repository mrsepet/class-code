/*
 * Name: Benjamin Edgar && Will Emmanuel
 * Email ID: bde2wd && wre9fz
 * Assignment: Homework 2
 * Lab Section: 103
 * No Other Collaborators
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PlayListTest {

	private PlayList p1;
	private PlayList p2;
	private Song s1;
	private Song s2;
	private Song s3;
	private Song s4;

	@Before
	public void setUp() throws Exception {
		p1 = new PlayList("p1");
		p2 = new PlayList("p2");

		s1 = new Song("t1", "a1", "s1.mp3");
		s2 = new Song("t2", "a2", "s2.mp3");
		s3 = new Song("t3", "a3", "s3.mp3");
		s4 = new Song("test1", "test2", 3, 3, "test3.mp3");
	}

	@Test
	// ID: test1
	public void testAddSong() {
		assertTrue(p1.addSong(s1));

		assertTrue(p1.getPlayableList().contains(s1));

	}

	@Test
	// ID: test2
	public void testAddPlaylist() {
		p1.addSong(s1);
		p1.addSong(s2);
		p2.addSong(s3);
		assertTrue(p2.addPlayList(p1));
		assertFalse(p2.addPlayList(p1));
		assertFalse(p2.addPlayList(p2));
	}

	@Test
	// ID: test3
	public void testLoadSongs() {
		assertTrue(p1.loadSongs("testFile1.txt"));
		assertFalse(p1.loadSongs("testFile2.txt"));
		assertFalse(p1.loadSongs("fake.txt"));

		assertTrue(p1.getPlayableList().contains(s4));

	}

}
