import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit4 for CS311 PROJ1. Currently, only tests for general functionality of
 * insert, search. To do: test for correct height, imax, delete, EC
 * 
 * @author Original Tests: Evan McKinney Edited and added to by Bernie Fay and
 *         Riley Thoma
 *
 */
public class TestIntervalTreap {

	private IntervalTreap it1;
	private ArrayList<Interval> TP;
	private ArrayList<Interval> TN;
	private Scanner sc;

	@Before
	public void setUp() throws Exception {
		it1 = new IntervalTreap();
		TP = new ArrayList<Interval>();
		TN = new ArrayList<Interval>();
	}

	@After
	public void tearDown() throws Exception {
		it1 = null;
		if (sc != null) {
			sc.close();
		}
		sc = null;
		TP = null;
		TN = null;
	}

	@Test
	public void testWithDelete() {
//		scanConstruct("src/small_1.txt");
//		for (Interval i : TP) {
//			assertNotNull(it1.intervalSearch(i));
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearch(j));
//		}
//		System.out.println("First Test");
//		testTreapStructure(it1);
//		//printNodeAndChildren(it1.getRoot());
//		while(it1.getRoot() != null) {
//			System.out.println();
//			/* Random maxes
//			  use 21298 for medium test
//			  use 2467 for small
//			  use 21 for mini_forDelete	
//			*/
//			int rand1 = new Random().nextInt(2467);
//			int rand2 = new Random().nextInt(2467);
//			Node z = null;
//			if(rand1 > rand2) {
//				z = it1.intervalSearch(new Interval(rand2,rand1));
//				System.out.println("Z: (" + rand2 + " - " + rand1 + ")");
//			} else {
//				z = it1.intervalSearch(new Interval(rand1,rand2));
//				System.out.println("Z: (" + rand1 + " - " + rand2 + ")");
//			}
//			if(z != null) {
//				//Node z = it1.getRoot();
//				System.out.println("Root: (" + it1.getRoot().getInterv().getLow() + " - " + it1.getRoot().getInterv().getHigh() + ")");
//				System.out.println("Interval to delete: (" + z.getInterv().getLow() + " - " + z.getInterv().getHigh() 
//						+ ")  Imax: " + z.getIMax() + "  Priority: " + z.getPriority());
//				System.out.println("Delete node");
//				it1.intervalDelete(z);
//				testHeight(it1);
//				System.out.println();
//				if(it1.getRoot() != null) {
//					System.out.println("Root: (" + it1.getRoot().getInterv().getLow() + " - " + it1.getRoot().getInterv().getHigh() + ")");
//					testTreapStructure(it1);
//					//printNodeAndChildren(it1.getRoot());
//				}
//			}
//		}
//		if(it1.getRoot() == null) {
//			System.out.println("Success: No nodes, empty");
//		} else {
//			System.out.println("Fail: Not empty");
//		}
	}

	@Test
	public void testMini() {
		scanConstruct("src/mini_1.txt");
		for (Interval i : TP) {
			assertNotNull(it1.intervalSearch(i));
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearch(j));
		}
		testTreapStructure(it1);
		testHeight(it1);
		checkImax(it1);
		/* Testing to see if removal of all nodes is working */
		/*
		 * System.out.println(); Node z = it1.getRoot();
		 * System.out.println("Root interval: (" + z.getInterv().getLow() + " - " +
		 * z.getInterv().getHigh() + ")  Imax: " + z.getIMax() + "  Priority: " +
		 * z.getPriority()); System.out.println("Delete root"); it1.intervalDelete(z);
		 * 
		 * System.out.println(); System.out.println("Root interval: (" +
		 * it1.getRoot().getInterv().getLow() + " - " +
		 * it1.getRoot().getInterv().getHigh() + ")"); testTreapStructure(it1);
		 * testHeight(it1); System.out.println(); z = it1.getRoot();
		 * System.out.println("Root interval: (" + z.getInterv().getLow() + " - " +
		 * z.getInterv().getHigh() + ")  Imax: " + z.getIMax() + "  Priority: " +
		 * z.getPriority()); System.out.println("Delete root"); it1.intervalDelete(z);
		 * System.out.println(); System.out.println("Root interval: (" +
		 * it1.getRoot().getInterv().getLow() + " - " +
		 * it1.getRoot().getInterv().getHigh() + ")"); testTreapStructure(it1);
		 * testHeight(it1);
		 * 
		 * System.out.println(); z = it1.getRoot();
		 * System.out.println("Root interval: (" + z.getInterv().getLow() + " - " +
		 * z.getInterv().getHigh() + ")  Imax: " + z.getIMax() + "  Priority: " +
		 * z.getPriority()); System.out.println("Delete root");
		 * 
		 * it1.intervalDelete(z); testHeight(it1); System.out.println();
		 * if(it1.getRoot() == null) { System.out.println("Success: No nodes, empty"); }
		 * else { System.out.println("Fail: Not empty"); }
		 */
	}

	// Test intervalSearchExactly (extra credit)
	@Test
	public void testMiniExact() {
		scanConstruct("src/mini_exact.txt");
		this.printNodeAndChildren(it1.getRoot(), 0);
		for (Interval i : TP) {
			assertNotNull(it1.intervalSearchExactly(i));
		}
		for (Interval j : TN) {
			try {
				assertNull(it1.intervalSearchExactly(j));
			} catch (AssertionError e) {
				System.out.println("The interval that failed: (" + j.getLow() + " - " + j.getHigh() + ")");
			}
		}
		testTreapStructure(it1);
	}

//	@Test
	public void testSmall() {
		scanConstruct("src/small_1.txt");
		for (Interval i : TP) {
			try {
				assertNotNull(it1.intervalSearch(i));
			} catch (AssertionError error) {
				System.out.println("The interval that failed: (" + i.getLow() + " - " + i.getHigh() + ")");
				testTreapStructure(it1);
				testHeight(it1);
				checkImax(it1);
				System.out.println("Size: " + it1.getSize());
				printNodeAndChildren(it1.root, 0);
				System.exit(-1);
			}
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearch(j));
		}
		testTreapStructure(it1);
		testHeight(it1);
		checkImax(it1);
	}

//	@Test
//	public void testSmallExact() {
//		scanConstruct("src/small_exact.txt");
//		
//		this.printNodeAndChildren(it1.getRoot(), 0);
//		
//		for (Interval i : TP) {
////			try {
//				assertNotNull(it1.intervalSearchExactly(i));
////			} catch(AssertionError error) {
////				System.out.println("The interval that failed: (" + i.getLow() + " - " + i.getHigh() + ")");
////			}
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearchExactly(j));
//		}
//		testTreapStructure(it1);
//	}

//	@Test
//	public void testSmallExact2() {
//		scanConstruct("src/small_exact2.txt");
//		
//		this.printNodeAndChildren(it1.getRoot(), 0);
//		
//		for (Interval i : TP) {
//			try {
//				assertNotNull(it1.intervalSearchExactly(i));
//			} catch(AssertionError error) {
//				System.out.println("The interval that failed: (" + i.getLow() + " - " + i.getHigh() + ")");
//				System.exit(-1);
//			}
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearchExactly(j));
//		}
//		testTreapStructure(it1);
//	}

	@Test
	public void testMedium() {
		scanConstruct("src/medium_1.txt");
		for (Interval i : TP) {
			try {
				assertNotNull(it1.intervalSearch(i));
			} catch (AssertionError error) {
				System.out.println("The interval that failed: (" + i.getLow() + " - " + i.getHigh() + ")");
				testTreapStructure(it1);
				testHeight(it1);
				checkImax(it1);
				System.out.println("Size: " + it1.getSize());
//				printNodeAndChildren(it1.root, 0);
				System.exit(-1);
			}
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearch(j));
		}
		testTreapStructure(it1);
		testHeight(it1);
		checkImax(it1);
	}
	
//	@Test
//	public void repeatedlyTest() {
//		for (int i = 0; i < 10000; i++) {
//			it1 = new IntervalTreap();
//			testSmall();
//			System.out.println(i);
//		}
//	}

//	@Test
//	public void testLarge() {
//		scanConstruct("src/large_1.txt");
//		for (Interval i : TP) {
////			try {
//				assertNotNull(it1.intervalSearch(i));
////			} catch (AssertionError error) {
////				System.out.println("The interval that failed: (" + i.getLow() + " - " + i.getHigh() + ")");
////				testTreapStructure(it1);
////				testHeight(it1);
////				checkImax(it1);
////				System.out.println("Size: " + it1.getSize());
//////				printNodeAndChildren(it1.root, 0);
////			}
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearch(j));
//		}
//		testTreapStructure(it1);
//		testHeight(it1);
//		checkImax(it1);
//	}

	/**
	 * Tests the deletion of the root node when the treap only contains one node.
	 */
	/*
	 * @Test public void testDeleteOnlyNodeInTreap() { IntervalTreap it = new
	 * IntervalTreap(); it.intervalInsert(new Node(new Interval(-5, 5)));
	 * assert(it.size==1); assert(it.getHeight()==0); assert(it.getSize()==1);
	 * 
	 * Node n = it.getRoot(); it.intervalDelete(n);
	 * 
	 * assert(it.root==null); assert(it.size==0);
	 * //System.out.println(it.getHeight()); assert(it.getHeight()<1);
	 * assert(it.getSize()==0); }
	 */

	/**
	 * Add a thousand random nodes and check that getHeight() is correct
	 */
	/*
	 * @Test public void testGetHeight() { IntervalTreap it = new IntervalTreap();
	 * Random rand = new Random();
	 * 
	 * int numNodes = 1000;
	 * 
	 * for(int i = 0; i < numNodes; i++) { int r1 = rand.nextInt(),r2 =
	 * rand.nextInt(); it.intervalInsert(new Node(new Interval(Math.min(r1,r2),
	 * Math.max(r1,r2)))); //System.out.println(it.getHeight()); testHeight(it); }
	 * 
	 * }
	 */

	private void testTreapStructure(IntervalTreap it0) {
		// Do an InOrder Traversal and append the nodes into an array
		ArrayList<Node> inOrder = new ArrayList<Node>();
		inOrder(it0.getRoot(), inOrder);

		// for(Node n: inOrder)
		// printNodeAndChildren(n);
		// System.out.println("Interval: (" + n.getInterv().getLow() + " - " +
		// n.getInterv().getHigh() + ") Imax: " + n.getIMax() + " Priority: " +
		// n.getPriority());

		// Check if the array is sorted. If it is not sorted, it's not a valid treap.
		for (int k = 0; k < inOrder.size() - 1; k++) {
			if (inOrder.get(k).getInterv().getLow() > inOrder.get(k + 1).getInterv().getLow()) {
				fail("failed treap's BST property!");
			}
		}
		System.out.println("Height = " + it0.getHeight());
	}

	public void inOrder(Node node, ArrayList<Node> array) {
		if (node == null) {
			return;
		}
		inOrder(node.getLeft(), array);
		array.add(node);

		// As you visit each node, check for the heap property.
		if (node.getParent() != null && node.getPriority() < node.getParent().getPriority()) {
			fail("failed treap's min-heap property!");
		}

		inOrder(node.getRight(), array);
	}

	public void printNodeAndChildren(Node x, int level) {
		for (int i = 0; i < level; i++)
			System.out.print("|\t");
		
		if (x == null) {
			System.out.println("null");
			return;
		}

		System.out.println("Node: (" + x.getInterv().getLow() + ", " + x.getInterv().getHigh() + ")");// Priority: " +
																										// x.getPriority()
																										// + " Height: "
																										// +
																										// x.getHeight());
//		if(x.getLeft() != null) {
//			System.out.println("\tLeft: (" + x.getLeft().getInterv().getLow() + " - " + x.getLeft().getInterv().getHigh() + ")");
//		} else {
//			System.out.println("\tLeft: null");
//		}
//		if(x.getRight() != null) {
//			System.out.println("\tRight: (" + x.getRight().getInterv().getLow() + " - " + x.getRight().getInterv().getHigh() + ")");
//		} else {
//			System.out.println("\tRight: null");
//		}
		this.printNodeAndChildren(x.getLeft(), level + 1);
		this.printNodeAndChildren(x.getRight(), level + 1);
	}

	/**
	 * Asserts that the height of the treap is correct
	 * 
	 * @param it
	 */
	void testHeight(IntervalTreap it) {
		if (it.root == null)
			assert (it.getHeight() < 1);
		else {
			int i = testHeightRecursive(it.root, 0);
			int j = it.getHeight();
			try {
				assert (j == i);
			} catch (AssertionError error) {
				System.out.println("HEIGHT SHOULD BE: " + i + "  YOUR HEIGHT: " + j);
			}

		}
	}

	/**
	 * Helper method for testHeight()
	 * 
	 * @param n             the node to calculate the depth of
	 * @param currentHeight the depth of n
	 * @return the depth of the tree formed by n
	 */
	int testHeightRecursive(Node n, int currentHeight) {
		int h = currentHeight;
		if (n.getLeft() != null)
			h = testHeightRecursive(n.getLeft(), currentHeight + 1);
		if (n.getRight() != null)
			h = Math.max(h, testHeightRecursive(n.getRight(), currentHeight + 1));
		return h;
	}

	/**
	 * Recursively check that all IMax values are what they should be
	 * 
	 * @param n the node to begin searching at
	 */
	void checkImaxRecursive(Node n) {
		if (n == null)
			return;

		try {
			assert (calculateImax(n) == n.getIMax());
		} catch (AssertionError e) {
			System.out.println("IMax bad");
		}

		if (n.getLeft() != null)
			checkImaxRecursive(n.getLeft());
		if (n.getRight() != null)
			checkImaxRecursive(n.getRight());
	}

	/**
	 * Calculates what IMax should be for a given node
	 * 
	 * @param n the node to calculate IMax for
	 * @return IMax
	 */
	int calculateImax(Node n) {
		int ret = n.getInterv().getHigh();

		if (n.getLeft() != null)
			ret = Math.max(ret, calculateImax(n.getLeft()));
		if (n.getRight() != null)
			ret = Math.max(ret, calculateImax(n.getRight()));

		return ret;
	}

	/**
	 * Recursively check that all IMax values in a treap are what they should be
	 * 
	 * @param it
	 */
	void checkImax(IntervalTreap it) {

		if (it.root == null)
			return;

		checkImaxRecursive(it.root);
		
	}

	private void scanConstruct(String fn) {
		File f = new File(fn);
		String line;
		String[] split;
		try {
			sc = new Scanner(f);
			sc.nextLine(); // skip first line "TP"
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.equals("TN"))
					break;
				split = line.split(" ");
				TP.add(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
			}
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.equals("Intervals"))
					break;
				split = line.split(" ");
				TN.add(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
			}
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				split = line.split(" ");
				it1.intervalInsert(new Node(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1]))));
			}
		} catch (FileNotFoundException e) {
			fail("File not found exception");
		}

	}
}