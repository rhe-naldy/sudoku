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

@SuppressWarnings("serial")
public class Main extends JFrame {
	// Main.java will be displaying the main menu of our sudoku game
	// The main menu will display the game title on top, with 2 different buttons consisting of "New Game" and "Quit".
	// Pressing "New Game" will lead the user to choose between 4 buttons ("Easy", "Medium", "Hard", and "Back")
	// Pressing "Quit" exits the program.
	
	public void mainMenu() {
		// Configure the program's frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800, 900));
		this.setLocationRelativeTo(null);
		
		// Display title
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
		
		// create "New Game" button
		JButton newGame = new JButton("New Game");
		newGame.setFont(new Font("Calibri", Font.BOLD, 40));
		newGame.setPreferredSize(new Dimension(410, 70));
		newGame.addActionListener(new ActionListener() {
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
		
		// create "Quit" button
		JButton quit = new JButton("Quit");
		quit.setFont(new Font("Calibri", Font.BOLD, 40));
		quit.setPreferredSize(new Dimension(410, 70));
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(DISPOSE_ON_CLOSE);
			}
		});
		
		// add the title and the buttons into the panel
		windowPanel.add(titleImage);
		windowPanel.add(newGame);
		windowPanel.add(quit);
	}
	
	public Main() {
		mainMenu();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Main main = new Main();
				main.setVisible(true);
			}
		});
	}
}
