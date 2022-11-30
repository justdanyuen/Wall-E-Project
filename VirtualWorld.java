import java.io.File;
import java.io.FileNotFoundException;
import java.security.cert.PolicyNode;
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

    public static String LOAD_FILE_NAME = "space.sav"; // scene 2

    public static final String FAST_FLAG = "-fast";
    public static final String FASTER_FLAG = "-faster";
    public static final String FASTEST_FLAG = "-fastest";
    public static final double FAST_SCALE = 0.5;
    public static final double FASTER_SCALE = 0.25;
    public static final double FASTEST_SCALE = 0.10;

    public static double timeScale = 1.0;

    public ImageStore imageStore;
    public WorldModel world;
    public WorldView view;
    public EventScheduler scheduler;

    // change to true depending on which scene you want to see
    private boolean scene1 = true; // earth1
    private boolean scene2 = false; // space
    private boolean scene3 = false; // earth2

    private boolean addedEve = false; // earth2

    private boolean addedBoot = false; //earth1

    private boolean walleReachedBoot = false;

    private Object[] entities1;
    private Object[] entities2;


//need to make these as Walle and Eve and Roach types to avoid typecasting
    private Walle walle;
    private WalleWithPlant walleWPlant;

    private Entity curWalle;
    private Eve eve;

    private EveLocked eveLocked;
    private Roach roach;
    private Boot boot;


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
        curScene.drawScene(world, null, this, imageStore);

        // store all entities currently in the world
        entities1 = world.getEntities().stream().toArray();

        startW = new Point(4, 3);
        Entity e = world.getOccupancyCell(startW);
        walle = ((Walle)e);// start pos

        startR = new Point(0, 0);
        Entity r = world.getOccupancyCell(startR); // start pos
        roach = ((Roach) r);

        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= nextTime) {
            this.scheduler.updateOnTime(time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }

        for(Object e : entities1){
            if (e instanceof Entity && e.getClass().equals(Trash.class)){
                if (walle.isAtItem(((Trash) e).getPosition())){
                    walle.updateScore();
                    world.removeEntity((Entity)e);
                }
            }
        }

//
        if (walle.getScore() >= 5 && scene1 == true && !addedBoot){
            Entity b = new Boot("boot",
                    new Point(9, 8),
                    imageStore.getImageList(Boot.BOOT_KEY),
                    Boot.BOOT_ANIMATION_PERIOD,
                    Boot.BOOT_ACTION_PERIOD,
                    Boot.BOOT_HEALTH);
            boot = ((Boot) b);
            world.addEntity(b);
            addedBoot = true;
        }


        if (addedBoot && walle.isAtItem(boot.getPosition()) && scene1 == true && !addedEve){
            //((Boot)boot).setPathingStrategy(new AStarPathingStrategy());
            walleReachedBoot = true;

            Point posWalle = walle.getPosition();

            world.removeEntity(walle);
            world.removeEntity(boot);

            walleWPlant = new WalleWithPlant("walle_plant",
                    posWalle,
                    imageStore.getImageList(WalleWithPlant.WALLEWITHPLANT_KEY),
                    WalleWithPlant.WALLEWITHPLANT_ANIMATION_PERIOD,
                    WalleWithPlant.WALLEWITHPLANT_ACTION_PERIOD);

            world.addEntity(walleWPlant);

            Entity eve1 = new Eve("eve",
                    new Point(19, 1),
                    imageStore.getImageList(Eve.EVE_KEY),
                    Eve.EVE_ANIMATION_PERIOD,
                    Eve.EVE_ACTION_PERIOD);

            ((Eve)eve1).setPathingStrategy(new Stationary());
            world.addEntity(eve1);

            addedEve = true;

            Entity e = world.getOccupancyCell(new Point(19, 1));
            eve = ((Eve)e);
        }


        if (eve != null && walleWPlant.reachedEve(eve.getPosition()) && scene1) {
            scene1 = false;
            scene2 = true;

            world.removeEntity(walleWPlant);

            roach.setPathingStrategy(new Stationary());

            curScene = s.createScene("2");

            // factory
            world.removeEntity(eve);

            curScene.drawScene(world, entities1, this, imageStore);

            Entity e = world.getOccupancyCell(new Point(18, 11));
            eveLocked = ((EveLocked) e);

            Entity w = world.getOccupancyCell(new Point(1, 7));
            walle = ((Walle) w);

            entities2 = world.getEntities().stream().toArray();
        }

        else if (eveLocked != null && walle.reachedEve(eveLocked.getPosition()) && scene2) {
            scene2 = false;
            scene3 = true;

            world.removeEntity(eveLocked);

            // factory
            curScene = s.createScene("3");
            curScene.drawScene(world, entities2, this, imageStore);

            Entity wP = world.getOccupancyCell(new Point(1, 7));
            walleWPlant = ((WalleWithPlant) wP);
        }

        scheduleActions(world, scheduler, imageStore);
        view.drawViewport();

    }



    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate
//    public void mousePressed() {
//        Point pressed = mouseToPoint(mouseX, mouseY);
//        System.out.println("CLICK! " + pressed.x + ", " + pressed.y);
//
//        Optional<Entity> entityOptional = world.getOccupant(pressed);
//        if (entityOptional.isPresent())
//        {
//            Entity entity = entityOptional.get();
//            // typecast to plant?
//            if (entity instanceof Plant) {
//                System.out.println(entity.getId() + ": " + entity.getClass().getTypeName() + " : " + ((Plant) entity).getHealth());
//            }
//            else {
//                System.out.println(entity.getId() + ": " + entity.getClass().getTypeName() + " : " + entity);
//            }
//        }
//
//    }

    private Point mouseToPoint(int x, int y)
    {
        return view.getViewport().viewportToWorld(mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
    }

    private boolean usesAStar(Entity entity){
        //System.out.println(entity.getClass());
        if (entity.getClass().equals(Eve.class) || entity.getClass().equals(Roach.class)){
            if (scene1){
                //System.out.println(((Roach)entity).getPathingStrategy());
                return ((Roach) entity).getPathingStrategy() instanceof AStarPathingStrategy;
            } else if (scene3) {
                return ((Eve) entity).getPathingStrategy() instanceof AStarPathingStrategy;
            }

        }
        return false;
    }


    // change to only effect wall-e
    public void keyPressed() {

            int dx = 0;
            int dy = 0;


            switch (key) {
                case 'w':
                    dy = -1;
                    break;
                case 's':
                    dy = 1;
                    break;
                case 'a':
                    dx = -1;
                    break;
                case 'd':
                    dx = 1;
                    break;
            }

            Point newP;

            if (walleReachedBoot && scene1 || scene3){
                curWalle = walleWPlant;
                newP = new Point(walleWPlant.getPosition().x + dx, walleWPlant.getPosition().y + dy);
            }
            else {
                curWalle = walle;
                newP = new Point(walle.getPosition().x + dx, walle.getPosition().y + dy);
            }

            if (!world.isOccupied(newP) && ((newP.x > 0 && newP.x < VIEW_COLS - 1) && (newP.y > 0 && newP.y < VIEW_ROWS - 1))){
                world.moveEntity(curWalle,newP);
                //System.out.println(walle.getPosition());
            }
            else if (world.isOccupied(newP)) {
                Point curPos = curWalle.getPosition();
                Entity e = world.getOccupancyCell(newP);

                if (usesAStar(e)){
                    // swap
                    curWalle.setPosition(e.getPosition());
                    e.setPosition(curPos);
                   // System.out.println("SWAP");
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

    public static void swapEntity(Entity first, Entity second){
        Point temp = first.getPosition();
        first.setPosition(second.getPosition());
        second.setPosition(temp);
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