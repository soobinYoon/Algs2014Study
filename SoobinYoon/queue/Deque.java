import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private Node head, tail;
    private int size;
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
        public void setItem(Item item){
            this.item = item;
        }
        public void setPrev(Node p){
            prev = p;
        }
        public void setNext(Node n){
            next = n;
        }
    }
    private void printAll() {
        Node current = head.next;
        System.out.print("printAll : ");
        while ( current != tail){
            System.out.print(current.item + ",");
            current = current.next;
        }
        System.out.print("\n");
    }
    public Deque() {                           // construct an empty deque
        //System.out.println("Deque()");
        head = new Node();
        tail = new Node();
        //head.next = tail;
        head.setNext(tail);
        //tail.prev = head;
        tail.setPrev(head);
 
        size = 0;
    }
    public boolean isEmpty() {                 // is the deque empty?
        if (0 == size())
            return true;
        return false;
    }
    public int size() {                        // return the number of items on the deque
        return size;
    }
    public void addFirst(Item item) {          // insert the item at the front
        if (item == null) {
            throw new NullPointerException("add null");
        }
        //System.out.println("addFirst(" + item + ")");
        Node oldFirst = head.next;
        first = new Node();
        
        //first.item = item;
        first.setItem(item);
        first.setPrev(head);
        first.setNext(oldFirst);
        oldFirst.setPrev(first);
        head.setNext(first);
        
        size++;
    }
    public void addLast(Item item) {           // insert the item at the end
        if (item == null) {
            throw new NullPointerException("add null");
        }
        //System.out.println("addLast(" + item + ")");
        Node oldLast = tail.prev;
        last = new Node();
        
        last.setItem(item);
        last.setPrev(oldLast);
        last.setNext(tail);
        oldLast.setNext(last);
        tail.setPrev(last);
        
        size++;
        
    }
    public Item removeFirst() {                // delete and return the item at the front
        if (isEmpty() == true) {
            throw new java.util.NoSuchElementException("empty");
            //return null;
        }
        Item temp = head.next.item;
        first = head.next.next;
        head.setNext(first);
        first.setPrev(head);
        //System.out.println("removeFirst()");
        size--;
        return temp;
    }
    public Item removeLast() {                 // delete and return the item at the end
        if (isEmpty() == true) {
            throw new java.util.NoSuchElementException("empty");
        }
        Item temp = tail.prev.item;
        last = tail.prev.prev;
        last.setNext(tail);
        tail.setPrev(last);
        //System.out.println("removeLast()");
        size--;
        return temp;
    }
    public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        private Node current = head.next;
        public boolean hasNext() { return current != null; }
        public void remove() { /* not supported */
            throw new UnsupportedOperationException("not supported");
        } 
        public Item next() { 
            if (current.item == null) {
                throw new java.util.NoSuchElementException("not supported");
            }
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    public static void main(String[] args) {   // unit testing
        Deque<String> d = new Deque<String>();
        d.addLast("a");
        //d.addFirst("0");
        d.printAll();
        d.removeLast();
        d.printAll();
        d.addFirst("b");
        d.printAll();
        d.addLast("c");
        d.printAll();
        d.removeLast();
        d.printAll();
        d.addFirst("d");
        d.printAll();
        d.removeLast();
        d.printAll();
        d.removeLast();
        d.printAll();
        d.removeFirst();
        d.printAll();
        d.removeFirst();
        d.printAll();
        System.out.println("\n~end~");
    }
}