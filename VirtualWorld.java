import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Optional;
import java.util.Set;

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

    //public static String LOAD_FILE_NAME = "earth1.sav"; // scene 1
    public static String LOAD_FILE_NAME = "space.sav"; // scene 2
    //public static String LOAD_FILE_NAME = "earth2.sav"; // scene 3


    public static final String FAST_FLAG = "-fast";
    public static final String FASTER_FLAG = "-faster";
    public static final String FASTEST_FLAG = "-fastest";
    public static final double FAST_SCALE = 0.5;
    public static final double FASTER_SCALE = 0.25;
    public static final double FASTEST_SCALE = 0.10;

    public static double timeScale = 1.0;

    public ImageStore imageStore;
    public WorldModel world;
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



    private Object[] entities1;
    private Object[] entities2;

    private Entity walle;
    private Entity eve;

    private Entity roach;
    private Point startW;
    private Point startE;
    private Point startR;

    // ignore this for now

    private SceneFactory s = new SceneFactory(); // needs to return a world model with everything setup??
    private Scene curScene;

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
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
                                    createDefaultBackground(imageStore));

        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH,
                                  TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);


        curScene = s.createScene("1");
        loadWorld(world, curScene.returnSceneFile(), imageStore); // scene 1
        // store all entities currently in the world
        entities1 = world.getEntities().stream().toArray();

        startW = new Point(4, 3);
        walle = world.getOccupancyCell(startW); // start pos

        startR = new Point(0, 0);
        roach = world.getOccupancyCell(startR); // start pos

        startE = new Point(19, 2);
        eve = world.getOccupancyCell(startE);
        ((Eve)eve).setPathingStrategy(new Stationary());


        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= nextTime) {
            this.scheduler.updateOnTime(time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }

//        while(!((Walle)walle).reachedEve(eve.getPosition())){
//            eve.setPosition(new Point (eve.getPosition().x - 1, eve.getPosition().y + 1));
//        }

        if (((Walle)walle).reachedEve(eve.getPosition()) && scene2 == false) {
            scene2 = true;
            walle.setPosition(new Point(1, 7));
            // set eve to new pos
            eve.setPosition(new Point(18, 11));

            drawScene2();
        }

        else if (((Walle)walle).reachedEve(eve.getPosition()) && scene2 == true && scene3 == false) {
            //scene2 = false;
            scene3 = true;
            walle.setPosition(new Point(1, 7));
            // set eve to new pos
            eve.setPosition(new Point(2, 7));
            ((Eve)eve).setPathingStrategy(new AStarPathingStrategy());
            drawScene3();
        }

        scheduleActions(world, scheduler, imageStore);
        view.drawViewport();

        textSize(20);
        text("SCORE: ", 10, 30); // add score system
    }

    public void drawScene2(){
        //Object[] entities2 = world.getEntities().stream().toArray();
        //this.world = new WorldModel(WORLD_ROWS, WORLD_COLS, createDefaultBackground(imageStore));

        //System.out.println(world.getEntities().size());
        curScene = s.createScene("2");
        ((Roach)roach).setPathingStrategy(new Stationary());

        for (Object e : entities1){
            //System.out.println(e.getClass());
            if (e instanceof Entity
                    && !e.getClass().equals(Walle.class)
                    && !e.getClass().equals(Eve.class)) {
                world.removeEntity((Entity) e);
            }
        }

        loadWorld(world, curScene.returnSceneFile(), imageStore); // scene 1

        entities2 = world.getEntities().stream().toArray();
        scheduleActions(world, scheduler, imageStore);
    }

    public void drawScene3(){
        curScene = s.createScene("3");

        for (Object e : entities2){
            System.out.println(e.getClass());
            if (e instanceof Entity
                    && !e.getClass().equals(Walle.class)
                    && !e.getClass().equals(Eve.class)){
                world.removeEntity((Entity) e);
            }
        }


        loadWorld(world, curScene.returnSceneFile(), imageStore); // scene 1
        scheduleActions(world, scheduler, imageStore);
    }


    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate
    public void mousePressed() {
        Point pressed = mouseToPoint(mouseX, mouseY);
        System.out.println("CLICK! " + pressed.x + ", " + pressed.y);

        // JUST TEMPORARY - check if scene change can happen
        if (pressed.x == 19 && pressed.y == 0){
            scene1 = false;
            scene2 = true;


        }

        if (pressed.x == 19 && pressed.y == 14){
            scene1 = false;
            scene2 = false;
            scene3 = true;
            walle.setPosition(new Point(1, 7));
            // set eve to new pos
            eve.setPosition(new Point(2, 7));
            ((Eve)eve).setPathingStrategy(new AStarPathingStrategy());

        }

        Optional<Entity> entityOptional = world.getOccupant(pressed);
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


            Point newP = new Point(walle.getPosition().x + dx, walle.getPosition().y + dy);

            if ((!world.isOccupied(newP)
                    || (world.isOccupied(newP) && world.getOccupancyCell(newP).getClass().equals(Trash.class)))
                    && (newP.x > 0 && newP.x < VIEW_COLS - 1) && (newP.y > 0 && newP.y < VIEW_ROWS - 1)){

                world.moveEntity(walle,newP);
                System.out.println(walle.getPosition());
            }
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
