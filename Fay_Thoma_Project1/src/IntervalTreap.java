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
	
	/**
	 * Insert a node into the treap
	 * @param z
	 * 	the node to insert into the treap
	 */
	public void intervalInsert(Node z) {
		//make sure the node isn't null
		if(z == null) return;
		
		//generate a random priority to maintain balanced treap
		z.setPriority(new Random().nextInt(Byte.MAX_VALUE));
		
		//set the IMax intially to the node's high value
		z.setImax(z.getInterv().getHigh());
		
		//go down the tree based the z's value until we reach a leaf
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
		
		//set the leaf to be z's parent
		z.setParent(y);
		
		//assign z to be the correct child of y based on low value
		if(y == null) {
			root = z;
		} else if(z.getInterv().getLow() < y.getInterv().getLow()) {
			y.setLeft(z);
		} else {
			y.setRight(z);
		}
		
		//perform rotations to maintain heap property of the treap
		//rotate based on priority
		while(z.getParent() != null && z.getPriority() < z.getParent().getPriority()) {
			if(z.equals(z.getParent().getLeft())) {
				this.rightRotate(z.getParent());
			} else {
				this.leftRotate(z.getParent());
			}
		}
		
		//adjust the IMax and Height of all the nodes above the inserted node
		y = z.getParent();
		while(y != null) {
			this.makeAdjustments(y);
			
			//assign as root if node doesn't have parent
			if(y.getParent() == null) {
				root = y;
			}
			
			y = y.getParent();
		}
		
		//increment the size of the treap
		size++;
		
		//set height to height of root node
		height = root.getHeight();
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
		Node successor = null;
		Node y = null;
		
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
			y = z.getRight();
		}
		// Case 2: z has a left child, but no right child: replace z by its left child
		else if(hasLeftChild && !hasRightChild) {
			deleteTransplant(z, z.getLeft());
			y = z.getLeft();
		}
		// Case 3: z has two children: replace z by its successor y = Minimum(z.right)
		else {
			//replace z with successor
			successor = minimum(z.getRight());
			if(z.getRight() != successor) {
				//Case 3b; change right child
				deleteTransplant(successor,successor.getRight());
				//set successor right
				successor.setRight(z.getRight());
				z.getRight().setParent(successor);
			}
			if(successor.getParent() != z) {
				y = successor.getParent();
			} else {
				y = successor;
			}
			deleteTransplant(z,successor);
			//set successor left
			successor.setLeft(z.getLeft());
			z.getLeft().setParent(successor);
		}
		//phase 2
		//traverse treap to update imax fields
		Node nodeToUpdate = y;
		if(nodeToUpdate == null) {
			if(z.getParent() == null) {
				z = null;
				size = 0;
				height--;
				return;
			}
			nodeToUpdate = z.getParent();
		}
		//from startUpdate to root
		while(nodeToUpdate != null) {
			makeAdjustments(nodeToUpdate);
			nodeToUpdate = nodeToUpdate.getParent();
		}
		
		//rotate y downward based on priority
		if(successor != null) {
			y = successor;
		}
		if(y != null) {
			while((y.getLeft() != null && y.getPriority() > y.getLeft().getPriority()) || (y.getRight() != null &&  y.getPriority() > y.getRight().getPriority())) {
				if(y.getLeft() != null && y.getRight() != null) {
					if(y.getLeft().getPriority() > y.getRight().getPriority()) {
						this.leftRotate(y);
					} else {
						this.rightRotate(y);
					}
				}	
				else if(y.getLeft() != null && y.getPriority() > y.getLeft().getPriority()) {
					this.rightRotate(y);
				}
				else if(y.getRight() != null && y.getPriority() > y.getRight().getPriority()) {
					this.leftRotate(y);
				}
			}
		}
		
		//update heights from first node that was rotated into y's spot upwards
		while(y != null) {
			makeAdjustments(y);
			y = y.getParent();
		}
		
		z = null;
		size--;
		//set height to height of root node
		height = root.getHeight();
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
		
		this.makeAdjustments(x);
		this.makeAdjustments(y);
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
		
		this.makeAdjustments(x);
		this.makeAdjustments(y);
	}
	
	
	/**
	 * Adjusts the iMax value and Height of x based on the rule specified in the assignment
	 * @param x
	 * 		the node to adjust the iMax value and Height of
	 */
	private void makeAdjustments(Node x) {
		if(x == null) return;
		else if(x.getLeft() == null && x.getRight() == null) {
			x.setImax(x.getInterv().getHigh());
			x.setHeight(0);
		} else if(x.getRight() == null) {
			x.setImax(Math.max(x.getInterv().getHigh(), x.getLeft().getIMax()));
			x.setHeight(x.getLeft().getHeight() + 1);
		} else if(x.getLeft() == null) {
			x.setImax(Math.max(x.getInterv().getHigh(), x.getRight().getIMax()));
			x.setHeight(x.getRight().getHeight() + 1);
		} else {
			x.setImax(Math.max(Math.max(x.getInterv().getHigh(), x.getLeft().getIMax()), x.getRight().getIMax()));
			x.setHeight(Math.max(x.getLeft().getHeight(), x.getRight().getHeight()) + 1);
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
		// IntervalSearch from CLRS
		Node x = root;
		while(x != null && !intervalExactOverlap(i, x.getInterv())) {
			if(x.getLeft() != null && x.getInterv().getLow() >= i.getLow()) {
				x = x.getLeft();
			}
			else {
				x = x.getRight();
			}
		}
//		System.out.println("x: (" + x.getInterv().getLow() + " - " + x.getInterv().getHigh() + ")");
		return x;
	}
	
	/**
	 * Check if intervals i and x overlap exactly
	 * @param i
	 * 	interval i
	 * @param x
	 * 	interval x
	 * @return
	 * 	true if intervals overlap exactly
	 *  false if intervals do not overlap exactly
	 */
	public boolean intervalExactOverlap(Interval i, Interval x) {
		boolean overlap = false;
		//check if they overlap exactly
		if(i.getLow() == x.getLow() && i.getHigh() == x.getHigh()) {
			overlap = true;
//		} else {
//			System.out.println("Lows: i = " + i.getLow() + "; x = " + x.getLow());
//			System.out.println("Highs: i = " + i.getHigh() + "; x = " + x.getHigh());
		}
		return overlap;
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
