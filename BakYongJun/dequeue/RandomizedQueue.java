import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count;
    private int N = 2;          //initial size of the stack    
    private Item[] randomizedQueue = (Item[]) new Object[N];
          
    // construct an empty RandomizedQueue
    public RandomizedQueue() {
        count = 0;
        N = 2;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return (count == 0);
    }
    
    // return the number of items on the deque
    public int size() {
        return N;
    }
    
    // insert the item at the front
    public void enqueue(Item item) {
        if (item == null)
            throw new UnsupportedOperationException();
                
//        StdOut.println("count:"+ count);
//        StdOut.println("N:"+ N);
//        StdOut.println("randomizedQueue.length:"+ randomizedQueue.length);
        if (count >= N) {            
            resize(N*2);    // double size of array if necessary
            N *= 2;
        }        
        randomizedQueue[count++] = item;        
        //assert
    }    
    
    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("deque underflow");
        Item item;
        int randomIndex = StdRandom.uniform(N);
        while(randomizedQueue[randomIndex] == null){
            randomIndex = StdRandom.uniform(N);
        }
        item = randomizedQueue[randomIndex];
        randomizedQueue[randomIndex] = null;
//        StdOut.println("dequeue  randomIndex:"+ randomIndex);
//        StdOut.println("dequeue  randomizedQueue["+randomIndex+"]:"+ randomizedQueue[randomIndex]);
        count--;               
        if (count < N/2){           
            resize(N/2);    // half size of array if necessary              
            N /= 2;
        }
//        assert
        
        return item;
    }
    
    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("deque underflow ");
        Item item;
        int randomIndex = StdRandom.uniform(N);
        while(randomizedQueue[randomIndex] == null){
            randomIndex = StdRandom.uniform(N);
        }
        item = randomizedQueue[randomIndex];
               
        return item;
    }
    
    // resize the underlying array holding the elements
    private void resize(int capacity) {
//        assert capacity >= N;
//        StdOut.println("resize  capacity:"+ capacity);
//        StdOut.println("resize  count:"+ count);
        
        Item[] temp = (Item[]) new Object[capacity];
        int index = 0;
        for (int i = 0; i < N; i++) {
            if (randomizedQueue[i] != null){
//                StdOut.println("resize index :"+ index);
//                StdOut.println("resize i :"+ i);
//                StdOut.println("resize N :"+ N);
//                StdOut.println("resize randomizedQueue[i] :"+ randomizedQueue[i]);
//                StdOut.println("--------------------------------------------------");
                temp[index] = randomizedQueue[i];
                index++;
            }
        }
        randomizedQueue = temp;
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator()  { return new RandomIterator();}

    // an iterator, doesn't implement remove() since it's optional
    private class RandomIterator implements Iterator<Item> {
        private int i;
        private int[] indexList;
        private int current = 0;
        
        public RandomIterator() {
            i = N;
            indexList = new int[i];
            for (int index = 0; index < i; i++){
                indexList[index] = index;
            }
            StdRandom.shuffle(indexList);
        }

        public boolean hasNext() {
            return current < i;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            current++;
            return randomizedQueue[current];
        }
    }
   
    public static void main(String[] args) {   // unit testing
        int N = 20;
        RandomizedQueue test = new RandomizedQueue();
        for(int i = 0; i < N; i++){
            int inputVal = StdRandom.uniform(100);
//            StdOut.println("inputVal :"+ inputVal);
            test.enqueue(inputVal);
        }
        
//        StdOut.println("test :"+ test.toString());
        for(int i = 0; i < N*3; i++){
            int randomVal = StdRandom.uniform(2);
//            StdOut.println(i +" randomVal :"+ randomVal);
            if (randomVal == 2){
                int inputVal = StdRandom.uniform(100);
//                StdOut.println("enqueue :"+ inputVal);
                test.enqueue(inputVal);
            } else if (randomVal == 0){ 
//                StdOut.println( i +" ,dequeue :"+ test.dequeue());
            } else if (randomVal == 1){
//                StdOut.println("sample :"+ test.sample());
            }
        }
    }    
}
