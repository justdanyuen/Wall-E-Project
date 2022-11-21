import processing.core.PImage;

import java.util.*;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public class Entity
{
    // change to private at the end
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    private String objectType;

    public Entity(
            String id,
            Point position,
            List<PImage> images

            )
    {
        this.id = id;

        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.objectType = objectType;
    }

    public String getId(){
        return this.id;
    }

    public List<PImage> getImages(){
        return this.images;
    }

    public Point getPosition(){return this.position;}

    public void setPosition(Point p){
        this.position = p;
    }

    public PImage getCurrentImage() {
        return this.images.get(this.imageIndex);
    }

    public int getImageIndex(){
        return this.imageIndex;
    }
    public void setImageIndex(int n){
        this.imageIndex = n;
    }
}
