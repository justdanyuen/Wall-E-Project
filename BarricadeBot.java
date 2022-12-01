import processing.core.PImage;

import java.util.List;

public class BarricadeBot extends Robot{
    public static final String BARRICADE_ROBOT_KEY = "barricade_robot";
    private List<Point> left;
    private boolean movingLeft = false;
    private List<Point> right;
    private boolean movingRight = false;
    private PathingStrategy pathingStrategy = new HorizontalPathingStrategy();
    public BarricadeBot(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public boolean moveTo(WorldModel world, Entity entity, EventScheduler scheduler){
        int count = 0;
        while (count < 3){
            movingLeft = true;
            this.setPosition(this.nextPosition(world, null));
            count++;

        }
        count = 0;
        movingLeft = false;

        while (count < 3){
            movingRight = true;
            this.setPosition(this.nextPosition(world, null));
        }

        movingLeft = true;
        movingRight = false;
        return true;
    }

    public Point nextPosition(WorldModel world, Point destPos){
        left = pathingStrategy.computePath(this.getPosition(), null, null, null, PathingStrategy.LEFT_NEIGHBOR);
        right = pathingStrategy.computePath(this.getPosition(), null, null, null, PathingStrategy.RIGHT_NEIGHBOR);

        if (movingLeft){
            return left.get(0);
        }
        else if(movingRight){
            return right.get(0);
        }
        return null;
    }

    void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler){

        this.moveTo(world, null, scheduler);

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());

    }
}