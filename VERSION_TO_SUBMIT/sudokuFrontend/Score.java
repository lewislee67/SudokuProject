package sudokuFrontend;

import java.io.Serializable;

/**
 * The Score class stores the name and score of a score holder
 */
public class Score implements Comparable<Score>, Serializable {

	/**
	 * Constructs a new Score, given a name and score
	 * 
	 * @param score Score
	 * @param name Name of score holder
	 */
	public Score(int score, String name) {
		this.score = score;
		this.name = name;
	}
	/**
	 * Returns the score this score holder achieved
	 * 
	 * @return Score Score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Returns the name of this score holder
	 * 
	 * @return Name of score holder
	 */
	public String getName() {
		return name;
	}
	/**
	 * Compares this scores value to another. Returns 0 if they are equal, 1 if this score is
	 * lower, -1 if this score is higher.
	 */
	public int compareTo(Score other) {
        if (score == other.getScore()) {
        	return 0;
        } else if (score < other.getScore() ) {
        	return -1;
        } else {
        	return 1;
        }
	}
	
	public String toString() {
		return name + "    " + score/3600 + (score/60)%60 + score%60;
	}
	
	private static final long serialVersionUID = -3743515899257465064L;
	private int score;
	private String name;
}
