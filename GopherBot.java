import processing.core.PImage;

import java.util.List;

public class GopherBot extends Robot{
    public static final String GOPHER_ROBOT_KEY = "gopher_robot";
    public GopherBot(String id, Point position, List<PImage> images, int animationPeriod){
        super(id, position, images, animationPeriod);
    }
}
