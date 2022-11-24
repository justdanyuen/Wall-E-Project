import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Eve extends ActionEntity{
    private boolean walleReachedEve = false;
    public static final String EVE_KEY = "eve";
    public static final int EVE_NUM_PROPERTIES = 6;
    public static final int EVE_ID = 1;
    public static final int EVE_COL = 2;
    public static final int EVE_ROW = 3;
    public static final int EVE_ANIMATION_PERIOD = 4;
    public static final int EVE_ACTION_PERIOD = 5;
//    public Eve(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
//        super(id, position, images, animationPeriod, actionPeriod);
//    }

    public Eve(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        if (walleReachedEve) {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }
}
