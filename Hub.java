import processing.core.PImage;

import java.util.List;

public class Hub extends StaticObstacle{
    public static final int HUB_NUM_PROPERTIES = 4;
    public static final int HUB_ID = 1;
    public static final int HUB_COL = 2;
    public static final int HUB_ROW = 3;
    public Hub(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
}
