import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class Game extends JFrame{
	private JPanel numberButtons; // panel to store the buttons consisting of number 1 to 9 and the delete button
	private SudokuPanel sudoku; // the sudoku panel that will be displayed
	private boolean flag; // to check the completion of the sudoku
	public void startGame(Mode gameMode) {
		Timer timer = new Timer();
		flag = true;
		
		// configure the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800, 900));
		this.setLocationRelativeTo(null);
		
		// display the title
		URL imgURL = getClass().getResource("/image/title.png");
		ImageIcon sudokuTitle = new ImageIcon(imgURL);
		Image image = sudokuTitle.getImage();
		Image scaled = image.getScaledInstance(800, 210, java.awt.Image.SCALE_SMOOTH);
		sudokuTitle = new ImageIcon(scaled);
		JLabel titleImage = new JLabel(sudokuTitle);
		titleImage.setSize(400, 100);
		
		// create a panel
		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		windowPanel.setPreferredSize(new Dimension(800, 900));
		this.add(windowPanel);
		
		// initialize the panel to store the buttons consisting of number 1 to 9 and the delete button
		numberButtons = new JPanel();
		numberButtons.setPreferredSize(new Dimension(800, 70));
		
		// initialize the sudoku panel
		Puzzle puzzle = gameMode.getPuzzle();
		sudoku = new SudokuPanel(puzzle);
		sudoku.setFontSize(20);

		// the text that shows up when the user has not completed or solved the sudoku
		JLabel fail = new JLabel("The puzzle is still not solved!");
		fail.setFont(new Font("Calibri", Font.BOLD, 40));
		fail.setVisible(false);
		
		// the text that shows up when the user has completed and solved the sudoku
		JLabel win = new JLabel("The puzzle is solved! You won!");
		win.setFont(new Font("Calibri", Font.BOLD, 40));
		win.setVisible(false);
		
		// creating the button number 1 to 9
		for(String num : puzzle.getNumbers()) {
			JButton button = new JButton(num);
			button.setPreferredSize(new Dimension(50, 50));
			button.addActionListener(sudoku.new NumActionListener());
			numberButtons.add(button);
		}
		
		// creating the delete button
		JButton delete = new JButton("X");
		delete.setPreferredSize(new Dimension(50, 50));
		delete.addActionListener(sudoku.new deleteActionListener());
		numberButtons.add(delete);
		
		// creating the back button, when pressed will take the user back to mode selection
		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(200, 50));
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						ChooseMode mode = new ChooseMode();
						mode.setVisible(true);
					}
				});
			}
		});
		
		// creating the restart button, when pressed will restart the sudoku panel back to it's initial state
		JButton restart = new JButton("Restart Game");
		restart.setPreferredSize(new Dimension(200, 50));
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						sudoku.restartPuzzle();
					}
				});
			}
		});
		
		// creating the check button, when pressed will check if the sudoku is solved or not. 
		// If solved, the sudoku will display a text congratulating the user and take the user back to the main menu
		JButton check = new JButton("Check");
		check.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				flag = sudoku.getPuzzle().check();
				
				if(flag == true) {
					fail.setVisible(false);
					win.setVisible(true);
					
					timer.schedule(new TimerTask() {
						public void run() {
							dispose();
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									Main main = new Main();
									main.setVisible(true);
								}
							});
						}
					}, 2000);
				} else if (flag == false){
					win.setVisible(false);
					fail.setVisible(true);
				}
			}
		});
		check.setPreferredSize(new Dimension(300, 50));
		
		// Add the title, sudoku panel, and the buttons into the panel.
		windowPanel.add(titleImage);
		windowPanel.add(sudoku);
		windowPanel.add(numberButtons);
		windowPanel.add(back);
		windowPanel.add(restart);
		windowPanel.add(check);
		windowPanel.add(win);
		windowPanel.add(fail);
		this.add(windowPanel);
		
		// the panel's background will be determined by the game mode the user is currently playing
		// easy mode sets the background to green
		// medium mode sets the background to blue
		// hard mode sets the background to red
		numberButtons.setBackground(gameMode.getBackgroundColour());
		windowPanel.setBackground(gameMode.getBackgroundColour());
	}
	
	public Game(Mode gameMode) {
		startGame(gameMode);
	}
}
