import processing.core.PImage;

import java.util.List;

public abstract class Robot extends ActionEntity implements Moveable{
    public static final String ROBOT_KEY = "robot";
    public static final int ROBOT_NUM_PROPERTIES = 6;
    public static final int ROBOT_ID = 1;
    public static final int ROBOT_COL = 2;
    public static final int ROBOT_ROW = 3;
    public static final int ROBOT_ANIMATION_PERIOD = 4;
    public static final int ROBOT_ACTION_PERIOD = 5;



    public Robot(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
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

    void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler){

        this.moveTo(world, null, scheduler);

    }

}