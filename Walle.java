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


    public int getScore(){
        return score;
    }

    public void updateScore(){
        score++;
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
                    score++;
                    System.out.println(score);
                }
            }
        }


        // trash.health --
        // score ++
        // check if scene needs to be changes - Walle reached Eve
    }


}