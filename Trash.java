import processing.core.PImage;

import java.util.List;

public class Trash extends ActionEntity{
    public static final String TRASH_KEY = "trash";
    public static final int TRASH_NUM_PROPERTIES = 4;
    public static final int TRASH_ID = 1;
    public static final int TRASH_COL = 2;
    public static final int TRASH_ROW = 3;
    public static final int TRASH_ANIMATION_PERIOD = 4;

    public static final int TRASH_ACTION_PERIOD = 5;
    public static final int TRASH_HEALTH = 1;

    public Trash(String id, Point position, List<PImage> images, int animationPeriod, int health){
        super(id, position, images, animationPeriod, health);
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

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (TRASH_HEALTH <= 0) {
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            return true;
        }

        return false;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {

        if (!this.transform(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }
}
