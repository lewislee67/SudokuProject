package sudokuFrontend;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import sudokuBackend.CompletedBoard;
import sudokuBackend.Difficulty;
import sudokuBackend.SolvableBoard;

/**
 * The GameManager class stores and controls an instance of a Sudoku Game.     
 */
public class GameManager implements Serializable {
	
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------------Public methods--------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
	/**
	 * Constructs a new empty GameManager.
	 */
	public GameManager() {
    	
    }
    
	/**
	 * Creates new data for an instance of a game of specified difficulty.
	 * 
	 * @param difficulty Difficulty of new game
	 */
    public void newGame(Difficulty difficulty) {
    	completed = new CompletedBoard();
    	solvable = new SolvableBoard(completed, difficulty);
    }
    
    /**
     * Returns whether the instance of the game is complete and correct.
     * 
     * @return True if all cells are non empty and hold correct values,
     * false otherwise.
     */
    public boolean isComplete() {
    	return solvable.isEqual(completed);
    }
    
    /**
     * Returns whether a cell is changeable or not.
     * 
     * @param row the row of the cell to check.
     * @param column the column of the cell to check.
     * @return true if the cell is changeable, false otherwise.
     */
    public boolean isChangeable(int row, int column) {
    	return solvable.isChangeable(row, column);
    }
    
    /**
     * Toggles the value of a specified cell.
     * If the value in the cell prior to this method being run
     * equals the "value" argument the cell will be empty,
     * otherwise it will contain value.
     * 
     * @param row The row of the cell to toggle.
     * @param column The column of the cell to toggle.
     * @param value Value to be set
     */
    public void toggleCell(int row, int column, int value) {
    	if (!solvable.cellIsEmpty(row, column) && 
    		solvable.getCell(row, column) == value) {
    		solvable.removeValue(row, column);
    	} else {
    	    solvable.setCell(row, column, value);
    	}
    }
    
    /**
     * Returns the value of a cell in the current instance of the game
     * 
     * @param row Row of cell
     * @param column Column of cell
     * @return Value of cell
     */
    public int getCell(int row, int column) {
    	if (solvable.cellIsEmpty(row,column)) {
    		return EMPTY;
    	} else {
    	    return solvable.getCell(row, column);
    	}
    }
    /**
     * Removes the value of a cell in the current instance of the game
     * 
     * @param row Row of cell
     * @param column Column of cell
     */
    public void clearCell(int row, int column) {
    	solvable.removeValue(row, column);
    }
    
    /**
     * Returns whether the current instance of the game is correct or not.
     * 
     * @return true if the current state of the game is valid false otherwise.
     */
    public boolean isValidState() {
    	return solvable.isSubset(completed);
    }
    
    /**
     * Returns whether a move is correct or not
     * 
     * @param row Row of cell
     * @param column Column of cell
     * @param value Value of cell
     * @return True or False
     */
    public boolean isValidMove(int row, int column, int value) {
    	return value == completed.getCell(row,column);
    }
    
    /**
     * Returns whether a move is correct or not in the current instance of the game (may not be correct overall).
     * 
     * @param row Row of the cell to check.
     * @param column column of the cell to check.
     * @param value Value of 
     * @return
     */
    public boolean isClashingMove(int row, int column, int value) {
        return solvable.checkBox(value, row, column) && 
        solvable.checkRow(value, row, column) && solvable.checkColumn(value, row, column);
    }
    
    /**
     * Solves the current instance of the game
     */
    public void solve() {
    	for (int i = 0; i < 9; i++) {
    		for (int j = 0; j < 9; j++) {
    			if (solvable.isChangeable(i,  j)) {
    				solvable.setCell(i, j, completed.getCell(i, j));
    			}
    		}
    	}
    }
    /**
     * Saves the current instance of the game
     */
    public void save() {
    	try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("GameManager.ser"));
			out.writeObject(completed);
			out.writeObject(solvable);
			out.close();
		} catch (FileNotFoundException e1) {

		} catch (IOException e1) {
	
		}	
    }
    /**
     * Loads the last saved instance of a game
     */
    public void load() {
    	try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("GameManager.ser"));
			completed = (CompletedBoard)in.readObject();
			solvable = (SolvableBoard)in.readObject();
			in.close();	
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
    }
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//---------------------------------Public Fields--------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
    public static final int EMPTY = 0;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//---------------------------------Private Fields--------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
	private CompletedBoard completed;
	private SolvableBoard solvable;
	private static final long serialVersionUID = -2796793713818390903L;
}
