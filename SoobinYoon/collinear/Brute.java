import java.util.Arrays;
public class Brute {
    
    public static void main(String[] args){
        //Brute brute = new Brute();
        //int N = StdIn.readInt();
        //brute.setN(StdIn.readInt());  // get number of input points
        //Point[] points = new Point[N];  // holds all input points
      
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] point = new Point[N];

        for (int i = 0; i < N; i++) {
            //System.out.println("point "+i );
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            point[i] = p;
            p.draw();
        }
        Arrays.sort(point);
        StdDraw.show(0);// display to screen all at once
        StdDraw.setPenRadius();// reset the pen radius
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.002);
        for (int a = 0; a < N; a++){
            for (int b = a+1; b < N; b++){
                if (b == a) 
                    continue;
                for (int c = b+1; c < N; c++){
                    if (c == a || c == b)
                        continue;
                    double slope1 = point[a].slopeTo(point[b]);
                    double slope2 = point[b].slopeTo(point[c]);
                    if (slope1 != slope2)
                        continue;
                    for (int d = c+1; d < N; d++){
                        if (d == a || d == b || d == c)
                            continue;
                        
                        //double slope1 = point[a].slopeTo(point[b]);
                        //double slope2 = point[b].slopeTo(point[c]);
                        double slope3 = point[c].slopeTo(point[d]);
                        //double slope4 = point[d].slopeTo(point[a]);
                        if ((slope1 == slope2) && (slope2 == slope3)) {
                            //System.out.println("slope1 : "+slope1);
                            //System.out.println("slope2 : "+slope2);
                            //System.out.println("slope3 : "+slope3);
                            //System.out.println("- - - - - - - - - -");
                            
                            System.out.print(point[a].toString() );
                            System.out.print(" -> ");
                            System.out.print(point[b].toString() );
                            System.out.print(" -> ");
                            System.out.print(point[c].toString() );
                            System.out.print(" -> ");
                            System.out.println(point[d].toString() );
                            
                            point[a].drawTo(point[d]);
                            //point[b].drawTo(point[c]);
                            //point[c].drawTo(point[d]);
                        }
                    }
                }
            }
        }
        
        StdDraw.show(0);// display to screen all at once
        StdDraw.setPenRadius();// reset the pen radius

    }
}
