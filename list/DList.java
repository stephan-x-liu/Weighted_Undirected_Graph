/* DList<T>.java */

package list;

import java.util.*;

/**
 * A DList<T> is a mutable, generic, doubly-linked list ADT. Its implementation
 * is circularly-linked and employs a sentinel node at the head of the list.
 * 
 * @author Eldon Schoop
 **/

public class DList<T> implements Iterable<T> {

	/**
	 * size is the number of items in the list. head references the sentinel
	 * node. Note that the sentinel node does not store an item, and is not
	 * included in the count stored by the "size" field.
	 **/

	protected int size;
	protected DListNode<T> head;

	/*
	 * DList<T> invariants: 
	 * 1) head != null. 
	 * 2) For every DListNode<T> x in a DList<T>, x.next != null. 
	 * 3) For every DListNode<T> x in a DList<T>, x.prev != null. 
	 * 4) For every DListNode<T> x in a DList<T>, if x.next == y, then y.prev == x. 
	 * 5) For every DListNode<T> x in a DList<T>, if x.prev == y, then y.next == x. 
	 * 6) For every DList<T> l, l.head.myList = null. (Note that l.head is the sentinel.) 
	 * 7) For every DListNode<T> x in a DList<T> l EXCEPT l.head (the sentinel), x.myList = l. 
	 * 8) size is the number of DListNodes, NOT COUNTING the sentinel, that can be accessed
	 *      from the sentinel (head) by a sequence of "next" references.
	 */
	
	/**
	 * @author Eldon Schoop
	 * @return an iterator over the curent DList<T>
	 */
	public Iterator<T> iterator() {
		return new DListIterator<T>(this);
	}

	/**
	 * isEmpty() returns true if this List is empty, false otherwise.
	 * 
	 * @return true if this List is empty, false otherwise. Performance: runs in
	 *         O(1) time.
	 **/
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * length() returns the length of this List.
	 * 
	 * @return the length of this List. Performance: runs in O(1) time.
	 **/
	public int length() {
		return size;
	}

	/**
	 * newNode() calls the DListNode<T> constructor. Use this method to allocate
	 * new DListNodes rather than calling the DListNode<T> constructor directly.
	 * That way, only this method need be overridden if a subclass of DList<T>
	 * wants to use a different kind of node.
	 * 
	 * @author Eldon Schoop
	 * @param item
	 *            the item to store in the node.
	 * @param list
	 *            the list that owns this node. (null for sentinels.)
	 * @param prev
	 *            the node previous to this node.
	 * @param next
	 *            the node following this node.
	 **/
	protected DListNode<T> newNode(T item, DList<T> list, DListNode<T> prev,
			DListNode<T> next) {
		return new DListNode<T>(item, list, prev, next);
	}

	/**
	 * DList<T>() constructs an empty DList<T>.
	 * 
	 * @author Eldon Schoop
	 **/
	public DList() {
		DListNode<T> newItem = newNode(null, null, null, null);
		head = newItem;
		head.next = newItem;
		head.prev = newItem;
	}

	/**
	 * Transforms an iterable into a new DList, populated with every element in
	 * the iterable.
	 * 
	 * @author Eldon Schoop
	 * @param inList an iterable to transform into a DList.
	 */
	public DList(Iterable<T> inList) {
		DListNode<T> newItem = newNode(null, null, null, null);
		head = newItem;
		head.next = newItem;
		head.prev = newItem;

		append(inList);
	}

	/**
	 * DList<T>(T) constructs a DList<T> with a single item.
	 * 
	 * @author Eldon Schoop
	 **/
	public DList(T elem) {
		DListNode<T> newItem = newNode(null, null, null, null);
		head = newItem;
		head.next = newItem;
		head.prev = newItem;
		insertFront(elem);
	}

	public void purge() {
		DListNode<T> newItem = newNode(null, null, null, null);
		head = newItem;
		head.next = newItem;
		head.prev = newItem;
		size = 0;
	}

	/**
	 * insertFront() inserts an item at the front of this DList<T>.
	 * 
	 * @author Eldon Schoop
	 * @param item is the item to be inserted. Performance: runs in O(1) time.
	 **/
	public void insertFront(T item) {
		DListNode<T> newItem = newNode(item, this, head, head.next);
		head.next.prev = newItem;
		head.next = newItem;
		size++;
	}

	/**
	 * insertBack() inserts an item at the back of this DList<T>.
	 * 
	 * @author Eldon Schoop
	 * @param item is the item to be inserted. Performance: runs in O(1) time.
	 **/
	public void insertBack(T item) {
		DListNode<T> newItem = newNode(item, this, head.prev, head);
		head.prev.next = newItem;
		head.prev = newItem;
		size++;
	}

	/**
	 * append() is simply an alias to insertBack().
	 * 
	 * @see DList#insertBack(Object)
	 */
	public void append(T item) {
		insertBack(item);
	}

	/**
	 * Appends an iterable, one item at a time, to "this" DList. Runs in linear
	 * time.
	 * 
	 * @param myIter the iterable to append to "this" DList.
	 */
	public void append(Iterable<T> myIter) {
		for (T elem : myIter) {
			if (elem != null) {
				insertBack(elem);
			}
		}
	}

	/**
	 * Appends an iterable to "this" DList, one item at a time. Appends items
	 * whether or not they are null. (Does not check). Runs in linear time.
	 * 
	 * @param myIter the iterable to append to "this" DList.
	 */
	public void appendWithNull(Iterable<T> myIter) {
		for (T elem : myIter) {
			insertBack(elem);
		}
	}

	/**
	 * Destructively appends a DList<T> to "this" DList<T>. Runs in constant time. Type
	 * parameters need to be the same. Destroys myList.
	 * 
	 * @param myList the DList<T> to append to "this" DList.
	 */
	public void appendDList(DList<T> myList) {
		if (myList.length() == 0) {
			return;
		} else if (myList.length() == 1) {
			insertBack(myList.frontItem());
			return;
		}
		DListNode<T> front = myList.front();
		DListNode<T> back = myList.back();
		front.prev = head.prev;
		back.next = head;
		head.prev.next = front;
		head.prev = back;

		size += myList.length();
		myList.purge();
	}

	/**
	 * front() returns the node at the front of this DList<T>. If the DList<T>
	 * is empty, return an "invalid" node--a node with the property that any
	 * attempt to use it will cause an exception. (The sentinel is "invalid".)
	 * 
	 * @author Eldon Schoop
	 * @return a DListNode<T> at the front of this DList<T>. Performance: runs
	 *         in O(1) time.
	 */
	public DListNode<T> front() {
		return head.next;
	}

	/**
	 * backItem() returns the item at the node at the front of "this" DList<T>.
	 * Does NOT throw an InvalidNodeException, make sure to double check the
	 * items!
	 * 
	 * @return the item at the front of "this" DList<T>
	 * @author Eldon Schoop Performance: runs in O(1) time.
	 */
	public T frontItem() {
		return head.next.item;
	}

	/**
	 * back() returns the node at the back of this DList<T>. If the DList<T> is
	 * empty, return an "invalid" node--a node with the property that any
	 * attempt to use it will cause an exception. (The sentinel is "invalid".)
	 * 
	 * @author Eldon Schoop
	 * @return a DListNode<T> at the back of this DList<T>. Performance: runs in
	 *         O(1) time.
	 */
	public DListNode<T> back() {
		return head.prev;
	}

	/**
	 * backItem() returns the item at the node at the back of "this" DList<T>.
	 * Does NOT throw an InvalidNodeException, make sure to double check the
	 * items!
	 * 
	 * @return the item at the back of "this" DList<T>
	 * @author Eldon Schoop Performance: runs in O(1) time.
	 */
	public T backItem() {
		return head.prev.item;
	}

	/**
	 * removeFront() removes the first item (and node) from a DList. If the list
	 * is empty, do nothing.
	 * 
	 * @author Eldon Schoop
	 */
	public void removeFront() {
		try {
			head.next.remove();
		} catch (InvalidNodeException e) {
			return;
		}
	}

	/**
	 * removeBack() removes the last item (and node) from a DList. If the list
	 * is empty, do nothing.
	 * 
	 * @author Eldon Schoop
	 */
	public void removeBack() {
		try {
			head.prev.remove();
		} catch (InvalidNodeException e) {
			return;
		}
	}

	/**
	 * partition() partitions dIn using the pivot item. On completion of this
	 * method, dIn is empty, and its items have been moved to dSmall, dEquals,
	 * and dLarge, according to their relationship to the pivot.
	 * 
	 * @param dIn
	 *            is a DList<T> of Comparable objects.
	 * @param pivot
	 *            is a Comparable item used for partitioning.
	 * @param dSmall
	 *            is a DList<T>, in which all items less than pivot will be
	 *            enqueued.
	 * @param dEquals
	 *            is a DList<T>, in which all items equal to the pivot will be
	 *            enqueued.
	 * @param dLarge
	 *            is a DList<T>, in which all items greater than pivot will be
	 *            enqueued.
	 * @author Eldon Schoop
	 * @see DList#sort()
	 **/
	private void partition(DList<T> dIn, Comparable pivot, DList<T> dSmall,
			DList<T> dEquals, DList<T> dLarge) {
		while (!dIn.isEmpty()) {
			if (pivot.compareTo((Comparable) dIn.frontItem()) > 0) {
				dSmall.insertBack(dIn.frontItem());
				dIn.removeFront();
			} else if (pivot.compareTo((Comparable) dIn.frontItem()) < 0) {
				dLarge.insertBack(dIn.frontItem());
				dIn.removeFront();
			} else {
				dEquals.insertBack(dIn.frontItem());
				dIn.removeFront();
			}
		}
	}

	/**
	 * sort() sorts "this" DList in place from smallest to largest using the
	 * quicksort algorithm.
	 * 
	 * Performance: Runs in O(n^2) worst case, and O(nlogn) average case time.
	 * @author Eldon Schoop
	 **/
	public void sort() {
		DList<T> smaller = new DList<T>();
		DList<T> same = new DList<T>();
		DList<T> larger = new DList<T>();
		if (length() > 1) {
			int pIdx = ((int) Math.random() * length());
			Object pivot = nth(pIdx);
			partition(this, ((Comparable) pivot), smaller, same, larger);
			smaller.sort();
			larger.sort();
		}
		appendDList(smaller);
		appendDList(same);
		appendDList(larger);
	}

	/**
	 * @param elem the element to check for inclusion in the DList
	 * @return true if the element is in the DList
	 */
	public boolean contains(T elem) {
		for (T e : this) {
			if (e == elem) {
				return true;
			}
		}
		return false;
	}

	/**
	 * nth() returns the item at the specified position. If position < 1 or
	 * position > this.length(), null is returned. Otherwise, the item at
	 * position "position" is returned. The list does not change.
	 * 
	 * @param position the desired position, from 1 to length(), in the list.
	 * @return the item at the given position in the list.
	 **/
	public T nth(int position) {
		DListNode<T> currentNode;

		currentNode = head.next;
		while (position > 0) {
			currentNode = currentNode.next;
			if (!currentNode.isValidNode()) {
				return null;
			}
			position--;
		}
		return currentNode.item;
	}

	/**
	 * Finds an element in the list which matches the search query
	 * 
	 * @param elem The element to search for
	 * @return the DListNode which contains the element
	 */
	public DListNode<T> find(T elem) {
		try {
			DListNode<T> curr = this.front();
			while (curr != null) {
				if (curr.item().equals(elem)) {
					return curr;
				}
				curr = curr.next();
			}
			return null;
		} catch (InvalidNodeException m) {
			return null;
		}
	}

	/**
	 * Removes the first occurrence of the given element
	 * 
	 * @param elem The element to find and remove
	 */
	public void remove(T elem) {
		try {
			find(elem).remove();
		} catch (InvalidNodeException m) {
			return;
		}
	}

	/**
	 * Utility function to count the non-null elements in an iterable.
	 * 
	 * @param myIter the iterable to count items of
	 * @return the number of non-null Objects in myIter
	 */
	public static int countNonNullElems(Iterable myIter) {
		int retval = 0;
		for (Object elem : myIter) {
			if (elem != null) {
				retval++;
			}
		}
		return retval;
	}

	/**
	 * toString() returns a String representation of this DList<T>.
	 * 
	 * @return a String representation of this DList<T>. Performance: runs in
	 *         O(n) time, where n is the length of the list.
	 */
	public String toString() {
		String result = "[  ";
		DListNode<T> current = head.next;
		while (current != head) {
			result = result + current.item + "  ";
			current = current.next;
		}
		return result + "]";
	}

	private static void testInvalidNode(DListNode p) {
		System.out.println("p.isValidNode() should be false: "
				+ p.isValidNode());
		try {
			p.item();
			System.out
					.println("p.item() should throw an exception, but didn't.");
		} catch (InvalidNodeException lbe) {
			System.out.println("p.item() should throw an exception, and did.");
		}
		try {
			p.setItem(new Integer(0));
			System.out
					.println("p.setItem() should throw an exception, but didn't.");
		} catch (InvalidNodeException lbe) {
			System.out
					.println("p.setItem() should throw an exception, and did.");
		}
		try {
			p.next();
			System.out
					.println("p.next() should throw an exception, but didn't.");
		} catch (InvalidNodeException lbe) {
			System.out.println("p.next() should throw an exception, and did.");
		}
		try {
			p.prev();
			System.out
					.println("p.prev() should throw an exception, but didn't.");
		} catch (InvalidNodeException lbe) {
			System.out.println("p.prev() should throw an exception, and did.");
		}
		try {
			p.insertBefore(new Integer(1));
			System.out
					.println("p.insertBefore() should throw an exception, but "
							+ "didn't.");
		} catch (InvalidNodeException lbe) {
			System.out
					.println("p.insertBefore() should throw an exception, and did.");
		}
		try {
			p.insertAfter(new Integer(1));
			System.out
					.println("p.insertAfter() should throw an exception, but "
							+ "didn't.");
		} catch (InvalidNodeException lbe) {
			System.out
					.println("p.insertAfter() should throw an exception, and did.");
		}
		try {
			p.remove();
			System.out
					.println("p.remove() should throw an exception, but didn't.");
		} catch (InvalidNodeException lbe) {
			System.out
					.println("p.remove() should throw an exception, and did.");
		}
	}

	private static void testEmpty() {
		DList<Integer> l = new DList<Integer>();
		System.out.println("An empty list should be [  ]: " + l);
		System.out.println("l.isEmpty() should be true: " + l.isEmpty());
		System.out.println("l.length() should be 0: " + l.length());
		System.out.println("Finding front node p of l.");
		DListNode<Integer> p = l.front();
		testInvalidNode(p);
		System.out.println("Finding back node p of l.");
		p = l.back();
		testInvalidNode(p);
		l.insertFront(new Integer(10));
		System.out.println("l after insertFront(10) should be [  10  ]: " + l);
	}

	private static void runJRSTests() {
		testEmpty();
		DList<Integer> l = new DList<Integer>();
		l.insertFront(new Integer(3));
		l.insertFront(new Integer(2));
		l.insertFront(new Integer(1));
		System.out.println("l is a list of 3 elements: " + l);
		try {
			DListNode<Integer> n;
			int i = 1;
			for (n = l.front(); n.isValidNode(); n = n.next()) {
				System.out.println("n.item() should be " + i + ": " + n.item());
				n.setItem(new Integer(((Integer) n.item()).intValue() * 2));
				System.out.println("n.item() should be " + 2 * i + ": "
						+ n.item());
				i++;
			}
			System.out.println("After doubling all elements of l: " + l);
			testInvalidNode(n);

			i = 6;
			for (n = l.back(); n.isValidNode(); n = n.prev()) {
				System.out.println("n.item() should be " + i + ": " + n.item());
				n.setItem(new Integer(((Integer) n.item()).intValue() * 2));
				System.out.println("n.item() should be " + 2 * i + ": "
						+ n.item());
				i = i - 2;
			}
			System.out.println("After doubling all elements of l again: " + l);
			testInvalidNode(n);

			n = l.front().next();
			System.out.println("Removing middle element (8) of l: " + n.item());
			n.remove();
			System.out.println("l is now: " + l);
			testInvalidNode(n);
			n = l.back();
			System.out.println("Removing end element (12) of l: " + n.item());
			n.remove();
			System.out.println("l is now: " + l);
			testInvalidNode(n);

			n = l.front();
			System.out.println("Removing first element (4) of l: " + n.item());
			n.remove();
			System.out.println("l is now: " + l);
			testInvalidNode(n);
		} catch (InvalidNodeException lbe) {
			System.err
					.println("Caught InvalidNodeException that should not happen.");
			System.err.println("Aborting the testing code.");
		}
	}

	private static void runEldonTests() {
		// ======== Test the Iterable Constructor ========
		Integer[] intList = new Integer[5];
		String intListStr = "{";
		for (int i = 0; i < 5; i++) {
			intList[i] = (int) (Math.random() * 10);
			intListStr += intList[i] + " ";
		}
		intListStr += "}";
		System.out.println("Original array: " + intListStr);
		DList<Integer> myDList = new DList<Integer>(Arrays.asList(intList));
		System.out.println("DList created:  " + myDList);
		// ======== End Iterable Constructor Test ========

		// Test Append
		Integer[] newList = { 1, 2, 3, 4 };
		System.out.println("Appending {1 2 3 4}.");
		myDList.append(Arrays.asList(newList));
		System.out.println("myDList is now: " + myDList);

		// Test Null Append Check
		Integer[] spam = new Integer[5];
		spam[1] = spam[3] = 12;
		System.out.println("Appending {null 12 null 12 null");
		myDList.append(Arrays.asList(spam));
		System.out.println("myDList is now: " + myDList);
		// Test iterator
		for (Integer i : myDList) {
			System.out.print("" + i + " ");
		}
	}

	public static DList<Integer> makeRandom(int size) {
		DList<Integer> d = new DList<Integer>();
		for (int i = 0; i < size; i++) {
			d.insertBack(new Integer((int) (size * Math.random())));
		}
		return d;
	}

	public static void testSort() {
		DList<Integer> d = makeRandom(10);
		System.out.println(d.toString());
		d.sort();
		System.out.println(d.toString());
	}

	public static void main(String[] argv) {
		// runJRSTests();
		// runEldonTests();
		// testSort();
	}
}
