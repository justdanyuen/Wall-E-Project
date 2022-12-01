import processing.core.PImage;

import java.util.List;

public class VacuumBot extends Robot{
    public static final String VACUUM_ROBOT_KEY = "vacuum_robot";
    private List<Point> left;
    private boolean movingLeft = false;
    private List<Point> right;
    private boolean movingRight = false;

    private PathingStrategy pathingStrategy = new HorizontalPathingStrategy();

    public VacuumBot(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public boolean moveTo(WorldModel world, Entity entity, EventScheduler scheduler){
        //int count = 0;
        //while (count < 3){
            movingLeft = true;
            world.moveEntity(this, this.nextPosition(world, null));
            //count++;
        //}

//        count = 0;
//        movingLeft = false;
//
//        while (count < 3){
//            movingRight = true;
//            world.moveEntity(this, this.nextPosition(world, null));
//            count++;
//        }

        //movingLeft = true;
        //movingRight = false;
        return true;
    }

    public Point nextPosition(WorldModel world, Point destPos){
        left = pathingStrategy.computePath(this.getPosition(), null, null, null, PathingStrategy.LEFT_NEIGHBOR);
        right = pathingStrategy.computePath(this.getPosition(), null, null, null, PathingStrategy.RIGHT_NEIGHBOR);

        if (left.size() != 0){
            return left.get(0);
        }
        return this.getPosition();
//        else if(movingRight){
//            return right.get(0);
//        }
//        return null;
    }


}
