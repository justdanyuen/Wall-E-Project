import processing.core.PImage;

import java.util.List;

public class BarricadeBot extends Robot{
    public static final String BARRICADE_ROBOT_KEY = "barricade_robot";
    public BarricadeBot(String id, Point position, List<PImage> images, int animationPeriod){
        super(id, position, images, animationPeriod);
    }
}
