package sudokuFrontend;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import sudokuBackend.Difficulty;
/**
 * An interface for a SudokuLayoutManager
 */
public interface SudokuLayoutManager {
	JPanel createBoard(JCell[][] cells);
	JPanel createGameArea(JPanel board, JButton[] keypad, JButton deleteButton, JToggleButton markButton);
	JPanel createInfoArea(JLabel timeLabel, Difficulty difficulty);
	JMenuBar createMenuBar(JMenu gameMenu, JMenu optionsMenu, JMenu helpMenu);
	void setFrame(JFrame frame, JPanel gameArea, JPanel infoFrame, JMenuBar menuBar);
  void setTheme(int themeNo);
}
