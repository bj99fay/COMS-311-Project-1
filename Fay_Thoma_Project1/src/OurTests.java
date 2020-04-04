import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OurTests {

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
	/*------------------------------------------------------------------------------------------------------------------------*/

	@Test
	public void overlapSmall() {
		scanConstruct("src/medium_1.txt");
		for (Interval i : TP) {
			File f = new File("src/medium_intervals.txt");
			String line;
			String[] split;
			try {
				sc = new Scanner(f);
				sc.nextLine(); // skip first line "Intervals"
				it1 = new IntervalTreap();
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					split = line.split(" ");
					it1.intervalInsert(new Node(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1]))));
				}
			} catch (FileNotFoundException e) {
				fail("File not found exception");
			}
			
			assertNotNull(it1.intervalSearch(i));
			
			List<Node> nodes = it1.overlappingIntervals(i);
			assertNotNull(nodes);
			
			for(Node node: nodes) {
				it1.intervalDelete(node);
			}
			nodes = it1.overlappingIntervals(i);
			assertNull(nodes);
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearch(j));
			assertNull(it1.overlappingIntervals(j));
		}
		testTreapStructure(it1);
		testHeight(it1);
		checkImax(it1);
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
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

	public void printInterval(Interval x) {
		if (x == null)
			return;
		
		System.out.println("Node: (" + x.getLow() + ", " + x.getHigh() + ")");
	}
	
	public void printNodeAndChildren(Node x, int level) {
		if (x == null)
			return;

		for (int i = 0; i < level; i++)
			System.out.print("|\t");

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
