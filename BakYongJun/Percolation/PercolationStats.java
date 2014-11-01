public class PercolationStats {
    
    private int gridSize = 0;  
    private int testTimes = 0;  
    private Percolation perc;
    private double mu = 0.0;
    private double omega = 0.0;
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {     
        //input validation
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should > 0"); 
        }
        
        gridSize = N;
        testTimes = T;
        double[] xt = new double[T];
        double sumXt = 0;
        int totalSites = gridSize*gridSize;
        for (int i = 0; i < T; i++) {
            perc = new Percolation(gridSize);
            ///(gridSize*gridSize);//opened sites/(gridSize*gridSize)
            xt[i] = monteCarloSimulation()/(double) totalSites;
//            StdOut.println("xt["+i+"]:"+ xt[i]);
//            StdOut.println("xt[i]/(double)totalSites:"+ xt[i]/(double)totalSites);
//            sumXt += xt[i];            
        }
        
//        mu = (double)sumXt / (double)T;
//        StdOut.println("mu1:"+mu);
        mu = StdStats.mean(xt);
//        StdOut.println("mu2:"+mu);
//        
//        double tmpSum = 0.0;
//        for(int i = 0; i < T; i++) {            
//            tmpSum += Math.pow((xt[i]-mu), 2);
//        }
//        omega = Math.sqrt(tmpSum/(T-1));
//        StdOut.println("omega1:"+omega);
        omega = StdStats.stddev(xt);
//        StdOut.println("omega2:"+omega);
    }
    
    // sample mean of percolation threshold        
    public double mean() {                      
        return (confidenceLo()+confidenceHi())/2;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {                    
        return omega;
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {             
        return (mu-1.96*omega/Math.sqrt(testTimes));
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {           
        return (mu+1.96*omega/Math.sqrt(testTimes));
    }
    
    private int monteCarloSimulation() {
        // draw N-by-N grid
        int opened = 0;
        
        while (true) {            
            int row = StdRandom.uniform(1, gridSize+1);
            int col = StdRandom.uniform(1, gridSize+1);
            //StdOut.println("row:"+row);
            //StdOut.println("col:"+col);
            if (!perc.isOpen(row, col)) {
                perc.open(row, col);
                if (perc.isFull(row, col)) {
                    opened++;
                    //StdOut.println("perc.percolates():"+perc.percolates());
                    if (perc.percolates()) 
                        return opened;
                }
                else if (perc.isOpen(row, col)) {
                    opened++;
                }    
            }
        }        
    }

    public static void main(String[] args) {    // test client (described below)
        //performs T independent computational experiments on an N-by-N grid
        StdRandom.setSeed((long) StdRandom.uniform());
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        
        PercolationStats test = new PercolationStats(n, t);
        StdOut.println("mean                    = "+ test.mean());
        StdOut.println("stddev                  = "+ test.stddev());
        StdOut.println("95% confidence interval = "+ test.confidenceLo() + ", "
            + test.confidenceHi());
    }
}