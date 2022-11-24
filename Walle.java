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


//    public void incrementScore(){
//        score += 1;
//    }

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
        // if cur pos == trash.position
        Object[] entities = world.getEntities().stream().toArray();

        for (Object e : entities){
            if (e.getClass().equals(Trash.class)){
                //world.removeEntity((Trash)e);
                ((Trash)e).updateHealth(-1); // subtract from health
                ((Trash)e).transform(world, scheduler, imageStore);
                score++;
            }
        }


        // trash.health --
        // score ++
        // check if scene needs to be changes - Walle reached Eve
    }

    public void moveWalle(WorldModel world, Point p){

        if ((!world.isOccupied(p)
                || (world.isOccupied(p) && world.getOccupancyCell(p).getClass().equals(Trash.class)))
                && (p.x > 0 && p.x < VirtualWorld.VIEW_COLS - 1) && (p.y > 0 && p.y < VirtualWorld.VIEW_ROWS - 1)){
            world.moveEntity(this, p);

            //world.removeEntityAt(new Point(1, 7));
        }
        this.setPosition(new Point(this.getPosition().x + p.x, this.getPosition().y + p.y));
    }

}
