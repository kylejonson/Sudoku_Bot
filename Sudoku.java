/**
 *Helper Class for the TwitterBot Class
 *@author Kyle Jonson
 *@since 3/14/2017
 *@version 1.0.0
 */
import java.util.*;
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
	public void generateSolution(){
		//int[] row = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		//Random r = new Random();
		explore(0,0);
	}
	/**
	 * Recursive method to make a solve sudoku board
	 * @param row changing row
	 * @param col changing column
	 * @return true when the first solution is found
	 */
	private boolean explore(int row, int col){
		if(row < 0){	//row is given by AbstractSudoku.nextOpen()
			return true;
		}else{
			for(int i = 0; i < 9; i++){
				if(isSafe(TestRow[i], row, col)){
					board[row][col] = TestRow[i];
					int NewRow = nextOpen()[0];
					int NewCol = nextOpen()[1];
					if(explore(NewRow,NewCol)){
						return true;
					}
					board[row][col] = 0;
				}
			}
			return false;
		}
	}
	/**
	 * @return a Sudoku Puzzle based on the already solved board
	 */
	public int[][] getPuzzle(){
		generatePuzzle();
		return this.board;
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
				temp1 = board[row][col];
				temp2 = board[col][row];
				board[row][col] = 0;
				board[col][row] = 0;
				if(solutions() > 1){
					board[row][col] = temp1;
					board[col][row] = temp2;
				}
			}else{
				temp1 = board[row][col];
				board[row][col] = 0;
				if(solutions() > 1){
					board[row][col] = temp1;
					if(count() < 28){
						break;
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
		int[][] testboard = this.board;			//Keeps the board data intact
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
