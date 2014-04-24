/* DListNode<T>.java */

package list;

/**
 * A DListNode<T> is a mutable, generic, node in a DList<T> (doubly-linked list).
 * 
 * @author Eldon Schoop
 **/

public class DListNode<T> {

	/**
	 * item references the item stored in the current node.
	 * myList references the List that contains this node.
	 * prev references the previous node in the DList<T>.
	 * next references the next node in the DList<T>.
	 */
	protected T item;
	protected DList<T> myList;
	protected DListNode<T> prev;
	protected DListNode<T> next;

	/**
	 * DListNode<T>() constructor.
	 * 
	 * @param i the item to store in the node.
	 * @param l the list this node is in.
	 * @param p the node previous to this node.
	 * @param n the node following this node.
	 */
	DListNode(T i, DList<T> l, DListNode<T> p, DListNode<T> n) {
		item = i;
		myList = l;
		prev = p;
		next = n;
	}

	/**
	 * isValidNode returns true if this node is valid; false otherwise.
	 * An invalid node is represented by a `myList' field with the value null.
	 * Sentinel nodes are invalid, and nodes that don't belong to a list are
	 * also invalid.
	 * 
	 * @return true if this node is valid; false otherwise.
	 * Performance: runs in O(1) time.
	 */
	public boolean isValidNode() {
		return myList != null;
	}

	/**
	 * item() returns this node's item. If this node is invalid,
	 * throws an exception.
	 * 
	 * @return the item stored in this node.
	 * Performance: runs in O(1) time.
	 */
	public T item() throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException();
		}
		return item;
	}

	/**
	 * setItem() sets this node's item to "item". If this node is invalid,
	 * throws an exception.
	 * Performance: runs in O(1) time.
	 */
	public void setItem(T item) throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException();
		}
		this.item = item;
	}

	/**
	 * next() returns the node following this node. If this node is invalid,
	 * throws an exception.
	 * 
	 * @return the node following this node.
	 * @exception InvalidNodeException if this node is not valid.
	 * Performance: runs in O(1) time.
	 */
	public DListNode<T> next() throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException("next() called on invalid node");
		}
		return next;
	}

	/**
	 * prev() returns the node preceding this node. If this node is invalid,
	 * throws an exception.
	 * 
	 * @param node the node whose predecessor is sought.
	 * @return the node preceding this node.
	 * @exception InvalidNodeException if this node is not valid.
	 * Performance: runs in O(1) time.
	 */
	public DListNode<T> prev() throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException("prev() called on invalid node");
		}
		return prev;
	}

	/**
	 * insertAfter() inserts an item immediately following this node. If this
	 * node is invalid, throws an exception.
	 * 
	 * @param item the item to be inserted.
	 * @exception InvalidNodeException if this node is not valid.
	 * Performance: runs in O(1) time.
	 */
	public void insertAfter(T item) throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException("insertAfter() called on invalid node");
		}
		// if (next.isValidNode()) {
		next.prev = new DListNode<T>(item, (DList<T>) myList, this, next);
		next = next.prev;
		myList.size++;
		// } else {
		// throw new InvalidNodeException("List is corrupted - previous node invalid.");
		// }
	}

	/**
	 * insertBefore() inserts an item immediately preceding this node. If this
	 * node is invalid, throws an exception.
	 * 
	 * @param item the item to be inserted.
	 * @exception InvalidNodeException if this node is not valid.
	 * Performance: runs in O(1) time.
	 */
	public void insertBefore(T item) throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException("insertBefore() called on invalid node");
		}
		// if (prev.isValidNode()) {
		prev.next = new DListNode<T>(item, (DList<T>) myList, prev, this);
		prev = prev.next;
		myList.size++;
		// } else {
		// throw new InvalidNodeException("List is corrupted - previous node invalid.");
		// }
	}

	/**
	 * remove() removes this node from its DList<T>. If this node is invalid,
	 * throws an exception.
	 * 
	 * @exception InvalidNodeException if this node is not valid.
	 * Performance: runs in O(1) time.
	 */
	public void remove() throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException("remove() called on invalid node");
		}
		prev.next = next;
		next.prev = prev;
		myList.size--;

		// Make this node an invalid node, so it cannot be used to corrupt myList.
		myList = null;
		// Set other references to null to improve garbage collection.
		next = null;
		prev = null;
	}

}
