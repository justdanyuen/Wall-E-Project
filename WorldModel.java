import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel
{
    private int numRows;
    private int numCols;
    private Background background[][];
    private Entity occupancy[][];
    private Set<Entity> entities;

    public static final String SAPLING_KEY = "sapling";

    public static final String TREE_KEY = "tree";

    public static final String BGND_KEY = "background";
    public static final int PROPERTY_KEY = 0;

    public static final String PIPE_B_KEY = "pipe_b";

    public static final String PIPE_M_KEY = "pipe_m";

    public static final String PIPE_E_KEY = "pipe_e";

    public static final String SIGN_SHORT_KEY1 = "sign_short_lt";
    public static final String SIGN_SHORT_KEY2 = "sign_short_rt";
    public static final String SIGN_SHORT_KEY3 = "sign_short_lb";
    public static final String SIGN_SHORT_KEY4 = "sign_short_rb";

    public static final String SIGN_LONG_KEY1 = "sign_long_b";
    public static final String SIGN_LONG_KEY2 = "sign_long_m";
    public static final String SIGN_LONG_KEY3 = "sign_long_e";

    public static final String HUB_1_KEY = "hub1";
    public static final String HUB_2_KEY = "hub2";
    public static final String HUB_3_KEY = "hub3";
    public static final String HUB_4_KEY = "hub4";
    public static final String HUB_5_KEY = "hub5";
    public static final String HUB_6_KEY = "hub6";


    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }

    public int getNumRows(){
        return this.numRows;
    }

    public int getNumCols(){
        return this.numCols;
    }

    public Set<Entity> getEntities(){
        return this.entities;
    }

    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0
                && pos.x < this.numCols;
    }

    private void setBackgroundCell(Point pos, Background background)
    {
        this.background[pos.y][pos.x] = background;
    }

    private Background getBackgroundCell(Point pos) {
        return this.background[pos.y][pos.x];
    }

    public void setBackground(Point pos, Background background)
    {
        if (this.withinBounds(pos)) {
            this.setBackgroundCell(pos, background);
        }
    }
    private void setOccupancyCell(Point pos, Entity entity)
    {
        this.occupancy[pos.y][pos.x] = entity;
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }
    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.y][pos.x];
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && this.getOccupancyCell(pos) != null;
    }

    public void removeEntityAt(Point pos) { //ORIGINALLY PRIVATE
        if (this.withinBounds(pos) && this.getOccupancyCell(pos) != null) {
            Entity entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */

            Point p = new Point(-1, -1);
            entity.setPosition(p);
            this.entities.remove(entity);
            this.setOccupancyCell(pos, null);
        }
    }

    public void removeEntity(Entity entity) {
        this.removeEntityAt(entity.getPosition());
    }

    public void addEntity(Entity entity) {
        //System.out.println(entity.getPosition());
        if (this.withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
           // System.out.println(entity.getClass() + " ADDED TO ENTITIES \n");
        }
    }
    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void tryAddEntity(Entity entity) {
        //System.out.println(entity.getPosition());
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }
        //System.out.println("ADDED");
        this.addEntity(entity);
    }

    public Optional<Entity> findNearest(Point pos, List<Class> kinds)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Class kind : kinds)
        {
            for (Entity entity : this.entities) {
                if (kind.isInstance(entity)) {
                    ofType.add(entity);
                }
            }
        }
        return pos.nearestEntity(ofType);
    }

    private boolean processLine(
            String line, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            Parse p = new Parse(properties, imageStore, this);
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return p.parseBackground();
                case House.HOUSE_KEY:
                    return p.parseHouse();
                case Tree.TREE_KEY:
                    return p.parseTree();
                case Sapling.SAPLING_KEY:
                    return p.parseSapling();
                case PIPE_B_KEY:
                    return p.parsePipe(PIPE_B_KEY);
                case PIPE_M_KEY:
                    return p.parsePipe(PIPE_M_KEY);
                case PIPE_E_KEY:
                    return p.parsePipe(PIPE_E_KEY);
                case Tires.TIRES_KEY:
                    return p.parseTires();
                case SIGN_LONG_KEY1:
                    return p.parseSign(SIGN_LONG_KEY1);
                case SIGN_LONG_KEY2:
                    return p.parseSign(SIGN_LONG_KEY2);
                case SIGN_LONG_KEY3:
                    return p.parseSign(SIGN_LONG_KEY3);
                case SIGN_SHORT_KEY1:
                    return p.parseSign(SIGN_SHORT_KEY1);
                case SIGN_SHORT_KEY2:
                    return p.parseSign(SIGN_SHORT_KEY2);
                case SIGN_SHORT_KEY3:
                    return p.parseSign(SIGN_SHORT_KEY3);
                case SIGN_SHORT_KEY4:
                    return p.parseSign(SIGN_SHORT_KEY4);
                case ShoppingCart.CART_KEY:
                    return p.parseCart();
                case Trash.TRASH_KEY:
                    return p.parseTrash();
                case BarricadeBot.BARRICADE_ROBOT_KEY:
                    return p.parseBarricadeRobot();
                case GopherBot.GOPHER_ROBOT_KEY:
                    return p.parseGopherRobot();
                case VacuumBot.VACUUM_ROBOT_KEY:
                    return p.parseVacuumRobot();
                case Walle.WALLE_KEY:
                    return p.parseWalle();
                case Roach.ROACH_KEY:
                    return p.parseRoach();
                case HUB_1_KEY:
                    return p.parseHub(HUB_1_KEY);
                case HUB_2_KEY:
                    return p.parseHub(HUB_2_KEY);
                case HUB_3_KEY:
                    return p.parseHub(HUB_3_KEY);
                case HUB_4_KEY:
                    return p.parseHub(HUB_4_KEY);
                case HUB_5_KEY:
                    return p.parseHub(HUB_5_KEY);
                case HUB_6_KEY:
                    return p.parseHub(HUB_6_KEY);
                case Eve.EVE_KEY:
                    return p.parseEve();
            }
        }
        return false;
    }

    public void load(Scanner in, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!this.processLine(in.nextLine(), imageStore)) {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e) {
                System.err.println(
                        String.format("invalid entry on line %d", lineNumber));
            }
            catch (IllegalArgumentException e) {
                System.err.println(
                        String.format("issue on line %d: %s", lineNumber,
                                e.getMessage()));
            }
            lineNumber++;
        }
    }
    public Optional<PImage> getBackgroundImage( Point pos)
    {
        if (this.withinBounds(pos)) {
            return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }
}
