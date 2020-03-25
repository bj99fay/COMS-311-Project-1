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
	
	/**
	 * Searching for an interval in the database that overlaps interval i
	 * To be implemented in O(log n) time
	 * @param i
	 * 	the given interval 
	 * @return
	 * 	Success: an interval that overlaps with i
	 * 	Unsuccessful: return null
	 */
	public Node intervalSearch(Interval i) {
		// IntervalSearch from CLRS
		Node x = root;
		while(x != null && !intervalOverlap(i, x.getInterv())) {
			if(x.getLeft() != null && x.getLeft().getIMax() >= i.getLow()) {
				x = x.getLeft();
			}
			else {
				x = x.getRight();
			}
		}
		return x;
	}
	
	/**
	 * Check if intervals i and x overlap using CLRS situations outlined
	 * @param i
	 * 	interval i
	 * @param x
	 * 	interval x
	 * @return
	 * 	true if intervals overlap
	 *  false if intervals do not overlap
	 */
	public boolean intervalOverlap(Interval i, Interval x) {
		boolean overlap = false;
		//check if they overlap using situations given by CLRS
		if(i.getLow() <= x.getHigh() && x.getLow() <= i.getHigh()) {
			overlap = true;
		}
		return overlap;
	}
	
	/**
	 * Extra credit
	 * 
	 * @param i
	 * 		the exact interval to search for
	 */
	public Node intervalSearchExactly(Interval i) {
		//TODO
		return null;
	}
	
	/**
	 * Extra credit
	 * 
	 * @param i
	 * 		the interval to find overlaps of
	 */
	public List<Interval> overlappingIntervals(Interval i) {
		//TODO
		return null;
	}
	
}
