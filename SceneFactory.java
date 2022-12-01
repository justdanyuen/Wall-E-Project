public class SceneFactory {
    // create first scene: earth
    // second: spaceship
    // third: earth with automatic maze pathing

    // make instance var to track scene input
    private String sceneNum = "1";

    // common methods
    public Scene createScene(String scene){
        if (scene == null || scene.isEmpty()){
            return null;
        }
        switch(scene){
            case("1"):
                return new EarthOneFactory();

            case("2"):
                return new SpaceFactory();

            case("3"):
                return new EarthTwoFactory();

            case ("4"):
                return new EarthThreeFactory();

            default:
                throw new IllegalArgumentException("invalid scene entry: "+ scene);
        }
    }

//    abstract void drawScene(Scene s, WorldModel world, Object[] entities, VirtualWorld v, ImageStore imageStore){
//        for (Object e : entities){
//            if (e instanceof Entity
//                    && !e.getClass().equals(Walle.class)
//                    && !e.getClass().equals(Eve.class)){
//                world.removeEntity((Entity) e);
//            }
//        }
//
//        v.loadWorld(world, s.returnSceneFile(), imageStore);
//
//    }

    public String getSceneNum() {
        return sceneNum;
    }

    public void setSceneNum(String num){
        // add addition conditions for invalid inputs
        sceneNum = num;
    }
}