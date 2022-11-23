package test;

import controller.PopulationController;
import controller.impl.NSGAIIPopulationController;
import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import entity.Type;
import service.algorithm.HEFT;
import service.io.Input;
import service.io.Output;
//import service.io.impl.ChartOutputImpl;
import service.io.impl.ConsoleOutputImpl;
import service.io.impl.XMLInputImpl;

import java.util.List;

public class HEFTtestor {
    public static void main(String[] args) {
        Type[] types = new Type[8];
        //bw :  bytes/sec
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
        Input input = new XMLInputImpl();
        input.input();

        for (int i = 0; i < DataPool.tasks.length; i++) {
            DataPool.tasks[i] = new Task(i);
            DataPool.tasks[i].setDataSize(3.43 * 1024 * 1024);
            DataPool.tasks[i].setReferTime(8.44);
        }

        PopulationController controller=new NSGAIIPopulationController();
        List<List<Chromosome>> list = controller.iterate();
        Chromosome chromosome = HEFT.generateChromosome();
        chromosome.print();
//        Output output=new ConsoleOutputImpl();
//        output.output(list);
    }
}
