import processing.core.PImage;

import java.util.List;

public class Trash extends Animated{
    public static final String TRASH_KEY = "trash";
    public static final int TRASH_NUM_PROPERTIES = 4;
    public static final int TRASH_ID = 1;
    public static final int TRASH_COL = 2;
    public static final int TRASH_ROW = 3;
    public static final int TRASH_ANIMATION_PERIOD = 4;
    public Trash(String id, Point position, List<PImage> images, int animationPeriod){
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
