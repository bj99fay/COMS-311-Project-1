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
		//phase 1
		boolean hasLeftChild = false;
		boolean hasRightChild = false;
		
		//Check for z's children
		if(z.getLeft() != null) {
			hasLeftChild = true;
		}
		if(z.getRight() != null) {
			hasRightChild = true;
		}
		
		//Case 1: z has no left child: replace z by its right child, which may be null.
		if(!hasLeftChild) {
			deleteTransplant(z, z.getRight());
		}
		// Case 2: z has a left child, but no right child: replace z by its left child
		else if(hasLeftChild && !hasRightChild) {
			deleteTransplant(z, z.getLeft());
		}
		// Case 3: z has two children: replace z by its successor y = Minimum(z.right)
		else {
			//replace z with successor
			Node successor = minimum(z.getRight());
			if(z.getRight() != successor) {
				//Case 3b; change right child
				deleteTransplant(successor,successor.getRight());
				//set successor right
				successor.setRight(z.getRight());
				z.getRight().setParent(successor);
			}
			deleteTransplant(z,successor);
			//set successor left
			successor.setLeft(z.getLeft());
			z.getLeft().setParent(successor);
		}
		
		//TODO not sure if this is needed; remove z?
		z = null;
		
		//phase 2
		/* The second phase rotates z’s replacement y (assuming y is not null) down the tree as far as necessary
			to satisfy the constraint that v.priority > v.parent.priority for every node v.
				Now, suppose we want to delete a node z from an interval treap. We begin by deleting z as in
			the first phase of ordinary treap deletion. Next, we update the necessary imax fields by traversing
			a path up the treap.
				• If z has been replaced by null, the path goes from the former parent of z to the root of the
					treap.
				• If z has been replaced by a node y, the path starts at y’s original position in the treap and
					goes up to the root.
				In both cases, we decrement the imax field of each node on the path, as needed. Since the
			expected length of this path is O(log n) in an n-node treap, the time spent in traversing the path to
			update imax fields is O(log n).
				Finally, if z has been replaced by a node y, we rotate y downward as in an ordinary treap, in
			order to reestablish the correct priority relationships. We handle the rotations in the same manner
			as for insertion — that is, we update imax fields after each rotation. Thus, like insertion, deletion
			takes expected O(log n) time for an n-node interval treap.*/
		
		
		
		
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
	
	/**
	 * Replace the subtree rooted at node u with the subtree
     *	rooted at node v
	 * @param u
	 * 	node whose subtree needs to be replaced
	 * @param v
	 * 	node whose subtree replaces u's
	 */
	private void deleteTransplant(Node u, Node v) {
		//update root if necessary
		if(u.getParent() == null) {
			root = v;
		}
		//z is the left child of its parent
		else if(u == u.getParent().getLeft()) {
			u.getParent().setLeft(v);
		} 
		//z is the right child of its parent
		else {
			u.getParent().setRight(v);
		}
		if(v != null) {
			v.setParent(u.getParent());
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
