//import java.util.*;
//import java.util.function.BiPredicate;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//class AStarPathingStrategy
//        implements PathingStrategy
//{
//
//    public List<Point> computePath(Point start, Point end,
//                                   Predicate<Point> canPassThrough,
//                                   BiPredicate<Point, Point> withinReach,
//                                   Function<Point, Stream<Point>> potentialNeighbors)
//    {
//        // Open and Closed list data structures
//
//        // list with points for path
//        List<Point> path = new LinkedList<>();
//// Open
//        // - Hash Table is best to find item
//        HashMap<Point, Node> openList = new HashMap();
//
//        // - need a good structure for sorting (min binary heap - sorts as you insert)
//        // sorts with f value (data of node == f)
//        Comparator<Node> compF = Comparator.comparing(Node::getF);
//        PriorityQueue<Node> openListSorted = new PriorityQueue<>(compF);
//
//// Closed
//        HashMap<Point, Node> closedList = new HashMap<>();
//
//        Node startNode = new Node(start);
//        startNode.setG(0); // start g from start == 0
//        Node curNode;
//        Point cur = start;
//
//        double curG = 0;
//
//        curNode = startNode;
//        //boolean found = false;
//
//        while(curNode != null && !withinReach.test(curNode.getPoint(), end)){
//            // analyze all valid adj nodes that are not already in closed list
//            List<Point> neighbors = potentialNeighbors.apply(cur)
//                    .filter(canPassThrough)
//                    .collect(Collectors.toList());
//
//            for(Point p : neighbors){
//                if (!closedList.containsKey(p)){ //
//                    Node n = new Node(p);
//                    if(!openList.containsKey(p)){
//                        //System.out.println("ADDED: " + p.x + ", " + p.y);
//                        // g = distance of cur node to start + distance from cur to adj
//                        curG += computeG(cur, p) + computeG(cur, start);
//
//                        if (n.getG() > curG || n.getG() == 0){
//                            n.setG(curG);
//                        }
//
//                        double h = computeH(end, p);
//                        double f = computeF(n.getG(), h);
//
//                        n.setPrev(curNode);
//                        n.setF(f);
//
//                        // Priority Queue / Min Heap for sorting
//                        openListSorted.add(n);
//                        // HashMap open list (for better finding)
//                        openList.put(p, n);
//                    }
//                }
//            }
//
//            // add cur to closed
//            closedList.put(curNode.getPoint(), curNode);
//            //System.out.println("CURRENT POINT: " + cur.x + ", " + cur.y);
//
//            // remove processed node from open list (move to closed)
//            openList.remove(cur);
//            openListSorted.remove(curNode);
//
//            // change cur node, repeat
//            // sort and get first node
//            if (openListSorted.size() != 0) {
//                // Priority Queue self sorting as insertion/deletion occurs
//                cur = openListSorted.peek().getPoint();
//                //System.out.println("NEW CURRENT POINT: " + cur.x + ", " + cur.y);
//                curNode = openListSorted.peek();
//            }
//            else {
//                curNode = null;
//            }
//
//        }
//
//        while (curNode.getPrev() != null){
//            path.add(0, curNode.getPoint());
//            curNode = curNode.getPrev();
//        }
////
////        if (path.size() != 0) {
////            path.remove(0);
////        }
//        for(Point p : path){
//            System.out.println("x: " + p.x + " y: " + p.y);
//        }
//
//        return path;
//    }
//
//    // MAKE PRIVATE AFTER TESTS
//    private double computeG(Point cur, Point adjacent){
//        // for diagonals
//        if ((cur.x - 1 == adjacent.x)&&(cur.y - 1 == adjacent.y)||
//                (cur.x + 1 == adjacent.x)&&(cur.y + 1 == adjacent.y)||
//                (cur.x - 1 == adjacent.x)&&(cur.y + 1 == adjacent.y)||
//                (cur.x + 1 == adjacent.x)&&(cur.y - 1 == adjacent.y)) {
//            return 1.4;
//        }
//        // for cardinal
//        return 1.0;
//    }
//
//    private double computeH(Point end, Point adjacent){
//        return Math.abs(end.x - adjacent.x) + Math.abs(end.y - adjacent.y);
//    }
//
//    private double computeF(double g, double h){
//        return g + h;
//    }
//}


// REWORKED (not actually working)

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        // Open and Closed list data structures

        // list with points for path
        List<Point> path = new LinkedList<>();
// Open
        // - Hash Table is best to find item
        HashMap<Point, Node> openList = new HashMap();

        // - need a good structure for sorting (min binary heap - sorts as you insert)
        // sorts with f value (data of node == f)
        Comparator<Node> compF = Comparator.comparing(Node::getF);
        PriorityQueue<Node> openListSorted = new PriorityQueue<>(compF);

// Closed
        HashMap<Point, Node> closedList = new HashMap();

        Node curNode = new Node(start);
        curNode.setG(0);
        curNode.setF(computeF(0, computeH(start, end)));

        // add start to open list
        openList.put(start, curNode);
        openListSorted.add(curNode);

        //System.out.println("START POINT: " + curNode.getPoint().x + ", " + curNode.getPoint().y);

        //double f = 0;

        while(openList.size() != 0) { // while there is still a point to be checked
            // if current happens to be next to end, goal reached
            if (withinReach.test(curNode.getPoint(), end) || curNode.getPoint() == end) {
                // build path and return
                while (curNode != null) {
                    path.add(0, curNode.getPoint());
                    curNode = curNode.getPrev();
                }
                // remove start
                path.remove(0);
//                for (Point p : path) {
//                    System.out.println("x: " + p.x + " y: " + p.y);
//                }
                return path;
            }

            //System.out.println("CURRENT POINT: " + curNode.getPoint().x + ", " + curNode.getPoint().y);
            // access points passed as an arg
            List<Point> neighbors = potentialNeighbors.apply(curNode.getPoint())
                    .filter(canPassThrough)
                    .collect(Collectors.toList());

            for (Point p : neighbors) {
                //System.out.println("ADJ POINT: " + p.x + ", " + p.y);
                // analyze all valid adj nodes not in closed list
                if (!closedList.containsKey(p)) {

                    // compute p
                    double g = computeG(curNode.getPoint(), p) + curNode.getG();

                    Node newN = new Node(p);

                    if (!openList.containsKey(p)) { // add to open if not there
                        newN.setPrev(curNode);
                        newN.setG(g);
                        newN.setF(computeF(g, computeH(end, p)));
                        openList.put(p, newN);
                        openListSorted.add(newN);


                    } else { // if in open list, check g and replace if needed
                        // if calc g is better than prev g, replace and
                        if (openList.get(p).getG() > g) {

                            openListSorted.remove(openList.get(p));

                            newN.setG(g);
                            newN.setPrev(curNode);

                            newN.setF(computeF(g, computeH(end, p)));

                            openList.replace(p, newN);
                            openListSorted.add(newN);

                        }
                    }
                }
            }
            // move cur to closed
            closedList.put(curNode.getPoint(), curNode);

            //System.out.println("REMOVING: " + curNode.getPoint());
            if (openListSorted.size() != 0){
                openList.remove(curNode.getPoint());
                curNode = openListSorted.remove();
            }
            else{
                return path;
            }

        }
        // return empty path if goal never found
        return path;
    }

    // MAKE PRIVATE AFTER TESTS
    private double computeG(Point cur, Point adjacent){
        // for diagonals
        if ((cur.x - 1 == adjacent.x)&&(cur.y - 1 == adjacent.y)||
                (cur.x + 1 == adjacent.x)&&(cur.y + 1 == adjacent.y)||
                (cur.x - 1 == adjacent.x)&&(cur.y + 1 == adjacent.y)||
                (cur.x + 1 == adjacent.x)&&(cur.y - 1 == adjacent.y)) {
            return 1.4;
        }
        // for cardinal
        return 1.0;
    }

    private double computeH(Point end, Point adjacent){
        return Math.abs(end.x - adjacent.x) + Math.abs(end.y - adjacent.y);
    }

    private double computeF(double g, double h){
        return g + h;
    }


}