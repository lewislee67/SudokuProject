package sudokuBackend;

/**
 * Difficulty can take on four possible values,
 * EASY, MEDIUM, HARD, or VERY HARD. The enum has associated
 * methods to get the string and integer representation of the given
 * Difficulty.
 */
public enum Difficulty {
    EASY ("Easy", 1),
    MEDIUM ("Medium", 2),
    HARD ("Hard", 3),
    VERY_HARD ("Very Hard", 4);
    
    private Difficulty(String difficultyString, int value) {
    	this.difficultyString = difficultyString;
    	difficultyValue = value;
    }
    
    /**
     * Returns the string representation of this
     * difficulty.
     * @return the string representation of this
     * difficulty.
     */
    public String toString() {
    	return difficultyString;
    }
    
    /**
     * Returns the integer representation of this
     * difficulty.
     * @return 1 if the difficulty is easy, 2 for medium,
     * 3 for hard, 4 for very hard.
     */
    public int getValue() {
    	return difficultyValue;
    }
    
    private int difficultyValue;
    private String difficultyString;
}
