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

    public boolean reachedEve(Point eve){
        Point p1 = new Point(eve.x - 1, eve.y);
        Point p2 = new Point(eve.x + 1, eve.y);
        Point p3 = new Point(eve.x, eve.y - 1);
        Point p4 = new Point(eve.x, eve.y + 1);
        if (this.getPosition().equals(p1) ||
                this.getPosition().equals(p2) ||
                this.getPosition().equals(p3) ||
                this.getPosition().equals(p4))
        {
            return true;
        }
        return false;
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
