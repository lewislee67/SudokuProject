package sudokuFrontend;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Highscores class stores and controls data relating to a set of high scores.
 */
public class Highscores implements Serializable {
	
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------------Public methods--------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
	/**
	 * Constructs a new empty set of ten high scores
	 */
	public Highscores() {
		scores = new ArrayList<Score>(10);
	}
	/**
	 * Adds a score to this set of high scores
	 * 
	 * @param toAdd Score to add
	 */
	public void addScore(Score toAdd) {
        scores.add(toAdd);
        Collections.sort(scores);
        if (scores.size() > 10) {
        	scores.remove(10);
        }
	}
	/**
	 * Returns the score at a specified position
	 * 
	 * @param position Position of score
	 * @return Score at specified position
	 */
    public Score getScore(int position) {
	    return scores.get(position - 1);
    }
    /**
     * Returns the string of a score at a given position, for displaying purposes
     * 
     * @param position Position of score
     * @return String of score
     */
    public String getScoreString(int position) {
    	if (position > scores.size()) {
    		return position + ": " + "--";
    	} else {
    		return position + ": " + scores.get(position - 1).toString();
    	}
    }
	/**
	 * Converts a score to a string
	 */
    public String toString() {
    	String scoreString = "";
    	
    	for(int i = 1; i <= 10; i++) {
    		scoreString = scoreString + i + ": ";
    		if (scores.size() >= i) {
    			scoreString = scoreString + scores.get(i).getName() + "\n";
    		} else {
    			scoreString = scoreString + "--" + "\n";
    		}
    	}
    	
    	return scoreString;
    }
    /**
     * Returns the number of scores in the set of high scores
     * 
     * @return Number of scores
     */
    public int numScores() {
    	return scores.size();
	}
	/**
	 * Returns whether a scores is a high score or not in the set of high scores
	 * 
	 * @param score Score
	 * @return True or False
	 */
	public boolean isHighscore(int score) {
		if (scores.size() < 10) {
			return true;
		} else {
			return score < scores.get(9).getScore();
		}
	}
	
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------------Private fields--------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
	private static final long serialVersionUID = 8146980368943222408L;
	private ArrayList<Score> scores;
	
}


