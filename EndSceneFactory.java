public class EndSceneFactory implements Scene{
    public String returnSceneFile() {
        return "end.sav";
    }

    public void drawScene(WorldModel world, Object[] entities, VirtualWorld v, ImageStore imageStore){
        for (Object e : entities){
            if (e instanceof Entity){
                world.removeEntity((Entity) e);
            }
        }

        WalleWithPlant walleWithPlant = new WalleWithPlant("walle_plant",
                new Point(1, 1),
                imageStore.getImageList(WalleWithPlant.WALLEWITHPLANT_KEY),
                WalleWithPlant.WALLEWITHPLANT_ANIMATION_PERIOD,
                WalleWithPlant.WALLEWITHPLANT_ACTION_PERIOD);

        world.addEntity(walleWithPlant);

        v.loadWorld(world, this.returnSceneFile(), imageStore);
    }
}
