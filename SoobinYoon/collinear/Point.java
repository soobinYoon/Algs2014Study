import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();;       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

        // compare points according to their x-coordinate
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p, Point q) {
            Point point0 = new Point(x, y);
            if (point0.slopeTo(p) < point0.slopeTo(q)) return -1;
            if (point0.slopeTo(p) > point0.slopeTo(q)) return +1;
            return 0;
        }
    }
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        int deltaY = that.y - this.y;
        int deltaX = that.x - this.x;
       // System.out.println("dX : " + deltaX + ", dY : " + deltaY);
        if (deltaX == 0 ) {
            if (deltaY == 0) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        } else if (deltaY == 0) {
            return +0.0;
        }
        double slope = (double)deltaY / (double)deltaX;
        //System.out.println("slope : " + slope);
        return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y == that.y) {
            if (this.x > that.x) {
                return 1;
            } else if (this.x < that.x) {
                return -1;
            } else {
                return 0;
            }
        } else if (this.y > that.y) {
            return 1;
        } else { // (this.y < that.y)
            return -1;
        }
        //return this.y - that.y;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}