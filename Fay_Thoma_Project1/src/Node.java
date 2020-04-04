import java.util.Random;

public class Node {
	private int priority, imin, imax, height;
	private Node parent, left, right;
	private Interval interv;

	/**
	 * @param i the interval to store in the node
	 * 
	 * generate a random priority in [0, 2^31 - 1) for the node
	 */
	public Node(Interval i) {
		interv = i;
		//generate a random priority to maintain balanced treap
		priority = new Random().nextInt(Integer.MAX_VALUE);
		height = 0;
	}
	
	/**
	 * @return the parent of the node
	 */
	public Node getParent() {
		return parent;
	}
	
	/**
	 * @return the left child of the node
	 */
	public Node getLeft() {
		return left;
	}
	
	/**
	 * @return the right child of the node
	 */
	public Node getRight() {
		return right;
	}
	
	/**
	 * @return the interval of the node
	 */
	public Interval getInterv() {
		return interv;
	}
	
	/**
	 * @return the imax of the node
	 */
	public int getIMax() {
		return imax;
	}
	
	/**
	 * @return the priority of the node
	 */
	public int getPriority() {
		return priority;
	}

	/*
	 * helper methods
	 */
	
	/**
	 * @return the height of the node
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @param height 
	 * 		the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * @param imax 
	 * 		the imax to set
	 */
	public void setIMax(int imax) {
		this.imax = imax;
	}
	
	/**
	 * @return the imin of the node
	 */
	public int getIMin() {
		return imin;
	}
	
	/**
	 * @param imax 
	 * 		the imin to set
	 */
	public void setIMin(int imin) {
		this.imin = imin;
	}

	/**
	 * @param parent 
	 * 		the parent to set
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * @param left 
	 * 		the left to set
	 */
	public void setLeft(Node left) {
		this.left = left;
	}

	/**
	 * @param right 
	 * 		the right to set
	 */
	public void setRight(Node right) {
		this.right = right;
	}
	
	/**
	 * @param priority 
	 * 		the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
