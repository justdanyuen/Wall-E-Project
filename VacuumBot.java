import processing.core.PImage;

import java.util.List;

public class VacuumBot extends Robot{
    public static final String VACUUM_ROBOT_KEY = "vacuum_robot";
    public VacuumBot(String id, Point position, List<PImage> images, int animationPeriod){
        super(id, position, images, animationPeriod);
    }
}
