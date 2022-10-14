import java.awt.Color;

public abstract class Mode {
	private Puzzle puzzle;
	
	public Mode() {
		super();
		this.puzzle = new SudokuGenerator().generateRandomSudoku(this.getVisibilityPercentage());
	}
	
	public Puzzle getPuzzle() {
		return puzzle;
	}
	
	public abstract Color getBackgroundColour(); // this method is created to get the colour for every mode
	public abstract double getVisibilityPercentage(); 
	// this method is created to determine the total amount of numbers to be displayed to the user
}
