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

        v.loadWorld(world, this.returnSceneFile(), imageStore);
    }
}
