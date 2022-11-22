import processing.core.PImage;

import java.util.List;

public class ShoppingCart extends StaticObstacle{
    public static final String CART_KEY = "shopping_cart";
    public static final int CART_NUM_PROPERTIES = 4;
    public static final int CART_ID = 1;
    public static final int CART_COL = 2;
    public static final int CART_ROW = 3;
    public ShoppingCart(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
}
