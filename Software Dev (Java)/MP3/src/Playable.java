public interface Playable {
	public void play();

	public void play(double seconds);

	public String getName(); // returns the name or title of Playable object.

	public int getPlayTimeSeconds();
}
