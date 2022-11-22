import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Fairy extends ActionEntity implements Moveable{
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000;

    public static final String FAIRY_KEY = "fairy";
    public static final int FAIRY_NUM_PROPERTIES = 6;
    public static final int FAIRY_ID = 1;
    public static final int FAIRY_COL = 2;
    public static final int FAIRY_ROW = 3;
    public static final int FAIRY_ANIMATION_PERIOD = 4;
    public static final int FAIRY_ACTION_PERIOD = 5;

    public static final AStarPathingStrategy a = new AStarPathingStrategy();
    public Fairy(String id,
                    Point position,
                    List<PImage> images, int animationPeriod, int actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);

    }
    public Point nextPosition(WorldModel world, Point destPos)
    {
        List<Point> path = a.computePath(this.getPosition(),
                                            destPos,
                                            p -> !world.isOccupied(p), // condition for obstacle
                                            (p1, p2) -> AStarPathingStrategy.neighbors(p1,p2),
                                            AStarPathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);

//        int horiz = Integer.signum(destPos.x - this.getPosition().x);
//        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.y - this.getPosition().y);
//            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.getPosition();
//            }
//        }
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
//        if (this.getPosition().adjacent(target.getPosition())) {
//            //world.removeEntity(target);
//            //scheduler.unscheduleAllEvents(target);
//            return true;
//        }
//        else {
            Point nextPos = this.nextPosition(world, target.getPosition());

//            if (!this.getPosition().equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
////                if (occupant.isPresent()) {
////                    scheduler.unscheduleAllEvents(occupant.get());
////                }

            world.moveEntity(this, nextPos);
            //}
            return false;
       // }
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fairyTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Dude.class)));

        if (fairyTarget.isPresent()) {
            //Point tgtPos = fairyTarget.get().getPosition();
            this.moveTo(world, fairyTarget.get(), scheduler);

//            if (this.moveTo(world, fairyTarget.get(), scheduler)) {
//                Sapling sapling = new Sapling("sapling_" + this.getId(), tgtPos,
//                        imageStore.getImageList(world.SAPLING_KEY), Sapling.SAPLING_ACTION_ANIMATION_PERIOD, Sapling.SAPLING_ACTION_ANIMATION_PERIOD, 0, Sapling.SAPLING_HEALTH_LIMIT);
//
//                world.addEntity(sapling);
//                sapling.scheduleActions(scheduler, world, imageStore);
//            }
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
    }
}
