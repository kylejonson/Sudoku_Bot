/**
 *Helper Class for the TwitterBot Class
 *@author Kyle Jonson
 *@since 3/14/2017
 *@version 1.0.0
 */
import java.util.Random;
public class Sudoku extends AbstractSudoku{
	private int[] TestRow;
	public Sudoku(){
		super();
		this.TestRow = new int[]{1,2,3,4,5,6,7,8,9};
		this.TestRow = shuffle(this.TestRow);
	}
	/**
	 * @return a Sudoku solution
	 */
	public int[][] getSolution(){
		generateSolution();
		return this.board;
	}
	/**
	 * Creates a solved Sudoku board
	 */
	private void generateSolution(){
		explore(0,0);
	}
	/**
	 * Recursive method to make a solve sudoku board
	 * @param row changing row
	 * @param col changing column
	 * @return true when the first solution is found
	 */
	private boolean explore(int row, int col){
		if(row < 0){						//row is given by AbstractSudoku.nextOpen()
			return true;					//Base Case; board is full & everything placed is valid
		}else{
			for(int i = 0; i < 9; i++){
				if(isSafe(TestRow[i], row, col)){
					board[row][col] = TestRow[i];
					int NewRow = nextOpen()[0];
					int NewCol = nextOpen()[1];
					if(explore(NewRow,NewCol)){
						return true;		//If to stop everything when the first solution is found
					}
					board[row][col] = 0;	//Remove / Backtrack
				}
			}
			return false;
		}
	}
	/**
	 * @return a Sudoku Puzzle based on the already solved board
	 */
	public int[][] getPuzzle(){
		int[][] temp = new int[9][9];		//I know this is bad
		for(int i = 0; i < 9; i++){			//But at the same time it works
			for(int j = 0; j < 9; j++){		//I'll fix it later
				temp[i][j] = board[i][j];	// 			-Kyle Jonson, java haiku
			}
		}
		generatePuzzle();
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				puzzle[i][j] = board[i][j];
				board[i][j] = temp[i][j];
			}
		}
		return this.puzzle;
	}
	/**
	 * Generates a new Sudoku Puzzle
	 * Run this after the generateSolution() method
	 */
	private void generatePuzzle(){
		int temp1;
		int temp2;
		Random rand = new Random();
		while(count() >= 17){
			int row = rand.nextInt(9);
			int col = rand.nextInt(9);
			if(count() > 40){
				temp1 = board[row][col];	//Over 40 elements it removes with rotational symmetry
								/*TODO: This is symmetry along a path y=-x. Make this actual rotational symmetry*/
				temp2 = board[col][row];
				board[row][col] = 0;
				board[col][row] = 0;
				if(solutions() > 1){
					board[row][col] = temp1;
					board[col][row] = temp2;
				}
			}else{							//Under 40 elements removes them one at a time
				temp1 = board[row][col];
				board[row][col] = 0;
				if(solutions() > 1){
					board[row][col] = temp1;
					if(count() < 28){		//28 is a good stopping point if a puzzle is complete
						break;				//It will make puzzles with less than 28 however
					}
				}
			}
		}
	}
	/**
	 *@return the number of solutions a given board has 
	 */
	private int solutions(){
		int count = 0;
		int[][] testboard = this.board;	
		int row = nextOpen()[0];
		int col = nextOpen()[1];
		return search(row,col,count,testboard);
	}
	/**
	 * Recursive helper method for Solutions
	 */
	private int search(int row, int col, int count, int[][] testboard){
		if(row < 0){	//row is given by AbstractSudoku.nextOpen()
			return count + 1;
		}else{
			for(int i = 0; i < 9; i++){
				if(isSafe(TestRow[i], row, col)){
					testboard[row][col] = TestRow[i];
					int NewRow = nextOpen()[0];
					int NewCol = nextOpen()[1];
					count = search(NewRow,NewCol,count,testboard);
					board[row][col] = 0;
				}
			}
			return count;
		}
	}
}
