package sudokuFrontend;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;

import sudokuBackend.Difficulty;
/**
 * The StandardLayoutManager class organizes all view components of a sudoku display
 */
public class StandardLayoutManager implements SudokuLayoutManager {
	/**
	 * Creates and organizes a board, given the corresponding cells
	 */
	public JPanel createBoard(JCell[][] cells) {
		JPanel board = new JPanel();
		board.setSize(new Dimension(550, 550));
		
		board.setLayout(new GridLayout(9,9));
    	
    	for(int i = 0; i < 9; i++) {
    		
    		for(int j = 0; j < 9; j++) {
    		    MatteBorder horizontalBorder;
    		    MatteBorder verticalBorder;
    		    
        		if ((i % 3) == 0) {
        		    horizontalBorder = BorderFactory.
        		    		createMatteBorder(1, 0, 0, 0, Color.black);
        		} else if (i % 3 == 1) {
        		    horizontalBorder = BorderFactory.
        		    		createMatteBorder(1, 0, 1, 0, Color.gray);
        		} else {
        		    horizontalBorder = BorderFactory.
        		    		createMatteBorder(0, 0, 1, 0, Color.black);
        		}
    		    
    		    if (j % 3 == 0) {
        		    verticalBorder = BorderFactory.
        		    		createMatteBorder(0, 1, 0, 0, Color.black);
    		    } else if (j % 3 == 1) {
        		    verticalBorder = BorderFactory.
        		    		createMatteBorder(0, 1, 0, 1, Color.gray);
    		    } else {
        		    verticalBorder = BorderFactory.
        		    		createMatteBorder(0, 0, 0, 1, Color.black);    		    	
    		    }
    		    
    		    CompoundBorder cellBorder = new CompoundBorder(horizontalBorder, verticalBorder);
                cells[i][j].setBorder(cellBorder);
    		    board.add(cells[i][j]);
    		}
    	}
    	
		return board;
	}
	/**
	 * Creates and organizes a new game area, given a board and keypad buttons
	 */
	public JPanel createGameArea(JPanel board, JButton[] keypadButtons,
			JButton deleteButton, JToggleButton markButton) {
		JPanel gameArea = new JPanel();
		gameArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		gameArea.setSize(new Dimension(550, 650));
		gameArea.setLayout(new BorderLayout());
		gameArea.setFocusable(true);
		
		JPanel keypad = new JPanel();
		keypad.setSize(new Dimension(550, 50));
		keypad.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		
		for(int i = 0; i < 9; i++) {
			keypad.add(keypadButtons[i]);
		}
		
		keypad.add(deleteButton);
		keypad.add(markButton);
		
		gameArea.add(board, BorderLayout.CENTER);
		gameArea.add(keypad, BorderLayout.SOUTH);
		return gameArea;
	}
	/**
	 * Creates and organizes a new information area, given a time label and difficulty 
	 */
	public JPanel createInfoArea(JLabel timeLabel, Difficulty difficulty) {
		String difficultyWord = difficulty.toString();
		JPanel infoArea = new JPanel();
		infoArea.setSize(new Dimension(550, 50));
		infoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		infoArea.setLayout(new BorderLayout());
		timeLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		JLabel difficultyLabel = new JLabel("Difficulty: " + difficultyWord);
		difficultyLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		infoArea.add(timeLabel, BorderLayout.WEST);
		infoArea.add(difficultyLabel, BorderLayout.EAST);
		return infoArea;
	}
	/**
	 * Creates and organizes a new menu bar, given three menus
	 */
	public JMenuBar createMenuBar(JMenu gameMenu, JMenu optionsMenu, JMenu helpMenu) {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(gameMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}
	/**
	 * Sets and organizes a frame, given the frame, game area, information area and menu bar.
	 */
	public void setFrame(JFrame frame, JPanel gameArea, JPanel infoArea, JMenuBar menuBar) {
		frame.setJMenuBar(menuBar);
		frame.setSize(new Dimension(550, 650));
		frame.setLayout(new BorderLayout());
		frame.add(gameArea, BorderLayout.CENTER);
		frame.add(infoArea, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
  /**
   * Sets the theme of the display, give the theme number.
   */
  public void setTheme(int themeNo)
	{
		if(themeNo == 0)
		{
			MetalTheme theme = new OceanTheme();
			MetalLookAndFeel.setCurrentTheme(theme);
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}				
			
		}
		else if(themeNo == 1)
		{
		    class WesternTheme extends MetalTheme
			{
			    public String getName() { return "Western"; }
			    
			    //highlighting around entire dropdown menu
			    private final ColorUIResource primary1 = new ColorUIResource(0x2B1D0E);
			    //selected item
		 	    private final ColorUIResource primary2 = new ColorUIResource(0xE08566);
		 	    //??? perhaps highlighting by dragging mouse or right clicking?
		 	    private final ColorUIResource primary3 = new ColorUIResource(0xE8A63F); 
			    
			    //highlighted box around selected root menu item(not dropdown)
			    private final ColorUIResource secondary1   = new ColorUIResource(0x2B1D0E);
			    //line under menu colour
			    private final ColorUIResource secondary2   = new ColorUIResource(0xA12800);//0xCC3300
			    //background colour for whole frame
			    private final ColorUIResource secondary3     = new ColorUIResource(0xE87B3F);//0xE9BF94
			   
			    
			          // the functions overridden from the base 
			          // class => DefaultMetalTheme
			    
			    protected ColorUIResource getPrimary1() { return primary1; }  
			    protected ColorUIResource getPrimary2() { return primary2; } 
			    protected ColorUIResource getPrimary3() { return primary3; } 
			    
			    protected ColorUIResource getSecondary1() { return secondary1; }
			    protected ColorUIResource getSecondary2() { return secondary2; }
			    protected ColorUIResource getSecondary3() { return secondary3; }
				//font used in number box grid down bottom
				public FontUIResource getControlTextFont() {//to do-look up each what each font does
					return new FontUIResource("Sans-Serif", Font.PLAIN, 12);
				}
				//font used in cells
				public FontUIResource getSystemTextFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 22);
				}
				//used in popup boxes i.e. about etc.
				public FontUIResource getUserTextFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 12);
				}
				//menu text. Change getMenuforeground and getMenuSelectedForeground
				public FontUIResource getMenuTextFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 14);
				}
				@Override
				public FontUIResource getWindowTitleFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 12);
				}
				@Override
				public FontUIResource getSubTextFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 12);
				}
				
								
				public ColorUIResource getMenuBackground()  {
					return new ColorUIResource(0xE9BF94);
				}
				
				
				
			}
		    MetalTheme theme = new WesternTheme(); 
		    MetalLookAndFeel.setCurrentTheme(theme);
		    try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				 
			
		}
		else if (themeNo == 2)
		{
			class SpaceTheme extends MetalTheme
	    	{
	    	    public String getName() { return "Space"; }
	    	    
	    	  //highlighting around entire dropdown menu
			    private final ColorUIResource primary1 = new ColorUIResource(0x610CE8);
			    //selected item
		 	    private final ColorUIResource primary2 = new ColorUIResource(0x610CE8);
		 	    //??? perhaps highlighting by dragging mouse or right clicking?
		 	    private final ColorUIResource primary3 = new ColorUIResource(0xF719FF); 
			    
			    //highlighted box around selected root menu item(not dropdown)
			    private final ColorUIResource secondary1   = new ColorUIResource(0x11007A);
			    //line under menu colour
			    private final ColorUIResource secondary2   = new ColorUIResource(Color.black);
			    //background colour for whole frame
			    private final ColorUIResource secondary3     = new ColorUIResource(Color.darkGray);//0x070030
			   
			    
			          // the functions overridden from the base 
			          // class => DefaultMetalTheme
			    
			    protected ColorUIResource getPrimary1() { return primary1; }  
			    protected ColorUIResource getPrimary2() { return primary2; } 
			    protected ColorUIResource getPrimary3() { return primary3; } 
			    
			    protected ColorUIResource getSecondary1() { return secondary1; }
			    protected ColorUIResource getSecondary2() { return secondary2; }
			    protected ColorUIResource getSecondary3() { return secondary3; }
				//font used in number box grid down bottom
				public FontUIResource getControlTextFont() {//to do-look up each what each font does
					return new FontUIResource("Sans-Serif", Font.PLAIN, 12);
				}
				//font used in cells
				public FontUIResource getSystemTextFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 22);
				}
				//used in popup boxes i.e. about etc.
				public FontUIResource getUserTextFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 12);
				}
				//menu text. Change getMenuforeground and getMenuSelectedForeground
				public FontUIResource getMenuTextFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 14);
				}
				@Override
				public FontUIResource getWindowTitleFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 12);
				}
				@Override
				public FontUIResource getSubTextFont() {
					return new FontUIResource("Sans-Serif", Font.PLAIN, 12);
				}
				
								
				public ColorUIResource getMenuBackground()  {
					return new ColorUIResource(Color.black);
				}
				
				public ColorUIResource getMenuForeground()  {
					return new ColorUIResource(Color.white);
				}
				
				public ColorUIResource getMenuSelectedForeground()  {
					return new ColorUIResource(0x85D6FF);
				}
				
				//this will change both the numbers in the cell and the timers to white. If you can figure out a way to
				//only change one, that would be great.
				/*public ColorUIResource getSystemTextColor()    {
				return new ColorUIResource(Color.white);
				}*/
	    	}
			MetalTheme theme = new SpaceTheme(); 
		    MetalLookAndFeel.setCurrentTheme(theme);
		    try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			
		}
		else if(themeNo == 3)
		{
			class AquaticTheme extends MetalTheme
	    	{
	    	    public String getName() { return "Aquatic"; }
	    	    
	    	          // blue shades
	    	    private final ColorUIResource primary1 = new ColorUIResource(0x0, 0x33, 0x99);
	    	    private final ColorUIResource primary2  =  new ColorUIResource(0xD0, 0xDD, 0xFF);
	    	    private final ColorUIResource primary3 =  new ColorUIResource(0x0, 0x99, 0xFF); 
	    	    
	    	    private final ColorUIResource secondary1 = new ColorUIResource(0x6F, 0x6F, 0x6F);
	    	    private final ColorUIResource secondary2 = new ColorUIResource(0x9F, 0x9F, 0x9F);
	    	    private final ColorUIResource secondary3 = new ColorUIResource(0x1f, 0x7f, 0xDC);
	    	    
	    	          // the functions overridden from the base 
	    	          // class => DefaultMetalTheme
	    	    
	    	    protected ColorUIResource getPrimary1() { return primary1; }  
	    	    protected ColorUIResource getPrimary2() { return primary2; } 
	    	    protected ColorUIResource getPrimary3() { return primary3; } 
	    	    
	    	    protected ColorUIResource getSecondary1() { return secondary1; }
	    	    protected ColorUIResource getSecondary2() { return secondary2; }
	    	    protected ColorUIResource getSecondary3() { return secondary3; }
	    	    
	    	  //font used in number box grid down bottom
				public FontUIResource getControlTextFont() {//to do-look up each what each font does
					return new FontUIResource("serif", Font.PLAIN, 12);
				}
				//font used in cells
				public FontUIResource getSystemTextFont() {
					return new FontUIResource("serif", Font.PLAIN, 22);
				}
				//used in popup boxes i.e. about etc.
				public FontUIResource getUserTextFont() {
					return new FontUIResource("serif", Font.PLAIN, 12);
				}
				//menu text. Change getMenuforeground and getMenuSelectedForeground
				public FontUIResource getMenuTextFont() {
					return new FontUIResource("serif", Font.PLAIN, 14);
				}
				@Override
				public FontUIResource getWindowTitleFont() {
					return new FontUIResource("serif", Font.PLAIN, 12);
				}
				@Override
				public FontUIResource getSubTextFont() {
					return new FontUIResource("serif", Font.PLAIN, 12);
				}
				
								
				public ColorUIResource getControlTextColor()   {
					return new ColorUIResource(Color.white);
				}
				
				public ColorUIResource getMenuForeground()    {
					return new ColorUIResource(Color.white);
				}
				
				public ColorUIResource getControlHighlight()      {
					return new ColorUIResource(0x6CD9FF);
				}
	    	}
			MetalTheme theme = new AquaticTheme(); 
		    MetalLookAndFeel.setCurrentTheme(theme);
		    try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
	}

}
