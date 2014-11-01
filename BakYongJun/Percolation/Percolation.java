/*************************************************************************
 *  Compilation:  javac-algs4 Precolation.java
 *  Execution:    java-algs4 Precolation
 *  Dependencies: WeightedQuickUnionUF
 *  
 *  Percolation data type
 * 
 *************************************************************************/

//WeightedQuickUnionUF 
//connected
//count
//find
//union

public class Percolation {
    private static boolean OPEN = true;
    private static boolean BLOCKED = false;
    private int gridSize = 0;    
    private WeightedQuickUnionUF uf;
    private boolean[][] grid;
        
    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N should > 0"); //input validation
        }
        
        gridSize = N;
        uf = new WeightedQuickUnionUF(gridSize*gridSize);
        grid = new boolean[gridSize][gridSize];        
//        System.out.println( grid[gridSize-1][0]);
    }
    private void checkIndices(int i, int j) {        
        if (i < 1 || i > gridSize) 
            throw new IndexOutOfBoundsException("row index i out of bounds");        
        if (j < 1 || j > gridSize) 
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }
    
    private int xyTo1D(int i, int j) {
         if (gridSize <= 0) {
            throw new IllegalArgumentException("N should > 0"); //input validation
        }
        checkIndices(i, j);        
        return gridSize * (i - 1) + (j - 1);  //i >= 1, j >= 1 in convention
    }
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {          
        //1. validate indices
        checkIndices(i, j);
        
        //2.mark the site as open
        if (!isOpen(i, j)) {
            grid[i-1][j-1] = OPEN;
        }
//        StdOut.println("open["+i+"]["+j+"] ?>>"+ grid[i][j]);
        
        //3.perform some sequence of WeightedQuickUnionUF operations 
//        that links the site in question to its open neighbors. 
        
        //upper neighbor 
        if (j > 1) {
            if (isOpen(i, j-1)) {
                if (!uf.connected(xyTo1D(i , j), xyTo1D(i , j-1))) 
                     uf.union(xyTo1D(i , j), xyTo1D(i , j-1));
            }
        }
           
        //down neighbor
        if (j < gridSize) {
            if (isOpen(i, j+1)) {
                if (!uf.connected(xyTo1D(i, j), xyTo1D(i, j+1))) 
                     uf.union(xyTo1D(i, j), xyTo1D(i, j+1));
            }
        }
        
        //left neighbor
        if (i > 1) {
            if (isOpen(i-1, j)) {
                if (!uf.connected(xyTo1D(i , j), xyTo1D(i-1 , j))) 
                     uf.union(xyTo1D(i , j), xyTo1D(i-1 , j));
            }
        }
        
        //right neighbor
        if (i < gridSize) {
            if (isOpen(i+1, j)) {
                if (!uf.connected(xyTo1D(i , j), xyTo1D(i+1 , j))) 
                     uf.union(xyTo1D(i , j), xyTo1D(i+1 , j));
            }
        }
    }
    
    public boolean isOpen(int i, int j) {     // is site (row i, column j) open?
         //These should be very simple methods.
        checkIndices(i, j);
//        StdOut.println("isOpen["+i+"]["+j+"] ?>>"+ grid[i][j]);
        return grid[i-1][j-1];               
    }
    
    public boolean isFull(int i, int j) {     // is site (row i, column j) full?
        //These should be very simple methods.
        checkIndices(i, j);
        
        for (int topCol = 1; topCol <= gridSize; topCol++) {
            if (isOpen(1, topCol) 
                    && uf.connected(xyTo1D(1, topCol) , xyTo1D(i , j)))
                   return true;               
        }
        return false;
    }
    
    public boolean percolates() {             // does the system percolate?
        //These should be very simple methods.
        for (int botCol = 1; botCol <= gridSize; botCol++) {
            if (isOpen(gridSize, botCol) && isFull(gridSize, botCol))
                return true;
        }
        return false;
    }
        
    

    public static void main(String[] args) {   // test client (optional)        
    }
    
}