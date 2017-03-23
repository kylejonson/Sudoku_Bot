/**
 * A queue for the tweets
 * @author Kyle Jonson
 * @since 3/14/2017
 * @version 1.0.1
 */
public class TweetQueue {
	/**
	 * Constructor for an empty queue
	 */
	TweetNode head;
	public TweetQueue(){
		head = null;
	}
	/**
	 * Constructor for the queue which starts at a give node
	 * @param node the given node
	 */
	public TweetQueue(TweetNode node){
		head = node;
	}
	/**
	 * adds a given TweetNode to the end
	 * @param node the give node
	 */
	public void add(TweetNode node){
		if(head == null){
			this.head = node;
		}
		TweetNode tweet = head;
		while((tweet = tweet.next) != null){
			tweet.next = node;
		}
	}
	/**
	 * Returns the TweetNode at an index
	 * @param index the index
	 * @return TweetNode at that index
	 */
	public TweetNode get(int index){
		if(head == null){
			return null;
		}
		int i = 0;
		TweetNode tweet = head;
		while(tweet.next != null){		//Loop until the end
			if(i < index){
				tweet = tweet.next;
				i++;
			}else{
				break;
			}
		}
		return tweet;
	}
	/**
	 * removes an an item at a given index
	 * @param index
	 */
	public void remove(int index){
		if(head == null){
			return;
		}
		int i = 0;
		TweetNode tweet = head;
		while(tweet.next != null){		//Loop until the end
			if(i < index){
				tweet = tweet.next;
				i++;
			}else{
				break;
			}
		}
		if(tweet.equals(head)){
			head = head.next;
		}else if(tweet.next.next == null){
			tweet.next = null;
		}else{
			tweet.next = tweet.next.next;
		}
	}
	/**
	 * Size of the queue
	 * @return int of the number of elements
	 */
	public int size(){
		return recursiveSize(head);
	}
	private int recursiveSize(TweetNode node){
		if(node == null){
			return 0;
		}else{
			return 1 + recursiveSize(node.next);
		}
	}
}
/**
 * TweetNode object to make the TweetQueue work
 * @author Kyle Jonson
 */
class TweetNode{
	int[][] solution;
	int[][] puzzle;
	TweetNode next;
	public TweetNode(int[][] solution, int[][] puzzle){
		this.solution = solution;
		this.puzzle = puzzle;
		this.next = null;
	}
	public TweetNode(int[][] solution, int[][] puzzle, TweetNode next){
		this.solution = solution;
		this.puzzle = puzzle;
		this.next = next;
	}
	public boolean equals(TweetNode node){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(puzzle[i][j] != node.puzzle[i][j]
					|| solution[i][j] != node.solution[i][j]){
					return false;
				}
			}
		}
		return true;
	}
}
