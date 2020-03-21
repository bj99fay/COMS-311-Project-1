import java.util.List;

public class IntervalTreap {
	Node root;
	int size, height;

	public IntervalTreap() {
		//TODO
		height = -1;
	}
	
	/**
	 * @return the root of the treap
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * @return the size of the treap
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * @return the height of the treap (-1 == an empty treap)
	 */
	public int getHeight() {
		return height;
	}
	
	public void intervalInsert(Node z) {
		//TODO
	}
	
	public void intervalDelete(Node z) {
		//TODO
	}
	
	public Node intervalSearch(Interval i) {
		//TODO
	}
	
	/**
	 * Extra credit
	 * 
	 * @param i
	 * 		the exact interval to search for
	 */
	public Node intervalSearchExactly(Interval i) {
		//TODO
	}
	
	/**
	 * Extra credit
	 * 
	 * @param i
	 * 		the interval to find overlaps of
	 */
	public List<Interval> overlappingIntervals(Interval i) {
		//TODO
	}
	
}
