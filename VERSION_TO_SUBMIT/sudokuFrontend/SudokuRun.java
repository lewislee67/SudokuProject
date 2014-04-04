package sudokuFrontend;

import javax.swing.SwingUtilities;

/**
 * Runs a game of sudoku and brings up the associated GUI.
 *
 */
public class SudokuRun implements Runnable {
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new SudokuRun());
    }
	
	public void run() {
		GameManager sudokuModel = new GameManager();
    	InfoManager sudokuInfo = new InfoManager();
    	ViewComponents sudokuView = new ViewComponents(new StandardLayoutManager());
    	@SuppressWarnings("unused")
		Controller sudokuController = new Controller(sudokuView, sudokuModel, sudokuInfo);
        sudokuView.setVisible(true);
	}
}
