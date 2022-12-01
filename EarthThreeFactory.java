public class EarthThreeFactory implements Scene{

    public String returnSceneFile() {
        return "earth3.sav";
    }

    public void drawScene(WorldModel world, Object[] entities, VirtualWorld v, ImageStore imageStore){
        for (Object e : entities){
            if (e instanceof Entity){
                world.removeEntity((Entity) e);
            }
        }


        Eve eve = new Eve("eve",
                new Point(2, 7),
                imageStore.getImageList(Eve.EVE_KEY),
                Eve.EVE_ANIMATION_PERIOD,
                Eve.EVE_ACTION_PERIOD);
        eve.setPathingStrategy(new AStarPathingStrategy());

        world.addEntity(eve);

        WalleWithPlant walleWithPlant = new WalleWithPlant("walle_plant",
                new Point(1, 7),
                imageStore.getImageList(WalleWithPlant.WALLEWITHPLANT_KEY),
                WalleWithPlant.WALLEWITHPLANT_ANIMATION_PERIOD,
                WalleWithPlant.WALLEWITHPLANT_ACTION_PERIOD);

        world.addEntity(walleWithPlant);


        v.loadWorld(world, this.returnSceneFile(), imageStore);
    }
}
