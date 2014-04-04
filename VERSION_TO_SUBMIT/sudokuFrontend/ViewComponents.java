package sudokuFrontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.io.File;


import sudokuBackend.Difficulty;

/**
 * The ViewComponents class deals with all aspects related to displaying and viewing
 */
public class ViewComponents extends JFrame {

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------------Public Methods--------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
	/**
	 * Constructs a new ViewComponents object given an implementation of a SudokuLayoutManager.
	 * 
	 * @param layout Implementation of SudokuLayoutManager
	 */
    public ViewComponents(SudokuLayoutManager layout) {
    	this.layout = layout;
    	initGameMenu();
    	initOptionsMenu();
    	initHelpMenu();
    	board = new JPanel();
    	gameArea = new JPanel();
    	infoArea = new JPanel();
    	
    	initKeypad();
    	deleteButton = new JButton("X");
    	deleteButton.setFocusable(false);
    	markButton = new JToggleButton("P");
    	markButton.setFocusable(false);
    	
    	pencilMarkMode = false;
    }
    /**
     * Initializes a new View for a game, given a difficulty.
     * 
     * @param difficulty Difficulty of the game
     */
    public void initialise(Difficulty difficulty) {
    	initCells();
    	remove(board);
    	remove(gameArea);
    	remove(infoArea);
    	board = layout.createBoard(cells);
    	gameArea = layout.createGameArea(board, keypad, deleteButton, markButton);
    	timeLabel = new JLabel();
    	infoArea = layout.createInfoArea(timeLabel, difficulty);
    	menuBar = layout.createMenuBar(gameMenu, optionsMenu, helpMenu);
        layout.setFrame(this, gameArea, infoArea, menuBar);
        gameArea.requestFocusInWindow();
    }
    /**
     * Sets the time on the display
     * 
     * @param time Time in seconds
     */
    public void setTime(int time) {
    	timeLabel.setText("Time: " + time/3600 + ":" + (time/60)%60 + ":" + time%60);
    }

//#################################Cell Methods#################################
    /**
     * Sets a permanent cell in the display, given a value.
     * 
     * @param row Row of cell
     * @param column Column of cell
     * @param value Value to set
     */
    public void setPermanentCell(int row, int column, int value) {
    		cells[row][column].setPermanentValue(value);
    }
    /**
     * Sets a changeable cell in the display, given a value
     *    
     * @param row Row of cell
     * @param column Column of cell
     * @param value Value to set
     */
    public void toggleCell(int row, int column, int value) {
		cells[row][column].toggleValue(value);
    }
    /**
     * Clears a specified cell in the display.
     * 
     * @param row Row of cell
     * @param column Column of cell
     */
    public void clearCell(int row, int column) {
    	cells[row][column].clear();
    }
    /**
     * Selects a specified cell in the display
     * 
     * @param row Row of cell
     * @param column Column of cell
     */
    public void selectCell(int row, int column) {
    	cells[row][column].select();
    }
    /**
     * Unselects a specified cell in the display
     * 
     * @param row Row of cell
     * @param column Column of cell
     */
    public void unselectCell(int row, int column) {
    	cells[row][column].unSelect();
    }
    /**
     * Adds a pencil marking to a specified cell, given a value
     * 
     * @param row Row of cell
     * @param column Column of cell
     * @param value Value to be set
     */
    public void addPencilCell(int row, int column, int value) {
    	cells[row][column].toggleMark(value);
    }
    /**
     * Toggles whether the display is in pencil marking mode or not
     */
    public void togglePencilMarkMode() {
		pencilMarkMode = !pencilMarkMode;
		markButton.setSelected(pencilMarkMode);
		gameArea.requestFocusInWindow();
	}

//###############################Listener Methods###############################
    /**
     * Adds a mouse listener to a specified cell
     * 
     * @param row Row of cell
     * @param column Column of cell
     * @param listener Mouse Listener
     */
    public void addCellListener(int row, int column, MouseListener listener) {
    	cells[row][column].addMouseListener(listener);
    }
    /**
     * Adds action listeners to the game menu (from top to bottom), given an array of listeners 
     * 
     * @param listener Array of action listeners
     */
    public void addGameMenuListener(ActionListener[] listener) {
    	for (int i = 0; i < 7; i++) {
    		gameMenuItems[i].addActionListener(listener[i]);
    	}
    }
    /**
     * Adds action listeners to the theme menu (from top to bottom), given an array of listeners 
     * 
     * @param listener Array of action listeners
     */
    public void addThemeListener(ActionListener[] listener) {
    	for (int i = 0; i < 4; i++) {
    		themeItems[i].addActionListener(listener[i]);
    	}
    }
    /**
     * Adds action listeners to the options menu (from top to bottom), given an array of listeners 
     * 
     * @param listener Array of action listeners
     */
    public void addOptionsMenuListener(ActionListener[] listener) {
    	for (int i = 0; i < 4; i++) {
    		optionsMenuItems[i].addActionListener(listener[i]);
    	}
    }
    /**
     * Adds action listeners to the help menu (from top to bottom), given an array of listeners 
     * 
     * @param listener Array of action listeners
     */
    public void addHelpMenuListener(ActionListener[] listener) {
    	for (int i = 0; i < 2; i++) {
    		helpMenuItems[i].addActionListener(listener[i]);
    	}
    }
    /**
     * Adds a mouse listener to the information area of the display
     * 
     * @param listener Mouse Listener
     */
    public void addInfoAreaListener(MouseListener listener) {
    	infoArea.addMouseListener(listener);
    }
    /**
     * Adds an action listener to a specified keypad button
     * 
     * @param keypadNum Number of the button
     * @param buttonPressedListener Action Listener
     */
	public void addKeypadListener(int keypadNum,
			ActionListener buttonPressedListener) {
		keypad[keypadNum].addActionListener(buttonPressedListener);
	}
	/**
	 * Adds a key listener to the game area in the display
	 */
	public void addKeyListener(KeyListener listener) {
		gameArea.addKeyListener(listener);
		gameArea.requestFocusInWindow();
	}    
	/**
	 * Adds an action listener to the delete button
	 * 
	 * @param buttonPressedListener Action Listener
	 */
	public void addDeleteListener(ActionListener buttonPressedListener) {
		deleteButton.addActionListener(buttonPressedListener);
	}
	/**
	 * Adds an action listener to the pencil button
	 * 
	 * @param pencilPressedListener Action Listener
	 */
	public void addPencilListener(ActionListener pencilPressedListener) {
		markButton.addActionListener(pencilPressedListener);
	}

//################################Display Methods###############################
	/**
	 * Displays a given string in a new dialog box
	 * 
	 * @param message String to be displayed
	 */
    public void displayMessage(String message) {
		
		JOptionPane.showMessageDialog(this, message, null, JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Displays a set of high scores
     * 
     * @param highScoreStrings Array of high score strings
     */
    public void displayHighscores(String[] highScoreStrings) {
    	JFrame messageFrame = new JFrame();
		messageFrame.setSize(new Dimension(250, 400));
		messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	JPanel textPanel = new JPanel();
    	textPanel.setLayout(new GridLayout(11,1));
    	JLabel scoreLabel = new JLabel(highScoreStrings[0]);
    	scoreLabel.setFont(new Font("Serif", Font.BOLD, 22));
    	textPanel.add(scoreLabel);

    	for (int i = 1; i <= 10; i++) {
		    scoreLabel = new JLabel(highScoreStrings[i]);
		    scoreLabel.setFont(new Font("Serif", Font.PLAIN, 22));
			textPanel.add(scoreLabel);
    	}

		messageFrame.add(textPanel);
		messageFrame.setVisible(true);
    }
    /**
     * Displays the how to play text in a new frame
     */
    public void displayHowToPlay() {
	    JFrame frame = new JFrame();
	    JEditorPane instructions = new JEditorPane();
	    instructions.setEditable(false);
	    instructions.setMargin(new Insets(0,7,0,2));
	    instructions.setFont(new Font("Serif", Font.PLAIN, 12));
	    instructions.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
	    instructions.setEditable(false);
	    instructions.setText("<h2><center>How To Play Sudoku</center></h2>"+
	    		"A sudoku puzzle initially displays a partially filled grid. "+
	    		"The aim of the game is to fill each row, column and block (3x3 square) with a number from 1-9. " +
        		"However, each number <i>can only appear once</i> in the same row, column or block.<br><br>" +
        		"Also note that each sudoku only ever has one solution.<br><br>" +
        		"It's easy to play, but difficult to master, and many of the more difficult puzzles require " +
        		"very advanced techniques. If you're interested, this websites explains commonly " +
        		"used techniques (including the most basic techniques if you still don't get it) very well:<br>"+
	    "www.sadmansoftware.com/sudoku/solvingtechniques.htm");
		frame.add(instructions);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("How to Play");
		frame.setSize(700, 300);
		frame.setVisible(true);
    }
    /**
     * Displays the about text in a new frame
     */
    public void displayAbout() {
    	JFrame frame = new JFrame();
    	JEditorPane created = new JEditorPane();
    	created.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
    	created.setText("<center><h3>Created by TacoCat</h3>" +
				"Lewis Lee<br>" +
				"Karl Krauth<br>" +
				"Joseph Fenech<br>" +
				"Luke Solomon</center><br><br>");
		created.setEditable(false);
		created.setFont(new Font("Serif", Font.PLAIN, 12));
		
		
		File dir1 = new File (".");
	    String path = dir1.getAbsolutePath();
	    if(path.endsWith("."))
	    {
	    	path = path.substring(0, path.length() - 2);
	    }
	    path.trim();
	    path +=  "/images/backwards.jpg";
	    path.trim();
	    ImageIcon icon = new ImageIcon(path);
	    JLabel label1 = new JLabel(icon);
	    label1.setOpaque(true);
	    label1.setBackground(Color.white);
	   
		frame.add(created, BorderLayout.NORTH);
		frame.add(label1, BorderLayout.CENTER);
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("About");
		frame.setSize(306, 470);
		frame.setVisible(true);
    }
    /**
     * Sets the theme of the display
     * 
     * @param option Theme
     */
    public void setTheme(int option) {
    	layout.setTheme(option);
    	SwingUtilities.updateComponentTreeUI(gameArea);
    	SwingUtilities.updateComponentTreeUI(infoArea);
    	SwingUtilities.updateComponentTreeUI(menuBar);
    }
    
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------------Private Methods-------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
    /**
     * Initializes the game menu
     */
    private void initGameMenu() {
    	gameMenuItems = new JMenuItem[10];
    	gameMenu = new JMenu("Game");
    	JMenu submenu = new JMenu("New Game");
    	gameMenuItems[0] = new JMenuItem("Easy");
    	gameMenuItems[1] = new JMenuItem("Medium");
    	gameMenuItems[2] = new JMenuItem("Hard");
    	gameMenuItems[3] = new JMenuItem("Very Hard");
    	submenu.add(gameMenuItems[0]);
    	submenu.add(gameMenuItems[1]);
    	submenu.add(gameMenuItems[2]);
    	submenu.add(gameMenuItems[3]);
    	gameMenu.add(submenu);
    	gameMenuItems[4] = new JMenuItem("Save Game");
    	gameMenu.add(gameMenuItems[4]);
    	gameMenuItems[5] = new JMenuItem("Load Game");
    	gameMenu.add(gameMenuItems[5]);
    	gameMenuItems[6] = new JMenuItem("Exit");
    	gameMenu.add(gameMenuItems[6]);
    }
    /**
     * Initializes the option menu
     */
    private void initOptionsMenu() {
    	optionsMenuItems = new JMenuItem[4];
    	optionsMenu = new JMenu("Options");
    	JMenu submenu = new JMenu("Theme");
		ButtonGroup group = new ButtonGroup();				
		themeItems = new JRadioButtonMenuItem[4];
		themeItems[0] = new JRadioButtonMenuItem("Classic");
		themeItems[1] = new JRadioButtonMenuItem("Western");
		themeItems[2] = new JRadioButtonMenuItem("Space");
		themeItems[3] = new JRadioButtonMenuItem("Aquatic");
		submenu.add(themeItems[0]);
		submenu.add(themeItems[1]);
		submenu.add(themeItems[2]);
		submenu.add(themeItems[3]);
		group.add(themeItems[0]);
		group.add(themeItems[1]);
		group.add(themeItems[2]);
		group.add(themeItems[3]);
		themeItems[0].setSelected(true);
    	optionsMenu.add(submenu);
    	submenu = new JMenu("Highscores");
    	optionsMenuItems[0] = new JMenuItem("Easy");
    	optionsMenuItems[1] = new JMenuItem("Medium");
    	optionsMenuItems[2] = new JMenuItem("Hard");
    	optionsMenuItems[3] = new JMenuItem("Very Hard");
    	submenu.add(optionsMenuItems[0]);
    	submenu.add(optionsMenuItems[1]);
    	submenu.add(optionsMenuItems[2]);
    	submenu.add(optionsMenuItems[3]);
    	optionsMenu.add(submenu);
    }
    /**
     * Initializes the help menu
     */
    private void initHelpMenu() {
    	helpMenuItems = new JMenuItem[2];
    	helpMenu = new JMenu("Help");
    	helpMenuItems[0] = new JMenuItem("How to Play");
    	helpMenuItems[1] = new JMenuItem("About");
    	helpMenu.add(helpMenuItems[0]); 
    	helpMenu.add(helpMenuItems[1]);
    }
    /**
     * Initializes the keypad with new buttons
     */
    private void initKeypad() {
    	keypad = new JButton[9];
    	
    	for(int i = 0; i < 9; i++) {
    		keypad[i] = new JButton(((Integer)(i + 1)).toString());
    		keypad[i].setFocusable(false);
    	}
    }
    /**
     * Initializes the cells with new JCells
     */
    private void initCells() {
        cells = new JCell[9][9];
        
        for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 9; j++) {
        		cells[i][j] = new JCell();
        	}
        }
    }

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//--------------------------------Private Fields--------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
    private SudokuLayoutManager layout;
    
	private static final long serialVersionUID = 6403211790978546147L;
	private JPanel gameArea;
	private JPanel board;
	private JPanel infoArea;
	private JLabel timeLabel;
	private JCell[][] cells;
	private boolean pencilMarkMode;
	private JButton[] keypad;
	private JButton deleteButton;
	private JToggleButton markButton;
	
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenu optionsMenu;
	private JMenu helpMenu;
	private JMenuItem[] gameMenuItems;
	private JMenuItem[] optionsMenuItems;
	private JRadioButtonMenuItem[] themeItems;
	private JMenuItem[] helpMenuItems;

}
