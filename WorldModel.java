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

    public static final String SIGN_SHORT_KEY = "sign_short";

    public static final String SIGN_LONG_KEY = "sign_long";



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

    private void removeEntityAt(Point pos) {
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
        if (this.withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
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
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

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
                case Dude.DUDE_KEY:
                    return p.parseDude();
                case  Fairy.FAIRY_KEY:
                    return p.parseFairy();
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
                case SIGN_LONG_KEY:
                    return p.parseSign(SIGN_LONG_KEY);
                case SIGN_SHORT_KEY:
                    return p.parseSign(SIGN_SHORT_KEY);
                case ShoppingCart.CART_KEY:
                    return p.parseCart();
                case Trash.TRASH_KEY:
                    return p.parseTrash();
                case Robot.ROBOT_KEY:
                    return p.parseRobot();
                case Walle.WALLE_KEY:
                    return p.parseWalle();
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
