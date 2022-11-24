import processing.core.PImage;

import java.util.List;

public class AnimatedObstacle extends Animated{
    public AnimatedObstacle(String id, Point position, List<PImage> images, int animationPeriod){
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
