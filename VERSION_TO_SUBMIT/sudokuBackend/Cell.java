package sudokuBackend;
import java.io.Serializable;

/**
 * A class that represents a cell in a board holds a value, whether it's empty and whether it's
 * changeable
 */
public class Cell implements Serializable {
    

	/**
	 * Constructor to generate a cell set to be 
	 * changeable with no initialized value.
	 */
	public Cell() {
    	empty = true;
    	changeable = true;
    }
	
	/**
	 * Constructor to generate a cell with an initialized value
	 * @param value an integer value that is to be stored in the cell
	 */
	public Cell(int value) {
		empty = false;
		changeable = true;
		this.value = value;
	}
    
    /**
     * A setter that set's the value of this cell to the value provided in the methods argument
     * @param value the value to be set on calling the method
     * @pre changeable == true.
     * @throws IllegalStateException
     */
    public void setValue(int value) {
    	if (!changeable) {
    		throw new IllegalStateException("Cell is not changeable.");
    	} else {
    	    this.value = value;
    	    empty = false;
    	}
    }
    
    /**
     * A method that sets changeable to the boolean value in canChange.
     * Allows cells to be changeable without throwing an exception
     * @param canChange a true or false boolean value indicating whether changeable will be either
     * true or false
     */
    public void setChangeable(boolean canChange) {
    	changeable = canChange;
    }
    
    /**
     * A method to clear a cell
     * @pre changeable == true.
     * @throws IllegalStateException
     */
    public void setEmpty() {
    	if (!changeable) {
    		throw new IllegalStateException("Cell is not changeable.");
    	} else {
    	    empty = true;
    	}
    }
    
    /**
     * A method to check whether the cell contains any element
     * @return returns true if empty == true; false if empty == false
     */
    public boolean isEmpty() {
    	return empty;
    }
    
    /**
     * A method to check whether a cell is changeable
     * @return returns true if changeable == true; false if changeable == false
     */
    public boolean isChangeable() {
        return changeable;
    }
    
    /**
     * A method that gets the value stored in the cell
     * @pre empty == false
     * @throws IllegalStateException
     * @return the value stored in the cell
     */
    public int getValue() {
    	if (empty) {
    		throw new IllegalStateException("Cell is empty.");
    	} else { 
    	    return value;
    	}
    }

	private static final long serialVersionUID = 6611221468310821663L;
    private int value;
    private boolean changeable;
    private boolean empty;
}
