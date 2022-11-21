import processing.core.PImage;

import java.util.List;

public class Sign extends Entity{
    public static final String SIGN_KEY = "sign";
    public static final int SIGN_NUM_PROPERTIES = 4;
    public static final int SIGN_ID = 1;
    public static final int SIGN_COL = 2;
    public static final int SIGN_ROW = 3;
    public Sign(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
}
