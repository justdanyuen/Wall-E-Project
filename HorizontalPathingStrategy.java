import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HorizontalPathingStrategy implements PathingStrategy{
    private int offset;
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors){
        List<Point> path = new LinkedList<>();

        Node curNode = new Node(start);

        //if the offset is zero,
        //if the previous ofset was 1, move to the left (-1)
        //else try to move to the right


        //if the offset is negative 1, try moving to the right (back to 0)


        //if the offset is one, try moving to the left ( back to 0)


        List<Point> neighbors = potentialNeighbors.apply(curNode.getPoint())
                .filter(canPassThrough)
                .collect(Collectors.toList());

        return path;
    }
}
