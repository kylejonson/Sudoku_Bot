/**
 * Helper Class for the TwitterBot Class
 * Makes an image out of a Sudoku puzzle matrix to post to twitter
 * @author Kyle Jonson
 * @since 3/14/2017
 * @version 1.0.0
 */
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class SudokuImage {

	private int[][] sudoku;
	private String filename;
	private final int SIDE = 631;
	private final int NINTH = SIDE / 9;
	/**
	 * Constructor for sudoku images
	 * @param data the sudoku data
	 * @param filename name of image file
	 */
	public SudokuImage(int[][] data, String filename){
		this.sudoku = data;
		this.filename = "a:\\" + filename + ".PNG";
	}
	/**
	 * Creates an Image from the given Sudoku board
	 */
	public void makeImage(){
		BufferedImage bi = new BufferedImage(SIDE, SIDE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.setPaint(Color.black);
		Font font = new Font("Helvetica", 0, 50);
		g.setFont(font);
		writeGrid(g);
		writeSudoku(g);
		try {
			ImageIO.write(bi, "PNG", new File(this.filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Draws the Sudoku grid
	 * @param g graphics
	 */
	private void writeGrid(Graphics2D g){
		g.drawLine(0, 0, 0, SIDE);
		g.drawLine(1, 0, 1, SIDE);
		g.drawLine(0, 0, SIDE, 0);
		g.drawLine(0, 1, SIDE, 1);
		for(int i = 1; i < 10; i++){
			if(i % 3 == 0){
				g.drawLine(i*NINTH-1,0,i*NINTH-1,SIDE);
				g.drawLine(0,i*NINTH-1,SIDE,i*NINTH-1);
			}
			g.drawLine(i*NINTH,0,i*NINTH,SIDE);
			g.drawLine(0,i*NINTH,SIDE,i*NINTH);
		}
		
	}
	/**
	 * Writes the sudoku numbers
	 * @param g graphics
	 */
	private void writeSudoku(Graphics2D g){
		String a;
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				a = Integer.toString(this.sudoku[j][i]);
				if(a.equals("0")){
					a = " ";
				}
				g.drawString(a, j * NINTH + 21, i * NINTH + 54);
			}
		}
	}
}
