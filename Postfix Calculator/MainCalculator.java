package calc;

import java.util.Scanner;

public class MainCalculator {
	public static int calculate(String s) {
        LinkedStack<Integer> stack = new LinkedStack<Integer>();
        for(int i = 0; i < s.length(); i++) {
        	char c = s.charAt(i);  
            if(Character.isDigit(c)) {
                stack.push(c - '0');	
            } 
            else {
                int num1 = stack.pop();
                int num2 = stack.pop();
                switch(c) {
                case '^':
                	stack.push((int) Math.pow(num2, num1));
                	break;
                case '*':
                	stack.push(num2 * num1);
                	break;
                case '-':
                    stack.push(num2 - num1);
                    break;
                case '/':
                	stack.push(num2 / num1);
                    break;
                case '+':
                   	stack.push(num2 + num1);
                   	break;
                }
            }
        }
        return stack.pop();   
    }
	public static void main(String[] args) {
		Converter calc = new Converter();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your infix expression here: \n");
		String infix = input.nextLine();
		String postfix = calc.toPostFix(infix);
		System.out.println("POSTFIX: "+postfix);
		System.out.println("ANSWER: " + calculate(postfix.replace(" ", "")));
	}
}
class Converter {
	public static boolean isNumeric(String s) { 
		try {  
		    Integer.parseInt(s);  
		    return true; 
		} 
		catch(NumberFormatException e){  
		    return false;  
		}   
	}
	// takes place of parserhelper, tokenizes
	public static int order(char c) {
		switch(c) {
		case('+'):
			return 1;
		case('-'):
			return 1;
		case('*'):
			return 2;
		case('/'):
			return 2;
		case('^'):
			return 3;
		}
		return 0;
	}
	public String toPostFix(String s) {
		String output = "";
		LinkedStack<Character> stack = new LinkedStack<Character>();
		char[] chars = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (order(c) > 0) {
				while (!stack.isEmpty() && order(stack.top()) >= order(c)) {
					output += stack.pop() + " ";
				}
				stack.push(c);
			}
			else if(c==')'){
				char x = stack.pop();
                while(x!='('){
                    output += x + " ";
                    x = stack.pop();
                }
			}
			else if(c=='('){
                stack.push(c);
            }
			else{
                output += c + " ";
            }
		}
		for (int i = 0; i <= stack.size(); i++) {
			output += stack.pop();
	    }
		return output;
	}
}
class LinkedStack<E> implements Stack<E> {
	private SinglyLinkedList<E> list = new SinglyLinkedList<>(); // an empty list
	public LinkedStack() { 
		
	} // new stack relies on the initially empty list
	public int size() { 
		return list.size(); 
	} 
	public boolean isEmpty() {
		return list.isEmpty(); 
	}
	public void push(E element) { 
		list.addFirst(element); 
	}
	public E top() { 
		return list.first(); 
	}
	public E bottom() {
		return list.last();
	}
	public E pop() { 
		return list.removeFirst(); 
	}
	public E bot() { 
		return list.removeLast(); 
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
