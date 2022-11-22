import processing.core.PImage;

import java.util.List;

public class Trash extends StaticObstacle{
    public static final String TRASH_KEY = "trash";
    public static final int TRASH_NUM_PROPERTIES = 4;
    public static final int TRASH_ID = 1;
    public static final int TRASH_COL = 2;
    public static final int TRASH_ROW = 3;
    public Trash(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
}
