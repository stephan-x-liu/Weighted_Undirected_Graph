/* list/DListIterator.java */

package list;

import java.util.*;

public class DListIterator<T> implements Iterator<T> {
	DList<T> l;
	DListNode<T> n;

	/**
	 * Creates an iterator which traverses a DList.
	 * 
	 * @param iterateMe a DList<T> to traverse
	 * @author Eldon Schoop
	 * @author JRS
	 */
	public DListIterator(DList<T> iterateMe) {
		l = iterateMe;
		n = l.head.next;
	}

	/**
	 * @return true if there are still items in the DList
	 * @author Eldon Schoop
	 * @author JRS
	 */
	public boolean hasNext() {
		return n != l.head;
	}

	/**
	 * Yields the next item in the DList. Throws NoSuchElementException if
	 * there are no more elements in the list.
	 * 
	 * @author Eldon Schoop
	 * @author JRS
	 */
	public T next() {
		if (n == l.head) {
			throw new NoSuchElementException(); // In java.util
		}
		T i = n.item;
		n = n.next;
		return i;
	}

	/**
	 * You just try calling this function. I dare you.
	 * 
	 * @author JRS
	 */
	public void remove() {
		/* Doing it the lazy way. Remove this, motherf! */
		throw new UnsupportedOperationException("Nice try, bozo."); // In java.lang
	}
}