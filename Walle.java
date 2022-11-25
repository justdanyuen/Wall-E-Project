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

    public void setScore(int n){
        score = n;
    }

    public boolean isAtTrash(Point trash){
        Point p1 = new Point(trash.x - 1, trash.y);
        Point p2 = new Point(trash.x + 1, trash.y);
        Point p3 = new Point(trash.x, trash.y - 1);
        Point p4 = new Point(trash.x, trash.y + 1);
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
                if (this.isAtTrash(((Trash) e).getPosition())){
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
