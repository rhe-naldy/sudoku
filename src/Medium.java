import java.awt.Color;

// Child class of Mode
public class Medium extends Mode {
	public Medium() {
		super();
	}

	@Override
	public Color getBackgroundColour() {
		return new Color(177, 183, 242); // medium mode -> pastel blue
	}

	@Override
	public double getVisibilityPercentage() {
		return 0.5; // about 50% of numbers on the sudoku is shown to the user, with the remaining 50% of numbers needed to be solved by the user
	}

}
