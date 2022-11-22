import processing.core.PImage;

import java.util.List;

public abstract class Animated extends Entity{

    private int animationPeriod;

    public Animated(String id,
                    Point position,
                    List<PImage> images, int animationPeriod){
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public Animation createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }
    // only animated entities use nextImage

    public void nextImage() {
        this.setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    } // no house or stump

    abstract void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore);

}
