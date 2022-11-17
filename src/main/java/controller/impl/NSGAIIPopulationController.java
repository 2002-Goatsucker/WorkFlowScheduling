package controller.impl;

import entity.Chromosome;
import entity.DataPool;
import entity.Type;
import utils.DataUtils;

import java.util.Random;

public class NSGAIIPopulationController extends AbstractPopulationController {
    public static Random random = new Random();
    public static int insNumber = 0;
    public static int typeNumber = 0;
    public static Type[] types = new Type[typeNumber];
    @Override
    public void doInitial() {
        int size = getSize();

        int[] taskOrder = DataPool.graph.TopologicalSorting();
        int n = taskOrder.length;
        int insNumber = n;
        int typeNumber = 8;
        Chromosome chromosome = new Chromosome();
        chromosome.setTask(taskOrder);
        chromosome.setTask2ins(getRandomInstance(n));
        chromosome.setIns2type(getRandomType(n));
        chromosome.setCost(DataUtils.getCost(chromosome));
        chromosome.setMakeSpan(DataUtils.getMakeSpan(chromosome));


    }

    @Override
    public void doProduce() {

    }

    @Override
    public void doSort() {

    }

    @Override
    public void doEliminate() {

    }

    public static int[] getRandomInstance(int size) {
        int[] instances = new int[size];
        for (int i = 0; i < size; i++) {
            instances[i] = random.nextInt(insNumber);
        }
        return instances;
    }
    public static int[] getRandomType(int size) {
        int[] types = new int[size];
        for (int i = 0; i < size; i++) {
            types[i] = random.nextInt(insNumber);
        }
        return types;
    }
}
