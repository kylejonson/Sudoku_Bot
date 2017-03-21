/**
 * This is a Twitter Bot which tweets a Sudoku problem, then after a day posts
 * the solution and a new problem.
 * 
 * Important note: By default this saves to a folder C:\Images
 * This code WILL NOT work if this folder doesn't exist
 * and you do not change the path
 * 
 * @author Kyle Jonson
 * @since 3/14/2017
 * @version 1.0.0
 *
 */
import java.io.File;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
 
public class TwitterBot {
	private final String PUZZLENAME = "c:\\images\\puzzle";
	private final String SOLUTIONNAME = "c:\\images\\solution";
	private TweetQueue queue;
	private File puzzle;
    private File solution;
    private Twitter twitter;
    /**
     * Twitter Bot Constructor
     * @param token
     * @param tokenSecret
     * @param consumer
     * @param consumerSecret
     */
    public TwitterBot(String token, String tokenSecret, String consumer, String consumerSecret){
		queue = new TweetQueue();
		authenticate(token, tokenSecret, consumer, consumerSecret);
	}
    public void authenticate(String token, String tokenSecret, String consumer, String consumerSecret){
    	TwitterFactory factory = new TwitterFactory();
    	AccessToken accesstoken = new AccessToken(token, tokenSecret);
    	this.twitter = factory.getInstance();
    	twitter.setOAuthConsumer(consumer, consumerSecret);
    	twitter.setOAuthAccessToken(accesstoken);
    }
    /**
     * Actually tweets out
     */
 	private void tweet(int count){
		SudokuImage PuzzleImage = new SudokuImage(queue.get(0).puzzle, PUZZLENAME);
		PuzzleImage.makeImage();
		this.puzzle = new File(PUZZLENAME + ".png");
		SudokuImage SolutionImage = new SudokuImage(queue.get(0).solution,SOLUTIONNAME); 
		SolutionImage.makeImage();
		this.solution = new File(SOLUTIONNAME + "png"); //Change this path for your image folder
		try{
			StatusUpdate status = new StatusUpdate("Puzzle #" + count);
			status.setMedia(puzzle);
			twitter.updateStatus(status);	//Tweet First Image
		}catch(TwitterException e){
			e.printStackTrace();
		}
		try{
			Thread.sleep((long) (8.64E7));	//Wait one day
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		try{
			StatusUpdate status = new StatusUpdate("Solution #" + count);
			status.setMedia(solution);
			twitter.updateStatus(status);	//Tweet Second Image
		}catch(TwitterException e){
			e.printStackTrace();
		}
	}
 	/**
 	 * Loops the twitter bot thorugh its action
 	 */
	public void tweetLoop(){
		Sudoku s = new Sudoku();								//Addding intial items to queue
		int[][] solve1 = s.getSolution();
		int[][] puzzle1 = s.getPuzzle();
		TweetNode node1 = new TweetNode(solve1, puzzle1);
		int[][] solve2 = s.getSolution();
		int[][] puzzle2 = s.getPuzzle();
		TweetNode node2 = new TweetNode(solve2, puzzle2,node1);
		queue.add(node2);
		int count = 1;
		while(queue.size() >= 0){								//WARNING: Loops forever 
			tweet(count);
			queue.remove(0);
			solve1 = s.getSolution();
			puzzle1 = s.getPuzzle();							
			queue.add(new TweetNode(solve1,puzzle1));			//Continues adding to queue
			count++;
		}
	}
}
