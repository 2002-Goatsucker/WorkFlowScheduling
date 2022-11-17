package controller.impl;

import entity.Chromosome;
import entity.DataPool;
import utils.DataUtils;

public class NSGAIIPopulationController extends AbstractPopulationController{

    @Override
    public void doInitial() {
        int size=getSize();
        int[] taskOrder= DataPool.graph.TopologicalSorting();
        Chromosome chromosome=new Chromosome();
        chromosome.setTask(taskOrder);
        chromosome.setTask2ins(null);
        chromosome.setIns2type(null);
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
}
