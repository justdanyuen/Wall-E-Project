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


    public boolean parseBoot()
    {
        if (this.properties.length == Boot.BOOT_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Boot.BOOT_COL]),
                    Integer.parseInt(this.properties[Boot.BOOT_ROW]));
            Boot boot = new Boot(this.properties[Boot.BOOT_ID],
                    pt,
                    this.imageStore.getImageList(Boot.BOOT_KEY),
                    Integer.parseInt(this.properties[Boot.BOOT_ANIMATION_PERIOD]),
                    Integer.parseInt(this.properties[Boot.BOOT_ACTION_PERIOD]),
                    Boot.BOOT_HEALTH);

            this.world.tryAddEntity(boot);
        }
        return this.properties.length == Boot.BOOT_NUM_PROPERTIES;
    }

    public boolean parseBarricadeRobot()
    {
        if (this.properties.length == Robot.ROBOT_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Robot.ROBOT_COL]),
                    Integer.parseInt(this.properties[Robot.ROBOT_ROW]));
            BarricadeBot robot = new BarricadeBot(this.properties[Robot.ROBOT_ID], pt,
                    imageStore.getImageList(BarricadeBot.BARRICADE_ROBOT_KEY),
                    Integer.parseInt(this.properties[Robot.ROBOT_ANIMATION_PERIOD]),
                    Integer.parseInt(this.properties[Robot.ROBOT_ACTION_PERIOD]));
            this.world.tryAddEntity(robot);
        }
        return this.properties.length == Robot.ROBOT_NUM_PROPERTIES;
    }

    public boolean parseGopherRobot()
    {
        if (this.properties.length == Robot.ROBOT_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Robot.ROBOT_COL]),
                    Integer.parseInt(this.properties[Robot.ROBOT_ROW]));
            GopherBot robot = new GopherBot(this.properties[Robot.ROBOT_ID],
                    pt,
                    imageStore.getImageList(GopherBot.GOPHER_ROBOT_KEY),
                    Integer.parseInt(this.properties[Robot.ROBOT_ANIMATION_PERIOD]),
                    Integer.parseInt(this.properties[Robot.ROBOT_ACTION_PERIOD]));
            this.world.tryAddEntity(robot);
        }
        return this.properties.length == Robot.ROBOT_NUM_PROPERTIES;
    }

    public boolean parseVacuumRobot()
    {
        if (this.properties.length == Robot.ROBOT_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[Robot.ROBOT_COL]),
                    Integer.parseInt(this.properties[Robot.ROBOT_ROW]));
            VacuumBot robot = new VacuumBot(this.properties[Robot.ROBOT_ID], pt,
                    imageStore.getImageList(VacuumBot.VACUUM_ROBOT_KEY),
                    Integer.parseInt(this.properties[Robot.ROBOT_ANIMATION_PERIOD]),
                    Integer.parseInt(this.properties[Robot.ROBOT_ACTION_PERIOD]));
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

    public boolean parseEveLocked()
    {
        if (this.properties.length == EveLocked.EVE_LOCKED_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[EveLocked.EVE_LOCKED_COL]),
                    Integer.parseInt(this.properties[EveLocked.EVE_LOCKED_ROW]));
            EveLocked eve = new EveLocked(this.properties[EveLocked.EVE_LOCKED_ID],
                    pt,
                    imageStore.getImageList(EveLocked.EVE_LOCKED_KEY),
                    Integer.parseInt(this.properties[EveLocked.EVE_LOCKED_ANIMATION_PERIOD]),
                    Integer.parseInt(this.properties[EveLocked.EVE_LOCKED_ACTION_PERIOD]));

            this.world.tryAddEntity(eve);
        }
        return this.properties.length == EveLocked.EVE_LOCKED_NUM_PROPERTIES;
    }

    public boolean parseWalleWPlant()
    {
        if (this.properties.length == WalleWithPlant.WALLEWITHPLANT_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(this.properties[WalleWithPlant.WALLEWITHPLANT_COL]),
                    Integer.parseInt(this.properties[WalleWithPlant.WALLEWITHPLANT_ROW]));
            WalleWithPlant walleWithPlant = new WalleWithPlant(this.properties[WalleWithPlant.WALLEWITHPLANT_ID],
                    pt,
                    imageStore.getImageList(WalleWithPlant.WALLEWITHPLANT_KEY),
                    Integer.parseInt(this.properties[WalleWithPlant.WALLEWITHPLANT_ANIMATION_PERIOD]),
                    Integer.parseInt(this.properties[WalleWithPlant.WALLEWITHPLANT_ACTION_PERIOD]));

            this.world.tryAddEntity(walleWithPlant);
        }
        return this.properties.length == WalleWithPlant.WALLEWITHPLANT_NUM_PROPERTIES;
    }

}