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
                return new Earth1();

            case("2"):
                return new Space();

            case("3"):
                return new Earth2();

            default:
                throw new IllegalArgumentException("invalid scene entry: "+ scene);
        }
    }

    public String getSceneNum() {
        return sceneNum;
    }

    public void setSceneNum(String num){
        // add addition conditions for invalid inputs
        sceneNum = num;
    }
}
