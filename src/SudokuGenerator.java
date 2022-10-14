import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {
	// method to generate a random sudoku puzzle
	public Puzzle generateRandomSudoku(double visibilityPercentage) {
		Puzzle puzzle = new Puzzle();
		
		Random randomGenerator = new Random();
		
		// generate the number 1 to 9 randomly within the sudoku board
		List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(puzzle.getNumbers()));
		for(int i = 0; i < puzzle.getRow(); i++) {
			int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
			puzzle.makeMove(i, 0, notUsedValidValues.get(randomValue), true);
			notUsedValidValues.remove(randomValue);
		}
		
		// solving the sudoku
		backtrackSudokuSolver(0, 0, puzzle);
		
		// determining the total amount of numbers to be shown in the puzzle
		int numberOfValuesToKeep = (int)(visibilityPercentage * (puzzle.getRow() * puzzle.getColumn()));
		
		// assigning the randomized slots to be shown in the puzzle
		for(int i = 0; i < numberOfValuesToKeep; ) {
			int randomRow = randomGenerator.nextInt(puzzle.getRow());
			int randomColumn = randomGenerator.nextInt(puzzle.getColumn());
			
			if(puzzle.isSlotMutable(randomRow, randomColumn) == true) {
				puzzle.makeMove(randomRow, randomColumn, puzzle.getValue(randomRow, randomColumn), false);
				i++;
			}
		}
		
		// removing the value in the hidden slots / slots for the players to fill
		for(int i = 0; i < puzzle.getRow(); i++) {
			for(int j = 0; j < puzzle.getColumn(); j++) {
				if(puzzle.isSlotMutable(i, j) == true) {
					puzzle.makeMove(i, j, " ", true);
				}
			}
		}
		
		return puzzle;
	}

	// method used to solve the sudoku
    private boolean backtrackSudokuSolver(int r, int c, Puzzle puzzle) {
    	//If the move is not valid return false
		if(!puzzle.inRange(r,c)) {
			return false;
		}
		
		if(puzzle.isSlotAvailable(r, c)) { // slot is empty or has no value assigned
			//loop to find the correct value for the current slot
			for(int i = 0; i < puzzle.getNumbers().length; i++) {
				
				//if the current number works in the current slot, then assign the value.
				if(!puzzle.numInRow(r, puzzle.getNumbers()[i]) && !puzzle.numInCol(c, puzzle.getNumbers()[i]) && !puzzle.numInBox(r, c, puzzle.getNumbers()[i])) {
					puzzle.makeMove(r, c, puzzle.getNumbers()[i], true);
					
					if(puzzle.boardFull()) {
						return true;
					}
					
					if(r == puzzle.getRow() - 1) {
						if(backtrackSudokuSolver(0, c + 1, puzzle)) return true;
					} else {
						if(backtrackSudokuSolver(r + 1, c, puzzle)) return true;
					}
				}
			}
		}
		else { // slot has assigned value
			// go to the next slot
			if(r == puzzle.getRow() - 1) {
				return backtrackSudokuSolver(0,c + 1,puzzle);
			} else {
				return backtrackSudokuSolver(r + 1,c,puzzle);
			}
		}
		// undo move
		puzzle.makeSlotEmpty(r, c);
		
		return false;
	}
}
