import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class KdTree {
    private Set<Point2D> realSet;
    private int setAmount;
    private Node root;
    
    public KdTree() {                              // construct an empty set of points 
        realSet = new TreeSet<Point2D>();
        setAmount = 0;
    }
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        //private String type;
        private boolean isVertical;
        //private Node parent;
        
        public Node(Point2D p, boolean isV) {
            this.p = p;
            isVertical = isV;
        }
        public void addRT(Point2D p) {
            rt = new Node(p, !isVertical);
        }
        public void addLB(Point2D p) {
            lb = new Node(p, !isVertical);
        }
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
    public void insert(Point2D queryP) {             // add the point to the set (if it is not already in the set)
        if (queryP == null) {
            throw new NullPointerException();
        }
        if ( contains(queryP) == true ) return;
        if ( size() == 0 ) {
            root = new Node(queryP, true);
        } else {
            Node currentN = root;
            while ( currentN != null) {
                Node nextN = null;
                if (currentN.isVertical == true && currentN.p.x() < queryP.x() ) {
                    nextN = currentN.lb;
                    if (nextN == null) { 
                        setAmount++;
                        currentN.addLB(queryP);
                        return;
                    }
                } else if (currentN.isVertical == true && currentN.p.x() >= queryP.x() ) {
                    nextN = currentN.rt;
                    if (nextN == null) { 
                        setAmount++;
                        currentN.addRT(queryP);
                        return;
                    }
                } else if (currentN.isVertical == false && currentN.p.y() < queryP.y() ) {
                    nextN = currentN.lb;
                    if (nextN == null) { 
                        setAmount++;
                        currentN.addLB(queryP);
                        return;
                    }
                } else if (currentN.isVertical == false && currentN.p.y() >= queryP.y() ) {
                    nextN = currentN.rt;
                    if (nextN == null) { 
                        setAmount++;
                        currentN.addRT(queryP);
                        return;
                    }
                }
                
                currentN = nextN;    
            }
        //currentN = new Node(queryP,true );
        }
        //setAmount++;
        //realSet.add(p);
    }
    public boolean contains(Point2D queryP) {           // does the set contain point p? 
        if (queryP == null) {
            throw new NullPointerException();
        }
        Node currentN = root;
        while ( currentN != null) {
            if ( currentN.p.x() == queryP.x() && currentN.p.y() == queryP.y()) {
                return true;
            } else {
                if (currentN.isVertical == true && currentN.p.x() < queryP.x() ) {
                    currentN = currentN.lb;
                } else if (currentN.isVertical == true && currentN.p.x() >= queryP.x() ) {
                    currentN = currentN.rt;
                } else if (currentN.isVertical == false && currentN.p.y() < queryP.y() ) {
                    currentN = currentN.lb;
                } else if (currentN.isVertical == false && currentN.p.y() >= queryP.y() ) {
                    currentN = currentN.rt;
                }
            }
       
        }
        
        //return realSet.contains(p);
        return false;
    }
    public void draw() {                        // draw all points to standard draw 
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
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
        if (queryP == null) {
            throw new NullPointerException();
        }
        
        if ( size() == 0 ) {
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
            return NN;
        }
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
          //       read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] point = new Point[N];
        
        for (int i = 0; i < N; i++) {
            System.out.println("point "+i );
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            point[i] = p;
            p.draw();
        }
    }
}