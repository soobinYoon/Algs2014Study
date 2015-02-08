import java.util.Iterator;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int N;   
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        a = (Item[]) new Object[2];
        N = 0;
    }
    private void printAll() {
        System.out.print("printAll : ");
        for (int i = 0; i < N; i++) {
            System.out.print(a[i]+",");
        }
        System.out.print("\n");
    }
    public boolean isEmpty()                 // is the queue empty?
    {
        return N == 0;
    }
    
    public int size()                        // return the number of items on the queue
    {
        return N;
    }
    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new NullPointerException("attempt to add null");
        a[N++] = item;
        if (N == a.length) resize(2*a.length);
        return;
    }
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    public Item dequeue()                    // delete and return a random item
    {
        if (isEmpty()) throw new java.util.NoSuchElementException("Stack underflow");
        Random random = new Random();
        //a[random.nextInt(N)];
        int chosenId = random.nextInt(N);
        Item item = a[chosenId];
        a[chosenId] = null;                              // to avoid loitering
        for(int i = chosenId; i < N-1; i++) {
            a[i] = a[i+1];
        }
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
    }
    
    public Item sample()                     // return (but do not delete) a random item
    {
        if (isEmpty()) throw new java.util.NoSuchElementException("Stack underflow");
        Random random = new Random();
        return a[random.nextInt(N)];
    }
    public Iterator<Item> iterator() {
        return new RandomizedArrayIterator();
    }
    
    // an iterator, doesn't implement remove() since it's optional
    private class RandomizedArrayIterator implements Iterator<Item> {
        private int[] idList;
        private int i;
        
        public RandomizedArrayIterator() {
            idList = new int[N];
            i = N;
            for (int i = 0; i < N; i++) {
                idList[i] = -1;
            }
            Random random = new Random();
            for (int j = 0; j < N; j++) {
                int id = random.nextInt(N);
                while (idList[id] >= 0) {
                    id = random.nextInt(N);
                }
                idList[id] = j;
            }
        }
        
        public boolean hasNext() {
            return i > 0;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new  java.util.NoSuchElementException();
            return a[idList[--i]];
        }
    }
    public static void main(String[] args)   // unit testing
    {
        
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("a");
        rq.printAll();
        rq.enqueue("b");
        rq.printAll();
        rq.dequeue();
        rq.printAll();
        rq.enqueue("c");
        rq.printAll();
        rq.enqueue("d");
        rq.enqueue("e");
        rq.printAll();
        rq.dequeue();
        rq.printAll();
        
        
        System.out.println("\n~end~");
    }
}