public class Subset {    
    public static void main(String[] args) {
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
//            if (!item.equals("-")) s.push(item);
//            else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
            s.enqueue(item);
//            StdOut.println(item);
        }
//        StdOut.println(args[0]);
        int k =  Integer.parseInt(args[0]);
//        int i = 0;
//        StdOut.println(k);
//        for (String t: s) {
//            StdOut.println(t);
//            i++;
//            if (i >= k) {
//                break;
//            }
//        }
        for(int i = 0; i < k; i++){
            StdOut.println(s.dequeue());
        }
//        StdOut.println("(" + s.size() + " left on stack)");
    }
}