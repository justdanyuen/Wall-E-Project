import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeFull extends Dude{

    public DudeFull(String id,
                    Point position,
                    List<PImage> images, int animationPeriod, int actionPeriod, int resourceLimit){
        super(id, position, images, animationPeriod, actionPeriod, resourceLimit);

    }
    private void transformFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        DudeNotFull miner = new DudeNotFull(this.getId(),
                this.getPosition(),
                this.getImages(),
                this.getAnimationPeriod(),
                this.getActionPeriod(),
                this.getResourceLimit(),
                0);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    public boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition())) {

            return true;
        }
//        else {
//            Point nextPos = this.nextPosition(world, target.getPosition());
//
//            if (!this.getPosition().equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            // return false;
//        }
        return false;
    }
    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(House.class)));

        if (fullTarget.isPresent() && this.moveTo(world,
                fullTarget.get(), scheduler))
        {
            this.transformFull(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }

}
