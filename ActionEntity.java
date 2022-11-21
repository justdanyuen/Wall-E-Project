import processing.core.PImage;

import java.util.List;

public abstract class ActionEntity extends Animated{
    private int actionPeriod;
    public ActionEntity(String id,
                    Point position,
                    List<PImage> images, int animationPeriod, int actionPeriod){
        super(id, position, images, animationPeriod);
        this.actionPeriod = actionPeriod;
    }
    public int getActionPeriod() {
        return this.actionPeriod;
    }
    abstract void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler);

    public Activity createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Activity(this, world, imageStore);
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {

            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    this.getActionPeriod());
            scheduler.scheduleEvent(this,
                    this.createAnimationAction(0),
                    this.getAnimationPeriod());
    }
}
