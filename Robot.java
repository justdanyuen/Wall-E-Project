import processing.core.PImage;

import java.util.List;

public class Robot extends AnimatedObstacle{
    public static final String ROBOT_KEY = "robot";
    public static final int ROBOT_NUM_PROPERTIES = 5;
    public static final int ROBOT_ID = 1;
    public static final int ROBOT_COL = 2;
    public static final int ROBOT_ROW = 3;
    public static final int ROBOT_ANIMATION_PERIOD = 4;

    public Robot(String id, Point position, List<PImage> images, int animationPeriod){
        super(id, position, images, animationPeriod);
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                this.createAnimationAction(0),
                this.getAnimationPeriod());
    }
}
