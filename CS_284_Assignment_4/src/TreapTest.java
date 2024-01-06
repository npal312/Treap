import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TreapTest {

	//Treap()
	@Test
	void testTreap() {
		String msg = "Treap values should be equal to expected.";
		try {
			Treap<Integer> test = new Treap<Integer>();
			assertEquals("null\n", test.toString(), msg);
		}
		catch(Exception e) {
			fail("Treap instance was not created.");
		}
	}
	
	
	//Treap(long)
	@Test
	void testTreapLong() {
		String msg = "Treap values should be equal to expected.";
		try {
			Treap<Integer> test = new Treap<Integer>(100);
			assertEquals("null\n", test.toString(), msg);
		}
		catch(Exception e) {
			fail("Treap instance was not created.");
		}
	}
	
	
	//add(E)
	@Test
	void testAdd() {
		String msg = "Node should have been added to the Treap.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertTrue(test.add(9), msg);
	}
	
	
	//add(E, int)
	@Test
	void testAddE() {
		String msg = "Node should have been added to the Treap correctly.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertTrue(test.add(8, 15), msg);
		assertEquals("[key: 1, priority: 84]\n-null\n-[key: 5, priority: 83]\n--[key: 2, priority: 31]\n---null\n---[key: 4, priority: 19]\n----[key: 3, priority: 12]\n-----null\n-----null\n----null\n--[key: 6, priority: 70]\n---null\n---[key: 7, priority: 26]\n----null\n----[key: 8, priority: 15]\n-----null\n-----null\n", test.toString(), msg);
	}
	
	@Test
	void testAddFalse() {
		String msg = "Node should not have been added to the Treap.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertFalse(test.add(2, 10), msg);
	}
	
	@Test
	void testAddRoot() {
		String msg = "Node should have been added to the Treap correctly.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertTrue(test.add(1, 84), msg);
		assertEquals("[key: 1, priority: 84]\n-null\n-[key: 5, priority: 83]\n--[key: 2, priority: 31]\n---null\n---[key: 4, priority: 19]\n----[key: 3, priority: 12]\n-----null\n-----null\n----null\n--[key: 6, priority: 70]\n---null\n---[key: 7, priority: 26]\n----null\n----null\n", test.toString(), msg);
	}
	
	@Test
	void testAddOnlyRoot() {
		String msg = "Node should have been added to the Treap correctly.";
		Treap<Integer> test = new Treap<Integer>();
		assertTrue(test.add(1, 84), msg);
		assertEquals("[key: 1, priority: 84]\n-null\n-null\n", test.toString(), msg);
	}
	
	
	//delete(E)
	@Test
	void testDeleteLeaf() {
		String msg = "Node should be deleted from the Treap correctly.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertTrue(test.delete(7), msg);
		assertEquals("[key: 1, priority: 84]\n-null\n-[key: 5, priority: 83]\n--[key: 2, priority: 31]\n---null\n---[key: 4, priority: 19]\n----[key: 3, priority: 12]\n-----null\n-----null\n----null\n--[key: 6, priority: 70]\n---null\n---null\n", test.toString(), msg);
	}
	
	@Test
	void testDeleteRoot() {
		String msg = "Node should be deleted from the Treap correctly.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertTrue(test.delete(1), msg);
		assertEquals("[key: 5, priority: 83]\n-[key: 2, priority: 31]\n--null\n--[key: 4, priority: 19]\n---[key: 3, priority: 12]\n----null\n----null\n---null\n-[key: 6, priority: 70]\n--null\n--[key: 7, priority: 26]\n---null\n---null\n", test.toString());
	}
	
	@Test
	void testDeleteInternalNode() {
		String msg = "Node should be deleted from the Treap correctly.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertTrue(test.delete(5), msg);
		assertEquals("[key: 1, priority: 84]\n-null\n-[key: 6, priority: 70]\n--[key: 2, priority: 31]\n---null\n---[key: 4, priority: 19]\n----[key: 3, priority: 12]\n-----null\n-----null\n----null\n--[key: 7, priority: 26]\n---null\n---null\n",test.toString());
	}
	
	@Test
	void testDeleteEmptyTreap() {
		String msg = "Treap should stay empty and .delete should return false.";
		Treap<Integer> test = new Treap<Integer>();
		assertFalse(test.delete(1), msg);
		assertEquals("null\n", test.toString(), msg);
	}
	
	@Test
	void testDeleteNotFound() {
		String msg = "Node should not be deleted from the Treap.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		String prevStr = test.toString();
		assertFalse(test.delete(8), msg);
		String nextStr = test.toString();
		assertEquals(prevStr, nextStr);
	}
	
	@Test
	void testDeleteFromOnlyRoot() {
		String msg = "Node should be found to be in the Treap.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(1, 84);
		assertTrue(test.delete(1), msg);
		assertEquals("null\n", test.toString(), msg);
	}
	
	
	//find(E)
	@Test
	void testFind() {
		String msg = "Node should be found to be in the Treap.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertTrue(test.find(3), msg);
	}
	
	@Test
	void testFindFalse() {
		String msg = "Node should not be found to be in the Treap.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertFalse(test.find(11), msg);
	}
	
	
	//toString()
	@Test
	void testToString() {
		String msg = "String representations should be equal.";
		Treap<Integer> test = new Treap<Integer>();
		test.add(4, 19);
		test.add(2, 31);
		test.add(6, 70);
		test.add(1, 84);
		test.add(3, 12);
		test.add(5, 83);
		test.add(7, 26);
		assertEquals("[key: 1, priority: 84]\n-null\n-[key: 5, priority: 83]\n--[key: 2, priority: 31]\n---null\n---[key: 4, priority: 19]\n----[key: 3, priority: 12]\n-----null\n-----null\n----null\n--[key: 6, priority: 70]\n---null\n---[key: 7, priority: 26]\n----null\n----null\n", test.toString(), msg);
	}
	
	@Test
	void testToStringEmptyTreap() {
		String msg = "String representations should be equal.";
		Treap<Integer> test = new Treap<Integer>();
		assertEquals("null\n", test.toString(), msg);
	}
}
