import java.awt.Color;

// Child class of Mode
public class Hard extends Mode {
	public Hard() {
		super();
	}

	@Override
	public Color getBackgroundColour() {
		return new Color(242, 177, 181); // hard mode -> pastel red
	}

	@Override
	public double getVisibilityPercentage() {
		return 0.3; // about 30% of numbers on the sudoku is shown to the user, with 70% of numbers needed to be solved by the user
	}

}
