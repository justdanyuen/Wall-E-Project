import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Soil extends ActionEntity{
    public static final String SOIL_KEY = "soil";
    public static final int SOIL_NUM_PROPERTIES = 4;
    public static final int SOIL_ID = 1;
    public static final int SOIL_COL = 2;
    public static final int SOIL_ROW = 3;
    public static final int SOIL_ANIMATION_PERIOD = 4;

    public static final int SOIL_ACTION_PERIOD = 5;

    private boolean walleReachedEve = false;
    private PathingStrategy pathingStrategy = new Stationary();

    public Soil(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
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
        Optional<Entity> eveTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Walle.class)));

        if (eveTarget.isPresent()) {
            this.moveTo(world, eveTarget.get(), scheduler);
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
    }
    // SCENE 3 - Eve uses AStar to follow Walle
}
