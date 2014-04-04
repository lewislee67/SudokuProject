package sudokuBackend;

import java.io.Serializable;


/**
 * Contains information about a 9X9 board, 
 * with associated methods to set and get specific cells.
 * 
 */
public abstract class AbstractBoard implements Board, Serializable {

	/**
	 * Creates a new empty board with all cells being empty and changeable.
	 */
	public AbstractBoard() {
		board = new Cell[9][9];
		int i = 0;
		int j = 0;
		
		while (i <= MAX_ROW) {
			while (j <= MAX_COLUMN) {
	    		board[i][j] = new Cell();
	    		j++;
	    	}
	    	j = 0;
	    	i++;
	    }
	}
	
    /**
     * Gets the value of the cell at the given row/column.
     * @param row the row of the cell to return.
     * @param column the column of the cell to return.
     * @return the value stored in the cell at row/column.
     * @pre cellIsEmpty(row,column) == false.
     */
    public int getCell(int row, int column) {
    	return board[row][column].getValue(); 
    }
    
    /**
     * Returns true if the two boards are equal.
     * @param toCompare the Board to compare this Board against.
     * @return true if the values in each cell are equal for both
     * boards, false otherwise.
     */
    public boolean isEqual(Object toCompare) {
	    if (toCompare == null || !(toCompare instanceof AbstractBoard)) {
	    	return false;
	    }
	    
    	int i = 0;
	    int j = 0;
	    while (i <= MAX_ROW) {
	        while (j <= MAX_COLUMN) {
			    if(board[i][j].isEmpty() && 
			    !((AbstractBoard)toCompare).cellIsEmpty(i,j)){
				    return false;
				} else if(!board[i][j].isEmpty() && 
				((AbstractBoard)toCompare).cellIsEmpty(i,j)){
			        return false;
				} else if(board[i][j].getValue() != 
				((AbstractBoard)toCompare).getCell(i, j)) {
			        return false;
				}
				
			    j++;
			}
	        
			j = 0;
			i++;
	    }
	    
	    //if gotten to here, all cells are the same
      	return true;
    }
    
	/**
	 * Sets the cell at the given row/column to a specific value.
	 * @param row the row of the cell to modify.
	 * @param column the column of the cell to modify.
	 * @param value the value to set the cell to.
	 * @pre isChangeable(row,column) == true.
	 * @pre row >= MIN_ROW && row <= MAX_ROW.
	 * @pre column >= MIN_COLUMN && column <= MAX_COLUMN.
	 * @pre value >= MIN_VALUE && value <= MAX_VALUE.
	 */
    protected void setCell(int row, int column, int value) {
    	board[row][column].setValue(value);
    }
      
    /**
     * Returns true if the cell at row/column is empty.
     * @param row the row of the cell to check.
     * @param column the column of the cell to check.
     * @return true if the cell is empty, false otherwise.
     */
    protected boolean cellIsEmpty(int row, int column) {
    	return board[row][column].isEmpty();
    }
    
    /**
     * Sets the cell at row/column to be empty.
     * @param row the row of the cell to empty.
     * @param column the column of the cell to empty.
     * @pre isChangeable(row,column) == true.
	 * @pre row >= MIN_ROW && row <= MAX_ROW.
	 * @pre column >= MIN_COLUMN && column <= MAX_COLUMN.
     */
    protected void removeValue(int row, int column) {
    	board[row][column].setEmpty();
    }
    
    
    /**
     * Returns true if this board is a subset of the given board,
     * false otherwise.
     * @param toCompare the Board to compare this Board against.
     * @return true if every single cell of this board is either not set
     * or has the same value as the corresponding cell of the given board,
     * false otherwise.
     */
    protected boolean isSubset(AbstractBoard toCompare) {
	    int i = 0;
	    int j = 0;
	    
	    while (i <= MAX_ROW) {
		    while (j <= MAX_COLUMN) {
			    if(board[i][j].isEmpty()){
			        //do nothing
			    } else if(toCompare.cellIsEmpty(i,j)){
				    return false;
			    } else if(board[i][j].getValue() != toCompare.getCell(i, j)){
			    	return false;
		    	}
			
			    j++;
		    }
		    j = 0;
		    i++;
	    }
        //if gotten to here, all cells are the same
        return true;
    }
    
    /**
     * Sets the cell at row/column to be changeable.
     * @param row the row of the cell to make changeable.
     * @param column the column of the cell to make changeable.
     */
    protected void setChangeable(int row, int column) {
    	board[row][column].setChangeable(true);
    }
    
    /**
     * Sets the cell at row/column to be unchangeable.
     * @param row the row of the cell to make unchangeable.
     * @param column the column of the cell to make unchangeable.
     */
    protected void setUnchangeable(int row, int column) {
    	board[row][column].setChangeable(false);
    }
    
    /**
     * Returns true if the cell at row/column is changeable,
     * false otherwise.
     * @param row the row of the cell to inspect.
     * @param column the column of the cell to inspect.
     * @return true if the cell at row/column is changeable,
     * false otherwise.
     */
    protected boolean isChangeable(int row, int column) {
    	return board[row][column].isChangeable();
    }
    
    /**
     * A method to check the row of a board for the existence of a value input
     * @param input the number that is checked
     * @param row the row that the number is checked against.
     * @return true if the row contains the value of input else false
     */
    protected boolean checkRow(int input, int row, int column) {
    	for (int i = MIN_COLUMN; i < MAX_COLUMN; i++) {
    		if (!board[row][i].isEmpty() && board[row][i].getValue() == input
    			&& column != i) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * A method to check the column of a board for the existence of a value input
     * @param input the number that is checked in the column
     * @param column the column that is checked in the board
     * @return true if the column contains the value input else false
     */
    protected boolean checkColumn(int input, int row, int column) {
    	for (int i = MIN_ROW; i < MAX_ROW; i++) {
    		if (!board[i][column].isEmpty() && board[i][column].getValue() == input
    			&& i != row) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * A method to check whether a box in a board contains the value of input 
     * @param input the number that is checked in the box
     * @param row the row used to identify the box
     * @param column the column used to identify the box
     * @return true if the box contains the value input else false
     */
    protected boolean checkBox(int input, int row, int column) {
    	int i = MIN_ROW + row - row % 3;
    	int j = MIN_COLUMN + column - column % 3;
    			
    	while(i < (row + 3 - row % 3)) {
    		while(j < (column + 3 - column % 3)) {
    			if (!board[i][j].isEmpty() && board[i][j].getValue() == input &&
    			i != row && j != column) {
    				return false;
    			}
    			
    			j++;
    		}
    		
    		j = MIN_COLUMN + column - column % 3;
    		i++;
    	}
    	
    	return true;
    }
    
    /**
     * A method used to debug boards
     */
    public void printBoard() {
    	int i = 0;
    	int j = 0;
    	
    	while (i < 9) {
    		while (j < 9) {
    			if (!cellIsEmpty(i,j)) {
    			    System.out.print(getCell(i,j) + " ");
    			} else {
    				System.out.print("  ");
    			}
    			j++;
    		}
    		System.out.print("\n");
    		j = 0;
    		i++;
    	}
    }

    private Cell[][] board;
	private static final long serialVersionUID = -4022342899785321245L;
}
