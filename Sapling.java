import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Sapling extends Plant{
    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_ID = 1;
    public static final int SAPLING_COL = 2;
    public static final int SAPLING_ROW = 3;
    public static final int SAPLING_HEALTH = 4;
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_NUM_PROPERTIES = 4;

    public static final int TREE_ANIMATION_MAX = 600;
    public static final int TREE_ANIMATION_MIN = 50;
    public static final int TREE_ACTION_MAX = 1400;
    public static final int TREE_ACTION_MIN = 1000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;

    private int healthLimit;

    public Sapling(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod, int health, int healthLimit){
        super(id, position, images, animationPeriod, actionPeriod, health);
        this.healthLimit = healthLimit;
    }

    private static int getNumFromRange(int max, int min)
    {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);
    }

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (super.getHealth() <= 0) {
            Stump stump = new Stump (this.getId(),
                    this.getPosition(),
                    imageStore.getImageList(STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }
        else if (super.getHealth() >= this.healthLimit)
        {
            Tree tree = new Tree("tree_" + this.getId(),
                    this.getPosition(),
                    imageStore.getImageList(world.TREE_KEY),
                    getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
                    getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
                    getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN)
                    );

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        this.updateHealth(1);
        if (!this.transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }
}
