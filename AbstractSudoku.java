/**
 * Abstract Class for all the base functions my Sudoku Board has
 * 
 * @author Kyle Jonson
 * @since 3/14/2017
 */
import java.util.Random;
public abstract class AbstractSudoku {
	protected int[][] board;
	protected int[][] puzzle;
	/**
	 * Constructor for a sudoku board
	 * fills it with zereos
	 */
	protected AbstractSudoku(){
		board = new int[9][9];
		puzzle = new int[9][9];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				board[i][j] = 0;	//Sets all values to zero
				puzzle[i][j] = 0;	//Zero is used in place of null for easier comparison
			}						
		}
	}
	/**
	 * @param value 1-9 value to test
	 * @param row row location, index in array
	 * @param col column location, index in array
	 * @return true if its safe for a number at a given location
	 */
	protected boolean isSafe(int value, int row, int col){
		if(board[row][col] == 0){
			if(checkRow(value,row)){
				if(checkCol(value,col)){
					if(checkSquare(value,row,col)){
						return true;	//Nested ifs so that not all are necessarily check every time										
					}					//This method is ran a lot so any small gain is a big one ya dig
				}
			}
		}
		return false;
	}
	/**
	 * @param value test value
	 * @param row the row array index
	 * @return true: okay to place
	 */
	private boolean checkRow(int value, int row){
		for(int i = 0; i <= 8; i++){
			if(board[row][i] == value){
				return false;
			}
		}
		return true;
	}
	/**
	 * @param value test value
	 * @param col the column array index
	 * @return true: okay to place
	 */
	private boolean checkCol(int value, int col){
		for(int i = 0; i <= 8; i++){
			if(board[i][col] == value){
				return false;
			}
		}
		return true;
	}
	/** 
	 * @param value test value
	 * @param row the row array index
	 * @param col the column array index
	 * @return true: okay to place
	 */
	private boolean checkSquare(int value, int row, int col){
		int a = 0;
	    int b = 0;
		if(row > 5){		//Tree for which subsquare you are checking
			a = 6;
		}else if(row > 2){
			a = 3;
		}
		if(col > 5){
			b = 6;
		}else if(col > 2){
			b = 3;
		}
		for(int i = a; i < a+3; i++){		//Checks the square
			for(int j = b; j < b+3; j++){	//With nested for loops
				if(board[i][j] == value){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Finds the next open spot
	 * @return int[] of [row, column]. [-1,-1] if no spot is found
	 */
	protected int[] nextOpen(){
		int[] coords = new int[2];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){	
				if(board[i][j] == 0){	//If it's 0 the spot is "empty"
					coords[0] = i;
					coords[1] = j;
					return coords;
				}
			}
		}
		coords[0] = -1;					//Negative location
		coords[1] = -1;					//Because board is full
		return coords;
	}
	/**
	 * Shuffles a given array using Fisher-Yates Shuffle
	 * Needed for making *random* sudoku solutions
	 * @param array
	 * @return a shuffled array
	 */
	protected int[] shuffle(int[] array){
		Random rand = new Random();
		for(int i = 1; i < array.length; i++){
			int ind = rand.nextInt(i);
			int a = array[ind];
			array[ind] = array[i];
			array[i] = a;
		}
		return array;
	}
	/**
	 * @return the number of elements on the board
	 */
	protected int count(){
		int count = 0;
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(board[i][j] != 0){
					count++;
				}
			}
		}
		return count;
	}
}