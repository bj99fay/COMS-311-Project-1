import java.util.List;
import java.util.Random;

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
		if(z == null) return;
		
		z.setPriority(new Random().nextInt(Byte.MAX_VALUE));
		z.setImax(z.getInterv().getHigh());
		Node y = null;
		Node x = root;
		while(x != null) {
			y = x;
			y.setImax(Math.max(y.getIMax(), z.getInterv().getHigh()));
			if(z.getInterv().getLow() < x.getInterv().getLow()) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		z.setParent(y);
		if(y == null) {
			root = z;
		} else if(z.getInterv().getLow() < y.getInterv().getLow()) {
			y.setLeft(z);
		} else {
			y.setRight(z);
		}
		
		while(z.getParent() != null && z.getPriority() < z.getParent().getPriority()) {
			if(z.equals(z.getParent().getLeft())) {
//				System.out.println("***Right Rotate***");
//				System.out.println("Parent: " + z.getParent().getInterv().getLow() + " | Priority: " + z.getParent().getPriority());
//				System.out.println("Node: " + z.getInterv().getLow() + "  | Priority: " + z.getPriority());
//				System.out.println("----------------------------------------");
				this.rightRotate(z.getParent());
			} else {
//				System.out.println("***Left Rotate***");
//				System.out.println("Parent: " + z.getParent().getInterv().getLow() + " | Priority: " + z.getParent().getPriority());
//				System.out.println("Node: " + z.getInterv().getLow() + "  | Priority: " + z.getPriority());
//				System.out.println("----------------------------------------");
				this.leftRotate(z.getParent());
			}
		}
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
	 * Performs a left rotation on the subtree rooted at x.parent (CLRS)
	 * and updates the iMax fields accordingly
	 * @param x
	 * 		the node to left rotate into its parent's spot
	 */
	private void leftRotate(Node x) {
		Node y = x.getRight();
		x.setRight(y.getLeft());
		if(y.getLeft() != null) {
			y.getLeft().setParent(x);
		}
		y.setParent(x.getParent());
		if(x.getParent() == null) {
			root = y;
		} else if(x == x.getParent().getLeft()) {
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		y.setLeft(x);
		x.setParent(y);
		
		if(y != null) this.adjustIMax(y);
		if(x != null) this.adjustIMax(x);
		if(x.getRight() != null) this.adjustIMax(x.getRight());
	}
	
	/**
	 * Performs a right rotation on the subtree rooted at y.parent (CLRS)
	 * and updates the iMax fields accordingly
	 * @param x
	 * 		the node to right rotate into its parent's spot
	 */
	private void rightRotate(Node x) {
		Node y = x.getLeft();
		x.setLeft(y.getRight());
		if(y.getRight() != null) {
			y.getRight().setParent(x);
		}
		y.setParent(x.getParent());
		if(x.getParent() == null) {
			root = y;
		} else if(x == x.getParent().getLeft()) {
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		y.setRight(x);
		x.setParent(y);
		
		if(y != null) this.adjustIMax(y);
		if(x != null) this.adjustIMax(x);
		if(x.getLeft() != null) this.adjustIMax(x.getLeft());
	}
	
	
	/**
	 * Adjusts the iMax value of x based on the rule specified in the assignment
	 * @param x
	 * 		the node to adjust the iMax value of
	 */
	private void adjustIMax(Node x) {
		if(x == null) return;
		else if(x.getLeft() == null && x.getRight() == null) {
			x.setImax(x.getInterv().getHigh());
		} else if(x.getRight() == null) {
			x.setImax(Math.max(x.getInterv().getHigh(), x.getLeft().getIMax()));
		} else if(x.getLeft() == null) {
			x.setImax(Math.max(x.getInterv().getHigh(), x.getRight().getIMax()));
		} else {
			x.setImax(Math.max(Math.max(x.getInterv().getHigh(), x.getLeft().getIMax()), x.getRight().getIMax()));
		}
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
