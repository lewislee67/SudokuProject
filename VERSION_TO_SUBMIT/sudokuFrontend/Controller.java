package sudokuFrontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import sudokuBackend.Difficulty;

/**
 * The Controller class defines (and appropriately binds) many 
 * action listeners that will update a given InfoManager, GameManager
 * and ViewComponents objects according to the user input.
 *
 */
public class Controller {

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------------Public methods--------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
	
	/**
	 * Creates a new Controller object and initializes the given InfoManager,
	 * GameManger, and ViewComponents to be at the beginning of an easy game.
	 * @param view the given object which displays information to the user.
	 * @param game the given object which holds information about the current state of the 
	 * game.
	 * @param info the given object which holds extra information about the game.
	 */
	public Controller(ViewComponents view, GameManager game, InfoManager info) {
		sudokuView = view;
		sudokuGame = game;
		sudokuInfo = info;

		loadingGame = false;
		sudokuInfo.loadHighscores();
		initialise(Difficulty.EASY);
		
		GameMenuListener[] gameMenuListener = new GameMenuListener[10];
		ThemeMenuListener[] themeMenuListener = new ThemeMenuListener[4];
		OptionsMenuListener[] optionsMenuListener = new OptionsMenuListener[4];
		HelpMenuListener[] helpMenuListener = new HelpMenuListener[2];
		
		for (int i = 0; i < 10; i++) {
			gameMenuListener[i] = new GameMenuListener(i);
		}
		
		for (int i = 0; i < 4; i++) {
			themeMenuListener[i] = new ThemeMenuListener(i);
			optionsMenuListener[i] = new OptionsMenuListener(i);
		}
		
		for (int i = 0; i < 2; i++) {
			helpMenuListener[i] = new HelpMenuListener(i);
		}
		
		
		sudokuView.addGameMenuListener(gameMenuListener);
		sudokuView.addThemeListener(themeMenuListener);
		sudokuView.addOptionsMenuListener(optionsMenuListener);
		sudokuView.addHelpMenuListener(helpMenuListener);
		
		for(int i = 0; i < 9; i++) {
		    sudokuView.addKeypadListener(i, new ButtonPressedListener(i + 1));
		}
		
		sudokuView.addDeleteListener(new DeletePressedListener());
		sudokuView.addPencilListener(new PencilPressedListener());
		sudokuView.addInfoAreaListener(new EmptySelectedListener());
		
	}
	
	/**
	 * Starts a new game of the given difficulty or updates the view
	 * if a game has just been loaded.
	 * @param difficulty the difficulty of the new game.
	 */
	private void initialise(Difficulty difficulty) {	
		if (!loadingGame) {
		    sudokuGame.newGame(difficulty);
		    sudokuInfo.initialise(difficulty);
		}
		sudokuView.initialise(difficulty);
		loadingGame = false;
		
		setViewCells();
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudokuGame.isChangeable(i, j)) {
					sudokuView.addCellListener(i, j, new CellSelectedListener(i, j));
				} else {
					sudokuView.addCellListener(i, j, new EmptySelectedListener());
				}
			}
		}
		
		sudokuView.setTime(sudokuInfo.getTime());
		sudokuView.addKeyListener(new KeyboardPressed());
		timer = new Timer(1000, new TimerEventListener());
		timer.start();
	}
	
	/**
	 * Ends a game and does appropriate end of game cleanup.
	 */
	private void finishGame() {

		timer.stop();	
		if (sudokuInfo.isHighscore()) {
			String name = (String)JOptionPane.showInputDialog(
					sudokuView, "Highscore!  What is your name?");
			sudokuInfo.addScore(name);
			sudokuInfo.saveHighscores();
			Difficulty difficulty = sudokuInfo.getDifficulty();
			sudokuView.displayHighscores(sudokuInfo.getHighscoreStrings(difficulty));
		} else {
			sudokuView.displayMessage("You Win!");
		}
		initialise(sudokuInfo.getDifficulty());
	}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------------Private methods-------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
	
	/**
	 * Updates the view to reflect the current state of the GameManager.
	 */
	private void setViewCells() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!sudokuGame.isChangeable(i, j)) {
					sudokuView.setPermanentCell(i, j, sudokuGame.getCell(i, j));
				} else if (sudokuGame.getCell(i, j) != 0) {
					sudokuView.toggleCell(i, j, sudokuGame.getCell(i, j));
				}
			}
		}
	}

	/**
	 * Toggles the active cell with the specified value.
	 * If the value equals the value previously in the cell, the cell will be set to empty.
	 * Otherwise it will be set to hold the given value.
	 * @param value the value with which to toggle the active cell.
	 */
	private void toggleActiveCell(int value) {
		if (activeRow != NULL && activeColumn != NULL) {
			if (sudokuGame.isChangeable(activeRow, activeColumn)) {
			    if (markMode) {
				    sudokuView.addPencilCell(activeRow, activeColumn, value);
			    } else {
				    sudokuGame.toggleCell(activeRow, activeColumn, value);
				    sudokuView.toggleCell(activeRow, activeColumn, value);
			    }
			}
		}
	}
	
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------Action Listener Definitions-------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
  /**
   * An action listener which reacts differently according to which 
   * GameMenuItem it is bound to.
   *
   */
    private class GameMenuListener implements ActionListener {

		int option;
		
		public GameMenuListener(int option) {
			this.option = option;
		}
		public void actionPerformed(ActionEvent e) {
			if (option >= 0 && option <= 3) {
				timer.stop();
			}
			
			if (option == 0) {
				initialise(Difficulty.EASY);
			} else if (option == 1) {
				initialise(Difficulty.MEDIUM);
			} else if (option == 2) {
				initialise(Difficulty.HARD);
			} else if (option == 3) {
				initialise(Difficulty.VERY_HARD);
			} else if (option == 4) {
				sudokuGame.save();
				sudokuInfo.save();
				sudokuView.displayMessage("Save successful");
			} else if (option == 5) {
				sudokuGame.load();
				sudokuInfo.load();
				loadingGame = true;
				sudokuView.setTime(sudokuInfo.getTime());
				timer.stop();
				initialise(sudokuInfo.getDifficulty());
			} else if (option == 6) {
				sudokuView.dispose();
			}	
		}
		
	}

    /**
     * An action listener which reacts differently according to which 
     * ThemeMenuItem it is bound to.
     *
     */
    
	private class ThemeMenuListener implements ActionListener {

		int option;
		
		public ThemeMenuListener(int option) {
			this.option = option;
		}
		public void actionPerformed(ActionEvent e) {
			sudokuView.setTheme(option);
		}
	}
	
   /**
	* An action listener which reacts differently according to which 
	* OptionMenuItem it is bound to.
	*
	*/
	private class OptionsMenuListener implements ActionListener {

		int option;
		
		public OptionsMenuListener(int option) {
			this.option = option;
		}
		public void actionPerformed(ActionEvent e) {
			if (option == 0) {
				sudokuView.displayHighscores(sudokuInfo.getHighscoreStrings(Difficulty.EASY));
			} else if (option == 1) {
				sudokuView.displayHighscores(sudokuInfo.getHighscoreStrings(Difficulty.MEDIUM));
			} else if (option == 2) {
				sudokuView.displayHighscores(sudokuInfo.getHighscoreStrings(Difficulty.HARD));
			} else if (option == 3) {
				sudokuView.displayHighscores(sudokuInfo.getHighscoreStrings(Difficulty.VERY_HARD));
			}
		}
	}
	
   /**
	* An action listener which reacts differently according to which 
	* HelpMenuItem it is bound to.
	*
	*/
	private class HelpMenuListener implements ActionListener {

		int option;
		public HelpMenuListener(int option) {
			this.option = option;
		}
		public void actionPerformed(ActionEvent e) {
			if (option == 0) {
				sudokuView.displayHowToPlay();
			} else if (option == 1) {
				sudokuView.displayAbout();
			}
		}
		
	}
    
	private class EmptySelectedListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if(activeRow != NULL && activeColumn != NULL) {
				sudokuView.unselectCell(activeRow, activeColumn);
				activeRow = NULL;
				activeColumn = NULL;
			}
		}
	}
	
	/**
	 * An action listener that changes the current active row/column.
	 * The row/column will change when the object to which the cell listener
	 * is bound is clicked.
	 */
	private class CellSelectedListener extends MouseAdapter {
		
		public CellSelectedListener (int row, int column) {
			this.row = row;
			this.column = column;
		}
		
		public void mousePressed(MouseEvent e) {
			if (activeRow != NULL && activeColumn != NULL) {
			    sudokuView.unselectCell(activeRow, activeColumn);
			}
			activeRow = row;
			activeColumn = column;
			sudokuView.selectCell(row, column);
		}
		
		int row;
		int column;
	}
	
	/**
	 * An action listener that toggles the active cell depending
	 * on what the button it is bound to.
	 */
	private class ButtonPressedListener implements ActionListener {
		public ButtonPressedListener(int buttonValue) {
			this.buttonValue = buttonValue;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			toggleActiveCell(buttonValue);
			if (sudokuGame.isComplete()) {
				finishGame();
			}
		}
		
		private int buttonValue;
	}
	
	/**
	 * An action listener that deletes the active cell at appropriate
	 * action events.
	 */
	private class DeletePressedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sudokuGame.clearCell(activeRow, activeColumn);
			sudokuView.clearCell(activeRow, activeColumn);
		}
	}

	/**
	 * An action listener that toggles pencil mark mode at every
	 * action event.
	 */
	private class PencilPressedListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			markMode = !markMode;
			sudokuView.togglePencilMarkMode();
		}
	}
	
	/**
	 * A keyboard listener that modifies sudokuGame
	 * and sudokuInfo according to the key pressed.
	 */
	private class KeyboardPressed extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int buttonPressed = e.getExtendedKeyCode();
			
			if (buttonPressed == KeyEvent.VK_UP) {
				changeRow(-1);
			} else if (buttonPressed == KeyEvent.VK_DOWN) {
				changeRow(1);
			} else if (buttonPressed == KeyEvent.VK_LEFT) {
				changeColumn(-1);
			} else if (buttonPressed == KeyEvent.VK_RIGHT) {
				changeColumn(1);
		    } else if (e.getKeyChar() > '0' && e.getKeyChar() <= '9') {
		    	toggleActiveCell(Character.getNumericValue(e.getKeyChar()));
		    	if (sudokuGame.isComplete()) {
					finishGame();
				}
		    } else if (buttonPressed == KeyEvent.VK_BACK_SPACE 
		    		|| buttonPressed == KeyEvent.VK_DELETE) {
		    	sudokuGame.clearCell(activeRow, activeColumn);
		    	sudokuView.clearCell(activeRow, activeColumn);
		    } else if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
		    	markMode = !markMode;
		    	sudokuView.togglePencilMarkMode();
		    }
			
			if (activeRow != NULL && activeColumn != NULL) {
				sudokuView.selectCell(activeRow, activeColumn);
			}
		}
		
		private void changeRow(int num) {
			sudokuView.unselectCell(activeRow, activeColumn);
			activeRow = (activeRow + num + 9) % 9;
			while (!sudokuGame.isChangeable(activeRow,activeColumn)) {
				activeRow = (activeRow + num + 9) % 9;
			}
		}
		
		private void changeColumn(int num) {
			sudokuView.unselectCell(activeRow, activeColumn);
			activeColumn = (activeColumn + num + 9) % 9;
			while (!sudokuGame.isChangeable(activeRow,activeColumn)) {
				activeColumn = (activeColumn + num + 9) % 9;
			}
		}
	}
	
	/**
	 * An action listener class that increments the timer counter
	 * stored in the sudokuInfo object at every time interval.
	 */
	private class TimerEventListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			sudokuInfo.incTime();
			sudokuView.setTime(sudokuInfo.getTime());
		}
		
	}
	

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------------Private fields--------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
	private final int NULL = -1;
	
	private int activeRow;
	private int activeColumn;
	private boolean markMode;
	
	private ViewComponents sudokuView;
	private GameManager sudokuGame;
	private InfoManager sudokuInfo;
	private Timer timer;
	private boolean loadingGame;
}

