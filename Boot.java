import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Boot extends ActionEntity implements Moveable{
    public static final String BOOT_KEY = "boot";
    public static final int BOOT_NUM_PROPERTIES = 7;
    public static final int BOOT_ID = 1;
    public static final int BOOT_COL = 2;
    public static final int BOOT_ROW = 3;
    public static final int BOOT_ANIMATION_PERIOD = 4;

    public static final int BOOT_ACTION_PERIOD = 5;
    public static final int BOOT_HEALTH = 1;
    private int health;
    private PathingStrategy pathingStrategy = new Stationary();


    public Boot(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod, int health){
        super(id, position, images, animationPeriod, actionPeriod);
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void updateHealth(int n) {
        this.health += n;
    }

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (health <= 0) {
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            System.out.println("BOOT REMOVED");
            return true;
        }
        return false;
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
        Optional<Entity> eveTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Walle.class)));

        if (eveTarget.isPresent()) {
            this.moveTo(world, eveTarget.get(), scheduler);
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
    }

}
