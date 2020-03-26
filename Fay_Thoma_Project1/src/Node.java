
public class Node {
	private int priority, imax;
	private Node parent, left, right;
	private Interval interv;

	/**
	 * @param i the interval to store in the node
	 * 
	 * generate a random priority in [0, 2^31 - 1) for the node
	 */
	public Node(Interval i) {
		interv = i;
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

	//helper methods
	
	/**
	 * @param imax 
	 * 		the imax to set
	 */
	public void setImax(int imax) {
		this.imax = imax;
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
