import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Auto extends ActionEntity implements Moveable{

    public static final String AUTO_KEY = "auto";
    public static final int AUTO_NUM_PROPERTIES = 6;
    public static final int AUTO_ID = 1;
    public static final int AUTO_COL = 2;
    public static final int AUTO_ROW = 3;
    public static final int AUTO_ANIMATION_PERIOD = 4;
    public static final int AUTO_ACTION_PERIOD = 5;

    //public static final AStarPathingStrategy a = new AStarPathingStrategy();

    private PathingStrategy pathingStrategy = new AStarPathingStrategy();

    public Auto(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public void setPathingStrategy(PathingStrategy p){
        this.pathingStrategy = p;
    }


    public Point nextPosition(WorldModel world, Point destPos)
    {
        List<Point> path = pathingStrategy.computePath(this.getPosition(),
                destPos,
                p -> !world.isOccupied(p), // condition for obstacle
                (p1, p2) -> PathingStrategy.neighbors(p1,p2),
                pathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);

        if (path.size() != 0){
            return path.get(0);
        }
        return this.getPosition();
    }

    public boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {

        Point nextPos = this.nextPosition(world, target.getPosition());

        world.moveEntity(this, nextPos);

        return false;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> autoTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Walle.class)));

        if (autoTarget.isPresent()) {
            this.moveTo(world, autoTarget.get(), scheduler);
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
    }
}
