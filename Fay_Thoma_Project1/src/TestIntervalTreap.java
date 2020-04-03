
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * Junit4 for CS311 PROJ1.
 * Currently, only tests for general functionality of insert, search.
 * To do: test for correct height, imax, delete, EC
 * 
 * @author Original Tests: Evan McKinney
 * Edited and added to by Bernie Fay and Riley Thoma
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
		sc.close();
		sc = null;
		TP = null;
		TN = null;
	}

//	@Test
//	public void testWithDelete() {
//		scanConstruct("src/small_1.txt");
//		for (Interval i : TP) {
//			assertNotNull(it1.intervalSearch(i));
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearch(j));
//		}
//		testTreapStructure(it1);
//		while(it1.getRoot() != null) {
//			System.out.println();
//			/* Random maxes
//			  use 21298 for medium test
//			  use 2467 for small
//			  use 21 for mini_delete	
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
//				System.out.println("Interval: (" + z.getInterv().getLow() + " - " + z.getInterv().getHigh() 
//						+ ")  Imax: " + z.getIMax() + "  Priority: " + z.getPriority());
//				System.out.println("Delete node");
//				it1.intervalDelete(z);
//				System.out.println();
//				if(it1.getRoot() != null) {
//					System.out.println("Root: (" + it1.getRoot().getInterv().getLow() + " - " + it1.getRoot().getInterv().getHigh() + ")");
//					testTreapStructure(it1);
//				}
//			}
//		}
//		if(it1.getRoot() == null) {
//			System.out.println("Success: No nodes, empty");
//		} else {
//			System.out.println("Fail: Not empty");
//		}
//	}

	
//	@Test
//	public void testMini() {
//		scanConstruct("src/mini_1.txt");
//		for (Interval i : TP) {
//			assertNotNull(it1.intervalSearch(i));
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearch(j));
//		}
//		testTreapStructure(it1);
//		/* Testing to see if removal of all nodes is working
//		 * System.out.println();
//		Node z = it1.getRoot();
//		System.out.println("Root interval: (" + z.getInterv().getLow() + " - " + z.getInterv().getHigh() 
//				+ ")  Imax: " + z.getIMax() + "  Priority: " + z.getPriority());
//		System.out.println("Delete root");
//		it1.intervalDelete(z);
//		
//		System.out.println();
//		System.out.println("Root interval: (" + it1.getRoot().getInterv().getLow() + " - " + it1.getRoot().getInterv().getHigh() + ")");
//		testTreapStructure(it1);
//		System.out.println();
//		z = it1.getRoot();
//		System.out.println("Root interval: (" + z.getInterv().getLow() + " - " + z.getInterv().getHigh() 
//				+ ")  Imax: " + z.getIMax() + "  Priority: " + z.getPriority());
//		System.out.println("Delete root");
//		it1.intervalDelete(z);
//		System.out.println();
//		System.out.println("Root interval: (" + it1.getRoot().getInterv().getLow() + " - " + it1.getRoot().getInterv().getHigh() + ")");
//		testTreapStructure(it1);
//		
//		System.out.println();
//		z = it1.getRoot();
//		System.out.println("Root interval: (" + z.getInterv().getLow() + " - " + z.getInterv().getHigh() 
//				+ ")  Imax: " + z.getIMax() + "  Priority: " + z.getPriority());
//		System.out.println("Delete root");
//		
//		it1.intervalDelete(z);
//		System.out.println();
//		if(it1.getRoot() == null) {
//			System.out.println("Success: No nodes, empty");
//		} else {
//			System.out.println("Fail: Not empty");
//		}*/
//	}
	
	//Test intervalSearchExactly (extra credit)
	@Test
	public void testMiniExact() {
		scanConstruct("src/mini_exact.txt");
		this.printNodeAndChildren(it1.getRoot());
		for (Interval i : TP) {
			assertNotNull(it1.intervalSearchExactly(i));
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearchExactly(j));
		}
		testTreapStructure(it1);
	}
	 
	 
//	@Test
//	public void testSmall() {
//		scanConstruct("src/small_1.txt");
//		for (Interval i : TP) {
//			assertNotNull(it1.intervalSearch(i));
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearch(j));
//		}
//		testTreapStructure(it1);
//	}
	
	@Test
	public void testSmallExact() {
		scanConstruct("src/small_exact.txt");
		
		this.printNodeAndChildren(it1.getRoot());
		
		for (Interval i : TP) {
//			try {
				assertNotNull(it1.intervalSearchExactly(i));
//			} catch(AssertionError error) {
//				System.out.println("The interval that failed: (" + i.getLow() + " - " + i.getHigh() + ")");
//			}
		}
		for (Interval j : TN) {
			assertNull(it1.intervalSearchExactly(j));
		}
		testTreapStructure(it1);
	}

//	@Test
//	public void testMedium() {
//		scanConstruct("src/medium_1.txt");
//		for (Interval i : TP) {
//			assertNotNull(it1.intervalSearch(i));
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearch(j));
//		}
//		testTreapStructure(it1);
//	}

//	@Test
//	public void testLarge() {
//		scanConstruct("src/large_1.txt");
//		for (Interval i : TP) {
//			assertNotNull(it1.intervalSearch(i));
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearch(j));
//		}
//		testTreapStructure(it1);
//	}
	
	private void testTreapStructure(IntervalTreap it0) {
		//Do an InOrder Traversal and append the nodes into an array
		ArrayList<Node> inOrder = new ArrayList<Node>();
		inOrder(it0.getRoot(), inOrder);
		
//		for(Node n: inOrder)
//			System.out.println("Interval: (" + n.getInterv().getLow() + " - " + n.getInterv().getHigh() + ")  Imax: " + n.getIMax() + "  Priority: " + n.getPriority());
		
		//Check if the array is sorted. If it is not sorted, it's not a valid treap. 
		for (int k =0; k < inOrder.size()-1; k++) {
			if (inOrder.get(k).getInterv().getLow() > inOrder.get(k+1).getInterv().getLow()) {
				fail("failed treap's BST property!");
			}
		}
		System.out.println("Height = " + it0.getHeight());
	}
	
	public void inOrder(Node node, ArrayList<Node> array){
	    if(node == null){  
	       return;
	    }
	    inOrder(node.getLeft(), array);
	    array.add(node);
	    
	    //As you visit each node, check for the heap property.
	    if (node.getParent()!=null && node.getPriority() < node.getParent().getPriority()) {
	    	fail("failed treap's min-heap property!");
	    }
	    
	    inOrder(node.getRight(), array);
	}
	
	public void printNodeAndChildren(Node x) {
		if(x == null) return;
		
		System.out.println("Node: (" + x.getInterv().getLow() + " - " + x.getInterv().getHigh() + ")");
		if(x.getLeft() != null) {
			System.out.println("\tLeft: (" + x.getLeft().getInterv().getLow() + " - " + x.getLeft().getInterv().getHigh() + ")");
		} else {
			System.out.println("\tLeft: null");
		}
		if(x.getRight() != null) {
			System.out.println("\tRight: (" + x.getRight().getInterv().getLow() + " - " + x.getRight().getInterv().getHigh() + ")");
		} else {
			System.out.println("\tRight: null");
		}
		this.printNodeAndChildren(x.getLeft());
		this.printNodeAndChildren(x.getRight());
	}
	
	
	private void scanConstruct(String fn) {
		File f = new File(fn);
		String line;
		String [] split;
		try {
			sc = new Scanner(f);
			sc.nextLine(); //skip first line "TP"
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.equals("TN")) break;
				split = line.split(" ");
				TP.add(new Interval(Integer.parseInt(split[0]),Integer.parseInt(split[1])));
			}
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.equals("Intervals")) break;
				split = line.split(" ");
				TN.add(new Interval(Integer.parseInt(split[0]),Integer.parseInt(split[1])));
			}
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				split = line.split(" ");
				it1.intervalInsert(new Node(new Interval(Integer.parseInt(split[0]),Integer.parseInt(split[1]))));
			}
		} catch (FileNotFoundException e) {
			fail("File not found exception");
		}

	}
}