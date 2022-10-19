import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("Please enter your number here: ");
		Scanner input = new Scanner(System.in);
		try {
			int num = input.nextInt();
			if (num < 2) {
				System.out.println("Please enter an integer greater than or equal to 2!");
				System.exit(0);
			}
			else {
				Sieve newSieve = new Sieve();
				newSieve.primesTo(num);
			}
		}
		catch(Exception e) {
			System.out.println("Please enter a valid integer!");
			System.exit(0);
		}
	}
}
class Sieve {
	// methods
	public void primesTo(int n) {
		LinkedQueue<Integer> numbers = new LinkedQueue<>();
		LinkedQueue<Integer> primes = new LinkedQueue<>();
		SinglyLinkedList<Integer> tmp = new SinglyLinkedList<>();
		for (int i = 2; i <= n; i++) { // enqueues all numbers from 2 to n
			numbers.enqueue(i);
		}
		int p = numbers.dequeue();
		primes.enqueue(p);
		while (Double.valueOf(p) <= Math.sqrt(n)) {
			int temp = numbers.dequeue();
				if (temp%p >= 1) {
				tmp.addLast(temp);
			}
			if (numbers.isEmpty()) {
				int len = tmp.size();
				for (int i = 0; i < len; i++) {
					numbers.enqueue(tmp.removeFirst());
				}
				p = numbers.dequeue();
				primes.enqueue(p);
			}
		}
		while (!numbers.isEmpty()) {
			int x = numbers.dequeue();
			primes.enqueue(x);
		}
		System.out.print("Primes up to " + String.valueOf(n) + " are: ");
		while (!primes.isEmpty()) {
			if (primes.size() == 1) {
				System.out.print(String.valueOf(primes.first()));
				primes.dequeue();
				break;
			}
			System.out.print(String.valueOf(primes.first()) + ", ");
			primes.dequeue();
		}
	}
	// constructor
	public Sieve() {
		
	}
}
interface Queue<E> {
	  /**
	   * Returns the number of elements in the queue.
	   * @return number of elements in the queue
	   */
	int size();

	  /**
	   * Tests whether the queue is empty.
	   * @return true if the queue is empty, false otherwise
	   */
	boolean isEmpty();

	  /**
	   * Inserts an element at the rear of the queue.
	   * @param e  the element to be inserted
	   */
	void enqueue(E e);

	  /**
	   * Returns, but does not remove, the first element of the queue.
	   * @return the first element of the queue (or null if empty)
	   */
	E first();

	  /**
	   * Removes and returns the first element of the queue.
	   * @return element removed (or null if empty)
	   */
	E dequeue();
}
class LinkedQueue<E> implements Queue<E> {

	  /** The primary storage for elements of the queue */
	private SinglyLinkedList<E> list = new SinglyLinkedList<>();   // an empty  list

	  /** Constructs an initially empty queue. */
	public LinkedQueue() {
		
	}                  // new queue relies on the initially empty list

	  /**
	   * Returns the number of elements in the queue.
	   * @return number of elements in the queue
	   */
	  @Override
	public int size() {
		return list.size(); 
	}

	  /**
	   * Tests whether the queue is empty.
	   * @return true if the queue is empty, false otherwise
	   */
	  @Override
	public boolean isEmpty() { 
		return list.isEmpty(); 
	}

	  /**
	   * Inserts an element at the rear of the queue.
	   * @param element  the element to be inserted
	   */
	  @Override
	public void enqueue(E element) {
		list.addLast(element); 
	  }

	  /**
	   * Returns, but does not remove, the first element of the queue.
	   * @return the first element of the queue (or null if empty)
	   */
	  @Override
	public E first() { 
		return list.first(); 
	}

	  /**
	   * Removes and returns the first element of the queue.
	   * @return element removed (or null if empty)
	   */
	  @Override
	public E dequeue() { 
		return list.removeFirst(); 
	}

	  /** Produces a string representation of the contents of the queue.
	   *  (from front to back). This exists for debugging purposes only.
	   */
	public String toString() {
		return list.toString();
	}
}
class SinglyLinkedList<E> implements Cloneable {
	// properties
	// nested class
	private static class Node<E> {
		// nested class properties
		private E element;
		private Node<E> next;
		// nested class constructor
		public Node(E e, Node<E> n) {  
			element = e;
			next = n;
		}
		// nested methods
	    public E getElement() { 
	    	return element; 
	    }
	    public Node<E> getNext() {
	    	return next; 
	    }
	    public void setNext(Node<E> n) { 
	    	next = n; 
	    }
	}
	private Node<E> head = null;
	private Node<E> tail = null;
	private int size = 0;
	// constructor
	public SinglyLinkedList() { 
	 
	}
	public int size() { 
		return size; 
	}
	public boolean isEmpty() { 
		return size == 0; 
	}
	public E first() {             // returns (but does not remove) the first element
		if (isEmpty()) {
			return null;
		}
		return head.getElement();
	}
	public E last() {              // returns (but does not remove) the last element
		if (isEmpty()) {
			return null;
		}
		return tail.getElement();
	}
	public void addFirst(E e) {                // adds element e to the front of the list
	  head = new Node<>(e, head);              // create and link a new node
	  if (size == 0) {
		  tail = head;     
	  }
	  size++;
  	}
	public void addLast(E e) {                 // adds element e to the end of the list
		Node<E> newest = new Node<>(e, null);    // node will eventually be the tail
		if (isEmpty()) {
			head = newest;
		}// special case: previously empty list
		else {
			tail.setNext(newest);        
		}// new node after existing tail
		tail = newest;                           // new node becomes the tail
		size++;
	}

  /**
   * Removes and returns the first element of the list.
   * @return the removed element (or null if empty)
   */
	public E removeFirst() {                   // removes and returns the first element
		if (isEmpty()) {
			return null;              // nothing to remove
		}
		E answer = head.getElement();
		head = head.getNext();                   // will become null if list had only one node
		size--;
		if (size == 0) {
			tail = null; 
		}// special case as list is now empty
		return answer;
	}
	public E removeLast() {                   // removes and returns the first element
		if (isEmpty()) {
			return null;              // nothing to remove
		}    
		if (size == 1) {
			return removeFirst();
		}
		E answer = tail.getElement();
		Node walk = head; 
		while (walk.getNext() != tail) {
		      walk = walk.next;                   // will become null if list had only one node
		}
		tail = walk;
		tail.setNext(null);
		size--;
		return answer;
	}
	@SuppressWarnings({"unchecked"})
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		SinglyLinkedList other = (SinglyLinkedList) o;   // use nonparameterized type
		if (size != other.size) {
			return false;
		}
		Node walkA = head;                               // traverse the primary list
		Node walkB = other.head;                         // traverse the secondary list
		while (walkA != null) {
			if (!walkA.getElement().equals(walkB.getElement())) {
				return false; //mismatch
			}
			walkA = walkA.getNext();
			walkB = walkB.getNext();
		}
		return true;   // if we reach this, everything matched successfully
	}

	@SuppressWarnings({"unchecked"})
	public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
		// always use inherited Object.clone() to create the initial copy
		SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone(); // safe cast
		if (size > 0) {                    // we need independent chain of nodes
			other.head = new Node<>(head.getElement(), null);
			Node<E> walk = head.getNext();      // walk through remainder of original list
			Node<E> otherTail = other.head;     // remember most recently created node
			while (walk != null) {              // make a new node storing same element
				Node<E> newest = new Node<>(walk.getElement(), null);
				otherTail.setNext(newest);     // link previous node to this one
				otherTail = newest;
				walk = walk.getNext();
			}
		}
		return other;
	}

	public int hashCode() {
		int h = 0;
		for (Node walk=head; walk != null; walk = walk.getNext()) {
			h ^= walk.getElement().hashCode();      // bitwise exclusive-or with element's code
			h = (h << 5) | (h >>> 27);              // 5-bit cyclic shift of composite code
		}
		return h;
	}

  /**
   * Produces a string representation of the contents of the list.
   * This exists for debugging purposes only.
   */
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		Node<E> walk = head;
		while (walk != null) {
			sb.append(walk.getElement());
			if (walk != tail) {
				sb.append(", ");
			}
			walk = walk.getNext();
		}
		sb.append(")");
		return sb.toString();
	}
}
