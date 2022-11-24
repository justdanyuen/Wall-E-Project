public class Parse {

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 5;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;
    public static final int OBSTACLE_ANIMATION_PERIOD = 4;

    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;

    String[] properties;
    ImageStore imageStore;
    WorldModel world;

    public Parse(String[] properties, ImageStore imageStore, WorldModel world){
        this.properties = properties;
        this.imageStore = imageStore;
        this.world = world;
    }

    public boolean parseHouse()
    {
        if (this.properties.length == House.HOUSE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[House.HOUSE_COL]),
                    Integer.parseInt(this.properties[House.HOUSE_ROW]));
            House house = new House(this.properties[House.HOUSE_ID], pt,
                    this.imageStore.getImageList(House.HOUSE_KEY));
            this.world.tryAddEntity(house);
        }

        return this.properties.length == House.HOUSE_NUM_PROPERTIES;
    }

    public boolean parseObstacle()
    {
        if (this.properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[OBSTACLE_COL]),
                    Integer.parseInt(this.properties[OBSTACLE_ROW]));
            Obstacle obstacle = new Obstacle(this.properties[OBSTACLE_ID], pt,
                    imageStore.getImageList(OBSTACLE_KEY),
                    Integer.parseInt(this.properties[OBSTACLE_ANIMATION_PERIOD]));
            this.world.tryAddEntity(obstacle);
        }
        return this.properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    public boolean parseTree()
    {
        if (this.properties.length == Tree.TREE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Tree.TREE_COL]),
                    Integer.parseInt(this.properties[Tree.TREE_ROW]));
            Tree tree = new Tree(this.properties[Tree.TREE_ID],
                    pt,
                    imageStore.getImageList(Tree.TREE_KEY),
                    Integer.parseInt(properties[Tree.TREE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Tree.TREE_ACTION_PERIOD]),
                    Integer.parseInt(properties[Tree.TREE_HEALTH])
            );
            this.world.tryAddEntity(tree);
        }

        return this.properties.length == Tree.TREE_NUM_PROPERTIES;
    }

    public boolean parseFairy()
    {
        if (this.properties.length == Fairy.FAIRY_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Fairy.FAIRY_COL]),
                    Integer.parseInt(this.properties[Fairy.FAIRY_ROW]));
            Fairy fairy = new Fairy(this.properties[Fairy.FAIRY_ID],
                    pt,
                    imageStore.getImageList(Fairy.FAIRY_KEY),
                    Integer.parseInt(properties[Fairy.FAIRY_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Fairy.FAIRY_ACTION_PERIOD])
            );
            this.world.tryAddEntity(fairy);
        }

        return this.properties.length == Fairy.FAIRY_NUM_PROPERTIES;
    }

    public boolean parseDude()
    {
        if (this.properties.length == Dude.DUDE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Dude.DUDE_COL]),
                    Integer.parseInt(this.properties[Dude.DUDE_ROW]));

            DudeNotFull dude = new DudeNotFull(this.properties[Dude.DUDE_ID],
                    pt,
                    this.imageStore.getImageList(Dude.DUDE_KEY),
                    Integer.parseInt(properties[Dude.DUDE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Dude.DUDE_ACTION_PERIOD]),
                    Integer.parseInt(properties[Dude.DUDE_LIMIT]),
                    0);

            world.tryAddEntity(dude);
        }

        return this.properties.length == Dude.DUDE_NUM_PROPERTIES;
    }

    public boolean parseSapling()
    {
        if (this.properties.length == Sapling.SAPLING_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Sapling.SAPLING_COL]),
                    Integer.parseInt(this.properties[Sapling.SAPLING_ROW]));
            String id = this.properties[Sapling.SAPLING_ID];
            int health = Integer.parseInt(this.properties[Sapling.SAPLING_HEALTH]);
            Sapling sapling = new Sapling(id, pt, this.imageStore.getImageList(Sapling.SAPLING_KEY), Sapling.SAPLING_ACTION_ANIMATION_PERIOD, Sapling.SAPLING_ACTION_ANIMATION_PERIOD, health, Sapling.SAPLING_HEALTH_LIMIT);
            this.world.tryAddEntity(sapling);
        }

        return this.properties.length == Sapling.SAPLING_NUM_PROPERTIES;
    }

    public boolean parseBackground()
    {
        if (this.properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[BGND_COL]),
                    Integer.parseInt(this.properties[BGND_ROW]));
            String id = this.properties[BGND_ID];
            this.world.setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

    // NEW ADDITIONS
    public boolean parsePipe(String key)
    {
        if (this.properties.length == Pipe.PIPE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Pipe.PIPE_COL]),
                    Integer.parseInt(this.properties[Pipe.PIPE_ROW]));
            Pipe pipe = new Pipe(this.properties[Pipe.PIPE_ID], pt,
                    this.imageStore.getImageList(key));
            this.world.tryAddEntity(pipe);
        }

        return this.properties.length == Pipe.PIPE_NUM_PROPERTIES;
    }

    public boolean parseTires()
    {
        if (this.properties.length == Tires.TIRES_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Tires.TIRES_COL]),
                    Integer.parseInt(this.properties[Tires.TIRES_ROW]));
            Tires tire = new Tires(this.properties[Tires.TIRES_ID], pt,
                    this.imageStore.getImageList(Tires.TIRES_KEY));
            this.world.tryAddEntity(tire);
        }

        return this.properties.length == Tires.TIRES_NUM_PROPERTIES;
    }

    public boolean parseSign(String key)
    {
        if (this.properties.length == Sign.SIGN_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Sign.SIGN_COL]),
                    Integer.parseInt(this.properties[Sign.SIGN_ROW]));
            Sign sign = new Sign(this.properties[Sign.SIGN_ID], pt,
                    this.imageStore.getImageList(key));
            this.world.tryAddEntity(sign);
        }

        return this.properties.length == Sign.SIGN_NUM_PROPERTIES;
    }

    public boolean parseCart()
    {
        if (this.properties.length == ShoppingCart.CART_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[ShoppingCart.CART_COL]),
                    Integer.parseInt(this.properties[ShoppingCart.CART_ROW]));
            ShoppingCart cart = new ShoppingCart(this.properties[ShoppingCart.CART_ID], pt,
                    this.imageStore.getImageList(ShoppingCart.CART_KEY));
            //System.out.println("CART CREATED");
            this.world.tryAddEntity(cart);
        }

        return this.properties.length == ShoppingCart.CART_NUM_PROPERTIES;
    }

    public boolean parseTrash()
    {
        //System.out.println(this.properties.length == Trash.TRASH_NUM_PROPERTIES);
        if (this.properties.length == Trash.TRASH_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Trash.TRASH_COL]),
                    Integer.parseInt(this.properties[Trash.TRASH_ROW]));

//            System.out.println(pt);
//            System.out.println(Trash.TRASH_ID);
//            System.out.println(Trash.TRASH_KEY);
//            System.out.println(Trash.TRASH_ANIMATION_PERIOD);
//            System.out.println(Trash.TRASH_ACTION_PERIOD);
            //System.out.println("CREATING");

            Trash trash = new Trash(this.properties[Trash.TRASH_ID],
                    pt,
                    this.imageStore.getImageList(Trash.TRASH_KEY),
                    Integer.parseInt(this.properties[Trash.TRASH_ANIMATION_PERIOD]),
                    Integer.parseInt(this.properties[Trash.TRASH_ACTION_PERIOD]),
                    Trash.TRASH_HEALTH);

            //System.out.println("TRASH CREATED");
            this.world.tryAddEntity(trash);
        }
        //System.out.println(" \n");
        return this.properties.length == Trash.TRASH_NUM_PROPERTIES;
    }

    public boolean parseBarricadeRobot()
    {
        if (this.properties.length == Robot.ROBOT_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Robot.ROBOT_COL]),
                    Integer.parseInt(this.properties[Robot.ROBOT_ROW]));
            Robot robot = new Robot(this.properties[Robot.ROBOT_ID], pt,
                    imageStore.getImageList(BarricadeBot.BARRICADE_ROBOT_KEY),
                    Integer.parseInt(this.properties[Robot.ROBOT_ANIMATION_PERIOD]));
            this.world.tryAddEntity(robot);
        }
        return this.properties.length == Robot.ROBOT_NUM_PROPERTIES;
    }

    public boolean parseGopherRobot()
    {
        if (this.properties.length == Robot.ROBOT_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Robot.ROBOT_COL]),
                    Integer.parseInt(this.properties[Robot.ROBOT_ROW]));
            Robot robot = new Robot(this.properties[Robot.ROBOT_ID], pt,
                    imageStore.getImageList(GopherBot.GOPHER_ROBOT_KEY),
                    Integer.parseInt(this.properties[Robot.ROBOT_ANIMATION_PERIOD]));
            this.world.tryAddEntity(robot);
        }
        return this.properties.length == Robot.ROBOT_NUM_PROPERTIES;
    }

    public boolean parseVacuumRobot()
    {
        if (this.properties.length == Robot.ROBOT_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Robot.ROBOT_COL]),
                    Integer.parseInt(this.properties[Robot.ROBOT_ROW]));
            Robot robot = new Robot(this.properties[Robot.ROBOT_ID], pt,
                    imageStore.getImageList(VacuumBot.VACUUM_ROBOT_KEY),
                    Integer.parseInt(this.properties[Robot.ROBOT_ANIMATION_PERIOD]));
            this.world.tryAddEntity(robot);
        }
        return this.properties.length == Robot.ROBOT_NUM_PROPERTIES;
    }



    public boolean parseRoach()
    {
        if (this.properties.length == Roach.ROACH_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Roach.ROACH_COL]),
                    Integer.parseInt(this.properties[Roach.ROACH_ROW]));
            Roach roach = new Roach(this.properties[Roach.ROACH_ID],
                    pt,
                    imageStore.getImageList(Roach.ROACH_KEY),
                    Integer.parseInt(properties[Roach.ROACH_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Roach.ROACH_ACTION_PERIOD])
            );
            this.world.tryAddEntity(roach);
        }

        return this.properties.length == Roach.ROACH_NUM_PROPERTIES;
    }

    public boolean parseHub(String key)
    {
        if (this.properties.length == Hub.HUB_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Hub.HUB_COL]),
                    Integer.parseInt(this.properties[Hub.HUB_ROW]));
            Hub hub = new Hub(this.properties[Hub.HUB_ID], pt,
                    this.imageStore.getImageList(key));
            this.world.tryAddEntity(hub);
        }

        return this.properties.length == Hub.HUB_NUM_PROPERTIES;
    }

    public boolean parseEve()
    {
        if (this.properties.length == Eve.EVE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Eve.EVE_COL]),
                    Integer.parseInt(this.properties[Eve.EVE_ROW]));
            Eve eve = new Eve(this.properties[Eve.EVE_ID],
                    pt,
                    imageStore.getImageList(Eve.EVE_KEY),
                    Integer.parseInt(properties[Eve.EVE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Eve.EVE_ACTION_PERIOD]));

            this.world.tryAddEntity(eve);
        }

        return this.properties.length == Eve.EVE_NUM_PROPERTIES;
    }

    public boolean parseWalle()
    {
        if (this.properties.length == Walle.WALLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Walle.WALLE_COL]),
                    Integer.parseInt(this.properties[Walle.WALLE_ROW]));
            Walle walle = new Walle(this.properties[Walle.WALLE_ID],
                    pt,
                    imageStore.getImageList(Walle.WALLE_KEY),
                    Integer.parseInt(this.properties[Walle.WALLE_ANIMATION_PERIOD]),
                    Integer.parseInt(this.properties[Walle.WALLE_ACTION_PERIOD]));

            this.world.tryAddEntity(walle);
        }
        return this.properties.length == Walle.WALLE_NUM_PROPERTIES;
    }
}
