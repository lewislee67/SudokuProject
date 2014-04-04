package sudokuFrontend;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import sudokuBackend.Difficulty;

/**
 * The InfoManager class stores and controls information related to an instance of a game, as well as high scores.
 */
public class InfoManager implements Serializable {
	/**
	 * Constructs a new InfoManager object with default settings and initializes high scores;
	 */
	public InfoManager() {
		time = 0;
		currentDifficulty = Difficulty.EASY;
		scores = new Highscores[4];
		for (int i = 0; i < 4; i++) {
			scores[i] = new Highscores();
		}
	}
	/**
	 * Sets information for a new game of given difficulty
	 * 
	 * @param difficulty Difficulty of game
	 */
	public void initialise(Difficulty difficulty) {
		time = 0;
		currentDifficulty = difficulty;
	}
    /**
     * Returns the difficulty of the current game
     * 
     * @return Difficulty of game
     */
    public Difficulty getDifficulty() {
    	return currentDifficulty;
    }
    /**
     * Returns an array of strings with the high scores of a given difficulty
     * 
     * @param difficulty Difficulty of high scores
     * @return Array of Strings representing the high scores
     */
    public String[] getHighscoreStrings(Difficulty difficulty) {
    	String[] highscoreStrings = new String[11];
    	
    	highscoreStrings[0] = difficulty.toString();
    	
    	for(int i = 1; i < 11; i++) {
    		highscoreStrings[i] = scores[difficulty.getValue() - 1].getScoreString(i);
    	}
    	
    	return highscoreStrings;
    }
    /**
     * Adds a score to the high score board of the current difficulty of the game
     * 
     * @param name Name of user
     */
    public void addScore(String name) {
    	scores[currentDifficulty.getValue() - 1].addScore(new Score(time, name));
    }
    /**
     * Returns whether the current instance of the game achieved a high score or not
     * 
     * @return true if the current game achieved a highscore, false otherwise.
     */
    public boolean isHighscore() {
    	return scores[currentDifficulty.getValue() - 1].isHighscore(time);
    }
    /**
     * Saves the current high scores
     */
    public void saveHighscores() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Highscores.ser"));
			out.writeObject(scores);
			out.close();
		} catch (FileNotFoundException e1) {

		} catch (IOException e1) {
	
		}		
    }
    /**
     * Loads the high scores
     */
    public void loadHighscores() {
   	
    	try {
			File file = new File("Highscores.ser");
			if (file.exists()) {

				ObjectInputStream in = new ObjectInputStream(new FileInputStream("Highscores.ser"));
				scores = (Highscores[])in.readObject();
				in.close();
			} else {
				file.createNewFile();
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Highscores.ser"));
				out.writeObject(scores);
				out.close();				
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace(); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); 
		}
    }
    /**
     * Saves the information of the current game
     */
    public void save() {    	
    	try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("InfoManager.ser"));
			out.writeObject((Integer)time);
			out.writeObject(currentDifficulty);
			out.close();
		} catch (FileNotFoundException e1) {

		} catch (IOException e1) {
	
		}	
    }
    /**
     * Loads the information of the last saved game
     * 
     * @return True if successful, False otherwise
     */
    public boolean load() {
		loadHighscores();
    	
    	try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("InfoManager.ser"));
			time = (Integer)in.readObject();
			currentDifficulty = (Difficulty)in.readObject();
			in.close();
				
			return true;		
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
    }
    /**
     * Increments the time of the current game
     */
	public void incTime() {
		time++;
	}
	/**
	 * Returns the time of the current game
	 * 
	 * @return Time
	 */
	public int getTime() {
		return time;
	}
    
	private static final long serialVersionUID = 1223202622207325907L;
	private int time;
	private Difficulty currentDifficulty;
	private Highscores[] scores;
}
