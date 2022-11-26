import controller.PopulationController;
import controller.impl.NSGAIIPopulationController;
import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import entity.Type;
import service.algorithm.CostMin;
import service.io.Input;
import service.io.Output;
import service.io.impl.*;
import utils.DataUtils;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        Type[] types = new Type[8];
        types[0] = new Type(0, 1.7/8, 39321600, 0.06);
        types[1] = new Type(1, 3.75/8, 85196800, 0.12);
        types[2] = new Type(2, 3.75/8, 85196800, 0.113);
        types[3] = new Type(3, 7.5/8, 85196800, 0.24);
        types[4] = new Type(4, 7.5/8, 85196800, 0.225);
        types[5] = new Type(5, 15/8, 131072000, 0.48);
        types[6] = new Type(6, 15/8, 131072000, 0.45);
        types[7] = new Type(7, 30/8, 131072000, 0.9);
        DataPool.typeNum = 8;
        DataPool.types = types;
        Input input=new XMLInputImpl();
        input.input();
        PopulationController controller=new NSGAIIPopulationController();
        List<List<Chromosome>> list = controller.iterate();
        Output output=new ChartOutputImpl();
        Output output1=new ConsoleOutputImpl();
        Output output2=new FileOutputImpl();
        output.output(list);
        output1.output(list);
        output2.output(list);
        System.out.println(DataUtils.getHV(500,100,list.get(0)));

    }
}
