import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

@SuppressWarnings("serial")
public class SudokuPanel extends JPanel {
	private Puzzle puzzle;
	private int currCol;
	private int currRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;
	
	public SudokuPanel(Puzzle puzzle) {
		this.setPreferredSize(new Dimension(540, 450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currCol = -1;
		currRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 20;
	}
	
	public Puzzle getPuzzle() {
		return puzzle;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	// method to draw the sudoku panel
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(1.0f, 1.0f, 1.0f));
		
		int slotWidth = this.getWidth() / puzzle.getColumn();
		int slotHeight = this.getHeight() / puzzle.getRow();
		
		usedWidth = (this.getWidth() / puzzle.getColumn()) * puzzle.getColumn();
		usedHeight = (this.getHeight() / puzzle.getRow()) * puzzle.getRow();
		
		g2d.fillRect(0, 0, usedWidth, usedHeight);
		
		g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
		for(int x = 0; x <= usedWidth; x += slotWidth) {
			if((x / slotWidth) % puzzle.getWidth() == 0) {
				g2d.setStroke(new BasicStroke(3)); // line that separates the grids
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else {
				g2d.setStroke(new BasicStroke(1)); // line that separates the slots
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}

		for(int y = 0; y <= usedHeight; y += slotHeight) {
			if((y / slotHeight) % puzzle.getHeight() == 0) {
				g2d.setStroke(new BasicStroke(3)); // line that separates the grids
				g2d.drawLine(0, y, usedWidth, y);
			}
			else {
				g2d.setStroke(new BasicStroke(1)); // line that separates the slots
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
		
		Font f = new Font("Calibri", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		
		// render the sudoku board
		for(int row = 0; row < puzzle.getRow(); row++) {
			for(int col = 0; col < puzzle.getColumn(); col++) {
				if(!puzzle.isSlotAvailable(row, col)) {
					int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
					g2d.drawString(puzzle.getValue(row, col), (col * slotWidth) + ((slotWidth / 2) - (textWidth / 2)), (row * slotHeight) + ((slotHeight / 2) + (textHeight / 2)));
				}
			}
		}
		
		// color the selected slot as blue
		if(currCol != -1 && currRow != -1) {
			g2d.setColor(new Color(0.0f, 0.0f, 1.0f, 0.3f));
			g2d.fillRect(currCol * slotWidth, currRow * slotHeight, slotWidth, slotHeight);
		}
		
		// color all the numbers assigned in the board, with the number being the number that was selected by the user
		if(currCol != -1 && currCol != -1 && puzzle.isValidValue(puzzle.getValue(currRow, currCol)) == true) {
			for(int i = 0; i < puzzle.getRow(); i++) {
				for(int j = 0; j < puzzle.getColumn(); j++) {
					if(puzzle.getValue(i, j).equals(puzzle.getValue(currRow, currCol)) == true && puzzle.getValue(i, j).equals(" ") == false){
						g2d.setColor(new Color(0.0f, 0.0f, 1.0f, 0.3f));
						g2d.fillRect(j * slotWidth, i * slotHeight, slotWidth, slotHeight);
					}
				}
			}
		}
	}
	
	// method to restart the game
	// the program will display the initial state of the puzzle
	public void restartPuzzle() {
		for(int i = 0; i < puzzle.getRow(); i++) {
			for(int j = 0; j < puzzle.getColumn(); j++) {
				if(puzzle.isSlotMutable(i, j) == true) {
					puzzle.makeMove(i, j, " ", true);
					repaint();
				}
			}
		}
		currCol = -1;
		currRow = -1;
	}
	
	// assign a value to a chosen slot
	public void messageFromActionListener(String buttonValue) {
		if(currCol != -1 && currRow != -1) {
			puzzle.makeMove(currRow, currCol, buttonValue, true);
			repaint();
		}
	}
	
	// the action listener that was added to button number 1 to 9
	public class NumActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFromActionListener(((JButton) e.getSource()).getText());	
		}
	}
	
	// the action listener that was added to the delete button
	public class deleteActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFromActionListener(" ");
		}
	}

	// the mouse listener that was added to the sudoku panel
	private class SudokuPanelMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				int slotWidth = usedWidth / puzzle.getColumn();
				int slotHeight = usedHeight / puzzle.getRow();
				currRow = e.getY() / slotHeight;
				currCol = e.getX() / slotWidth;
				e.getComponent().repaint();
			}
		}
	}
}
