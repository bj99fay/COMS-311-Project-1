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
				this.rightRotate(z.getParent());
			} else {
				this.leftRotate(z.getParent());
			}
		}
	}
	
	/**
	 * Removes node z from the interval treap
	 * @param z
	 * 	Node to be removed
	 */
	public void intervalDelete(Node z) {
		//TODO
		boolean hasLeftChild = false;
		boolean hasRightChild = false;
		boolean isRoot = false;
		
		//Check for z's children
		if(z.getLeft() != null) {
			hasLeftChild = true;
		}
		if(z.getRight() != null) {
			hasRightChild = true;
		}
		//Check if z is the root
		if(z.getParent() == null) {
			isRoot = true;
		}
		
		//Case 1: z has no left child: replace z by its right child, which may be null.
		if(!hasLeftChild) {
			//update root if necessary
			if(isRoot) {
				root = z.getRight();
			} else {
				//z is the left child of its parent
				if(z.getParent().getLeft() == z) {
					z.getParent().setLeft(z.getRight());
				} 
				//z is the right child of its parent
				else {
					z.getParent().setRight(z.getRight());
				}
			}
			if(z.getRight() != null) {
				z.getRight().setParent(z.getParent());
			}
			//remove z
			z = null;
		}
		
		// Case 2: z has a left child, but no right child: replace z by its left child
		if(hasLeftChild && !hasRightChild) {
			//z = z.getLeft();
			//update root if necessary
			if(isRoot) {
				root = z.getLeft();
			}
			
			z = null;
		}
		
		// Case 3: z has two children: replace z by its successor y = Minimum(z.right)
		if(hasLeftChild && hasRightChild) {
			//replace z with successor
			Node successor = minimum(z.getRight());
			z = successor;
			if(isRoot) {
				root = z;
			}
			//fix successor's links
			
		}
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
	 * Get the node in the subtree rooted at x
	 * that is the left most interval.
	 * @param x
	 * 	root left most node in subtree
	 * @return
	 * 	node left most node in subtree
	 */
	private Node minimum(Node x) {
		while(x.getLeft() != null) {
			x = x.getLeft();
		}
		return x;
	}
	
	
	/** Note: FUNCTION MAY NOT BE NECESSARY
	 * Return the node containing the interval
	 * 	immediately following x in the intervalTreap
	 *  that contains x.
	 * @param x
	 * 	Node to return successor of
	 * @return
	 * 	node with interval immediately following x
	 */
	private Node successor(Node x) {
		if(x.getRight() != null) {
			return minimum(x.getRight());
		}
		
		Node y = x.getParent();
		while(y != null && x == y.getRight()) {
			x = y;
			y = y.getParent();
		}
		return y;
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
