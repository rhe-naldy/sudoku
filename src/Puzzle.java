
public class Puzzle {
	private String[][] board;
	private boolean[][] mutable;
	private final int row; // 9
	private final int column; // 9
	private final int width; // 3
	private final int height; // 3
	private final String[] numbers; // 1-9
	
	// this constructor creates an empty sudoku puzzle
	public Puzzle() {
		this.row = 9;
		this.column = 9;
		this.width = 3;
		this.height = 3;
		this.numbers = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		this.board = new String[9][9];
		this.mutable = new boolean[9][9];
		initializeBoard();
		initializeMutableSlots();
	}
	
	// this constructor copies the empty sudoku puzzle, used in SudokuGenerator.java file that completely solves the puzzle
	// when the program decides to set a slot in the empty sudoku puzzle to be shown to the user, 
	// the program will take the number from the same slot in the solved copy of the puzzle and assign it into the original puzzle.
	public Puzzle(Puzzle puzzle) {
		this.row = puzzle.row;
		this.column = puzzle.column;
		this.width = puzzle.width;
		this.height = puzzle.height;
		this.numbers = puzzle.numbers;
		this.board = new String[row][column];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
				board[i][j] = puzzle.board[i][j];
			}
		}
		this.mutable = new boolean[row][column];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
				this.mutable[i][j] = puzzle.mutable[i][j];
			}
		}
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public String[] getNumbers() {
		return this.numbers;
	}
	
	// initialize the number to the choosen slot when the user assign a number by pressing the number buttons
	public void makeMove(int row, int col, String value, boolean isMutable) {
		if(this.isValidValue(value) && this.isSlotMutable(row, col)) {
			this.board[row][col] = value;
			this.mutable[row][col] = isMutable;
		}
	}
	
	// method used in SudokuGenerator.java file to help solve the copy of the puzzle
	public boolean numInCol(int col, String value) {
		if(col <= this.column) {
			for(int i = 0; i < this.row; i++) {
				if(this.board[i][col].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// method used in SudokuGenerator.java file to help solve the copy of the puzzle
	public boolean numInRow(int row, String value) {
		if(row <= this.row) {
			for(int i = 0; i < this.column; i++) {
				if(this.board[row][i].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// method used in SudokuGenerator.java file to help solve the copy of the puzzle
	public boolean numInBox(int row, int col, String value) {
		if(this.inRange(row, col)) {
			int boxRow = row / this.height;
			int boxCol = col / this.width;
			
			int startingRow = (boxRow * this.height);
			int startingCol = (boxCol * this.width);
			
			for(int i = startingRow; i <= (startingRow + this.height) -1; i++) {
				for(int j = startingCol; j <= (startingCol + this.width) -1; j++) {
					if(this.board[i][j].equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	// method to check the availability of a slot
	public boolean isSlotAvailable(int row, int col) {
		 return (this.inRange(row, col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
	}
	
	// method to check if the slot can be altered by the user (if the user can change or assign a number)
	public boolean isSlotMutable(int row, int col) {
		return this.mutable[row][col];
	}
	
	// method to get the value of a slot
	public String getValue(int row, int col) {
		if(this.inRange(row, col)) {
			return this.board[row][col];
		}
		return "";
	}
	
	public String[][] getBoard() {
		return this.board;
	}
	
	// method to check if the value assigned by the user is valid or not
	public boolean isValidValue(String value) {
		for(String str : this.numbers) {
			if(str.equals(value)) return true;
		}
		if(value.equals(" ")) return true;
		return false;
	} 
	
	// method to check if the selected slot is in the sudoku panel
	public boolean inRange(int row, int col) {
		return row <= this.row && col <= this.column && row >= 0 && col >= 0;
	}
	
	// method to check if the board is fully assigned
	public boolean boardFull() {
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.column; j++) {
				if(this.board[i][j].equals("")) return false;
			}
		}
		return true;
	}
	
	// method used in SudokuGenerator.java file to undo the solving process
	public void makeSlotEmpty(int row, int col) {
		this.board[row][col] = "";
	}
	
	@Override
	public String toString() {
		String str = "Game Board:\n";
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.column; j++) {
				str += this.board[i][j] + " ";
			}
			str += "\n";
		}
		return str + "\n";
	}
	
	// method to initialize the sudoku board and set all the slots to an empty slot.
	private void initializeBoard() {
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.column; j++) {
				this.board[i][j] = "";
			}
		}
	}
	
	// method to set all the slots in a sudoku board to be mutable or able to be altered (can be changed or assigned)
	private void initializeMutableSlots() {
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.column; j++) {
				this.mutable[i][j] = true;
			}
		}
	}
	
	// method to check if the sudoku is solved or not.
	public boolean check() {
		int temp = 0;
		//check per column
		for(int i = 0; i < this.getRow(); i++) {
			for(String str : this.numbers) {
				temp = 0;
				for(int j = 0; j < this.getColumn(); j++) {
					if(this.getValue(i, j).equals(str) == true) {
						temp += 1;
					}
				}
				if(temp > 1 || temp == 0) {
					return false;
				}
			}
		}
		
		//check per row
		for(int i = 0; i < this.getColumn(); i++) {
			for(String str : this.numbers) {
				temp = 0;
				for(int j = 0; j < this.getRow(); j++) {
					if(this.getValue(j, i).equals(str) == true) {
						temp += 1;
					}
				}
				if(temp > 1 || temp == 0) {
					return false;
				}
			}
		}
		
		// check per grid
		for(int i = 0; i < 9; i += 3) {
			for(int j = 0; j < 9; j += 3) {
				for(String str : this.numbers) {
					temp = 0;
					if(this.getValue(i, j).equals(str) == true) {
						temp += 1;
					}
					if(this.getValue(i + 1, j).equals(str) == true) {
						temp += 1;
					}
					if(this.getValue(i + 2, j).equals(str) == true) {
						temp += 1;
					}
					if(this.getValue(i, j + 1).equals(str) == true) {
						temp += 1;
					}
					if(this.getValue(i + 1, j + 1).equals(str) == true) {
						temp += 1;
					}
					if(this.getValue(i + 2, j + 1).equals(str) == true) {
						temp += 1;
					}
					if(this.getValue(i, j + 2).equals(str) == true) {
						temp += 1;
					}
					if(this.getValue(i + 1, j + 2).equals(str) == true) {
						temp += 1;
					}
					if(this.getValue(i + 2, j + 2).equals(str) == true) {
						temp += 1;
					}
					
					if(temp > 1 || temp == 0) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
}
