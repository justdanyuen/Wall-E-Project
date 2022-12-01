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


        List<Point> neighbors = potentialNeighbors.apply(start) // use start
                .collect(Collectors.toList());

        return neighbors; // return neighbors
    }
}