package sudokuBackend;

import java.util.Random;

/**
 * This class is an implementation of the Board interface that extends basic functionality found
 * in the AbstractBoard class, this class is used to generate solvable sudoku boards and provides
 * alternative difficulties. The solvable board generated sets all given cells to be unchangeable
 */
public class SolvableBoard extends AbstractBoard implements Board{

	/**
	 * A constructor to generate a solvable board given a completed board and a difficulty
	 * @param completeBoard a 9x9 sudoku board completely filled in 
	 * @param difficulty a difficulty of either {easy, medium, hard, very hard}
	 */
	public SolvableBoard(CompletedBoard completeBoard, Difficulty difficulty) {
    	super();
    	
    	if (difficulty == Difficulty.EASY) {
    		createArbitraryDifficult(completeBoard, Difficulty.EASY);
    	} else if (difficulty == Difficulty.MEDIUM) {
    		createArbitraryDifficult(completeBoard, Difficulty.MEDIUM);
    	} else if (difficulty == Difficulty.HARD) {
    		createHardDifficulty(completeBoard);
    	} else if (difficulty == Difficulty.VERY_HARD) {
    		createVeryHardDifficulty(completeBoard);
    	} else {
    		throw new IllegalArgumentException("Invalid difficulty.");
    	}
    	
    	setGivensUnchangeable();
    }
    
    @Override
    public void setCell(int row, int column, int value) {
    	super.setCell(row, column, value);
    }
    
    @Override
    public boolean cellIsEmpty(int row, int column) {
    	return super.cellIsEmpty(row,column);
    }
    
    @Override
    public void removeValue(int row, int column) {
    	super.removeValue(row, column);
    }
    
    @Override
    public boolean isSubset(AbstractBoard toCompare) {
    	return super.isSubset(toCompare);
    }
    
    @Override
    public boolean checkRow(int input, int row, int column) {
    	return super.checkRow(input, row, column);
    }
    
    @Override
    public boolean checkColumn(int input, int row, int column) {
    	return super.checkColumn(input, row, column);
    }
    
    @Override
    public boolean checkBox(int input, int row, int column) {
    	return super.checkBox(input, row, column);
    }
    
    @Override
    public boolean isChangeable(int row, int column) {
    	return super.isChangeable(row, column);
    }
    

    /**
     * creates a hard difficulty sudoku board; it removes some cells randomly at first after which 
     * it tries to remove every cell in order while preserving unique solutions; is also governed 
     * by execution time however it has it's own backtracking algorithm to check for unique 
     * solutions that improves performance with this removal method
     * @param completeBoard a completed to board that this method can use to build it's solvable 
     * board
     */
    private void createHardDifficulty(CompletedBoard completeBoard) {
    	Random numGenerator = new Random();
    	int operations = 0;
    	int i = 0;
    	int j = 0;
    	while (i <= Board.MAX_ROW) {
    		while (j <= Board.MAX_COLUMN) {
    			setCell(i, j, completeBoard.getCell(i, j));
    			j++;
    		}
    		j = 0;
    		i++;
    	}
    	
    	int randomRow;
    	int randomCol;
    	while (operations < 30) {
    		randomRow = numGenerator.nextInt(9);
    		randomCol = numGenerator.nextInt(9);
	    	if (!cellIsEmpty(randomRow, randomCol)) {
	    		int val = getCell(randomRow, randomCol);
	    		removeValue(randomRow, randomCol);
	    		if (checkHardSoln()){
		    		operations++;
		    	} else {
		    		setCell(randomRow, randomCol, val);
		    	}
	    	}
    	}

    	i = 0;
    	j = 0;
    	long start = System.currentTimeMillis();
    	long end = start + 1000; // 1000 ms
    	while (i <= Board.MAX_ROW && System.currentTimeMillis() < end) {
    		while (j <= Board.MAX_COLUMN && System.currentTimeMillis() < end) {
    			if (!cellIsEmpty(i, j)) {
					int val = getCell(i, j);
					removeValue(i, j);
					if (!checkHardSoln()){
			    		setCell(i, j, val);
			    	}
    			}
    			j++;
    		}
    		j = 0;
    		i++;
    	}
    }
    
    /**
     * a method to create very hard sudoku boards; governed by it's execution time it attempts to 
     * randomly remove as many cells as it can within it's time limit
     * @param completeBoard a completed board from which this method can build a solvable board
     */
	private void createVeryHardDifficulty(CompletedBoard completeBoard) {
    	int operations = 0;
    	Random numGenerator = new Random();
    	
    	int i = 0;
    	int j = 0;
    	while (i <= Board.MAX_ROW) {
    		while (j <= Board.MAX_COLUMN) { 
    			setCell(i, j, completeBoard.getCell(i, j));
    			j++;
    		}
    		j = 0;
    		i++;
    	}
    	
    	
    	int randomRow;
    	int randomCol;
    	
    	long start = System.currentTimeMillis();
    	long end = start + 1000; // 1000 ms
    	while (System.currentTimeMillis() < end) {
    		randomRow = numGenerator.nextInt(9);
    		randomCol = numGenerator.nextInt(9);
    		if (operations < 16) {
    			if (randomRow < 8 && randomCol < 8) {
					if (!cellIsEmpty(randomRow, randomCol) 
							&& !cellIsEmpty(randomRow + 1, randomCol) 
							&& !cellIsEmpty(randomRow, randomCol + 1) 
							&& !cellIsEmpty(randomRow +1, randomCol +1)) {
						int cellA = getCell(randomRow, randomCol);
						int cellB = getCell(randomRow + 1, randomCol);
						int cellC = getCell(randomRow, randomCol + 1);
						int cellD = getCell(randomRow + 1, randomCol + 1);
						removeValue(randomRow, randomCol);
						removeValue(randomRow + 1, randomCol);
						removeValue(randomRow, randomCol + 1);
						removeValue(randomRow + 1, randomCol + 1);
						if (checkNsoln()){
				    		operations+= 4;
				    	} else {
				    		setCell(randomRow, randomCol, cellA);
				    		setCell(randomRow + 1, randomCol, cellB);
				    		setCell(randomRow, randomCol + 1, cellC);
				    		setCell(randomRow + 1, randomCol + 1, cellD);
			    		}
					}
    			}
    		} else {
	    		if (!cellIsEmpty(randomRow, randomCol)) {
		    		int val = getCell(randomRow, randomCol);
		    		removeValue(randomRow, randomCol);
		    		if (checkNsoln()){
			    		operations++;
			    	} else {
			    		setCell(randomRow, randomCol, val);
			    	}
		    	}
    		}
    	}
    }
    
	/**
	 * an arbitrary difficult method to generate solvable boards, given a difficulty n 
	 * it will remove 41 + n * 4 cells from the sudoku board where n is a number between 1 - 2
	 * inclusive; removes random cells from the board to create arbitrarily difficult boards
	 * @param completeBoard a completed board from which to build the solvable board
	 * @param difficulty the difficulty of the board; either EASY(1) or MEDIUM(2)
	 */
    private void createArbitraryDifficult(CompletedBoard completeBoard, Difficulty difficulty) {
    	int operations = 0;
    	Random numGenerator = new Random();
    	
    	int i = 0;
    	int j = 0;
    	while (i <= Board.MAX_ROW) {
    		while (j <= Board.MAX_COLUMN) {
    			setCell(i, j, completeBoard.getCell(i, j));
    			j++;
    		}
    		j = 0;
    		i++;
    	}
    	
    	int attempts = 0;
    	
    	int randomRow;
    	int randomCol;
    	
    	while (operations < (41 + (difficulty.getValue()) * 4) && attempts < 15) {
    		randomRow = numGenerator.nextInt(9);
    		randomCol = numGenerator.nextInt(9);
    		if (operations < 16) {
    			if (randomRow < 8 && randomCol < 8) {
					if (!cellIsEmpty(randomRow, randomCol) 
							&& !cellIsEmpty(randomRow + 1, randomCol) 
							&& !cellIsEmpty(randomRow, randomCol + 1) 
							&& !cellIsEmpty(randomRow +1, randomCol +1)) {
						int cellA = getCell(randomRow, randomCol);
						int cellB = getCell(randomRow + 1, randomCol);
						int cellC = getCell(randomRow, randomCol + 1);
						int cellD = getCell(randomRow + 1, randomCol + 1);
						removeValue(randomRow, randomCol);
						removeValue(randomRow + 1, randomCol);
						removeValue(randomRow, randomCol + 1);
						removeValue(randomRow + 1, randomCol + 1);
						if (checkNsoln()){
				    		operations+= 4;
				    	} else {
				    		attempts++;
				    		setCell(randomRow, randomCol, cellA);
				    		setCell(randomRow + 1, randomCol, cellB);
				    		setCell(randomRow, randomCol + 1, cellC);
				    		setCell(randomRow + 1, randomCol + 1, cellD);
			    		}
					}
    			}
    		} else {
	    		if (!cellIsEmpty(randomRow, randomCol)) {
		    		int val = getCell(randomRow, randomCol);
		    		removeValue(randomRow, randomCol);
		    		if (checkNsoln()){
			    		operations++;
			    	} else {
			    		attempts++;
			    		setCell(randomRow, randomCol, val);
			    	}
		    	}
    		}
    	}
    }

    /**
     * checks for unique solutions to a board
     * @return true if there is only one solution; false otherwise
     */
    private boolean checkNsoln() {
    	boolean uniqueSoln = false;
    	num = 0;
    	if (backtrackSoln(0) == 1) {
    		uniqueSoln = true;
    	}
    	return uniqueSoln;
    }
    
    /**
     * reverse solution checker for hard difficulty, increases execution time by needing 
     * to backtrack less given hard's algorithm
     * @return true if there is only one solution; false otherwise
     */
    private boolean checkHardSoln() {
    	boolean uniqueSoln = false;
    	num = 0;
    	if (backtrackHard(80) == 1) {
    		uniqueSoln = true;
    	}
		return uniqueSoln;
	}
    
    /**
     * a variant of a backtracking algorithm to check from the bottom right corner right to left, 
     * down to up checking for unique solutions only used by hard algorithm as it improves it's 
     * execution time
     * @param index the index used to recursively find the next cell to check
     * @return 1 if there is a unique solution; 0 if no solutions(shouldn't happen); otherwise an 
     * integer greater than 1 (multiple solutions);
     */
    private int backtrackHard(int index) {
    	if (isFinished()) {
    		num++;
    		return num;
    	}
		int i = index / 9;
		int j = index % 9;
		if (cellIsEmpty(i, j)) {
			for (int k = 1; k <= 9; k++) {
				if (checkRow(k, i, j) && checkColumn(k, i, j) && checkBox(k, i, j)) {
					setCell(i, j, k);
					backtrackHard(index - 1);
					removeValue(i, j);
				}
				if (num > 1) {
					return num;
				}
			}
			return num;
		} else {
			backtrackHard(index - 1);
		}

    	return num;
	}
    
    /**
     * a recursive backtracking algorithm to check for number of solutions with early exit
     * @param b a board to check for n solutions
     * @return an integer with the number of solutions 0(no solutions); 1(unique);
     * or > 1 (multiple solutions)
     */
    private int backtrackSoln(int index) {
    	if (isFinished()) {
    		num++;
    		return num;
    	}
		int i = index / 9;
		int j = index % 9;
		if (cellIsEmpty(i, j)) {
			for (int k = 1; k <= 9; k++) {
				if (checkRow(k, i, j) && checkColumn(k, i, j) && checkBox(k, i, j)) {
					setCell(i, j, k);
					backtrackSoln(index + 1);
					removeValue(i, j);
				}
				if (num > 1) {
					return num;
				}
			}
			return num;
		} else {
			backtrackSoln(index + 1);
		}

    	return num;
    }
    
    /**
     * a method to check whether the board is at a solution state or not
     * @return true if there are no empty cells false otherwise
     */
    private boolean isFinished() {
    	boolean retval = true;
    	
    	for (int i = 0; i <= Board.MAX_ROW; i++) {
    		for (int j = 0; j <= Board.MAX_COLUMN; j++) {
    			if (cellIsEmpty(i, j)) {
    				retval = false;
    			}
    		}
    	}
    	
    	return retval;
    }

    /**
     * a method that sets all the givens to be unchangeable and all the spaces to be changeable
     */
    private void setGivensUnchangeable() {
        int i = 0;
        int j = 0;
        while (i <= Board.MAX_ROW) {
            while (j <= Board.MAX_COLUMN) {
                if (!cellIsEmpty(i,j)) {
                    setUnchangeable(i, j);
                } else {
                    setChangeable(i, j);
                }
                j++;
            }
            j = 0;
            i++;
        }
    }
    
	private static final long serialVersionUID = 5319100561708471310L;
    private int num;
}
