import processing.core.PImage;

import java.util.List;

public class KeyMovedWalle extends Animated{
    public static final String W_KEY = "walle";
    public static final int W_NUM_PROPERTIES = 4;
    public static final int W_ID = 1;
    public static final int W_COL = 2;
    public static final int W_ROW = 3;

    public static final int W_ANIMATION_PERIOD = 6;

    public KeyMovedWalle(String id, Point position, List<PImage> images, int animationPeriod){
        super(id, position, images, animationPeriod);
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {

        scheduler.scheduleEvent(this,
                this.createAnimationAction(0),
                this.getAnimationPeriod());
    }
}
