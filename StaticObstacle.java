import processing.core.PImage;

import java.util.List;

public class StaticObstacle extends Entity {
    private static Point pos;
    public StaticObstacle(String id, Point position, List<PImage> images){
        super(id, position, images);
        this.pos = position;
    }

    public static Point getPos(){
        return pos;
    }

}
