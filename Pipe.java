import processing.core.PImage;

import java.util.List;

public class Pipe extends Entity{
    public static final int PIPE_NUM_PROPERTIES = 4;
    public static final int PIPE_ID = 1;
    public static final int PIPE_COL = 2;
    public static final int PIPE_ROW = 3;
    public Pipe(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
}
