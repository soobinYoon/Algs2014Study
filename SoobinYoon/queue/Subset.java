


public class Subset {
    private RandomizedQueue<String> inputList;   
    //private int k;
    
    public Subset() { 
        //inputList = new String[k];
        inputList = new RandomizedQueue<String>();
    }

    public static void main(String[] args) {    // test client (described below)
        int k = Integer.parseInt(args[0]);
        Subset subset = new Subset();
        
        while (!StdIn.isEmpty()) {
            String temp = StdIn.readString();
            //System.out.println("temp : "+temp);
            subset.inputList.enqueue(temp);
        }
       // System.out.println("while end");
        for (int i = 0; i < k; i++) {
            System.out.println(subset.inputList.dequeue());
        }
        //System.out.println("end");
        //while ((input = scanner.nextLine()) != null) {

    }
}