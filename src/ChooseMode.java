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
public class ChooseMode extends JFrame {	
	public void gameModeMenu() {
		// configure the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800, 900));
		this.setLocationRelativeTo(null);
		
		// display the tile
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
		
		// create "Easy" mode button
		JButton easy = new JButton("Easy");
		easy.setFont(new Font("Calibri", Font.BOLD, 40));
		easy.setPreferredSize(new Dimension(410, 70));
		easy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Game easyMode = new Game(new Easy());
						easyMode.setVisible(true);
					}
				});
			}
		});
		
		// create "Medium" mode button
		JButton medium = new JButton("Medium");
		medium.setFont(new Font("Calibri", Font.BOLD, 40));
		medium.setPreferredSize(new Dimension(410, 70));
		medium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Game mediumMode = new Game(new Medium());
						mediumMode.setVisible(true);
					}
				});
			}
		});
		
		// create "Hard" mode button
		JButton hard = new JButton("Hard");
		hard.setFont(new Font("Calibri", Font.BOLD, 40));
		hard.setPreferredSize(new Dimension(410, 70));
		hard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Game hardMode = new Game(new Hard());
						hardMode.setVisible(true);
					}
				});
			}
		});
		
		// create "Back" button
		JButton back = new JButton("Back to Main Menu");
		back.setFont(new Font("Calibri", Font.BOLD, 40));
		back.setPreferredSize(new Dimension(410, 70));
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Main main = new Main();
						main.setVisible(true);
					}
				});
			}
		});
		
		// add the title and the button into the panel
		windowPanel.add(titleImage);
		windowPanel.add(easy);
		windowPanel.add(medium);
		windowPanel.add(hard);
		windowPanel.add(back);
	}
	
	public ChooseMode() {
		gameModeMenu();
	}
}
