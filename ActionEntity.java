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

    public boolean isAtItem(Point item){
        Point p1 = new Point(item.x - 1, item.y);
        Point p2 = new Point(item.x + 1, item.y);
        Point p3 = new Point(item.x, item.y - 1);
        Point p4 = new Point(item.x, item.y + 1);
        if (this.getPosition().equals(p1) ||
                this.getPosition().equals(p2) ||
                this.getPosition().equals(p3) ||
                this.getPosition().equals(p4))
        {
            //score++;
            return true;
        }
        return false;
    }
}
