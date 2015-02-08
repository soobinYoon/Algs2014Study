import java.util.ArrayList;
import java.util.List;

public class Board  {
    // construct a board from an N-by-N array of blocks
      // (where blocks[i][j] = block in row i, column j)
    private final char[][] blocks;
    private int dimension;
    private Board twin;
    //private char[][] goalBlocks;
    //private Board goal = null;
    
    private Position blankPosition;
    
    public Board(int[][] blocks)           
    {            

        dimension = blocks.length;
        this.blocks = new char[dimension][dimension];
       // goalBlocks =  new char[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
           // System.out.println("i : "+i);
            for (int j = 0; j < dimension; j++) {
               // System.out.println("j : "+j);
                this.blocks[i][j] = (char) blocks[i][j]; 
               // goalBlocks[i][j] = (char) (i * dimension + j + 1);
                if (blocks[i][j] == 0) {
                    blankPosition = new Position(i, j);
                }
            }
        }
        //goalBlocks[dimension-1][dimension-1] = (char) 0;
    }
    /*
    public Board(char[][] blocks) {
        this.blocks = blocks;
        dimension = blocks.length;
        goalBlocks =  new char[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                //this.blocks[i][j] = blocks[i][j]; 
                goalBlocks[i][j] = (char) (i * dimension + j + 1);
                if (blocks[i][j] == (char) 0) {
                    blankPosition = new Position(i, j);
                }
            }
        }
        goalBlocks[dimension-1][dimension-1] = (char) 0;
    }*/
    
    private class Position {
        public int x;
        public int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public boolean isValid() {
            if ( x < 0 || x >= dimension || y < 0 || y >= dimension ) return false;
            else return true;
        }
    }

    public int dimension()                 // board dimension N
    {
        return dimension;
    }
    public int hamming()                   // number of blocks out of place
    {
        int hScore = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != (char) (i * dimension + j + 1)) {
                    hScore++;
                }
            }
        }
        return hScore;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int mScore = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != (char) (i * dimension + j + 1)) {
                    int gap;
                    gap = Math.abs(((int) blocks[i][j] - 1) / dimension - i); //calculate vertical gap
                    //System.out.println("for block i:"+i+", j" + j +", value :"+blocks[i][j]+ ", gap1 = " + gap);
                    gap = gap + Math.abs(((int) blocks[i][j] - 1) % dimension - j); // calculate horizontal gap
                    //System.out.println("for block i:"+i+", j" + j +", value :"+blocks[i][j]+ ", gap2 = " + gap);
                    mScore = mScore + gap;
                }
            }
        }
        return mScore;
    }
    public boolean isGoal()                // is this board the goal board?
    {/*
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (){}
            }
        }*/
 // System.out.println("make goal Board");
      //  this.goal = new Board(goalBlocks);
        /*if (goal == null ) {
            goal = new Board((int[][])goalBlocks);
        }*/
        if (hamming() == 0) {
            return true;
        } else {
            return false;
        }
    }
    public Board twin()                    // a boadr that is obtained by exchanging two adjacent blocks in the same row
    {  
        int[][] twinBlocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                 twinBlocks[i][j] =  blocks[i][j];
            }
        }
        //System.arraycopy(blocks, 0, twinBlocks, 0, blocks.length);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension-1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j+1] != 0) {
                    //int temp = blocks[i][j];
                    twinBlocks[i][j+1] =  blocks[i][j];
                    twinBlocks[i][j] = blocks[i][j+1];
                    
                    //System.out.println("make twin Board");
                    twin = new Board(twinBlocks);
                    //System.out.println(toString());
                    //System.out.println(twin.toString());
                    return twin;
                }
            }
        }
        return twin;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == null) return false;
        if (y.getClass() != Board.class) return false;
        Board yB = (Board) y;
        if (dimension() != yB.dimension()) {
            return false;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if ( blocks[i][j]  != yB.blocks[i][j]  ) {
                    return false;
                }
            }
        }
        return true;
    }
    

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        List<Board> neighbors = new ArrayList<Board>();
        Position east = new Position(blankPosition.x + 1, blankPosition.y);
        Position west = new Position(blankPosition.x - 1, blankPosition.y);
        Position south = new Position(blankPosition.x, blankPosition.y + 1 );
        Position north = new Position(blankPosition.x, blankPosition.y - 1);
        List<Position> pList = new ArrayList<Position>();
        pList.add(east);
        pList.add(west);
        pList.add(south);
        pList.add(north);
        for ( Position p : pList) {
            if ( p.isValid() == true ) {
                int[][] tempBlocks = new int[dimension][dimension];
                for (int i = 0; i < dimension; i++) {
                    for (int j = 0; j < dimension; j++) {
                        tempBlocks[i][j] = blocks[i][j];
                    }
                }
                tempBlocks[blankPosition.x][blankPosition.y] = tempBlocks[p.x][p.y];
                tempBlocks[p.x][p.y] = 0;
                Board n = new Board(tempBlocks);
                neighbors.add(n);
            } else {
                continue;
            }
        }
   
        return neighbors;
    }

    
    
    public String toString()               // string representation of this board (in the output format specified below)
    {
        String string = dimension + "\n";
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                string = string + (int) blocks[i][j] + " ";
            }
            string = string + "\n";
        }
        return string;
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] aBlocks = {{1,2},{3,0}};
        Board a = new Board(aBlocks);
        System.out.print(a.toString());
        System.out.println("isGoal? " + a.isGoal());
        int[][] bBlocks = {{1,  2,  3},{0,  7,  6 },{5,  4,  8}};
        Board b = new Board(bBlocks);
        System.out.println(b.toString());
        System.out.println("isGoal? " + b.isGoal());
        for (Board n : b.neighbors()) {
            System.out.print(n.toString());
        }
        System.out.println(b.manhattan());
        Board t = b.twin();
    }
}
