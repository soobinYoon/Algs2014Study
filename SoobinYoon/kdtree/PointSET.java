import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class PointSET { //represents a set of points in the unit square.
    private Set<Point2D> realSet;
    private int setAmount;
    public PointSET() {                              // construct an empty set of points 
        System.out.println("make PointSET");
        realSet = new TreeSet<Point2D>();
        setAmount = 0;
    }
    public boolean isEmpty() {                     // is the set empty? 
        if (size() == 0) {
            return true;
        } else {
            return false;
        }
    }
    public int size() {                         // number of points in the set 
        return setAmount;
    }
    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new NullPointerException();
        }
        if ( contains(p) == true ) return;
        setAmount++;
        realSet.add(p);
    }
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) {
            throw new NullPointerException();
        }
        return realSet.contains(p);
    }
    public void draw() {                        // draw all points to standard draw 
        //System.out.println("PointSET// draw() ");
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        //StdDraw.point(x, y);
        for(Point2D p : realSet) {
            StdDraw.point(p.x(), p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        if (rect == null) {
            throw new NullPointerException();
        }
        ArrayList<Point2D> array = new ArrayList<Point2D>();
        for(Point2D p : realSet) {
            if ( p.x() > rect.xmin() && p.x() < rect.xmax() && p.y() > rect.ymin() && p.y() < rect.ymax() ) {
                array.add(p);
            }
        }
        return array;
    }
    public Point2D nearest(Point2D queryP) {            // a nearest neighbor in the set to point p; null if the set is empty 
       // System.out.println("PointSET// nearest() ");
        if (queryP == null) {
           // System.out.println("PointSET// nearest() -- query Point is null");
            throw new NullPointerException();
        }
        
        if ( size() == 0 ) {
            //System.out.println("PointSET// nearest() -- set size() is 0 ");
            return null;
        } else {
            Point2D NN = null;
            double distanceNN = -1;
            for ( Point2D point : realSet ) {
                if ( distanceNN == -1 || queryP.distanceSquaredTo( point ) < distanceNN ) {
                    distanceNN = queryP.distanceSquaredTo( point );
                    NN = point;
                }
            }
            //System.out.println("PointSET// nearest() -- return NN");
            return NN;
        }
    }
    
    public static void main(String[] args) {      // unit testing of the methods (optional) 
    }             
}