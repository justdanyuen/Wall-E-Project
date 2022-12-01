import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public interface Scene {
    String returnSceneFile();
    void drawScene(WorldModel world, Object[] entities, VirtualWorld v, ImageStore imageStore);

//    static void loadWorld(
//            WorldModel world, String filename, ImageStore imageStore)
//    {
//        try {
//            Scanner in = new Scanner(new File(filename));
//            world.load(in, imageStore);
//        }
//        catch (FileNotFoundException e) {
//            System.err.println(e.getMessage());
//        }
//    }

}