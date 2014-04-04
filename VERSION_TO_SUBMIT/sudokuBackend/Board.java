package sudokuBackend;

/**
 * An interface for a 9x9 board
 */
public interface Board {
	
	/**
	 * Gets the cell at the given row/column.
	 */
    int getCell(int row, int column);
    
    /**
     * Returns whether two boards are equal.
     * @param toCompare the board with which the comparison is made.
     * @return true if both boards are equal.
     */
    public boolean isEqual(Object toCompare);
    
    public static final int MIN_ROW = 0;
    public static final int MAX_ROW = 8;
    public static final int MIN_COLUMN = 0;
    public static final int MAX_COLUMN = 8;
}
