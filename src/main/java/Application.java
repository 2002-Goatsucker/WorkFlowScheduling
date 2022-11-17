import controller.PopulationController;
import controller.impl.NSGAIIPopulationController;
import entity.DataPool;
import entity.Type;
import service.io.Input;
import service.io.impl.ConsoleInputImpl;

public class Application {
    public static void main(String[] args) {
        Type[] types = new Type[8];
        types[0] = new Type(0, 1.7f, 39321600, 0.06f);
        types[1] = new Type(1, 3.75f, 85196800, 0.12f);
        types[2] = new Type(2, 3.75f, 85196800, 0.113f);
        types[3] = new Type(3, 7.5f, 85196800, 0.24f);
        types[4] = new Type(4, 7.5f, 85196800, 0.225f);
        types[5] = new Type(5, 15f, 131072000, 0.48f);
        types[6] = new Type(6, 15f, 131072000, 0.45f);
        types[7] = new Type(7, 30f, 131072000, 0.9f);
        DataPool.typeNum = 8;
        DataPool.types = types;
        Input input=new ConsoleInputImpl();
        PopulationController controller=new NSGAIIPopulationController();
        controller.iterate();

    }
}
