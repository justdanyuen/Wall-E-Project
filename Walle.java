import processing.core.PImage;

import java.util.List;

public class Walle extends ActionEntity{
    public static final String WALLE_KEY = "walle";
    public static final int WALLE_NUM_PROPERTIES = 6;
    public static final int WALLE_ID = 1;
    public static final int WALLE_COL = 2;
    public static final int WALLE_ROW = 3;
    public static final int WALLE_ANIMATION_PERIOD = 4;
    public static final int WALLE_ACTION_PERIOD = 5;

    private int score = 0;

    private static boolean changeScene = false;

    public Walle(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
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


    public void incrementScore(){
        score += 1;
    }

    public int getScore(){
        return score;
    }

    public boolean isAtTrash(Point trash){
        if (this.getPosition().equals(trash)){
            return true;
        }
        return false;
    }

    public boolean reachedEve(Point eve){
        if (this.getPosition().equals(eve)){
            changeScene = true;

            return true;
        }
        return false;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        //if () {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    this.getActionPeriod());
        //}
    }


}
