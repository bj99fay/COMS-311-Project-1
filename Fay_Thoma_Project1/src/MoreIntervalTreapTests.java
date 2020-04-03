/*import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;*/

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.Test;

/**
 * 
 * @author Original: Micah Mundy
 * Edited by Riley Thoma and Bernie Fay
 *
 */

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntervalTreapTest {

	/**
	 * Adds two intervals to the treap and checks their priority
	 */
	@Test
	void test01CreateIntervalTreap() {
		Interval i = new Interval(0, 4);
		Node n = new Node(i);
		n.setPriority(1);


		Interval j = new Interval(4, 5);
		Node o = new Node(j);
		o.setPriority(2);

		IntervalTreap it = new IntervalTreap();
		it.intervalInsert(n);
		it.intervalInsert(o);

		//The root must have the lowest priority
		assert (it.root == n);
	}

	/**
	 * Same as test 02, but priorities are reversed
	 */
	@Test
	void test02CreateIntervalTreap() {
		Interval i = new Interval(0, 4);
		Node n = new Node(i);
		n.setPriority(2);


		Interval j = new Interval(4, 5);
		Node o = new Node(j);
		o.setPriority(1);

		IntervalTreap it = new IntervalTreap();
		it.intervalInsert(n);
		it.intervalInsert(o);

		//The root must have the lowest priority
		assert (it.root == o);
	}

	/**
	 * Same as test 2, but nodes are added in reverse order
	 */
	@Test
	void test03CreateIntervalTreap() {
		Interval i = new Interval(0, 4);
		Node n = new Node(i);
		n.setPriority(1);


		Interval j = new Interval(4, 5);
		Node o = new Node(j);
		o.setPriority(2);

		IntervalTreap it = new IntervalTreap();
		it.intervalInsert(o);
		it.intervalInsert(n);

		//The root must have the lowest priority
		assert (it.root == n);
	}


	/**
	 * Same as test 3, but nodes are added in reverse order
	 */
	@Test
	void test04CreateIntervalTreap() {
		Interval i = new Interval(0, 4);
		Node n = new Node(i);
		n.setPriority(2);


		Interval j = new Interval(4, 5);
		Node o = new Node(j);
		o.setPriority(1);
		
		IntervalTreap it = new IntervalTreap();
		it.intervalInsert(o);
		it.intervalInsert(n);

		//The root must have the lowest priority
		assert (it.root == o);

		checkIntevalTreap(it);
	}


	/**
	 * Same as test 4, but priorities are not enforced
	 */
	@Test
	void test05CreateIntervalTreap() {
		Interval i = new Interval(0, 4);
		Node n = new Node(i);


		Interval j = new Interval(4, 5);
		Node o = new Node(j);

		IntervalTreap it = new IntervalTreap();
		it.intervalInsert(o);
		it.intervalInsert(n);

		checkIntevalTreap(it);
	}



	/**
	 * Chuck a million nodes into the treap and check if it
	 * has treap-like properties
	 */
	@Test
	void test06CreateIntervalTreap() {
		IntervalTreap it = new IntervalTreap();

		int numNodes = 1000000;

		for(int i = 0; i < numNodes; i++) {
			insertInterval(it, i, i*2);
		}

		if(numNodes < 51) {
			printIntervalTreap(it);
		}

		assert numNodes == it.size;

		checkIntevalTreap(it);
	}

	/**
	 * Chuck a million <b>random</b> nodes into the 
	 * treap and check if it has treap-like properties
	 */
	@Test
	void test07CreateIntervalTreap() {
		IntervalTreap it = new IntervalTreap();
		Random rand = new Random();

		int numNodes = 1000000;

		for(int i = 0; i < numNodes; i++) {
			int r1 = rand.nextInt(),
					r2 = rand.nextInt();
			insertInterval(it, Math.min(r1,r2), Math.max(r1,r2));
		}

		if(numNodes < 51) {
			printIntervalTreap(it);
		}

		assert numNodes == it.size;

		checkIntevalTreap(it);
	}




	/**
	 * Test a couple cases where every node must bubble up
	 * to become the root node
	 */
	@Test
	void test08CreateIntervalTreap() {

		int numNodes = 10;

		IntervalTreap it = new IntervalTreap();

		for(int i = 0; i < numNodes; i++) {
			Node n = new Node(new Interval(i, i+8));
			n.setPriority(numNodes-i);
			it.intervalInsert(n);

			assert(it.root == n);
		}

		for(int i = 0; i < numNodes; i++) {
			Node n = new Node(new Interval(-i-8, -i));
			n.setPriority(-i);
			it.intervalInsert(n);

			assert(it.root == n);
		}

		checkIntevalTreap(it);
	}

	@Test
	void test09SquiggleTrees() {
		IntervalTreap it = new IntervalTreap();
		insertInterval(it, 0, 16, 1);
		insertInterval(it, 8, 15, 2);
		insertInterval(it, 4, 17, 3);

		checkIntevalTreap(it);

		it = new IntervalTreap();
		insertInterval(it, 0, 16, 1);
		insertInterval(it, 4, 17, 3);
		insertInterval(it, 8, 15, 2);

		checkIntevalTreap(it);

		it = new IntervalTreap();
		insertInterval(it, 4, 17, 3);
		insertInterval(it, 8, 15, 2);
		insertInterval(it, 0, 16, 1);

		checkIntevalTreap(it);

		it = new IntervalTreap();
		insertInterval(it, 4, 17, 4);
		insertInterval(it, 2, 16, 1);
		insertInterval(it, 0, 15, 2);
		insertInterval(it, 8, 14, 3);

		checkIntevalTreap(it);

	}


	/**
	 * Add entries until something breaks
	 */


//	@Test void test10CreateIntervalTreap() { 
//		IntervalTreap it = new IntervalTreap();
//		Random rand = new Random();
//
//		while(true) {
//
//			System.out.println("\n\n\n\n");
//
//			int r1 = rand.nextInt(),
//					r2 = rand.nextInt();
//			insertInterval(it, Math.min(r1,r2), Math.max(r1,r2));
//
//			System.out.println("\n"); printIntervalTreap(it);
//
//
//			checkSize(it); 
//			checkPriorities(it); 
//			checkTreeStructure(it); 
//			checkImax(it); 
//		}
//
//	}
	
	/**
	 * Tests the deletion of the root node when the treap
	 * only contains one node.
	 */
	@Test 
	public void test12DeleteOnlyNodeInTreap() {
		IntervalTreap it = new IntervalTreap();
		insertInterval(it, -5, 5);
		assert(it.size==1);
		assert(it.getHeight()==0);
		assert(it.getSize()==1);
		
		Node n = it.getRoot();
		it.intervalDelete(n);
		
		assert(it.root==null);
		assert(it.size==0);
		System.out.println(it.getHeight());
		assert(it.getHeight()<1);
		assert(it.getSize()==0);
	}
	
	/**
	 * Add a bunch of elements and delete them. <p>
	 * Only works if tests 07 and 08 work.
	 */
//	@Test
//	public void test13DeleteLast() {
//
//		IntervalTreap it = new IntervalTreap();
//		Random rand = new Random();
//
//		int numNodes = 5000;
//		
//		//Begin addition
//		for(int i = 0; i < numNodes; i++) {
//			int r1 = rand.nextInt(),
//					r2 = rand.nextInt();
//			insertInterval(it, Math.min(r1,r2), Math.max(r1,r2));
//		}
//		
//		checkIntevalTreap(it);
//		
//		//Begin deletion
//		while(it.size!=0) {
//			Node n = it.findLast();
//			it.intervalDelete(n);
//			
//			checkIntevalTreap(it);
//		}
//	}
	
	/**
	 * Add a bunch of elements and delete them randomly. <p>
	 */
	@Test
	public void test14DeleteRandom() {
		

		int numNodes = 10000;

		IntervalTreap it = new IntervalTreap();
		Random rand = new Random();
		ArrayList<Node> nodes = new ArrayList<Node>(numNodes);

		//Begin addition
		for(int i = 0; i < numNodes; i++) {
			int r1 = rand.nextInt(),
					r2 = rand.nextInt();
			Node n = new Node(new Interval(Math.min(r1,r2), Math.max(r1,r2)));
			nodes.add(n);
			it.intervalInsert(n);
		}
		
		checkIntevalTreap(it);
		
		//Shuffle the ArrayList so objects are deleted
		//randomly.
		Collections.shuffle(nodes);
		
		for(Node n: nodes) {
			it.intervalDelete(n);
			
			//Check size
			assert(it.getSize()==numNodes-1);
			numNodes--;
			
			//Check treap structure
			checkIntevalTreap(it);
		}
	}
	
	/**
	 * Add a thousand random nodes and check that getHeight() is correct
	 */
	@Test public void test15GetHeight() {
		IntervalTreap it = new IntervalTreap();
		Random rand = new Random();

		int numNodes = 1000;

		for(int i = 0; i < numNodes; i++) {
			int r1 = rand.nextInt(),
					r2 = rand.nextInt();
			insertInterval(it, Math.min(r1,r2), Math.max(r1,r2));
			
			testHeight(it);
		}

	}
	


	//Helper methods


	/**
	 * Recursively check that the priority of parent nodes
	 * is less than that of their children
	 * @param n the node to begin searching
	 */
	void testPrioritiesRecursive(Node n) {
		if(n==null) return;

		if(n.getLeft() != null) {
			assert (n.getPriority() < n.getLeft().getPriority());
			testPrioritiesRecursive(n.getLeft());
		}

		if(n.getRight() != null) {
			assert (n.getPriority() < n.getRight().getPriority());
			testPrioritiesRecursive(n.getRight());
		}
	}

	/**
	 * Test that the structure of the treap
	 * follows the treap property that the priority
	 * of each node is less than that of all its
	 * descendants.
	 * @param it
	 */
	void checkPriorities(IntervalTreap it) {
		testPrioritiesRecursive(it.root);
	}

	/**
	 * Shorthand for the following <p>
	 * <code>it.intervalInsert(new Node(new Interval(low, high)));</code>
	 * @param it an interval treap
	 * @param low the low value of the interval to insert in to the treap
	 * @param high the high value of the interval to insert in to the treap
	 */
	void insertInterval(IntervalTreap it, int low, int high) {
		it.intervalInsert(new Node(new Interval(low, high)));
	}

	/**
	 * Shorthand for the following <p>
	 *
	 * <code>
	 *
	 * Node n = new Node(new Interval(low, high));                   <p>
	 * n.priority = priority;                                        <p>
	 * it.intervalInsert(n);                                         <p>
	 *
	 * </code>
	 *
	 * @param it an interval treap
	 * @param low the low value of the interval to insert in to the treap
	 * @param high the high value of the interval to insert in to the treap
	 * @param priority the priority of the node created to encapsulate the interval
	 * that is to be added to the interval treap
	 */
	void insertInterval(IntervalTreap it, int low, int high, int priority) {
		Node n = new Node(new Interval(low, high));
		n.setPriority(priority);
		it.intervalInsert(n);
	}

	/**
	 * Recursively check that a node n and all its children follow the
	 * following rules:
	 * <p>
	 * Let k be the lowest value of the interval in a node
	 * <p>
	 * 1. n.left.k  < n.k
	 * <p>
	 * 2. n.right.k > n.k
	 *
	 * @param n the node to begin searching
	 */
	void checkTreeStructureRecursive(Node n) {
		if(n==null) return;
		
		if(n.getLeft() != null) {
			assert (n.getLeft().getInterv().getLow() <= n.getInterv().getLow());
			checkTreeStructureRecursive(n.getLeft());
		}

		if(n.getRight() != null) {
			assert (n.getRight().getInterv().getLow() >= n.getInterv().getLow());
			checkTreeStructureRecursive(n.getRight());
		}

		//Check node relationships to see if they
		//are mutual

		//Ensure that n is its parent's child, if the parent exists
		if(n.getParent() != null) {
			Node p = n.getParent();
			if(p.getLeft() != n && p.getRight() != n)
				fail("Node is not a child of its parent");
		}

		//Ensure that the children of n have their
		//parent fields set to n
		if(n.getLeft() != null) {
			if(n.getLeft().getParent() == null)
				fail("Left child's parent is null");
			if(n.getLeft().getParent() != n)
				fail("Left child's parent is incorrect");
		}

		if(n.getRight() != null) {
			if(n.getRight().getParent() == null)
				fail("Right child's parent is null");
			if(n.getRight().getParent() != n)
				fail("Right child's parent is incorrect");
		}
	}

	/**
	 *
	 * Recursively check that each node n and all its children follow the
	 * following rules:
	 * <p>
	 * Let k be the lowest value of the interval in a node
	 * <p>
	 * 1. n.left.k  < n.k
	 * <p>
	 * 2. n.right.k > n.k
	 *
	 * @param it the IntervalTreap to search
	 */
	void checkTreeStructure(IntervalTreap it) {
		checkTreeStructureRecursive(it.root);
	}

	/**
	 * Recursively check that all IMax values are what they should be
	 * @param n the node to begin searching at
	 */
	void checkImaxRecursive(Node n) {
		if(n==null) return;
		
		assert(calculateImax(n)==n.getIMax());

		if(n.getLeft() != null) checkImaxRecursive(n.getLeft());
		if(n.getRight() != null) checkImaxRecursive(n.getRight());
	}

	/**
	 * Calculates what IMax should be for a given node
	 * @param n the node to calculate IMax for
	 * @return IMax
	 */
	int calculateImax(Node n) {
		int ret = n.getInterv().getHigh();

		if(n.getLeft() != null) ret = Math.max(ret, calculateImax(n.getLeft()));
		if(n.getRight() != null) ret = Math.max(ret, calculateImax(n.getRight()));

		return ret;
	}

	/**
	 * Recursively check that all IMax values in a treap are what
	 * they should be
	 * @param it
	 */
	void checkImax(IntervalTreap it) {

		if(it.root==null) return;
		
		checkImaxRecursive(it.root);
	}

	/**
	 * Calculates the number of nodes in a subtree
	 * @param n the node to calculate IMax for
	 * @return IMax
	 */
	int sizeOfSubtree(Node n) {
		if(n==null) return 0;

		int ret = 1;

		if(n.getLeft() != null) ret += sizeOfSubtree(n.getLeft());
		if(n.getRight() != null) ret += sizeOfSubtree(n.getRight());

		return ret;
	}

	/**
	 * Check the size of an interval treap
	 * @param it
	 */
	void checkSize(IntervalTreap it) {
		if(it.root==null) assert it.size==0;
		int diff = it.getSize()-sizeOfSubtree(it.root);
		if(diff!=0) {
			if(diff>0) fail("expected size of interval treap is " + diff + " greater than actual size");
			else fail("actual size of interval treap is " + -diff + " greater than expected size");
		}
	}

	/**
	 * Prints the contents of the subtree starting at a node
	 * @param n the node that is the subtree's root
	 * @param depth the initial depth of the node, used for recursion
	 */
	void printSubtreeRecursive(Node n, int depth) {
		for(int i = 0; i < depth-1; i++) {
			System.out.print("|   ");
		}
		if(depth!=0) System.out.print("|---");

		if(n==null) {
			System.out.println();
			return;
		}

		int expectedIMax = calculateImax(n);

		System.out.print(n.getIMax());
		if(n.getIMax() != expectedIMax) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.err.print(" exp. ");
			System.err.print(expectedIMax);
		}

		System.out.print(" (");
		System.out.print(n.getInterv().getLow());
		System.out.print(", ");
		System.out.print(n.getInterv().getHigh());
		System.out.println(")");

		if(n.getLeft()!=null || n.getRight()!=null) {
			printSubtreeRecursive(n.getLeft(), depth+1);
			printSubtreeRecursive(n.getRight(), depth+1);
		}

	}
	
	/**
	 * Asserts that the height of the treap is correct
	 * @param it
	 */
	void testHeight(IntervalTreap it) {
		if(it.root==null) assert(it.getHeight()<1);
		else {
			int i = testHeightRecursive(it.root, 0);
			int j = it.getHeight();
			assert(j==i);
		}
	}
	
	/**
	 * Helper method for testHeight()
	 * @param n the node to calculate the depth of
	 * @param currentHeight the depth of n
	 * @return the depth of the tree formed by n
	 */
	int testHeightRecursive(Node n, int currentHeight) {
		int h = currentHeight;
		if(n.getLeft()!=null) h = testHeightRecursive(n.getLeft(), currentHeight+1);
		if(n.getRight()!=null) h = Math.max(h, testHeightRecursive(n.getRight(), currentHeight+1));
		return h;
	}

	/**
	 * Recursively prints the contents of an interval treap.
	 * <p>
	 * The first number is the low value of the interval in the node. <br>
	 * The second number is the high value of the interval in the node. <br>
	 * The third number is the imax value of the node. <br>
	 *
	 * @param it
	 */
	void printIntervalTreap(IntervalTreap it) {
		printSubtreeRecursive(it.root, 0);
	}
	
	public void checkIntevalTreap(IntervalTreap it) {
		checkSize(it);
		checkPriorities(it);
		checkTreeStructure(it);
		checkImax(it);
	}
	

}