import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;          // size of the stack
    
    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
        assert check();
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return (N == 0);
    }
    
    // return the number of items on the deque
    public int size() {
        return N;
    }
    
    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new UnsupportedOperationException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
//        StdOut.println("item :" + item);
//        StdOut.println("first :" + first);
//        StdOut.println("oldfirst :" + oldfirst);
        
        first.prev = null;
        if (isEmpty()) {
            last = first;
            last.prev = first;
            first.next = last;
        } else
            oldfirst.prev = first;
        
        N++;
        
        
        assert check();
    }
    
    // insert the item at the end
    public void addLast(Item item) {   
        if (item == null)
            throw new UnsupportedOperationException();
        
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        
        if (isEmpty()) {
            first = last;
            first.next = last;
            last.prev = first;
        } else
            oldlast.next = last;
        
        N++;  
        
        Node temp = (first.next).prev;
        StdOut.println("yy"+temp);
        assert check();
    }
    
    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("deque underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        N--;
        if (isEmpty()) {
            last = null;
            first = null;
        }
        assert check();
        
        return item;
    }
    
    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("deque underflow ");
        Item item = last.item;        // save item to return
        last = last.prev;            // delete first node
        N--;
       if (isEmpty()) {
            last = null;
            first = null;
        }
        assert check();
        
        return item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()  { return new ListIterator();  }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
   
    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null) return false;
            if (last != null) return false;
        }
        else if (N == 1) {
            if (first == null)      return false;
            if (first.next != null) return false;
//            if (!first.equals(last))      return false;
        }
        else {
            if (first.next == null) return false;
            if (last.prev == null) return false;
        }

        // check internal consistency of instance variable N
        int numberOfNodes = 0;
        for (Node x = first; x != null; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != N) return false;
        
        for (Node x = last; x != null; x = x.prev) {
            numberOfNodes++;
        }
        if (numberOfNodes != N) return false;

        return true;
    }
    public static void main(String[] args) {   // unit testing
        int N = 100;
        Deque testDeque = new Deque();
        for (int i = 0; i < N; i++) {
            testDeque.addLast(i);
        }
        for (int i = 0; i < N; i++) {
//            StdOut.println(testDeque.removeLast());
        }
    }    
}
