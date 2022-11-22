import processing.core.PImage;

import java.util.List;

public class Tires extends StaticObstacle{
    public static final String TIRES_KEY = "tires";
    public static final int TIRES_NUM_PROPERTIES = 4;
    public static final int TIRES_ID = 1;
    public static final int TIRES_COL = 2;
    public static final int TIRES_ROW = 3;
    public Tires(String id, Point position, List<PImage> images){
        super(id, position, images);
    }

}
