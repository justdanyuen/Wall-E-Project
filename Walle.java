import processing.core.PImage;

import java.util.List;

public class Walle extends Animated{
    public static final String WALLE_KEY = "walle";
    public static final int WALLE_NUM_PROPERTIES = 5;
    public static final int WALLE_ID = 1;
    public static final int WALLE_COL = 2;
    public static final int WALLE_ROW = 3;
    public static final int WALLE_ANIMATION_PERIOD = 4;



    public Walle(String id, Point position, List<PImage> images, int animationPeriod){
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
