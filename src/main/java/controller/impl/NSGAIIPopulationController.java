package controller.impl;

import entity.Chromosome;
import entity.DataPool;
import service.algorithm.impl.NSGAII;
import utils.DataUtils;

import java.util.Random;

public class NSGAIIPopulationController extends AbstractPopulationController{
    private final NSGAII nsgaii=new NSGAII();
    @Override
    public void doInitial() {
        int size=getSize();
        int[] taskOrder= DataPool.graph.TopologicalSorting();
        Chromosome chromosome = new Chromosome();
        chromosome.setTask(taskOrder);
        chromosome.setTask2ins(null);
        chromosome.setIns2type(null);
        chromosome.setCost(DataUtils.getCost(chromosome));
        chromosome.setMakeSpan(DataUtils.getMakeSpan(chromosome));
        for(int i=0;i<size;++i) {
            getFa().add(chromosome);
            NSGAII.mutateOrder(chromosome);
            chromosome.setTask2ins(null);
            chromosome.setIns2type(null);
            chromosome.setCost(DataUtils.getCost(chromosome));
            chromosome.setMakeSpan(DataUtils.getMakeSpan(chromosome));
        }
    }

    @Override
    public void doProduce() {
        try {
            Random random=new Random();
            for(int i=0;i<getSize();++i){
                int num1=random.nextInt(getSize());
                int num2=random.nextInt(getSize());
                while (num1==num2){
                    num2=random.nextInt(getSize());
                }
                Chromosome child1=getFa().get(num1);
                Chromosome child2= getFa().get(num2);
                Chromosome child=DataUtils.getBetter(child1,child2);
                getSon().add(child);
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doSort() {

    }

    @Override
    public void doEliminate() {

    }
}
