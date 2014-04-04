package sudokuFrontend;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The JCell class represents a cell area one the Sudoku Board
 */
public class JCell extends JPanel {
	/**
	 * Constructs a new empty JCell
	 */
	public JCell() {
    	setBackground(Color.white);
    	currentValue = new JLabel(" ");
    	currentValue.setFont(new Font("Serif", Font.PLAIN, 26));
    	pencilMarks = new JLabel[9];
    	for (int i = 0; i < 9; i++) {
    		pencilMarks[i] = new JLabel(" ");
    		pencilMarks[i].setFont(new Font("Serif", Font.PLAIN, 14));
    	}
    	setMarkMode(false);
    }
    /**
     * Sets a permanent value on the JCell
     * 
     * @param value Value to be displayed
     */
    public void setPermanentValue(int value) {
    	currentValue.setFont(new Font("Serif", Font.BOLD, 26));
    	currentValue.setText(Integer.toString(value));
    }
    /**
     * Sets a changeable value on the JCell
     * 
     * @param value Value to be displayed
     */
    public void toggleMark(int value) {
    	setMarkMode(true);
    	if (pencilMarks[value - 1].getText().equals(" ")) {
    		pencilMarks[value - 1].setText(Integer.toString(value));
    		addOrder.push(value);
    	} else {
    	    pencilMarks[value - 1].setText(" ");
    	    addOrder.remove((Integer)value);
    	}
    }
    /**
     * Removes a pencil mark from the JCell, given a value
     * 
     * @param value Value to be removed
     */
    public void removeMark(int value) {
    	pencilMarks[value - 1].setText(" ");
    }
    /**
     * Sets a changeable value to be displayed in the JCell
     * 
     * @param value Value to be displayed
     */
    public void toggleValue(int value) {  	
    	setMarkMode(false);
    	if (currentValue.getText().equals(((Integer)value).toString())) {
    		currentValue.setText(" ");
    	} else {
    		currentValue.setText(Integer.toString(value));	
    	}
    }
    /**
     * Selects this JCell
     */
    public void select() {  	
    	setBackground(Color.lightGray);
    }
    /**
     * Unselects this JCell
     */
    public void unSelect() {
    	setBackground(Color.white);
    }
    /**
     * Removes the value in this JCell
     */
    public void removeValue() {
    	currentValue.setText(" ");
    }
    /**
     * Clears a value in this JCell, or removes the last added pencil marking
     */
    public void clear() {
    	if (markMode) {
    		clearMark();
    	} else {
    		removeValue();
    	}
    }
    /**
     * Removes the last added pencil marking
     */
    public void clearMark() {
    	removeMark(addOrder.pop());
    }
    /**
     * Sets whether this JCell is in marking mode or not
     * 
     * @param isMarkMode Mode to set to
     */
    private void setMarkMode(Boolean isMarkMode) {
    	if (markMode != isMarkMode) {
    		removeAll();
    		revalidate();
    		repaint();
	    	if (isMarkMode) {
	    		markMode = true;
	    		setLayout(new GridLayout(3, 3));
	    		for(int i = 0; i < 9; i++) {
	    		    pencilMarks[i].setText(" ");
	    			add(pencilMarks[i]);
	    		}
	    		addOrder = new Stack<Integer>();
	    	} else {
	    		markMode = false;
	    		setLayout(new FlowLayout());
	    		currentValue.setText(" ");
	    		add(currentValue);
	    	}
    	}
    }
    
	private static final long serialVersionUID = -2120110035061866139L;
    private Boolean markMode;
    private JLabel currentValue;
    private JLabel[] pencilMarks;
    private Stack<Integer> addOrder;
}
