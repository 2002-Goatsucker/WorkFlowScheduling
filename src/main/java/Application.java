import controller.PopulationController;
import controller.impl.NSGAIIPopulationController;
import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import entity.Type;
import service.io.Input;
import service.io.Output;
import service.io.impl.ChartOutputImpl;
import service.io.impl.ConsoleInputImpl;
import service.io.impl.ConsoleOutputImpl;
import service.io.impl.XMLInputImpl;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        Type[] types = new Type[8];
        types[0] = new Type(0, 1.7, 39321600, 0.06);
        types[1] = new Type(1, 3.75, 85196800, 0.12);
        types[2] = new Type(2, 3.75, 85196800, 0.113);
        types[3] = new Type(3, 7.5, 85196800, 0.24);
        types[4] = new Type(4, 7.5, 85196800, 0.225);
        types[5] = new Type(5, 15, 131072000, 0.48);
        types[6] = new Type(6, 15, 131072000, 0.45);
        types[7] = new Type(7, 30, 131072000, 0.9);
        DataPool.typeNum = 8;
        DataPool.types = types;
        Input input=new XMLInputImpl();
        input.input();
        PopulationController controller=new NSGAIIPopulationController();
        List<List<Chromosome>> list = controller.iterate();
        Output output=new ChartOutputImpl();
        output.output(list);
    }
}
