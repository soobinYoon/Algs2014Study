import java.util.Arrays;

public class Fast {

    public static void main(String[] args){
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
        for (int i = 0; i<N; i++){
           // System.out.println( point[i].x + ", "+point[i].y );
        }
        for (int a = 0; a < N-1; a++) {
            Arrays.sort(point);
            Arrays.sort(point, a+1, N, point[a].SLOPE_ORDER);
            for (int i = 0; i<N; i++){
                //System.out.println( point[i].x + ", "+point[i].y );
            }
            for (int i = a+1; i<N; i++){
               // System.out.println( "slope:"+point[a].slopeTo(point[i]) );
            }
            double tempSlope;
            tempSlope = point[a].slopeTo(point[a+1]);
            //System.out.println("slope changed to.." + tempSlope);
            int id = a+1;
            int count = 0;
            while (id <= N) {

               /* if (tempSlope == point[a].slopeTo(point[id])) {
                    //System.out.println("go on..");
                    //continue;
                } */
                if (id == N || tempSlope != point[a].slopeTo(point[id]) ) {
                    
                    if (id == N) {
                        //id++;
                        //System.out.println("last element");
                    } else {
                        tempSlope = point[a].slopeTo(point[id]);
                        //System.out.println("slope changed to.." + tempSlope);
                    }                    
                    //System.out.println("count//.." + count);
                    if (count > 2) {
                       // System.out.print("find match : ");     
                        //Arrays.sort(point, id - count, id);
                        point[a].drawTo(point[id-1]);
                        
                        System.out.print(point[a].toString() );
                        for (int i = id - count ; i <= id-1 ; i++) {
                            //point[a].drawTo(point[i]);
                            StdDraw.show(0);
                            System.out.print(" -> ");
                            System.out.print(point[i].toString());
                        }
                        System.out.print("\n");
                    } 
                    count = 0;
                }
                id++;
                count++;
            }
        }
        //System.out.println("end");
        StdDraw.show(0);// display to screen all at once
        StdDraw.setPenRadius();// reset the pen radius
    }
}