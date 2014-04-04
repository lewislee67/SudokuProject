package sudokuBackend;
import java.io.Serializable;
import java.util.Random;


/**
 * Implements serializable and extends AbstractBoard. Generates a completed 9x9 board used for 
 * sudoku
 * Used in conjunction with solvable boards to construct a sudoku puzzle
 */
public class CompletedBoard extends AbstractBoard implements Serializable {

	/**
	 * A constructor for completed boards constructs an abstract board and then fills in all the
	 * cells with random values using a backtracking algorithm
	 */
	public CompletedBoard() {
    	super();
    	Random numGenerator = new Random();	
    	int currValue = 0;
    	int j = 0;
    	int i = 0;
    	int [][] numIncrements = new int[9][9];
    	
    	while (i <= Board.MAX_ROW) {
    		while (j <= Board.MAX_COLUMN) {
    			if (numIncrements[i][j] == 0) {
    			    currValue = numGenerator.nextInt(9) + 1;
    		    } else {
    		    	currValue = incValue(currValue);
    		    }
    	        if (super.checkRow(currValue, i, j) && super.checkColumn(currValue, i, j)
    	        && super.checkBox(currValue, i, j)) {
    		        super.setCell(i, j, currValue);
    		        j++;
    		        if (j == 9) {
    		            break;
    		        }
    		        numIncrements[i][j] = 0;
    		    } else {
    		    	numIncrements[i][j]++;
    		    }
	        	
    	        if (numIncrements[i][j] > 8) {
    	        	super.removeValue(i,j);
    	        	if (j == 0) {
    	        		i--;
    	        		j = 8;
    	        	} else {
    	        		j--;
    	        	}
    	        	
    		        numIncrements[i][j]++;
    	        	currValue = super.getCell(i, j);
    	        }
    	    }
    		j = 0;
    		i++;
    	}
    }

	/**
	 * A private method that is used to increment values while keeping them within the bounds of
	 * 1 - 9
	 * @param value an integer that needs to be incremented
	 * @return (value mod 9) + 1
	 */
    private int incValue (int value) {
    	int newValue;
    	
    	if (value == 9) {
    		newValue = 1;
    	} else {
    		newValue = value + 1;
    	}
    	
    	return newValue;
    }
    
	private static final long serialVersionUID = 3331985536204951444L;

}
