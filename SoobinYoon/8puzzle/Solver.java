import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
public class Solver {
    private Board origin;
    private Board twin;
    private int move;
    private MinPQ<Node> queue;
    private Node originTreeRoot;
    private Node twinTreeRoot;
    private Boolean isSolvable = false;
    private ArrayList<Board> solution;
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        origin = initial;
        //System.out.println(origin.toString());
        twin = origin.twin();
        //System.out.println(origin.toString());
        originTreeRoot = new Node(origin, true);
        twinTreeRoot = new Node(twin, false);
        queue = new MinPQ<Node>();
        //queue.insert(origin);
       // queue.insert(twin);
        //System.out.println("twinTreeRoot.priority() = " + twinTreeRoot.priority());
        queue.insert(originTreeRoot);
        queue.insert(twinTreeRoot);
        
        //System.out.println("before while");
        int count = 0;
        while (isSolvable == false) {
            /*count++;
            if (count == 100) {
                System.out.println("count break");
                break;
            }*/
            //System.out.println("start another loop---------------------------");
            Node currentNode = queue.delMin();
            Board currentB = currentNode.getBoard();
            /*
            System.out.println("isOrigin = " + currentNode.isOrigin);
            System.out.println("move = " + currentNode.getMove());
            System.out.println("manhattan = " + currentB.manhattan());
            System.out.println("priority = " + currentNode.priority());
            System.out.println(currentB.toString());
            */
            if ( currentB.isGoal() == true ) { 
                //System.out.println("find goal");
                if ( currentNode.isOrigin == false ) {
                    //System.out.println("it's twin");
                    isSolvable = false; 
                    break;
                }
                isSolvable = true;
                move = currentNode.getMove();
                solution = new ArrayList<Board>();
                solution.add(currentNode.getBoard());
                while (currentNode.getMove() != 0) {
                    currentNode = currentNode.getParent();
                    solution.add(0, currentNode.getBoard());
                }
                return;
            }
           // System.out.println("didn't find goal");
            //else //didn't find goal
            Iterable<Board> nList = currentB.neighbors();
            for ( Board b : nList ) {
                Node parentN = currentNode; 
                Boolean sameLikeParent = false;
                while (parentN.getParent() != null) { 
                    if ( b.equals((Object) parentN.getBoard()) ) {
                        //System.out.println("same like parent");
                        sameLikeParent = true;
                        break;
                    }
                    parentN = parentN.getParent();
                }
                if (sameLikeParent == true) continue;
                Node nNode = currentNode.addChild(b);
                //Node nNode = Node(b, currentNode);
                queue.insert(nNode);
                //System.out.println("insert new Node");
            }
        }
    }
    private class Node implements Comparable<Node> {
        private Board board;
        private int move;
        public  boolean isOrigin;
        private Node parent = null;
        private List<Node> childList = new ArrayList<Node>();
        public Node(Board b, Boolean isOrigin) {
            board = b;
            move = 0;
            this.isOrigin = isOrigin;
        }
        public Node(Board b, Node p){
            board = b;
            parent = p;
            move = p.getMove() + 1;
            isOrigin = p.isOrigin;
        }
        public Board getBoard() {
            return board;
        }
        public int priority() {
            return getBoard().manhattan() + move;
        }
        public int compareTo(Node that) {
            return this.priority() - that.priority();
        } 
        public Node getParent() {
            return parent;
        }
        public int getMove() {
            return move;
        }
        public Node addChild(Board b) {
            Node childNode = new Node(b, this);
            childList.add(childNode);
            return childNode;
        }
    }
    public boolean isSolvable() {           // is the initial board solvable?
        return isSolvable;
    }
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (isSolvable() == false ) return -1;
        return move;
    } 
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) return null;
        return solution;
    }
    //////////////////////////////////////////////////////////////////
    public static void main(String[] args)  {
        
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board.toString());
        }
    }
}