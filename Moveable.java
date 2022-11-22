import processing.core.PImage;

import java.util.List;

public interface Moveable{

     boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler);
     Point nextPosition(WorldModel world, Point destPos);

}
