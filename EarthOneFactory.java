public class EarthOneFactory implements Scene{
    public String returnSceneFile() {
        return "earth1.sav";
    }

    public void drawScene(WorldModel world, Object[] entities, VirtualWorld v, ImageStore imageStore){
        v.loadWorld(world, this.returnSceneFile(), imageStore); // scene 1
    }
}
