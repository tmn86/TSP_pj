/* *****************************************************************************
 *
 *  Description: a object API implement two greedy heuristics
 * to find good, but not optimal, solutions to the traveling salesperson problem.
 *
 **************************************************************************** */

public class Tour {
    private Node first; // first node in the circular list

    private class Node {
        private Point p; // a point
        private Node next; // next point

        // constructor for Node
        private Node(Point point) {
            this.p = point;
            this.next = null;
        }

    }

    // creates an empty tour
    public Tour() {
        first = null;
    }

    // creates the 4-point tour a->b->c->d->a (for debugging)
    public Tour(Point a, Point b, Point c, Point d) {
        Node a1 = new Node(a);
        Node b1 = new Node(b);
        Node c1 = new Node(c);
        Node d1 = new Node(d);
        first = a1;
        first.next = b1;
        b1.next = c1;
        c1.next = d1;
        d1.next = first;

    }

    // returns the number of points in this tour
    public int size() {
        if (first == null) return 0;
        // traverse linked list until you find the last node
        Node point = first;
        int total = 0;
        do {
            total++;
            point = point.next;
        } while (point != first);
        return total;
    }

    // returns the length of this tour
    public double length() {
        if (first == null) return 0.0;
        Node point = first;
        double len = 0;
        do {
            len += point.p.distanceTo(point.next.p);
            point = point.next;
        } while (point != first);
        return len;
    }

    // returns a string representation of this tour
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (first == null)
            return "";

        Node point = first;
        do {
            str.append(point.p + "\n"); // build string
            point = point.next; // traverse list
        } while (point != first);
        return str.toString();

    }

    // draws this tour to standard drawing
    public void draw() {
        if (first == null) return;
        Node point = first;
        do {
            point.p.drawTo(point.next.p);
            point = point.next;
        } while (point != first);
    }

    /**
     * inserts p using the nearest neighbor heuristic
     *
     * @param p point to be added to the list
     */
    public void insertNearest(Point p) {
        double d, dMin; // distance and minimun distance variable
        dMin = Double.POSITIVE_INFINITY; // set minimum distance infinity originally
        if (first == null) {
            first = new Node(p);
            first.next = first;
        }
        else {
            Node point = first;
            Node near = null; // new node to get the nearest neighbor with p
            do {
                d = point.p.distanceTo(p);
                if (d < dMin) {
                    dMin = d;
                    near = point;
                }
                point = point.next;
            } while (point != first);
            Node closest = new Node(p);
            // put p after near
            closest.next = near.next;
            near.next = closest;
        }
    }

    /**
     * inserts p using the smallest increase heuristic
     *
     * @param p point to be added to the list
     */
    public void insertSmallest(Point p) {
        // distance added, minumun length and original length variables
        double addDistance, lenMin, len;
        len = length();
        lenMin = Double.POSITIVE_INFINITY; // minimun length is infinity originally
        if (first == null) {
            first = new Node(p);
            first.next = first;
        }
        else {
            Node point = first;
            Node near = null; // new node to get the neighbor satisfy minimum length
            do {
                // total distance would be added if p link after a point
                addDistance = point.p.distanceTo(p) + point.next.p.distanceTo(p);
                // distance that would be remove after add p after that point
                double subtractDistance = point.p.distanceTo(point.next.p);
                // new total length updated after p was added
                double totallen = len - subtractDistance + addDistance;

                if (totallen < lenMin) {
                    lenMin = totallen;
                    near = point;
                }
                point = point.next;
            } while (point != first);

            Node closest = new Node(p);
            // put p after near
            closest.next = near.next;
            near.next = closest;
        }
    }

    // Test for different methods
    public static void main(String[] args) {
        // define 4 points that are the corners of a square
        Point a = new Point(100.0, 100.0);
        Point b = new Point(500.0, 100.0);
        Point c = new Point(500.0, 500.0);
        Point d = new Point(100.0, 500.0);
        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);
        // print the size to standard output
        int s = squareTour.size();
        StdOut.println("Number of points = " + s);
        // print the tour length to standard output
        double length = squareTour.length();
        StdOut.println("Tour length = " + length);
        // print the tour to standard output
        StdOut.println(squareTour);
        Tour newTour = new Tour();
        newTour.insertSmallest(new Point(110.0, 225.0));
        newTour.insertSmallest(new Point(283.0, 379.0));
        newTour.insertSmallest(new Point(306.0, 360.0));
        newTour.insertSmallest(new Point(343.0, 110.0));
        newTour.insertSmallest(new Point(552.0, 199.0));
        newTour.insertSmallest(new Point(490.0, 285.0));
        newTour.insertSmallest(new Point(397.0, 566.0));
        newTour.insertSmallest(new Point(325.0, 554.0));
        newTour.insertSmallest(new Point(157.0, 443.0));
        newTour.insertSmallest(new Point(161.0, 280.0));
        int size = newTour.size();
        StdOut.println("Number of points = " + size);
        double leng = newTour.length();
        StdOut.println("Tour length = " + leng);
    }
}
