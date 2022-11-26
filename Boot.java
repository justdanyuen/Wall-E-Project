import processing.core.PImage;

import java.util.List;

public class Boot extends ActionEntity{
    public static final String BOOT_KEY = "boot";
    public static final int BOOT_NUM_PROPERTIES = 7;
    public static final int BOOT_ID = 1;
    public static final int BOOT_COL = 2;
    public static final int BOOT_ROW = 3;
    public static final int BOOT_ANIMATION_PERIOD = 4;

    public static final int BOOT_ACTION_PERIOD = 5;
    public static final int BOOT_HEALTH = 1;

    private int health;

    public Boot(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod, int health){
        super(id, position, images, animationPeriod, actionPeriod);
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void updateHealth(int n) {
        this.health += n;
    }

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (health <= 0) {
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            System.out.println("BOOT REMOVED");
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
