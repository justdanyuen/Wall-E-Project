public class SpaceFactory implements Scene{
    public String returnSceneFile() {
        return "space.sav";
    }

    public void drawScene(WorldModel world, Object[] entities, VirtualWorld v, ImageStore imageStore){
        for (Object e : entities){
            if (e instanceof Entity
                    && !e.getClass().equals(Walle.class)){
                world.removeEntity((Entity) e);
            }
        }

        EveLocked e = new EveLocked("eve_locked",
                new Point(18, 11),
                imageStore.getImageList(EveLocked.EVE_LOCKED_KEY),
                EveLocked.EVE_LOCKED_ANIMATION_PERIOD,
                EveLocked.EVE_LOCKED_ACTION_PERIOD);

        world.addEntity(e);

        Entity w = new Walle("walle",
                new Point(1, 7),
                imageStore.getImageList(Walle.WALLE_KEY),
                Walle.WALLE_ANIMATION_PERIOD,
                Walle.WALLE_ACTION_PERIOD);

        world.addEntity(w);


        v.loadWorld(world, this.returnSceneFile(), imageStore);
    }
}