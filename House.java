import processing.core.PImage;

import java.util.List;

public class House extends StaticObstacle{
    public static final String HOUSE_KEY = "house";
    public static final int HOUSE_NUM_PROPERTIES = 4;
    public static final int HOUSE_ID = 1;
    public static final int HOUSE_COL = 2;
    public static final int HOUSE_ROW = 3;
    public House(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
}
