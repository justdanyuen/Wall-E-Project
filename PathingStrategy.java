

import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.List;

interface PathingStrategy
{
   /*
    * Returns a prefix of a path from the start point to a point within reach
    * of the end point.  This path is only valid ("clear") when returned, but
    * may be invalidated by movement of other entities.
    *
    * The prefix includes neither the start point nor the end point.
    */
   List<Point> computePath(Point start, Point end,
                           Predicate<Point> canPassThrough,
                           BiPredicate<Point, Point> withinReach,
                           Function<Point, Stream<Point>> potentialNeighbors);

   static final Function<Point, Stream<Point>> CARDINAL_NEIGHBORS =
           point ->
                   Stream.<Point>builder()
                           .add(new Point(point.x, point.y - 1))
                           .add(new Point(point.x, point.y + 1))
                           .add(new Point(point.x - 1, point.y))
                           .add(new Point(point.x + 1, point.y))
                           .build();
   public static final Function<Point, Stream<Point>> DIAGONAL_NEIGHBORS =
           point ->
                   Stream.<Point>builder()
                           .add(new Point(point.x - 1, point.y - 1))
                           .add(new Point(point.x + 1, point.y + 1))
                           .add(new Point(point.x - 1, point.y + 1))
                           .add(new Point(point.x + 1, point.y - 1))
                           .build();

   public static final Function<Point, Stream<Point>> DIAGONAL_CARDINAL_NEIGHBORS =
           point ->
                   Stream.<Point>builder()
                           .add(new Point(point.x - 1, point.y - 1))
                           .add(new Point(point.x + 1, point.y + 1))
                           .add(new Point(point.x - 1, point.y + 1))
                           .add(new Point(point.x + 1, point.y - 1))
                           .add(new Point(point.x, point.y - 1))
                           .add(new Point(point.x, point.y + 1))
                           .add(new Point(point.x - 1, point.y))
                           .add(new Point(point.x + 1, point.y))
                           .build();

   public static boolean withinBounds(Point p)
   {
      return p.y >= 0 && p.y < 14 &&
              p.x >= 0 && p.x < 19;
   }

   public static boolean neighbors(Point p1, Point p2)
   {
      return p1.x+1 == p2.x && p1.y == p2.y ||
              p1.x-1 == p2.x && p1.y == p2.y ||
              p1.x == p2.x && p1.y+1 == p2.y ||
              p1.x == p2.x && p1.y-1 == p2.y;

      //added neighbor testing for diagonals
//             p1.x-1 == p2.x && p1.y-1 == p2.y ||
//             p1.x+1 == p2.x && p1.y+1 == p2.y ||
//             p1.x+1 == p2.x && p1.y-1 == p2.y ||
//             p1.x-1 == p2.x && p1.y+1 == p2.y;
   }
}