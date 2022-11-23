import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Optional;

import processing.core.*;

public final class VirtualWorld extends PApplet
{
    public static final int TIMER_ACTION_PERIOD = 100;

    public static final int VIEW_WIDTH = 800;
    public static final int VIEW_HEIGHT = 600;
    public static final int TILE_WIDTH = 40;
    public static final int TILE_HEIGHT = 40;
    public static final int WORLD_WIDTH_SCALE = 1;
    public static final int WORLD_HEIGHT_SCALE = 1;

    public static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    public static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    public static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    public static final String IMAGE_LIST_FILE_NAME = "imagelist";
    public static final String DEFAULT_IMAGE_NAME = "background_default";
    public static final int DEFAULT_IMAGE_COLOR = 0x808080;

    //public static String LOAD_FILE_NAME = "world.sav"; // scene 1
    //public static String LOAD_FILE_NAME = "space.sav"; // scene 2
    public static String LOAD_FILE_NAME = "earth2.sav"; // scene 3


    public static final String FAST_FLAG = "-fast";
    public static final String FASTER_FLAG = "-faster";
    public static final String FASTEST_FLAG = "-fastest";
    public static final double FAST_SCALE = 0.5;
    public static final double FASTER_SCALE = 0.25;
    public static final double FASTEST_SCALE = 0.10;

    public static double timeScale = 1.0;

    public ImageStore imageStore;
    public WorldModel world1;
    public WorldModel world2;
    public WorldModel world3;
    public WorldView view;
    public EventScheduler scheduler;

    // start positions for walle depending on the scene
    // scene 3 placement tbd
    private Point p1 = new Point(4, 3);
    private Point p2 = new Point(1, 7);
    private Point p3 = new Point(1, 7);

    // change to true depending on which scene you want to see
    private boolean scene1 = true; // earth1
    private boolean scene2 = false; // space
    private boolean scene3 = false; // earth2

    // ignore this for now
    private SceneFactory s = new SceneFactory(); // needs to return a world model with everything setup??

    public long nextTime;

    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        this.imageStore = new ImageStore(
                createImageColored(TILE_WIDTH, TILE_HEIGHT,
                                   DEFAULT_IMAGE_COLOR));

        // scenes
        this.world1 = new WorldModel(WORLD_ROWS, WORLD_COLS,
                                    createDefaultBackground(imageStore));

        this.world2 = new WorldModel(WORLD_ROWS, WORLD_COLS,
                createDefaultBackground(imageStore));

        this.world3 = new WorldModel(WORLD_ROWS, WORLD_COLS,
                createDefaultBackground(imageStore));

        // this.world = SceneFactory.getWorldModel() ??

        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world1, TILE_WIDTH,
                                  TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);



        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);

        // key to scene change: load world
        // move to draw if wanting to change scene bc setup runs once
        // issue: draw runs 60x per second
        loadWorld(world1, LOAD_FILE_NAME, imageStore); // scene 1
        //loadWorld(world2, SCENE_2, imageStore); // scene 1
        //loadWorld(world3, SCENE_3, imageStore); // scene 1

        scheduleActions(world1, scheduler, imageStore);

        // new additions
        // size(100, 30);


        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= nextTime) {
            this.scheduler.updateOnTime(time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }

        view.drawViewport();

        textSize(20);
        text("SCORE: ", 10, 30);
    }

    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate
    public void mousePressed() {
        Point pressed = mouseToPoint(mouseX, mouseY);
        System.out.println("CLICK! " + pressed.x + ", " + pressed.y);

        Optional<Entity> entityOptional = world1.getOccupant(pressed);
        if (entityOptional.isPresent())
        {
            Entity entity = entityOptional.get();
            // typecast to plant?
            if (entity instanceof Plant) {
                System.out.println(entity.getId() + ": " + entity.getClass().getTypeName() + " : " + ((Plant) entity).getHealth());
            }
            else {
                System.out.println(entity.getId() + ": " + entity.getClass().getTypeName() + " : " + entity);
            }
        }

    }

    private Point mouseToPoint(int x, int y)
    {
        return view.getViewport().viewportToWorld(mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
    }

    // change to only effect wall-e
    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }

            //view.shiftView(dx, dy);
            Point curP;
            if(scene1){
                curP = p1;
            } else if (scene2 || scene3) {
                curP = p2;
            }
            else{
                curP = null;
            }

            Entity walle = world1.getOccupancyCell(curP); // start pos

            Point newP = new Point(walle.getPosition().x + dx, walle.getPosition().y + dy);

            if ((world1.getOccupancyCell(newP) == null || world1.getOccupancyCell(newP).equals(Fairy.class)) &&(newP.x > 0 && newP.x < VIEW_COLS - 1) && (newP.y > 0 && newP.y < VIEW_ROWS - 1)){
                world1.moveEntity(walle, newP);
            }
            if (scene1){
                p1 = new Point(walle.getPosition().x, walle.getPosition().y);
            } else if (scene2 || scene3) {
                p2 = new Point(walle.getPosition().x, walle.getPosition().y);
            }

            //e = world.getOccupant(new Point(e.getPosition().x + dx, e.getPosition().y + dy));
        }
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                              imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    static void loadImages(
            String filename, ImageStore imageStore, PApplet screen)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.loadImages(in, screen);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadWorld(
            WorldModel world, String filename, ImageStore imageStore)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            world.load(in, imageStore);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Animated){
                ((Animated)entity).scheduleActions(scheduler, world, imageStore);
            }

        }
    }

    public static void parseCommandLine(String[] args) {
        if (args.length > 1)
        {
            if (args[0].equals("file"))
            {

            }
        }
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }
}
