import java.awt.Color;

// Child class of Mode
public class Easy extends Mode {
	public Easy() {
		super();
	}

	@Override
	public Color getBackgroundColour() {
		return new Color(187, 245, 184); // easy mode -> pastel green
	}
	
	@Override
	public double getVisibilityPercentage() {
		return 0.7; // about 70% of numbers on the sudoku is shown to the user, with 30% of numbers needed to be solved by the user
	}
}
