import processing.core.PImage;

import java.util.List;

public class WalleWithPlant extends ActionEntity{
    public static final String WALLEWITHPLANT_KEY = "walle_plant";
    public static final int WALLEWITHPLANT_NUM_PROPERTIES = 6;
    public static final int WALLEWITHPLANT_ID = 1;
    public static final int WALLEWITHPLANT_COL = 2;
    public static final int WALLEWITHPLANT_ROW = 3;
    public static final int WALLEWITHPLANT_ANIMATION_PERIOD = 4;
    public static final int WALLEWITHPLANT_ACTION_PERIOD = 5;


    public WalleWithPlant(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);

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


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        // if cur pos == trash.position
        Object[] entities = world.getEntities().stream().toArray();

        for (Object e : entities){
            if (e.getClass().equals(Trash.class)){
                if (this.isAtItem(((Trash) e).getPosition())){
                    ((Trash)e).updateHealth(-1);
                }
            }
        }


        // trash.health --
        // score ++
        // check if scene needs to be changes - WalleWithPlant reached Eve
    }



}
