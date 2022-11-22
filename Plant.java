import processing.core.PImage;

import java.util.List;

public abstract class Plant extends ActionEntity{
    public static final String STUMP_KEY = "stump";
    private int health;

    public Plant(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod, int health){
        super(id, position, images, animationPeriod, actionPeriod);
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void updateHealth(int n) {
        this.health += n;
    }
    abstract boolean transform(WorldModel world,EventScheduler scheduler,ImageStore imageStore);
}
