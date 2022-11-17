package controller.impl;

import entity.Chromosome;
import entity.DataPool;
import service.algorithm.impl.NSGAII;
import utils.DataUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NSGAIIPopulationController extends AbstractPopulationController{
    private final NSGAII nsgaii=new NSGAII();
    @Override
    public void doInitial() {
        int size=getSize();
        int[] taskOrder= DataPool.graph.TopologicalSorting();
        Chromosome chromosome = new Chromosome();
        chromosome.setTask(taskOrder);
        chromosome.setTask2ins(NSGAII.getRandomInstance(taskOrder.length));
        chromosome.setIns2type(NSGAII.getRandomType(taskOrder.length));
        chromosome.setCost(DataUtils.getCost(chromosome));
        chromosome.setMakeSpan(DataUtils.getMakeSpan(chromosome));
        for(int i=0;i<size;++i) {
            getFa().add(chromosome);
            NSGAII.mutateOrder(chromosome);
            chromosome.setTask2ins(NSGAII.getRandomInstance(taskOrder.length));
            chromosome.setIns2type(NSGAII.getRandomType(taskOrder.length));
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
        List<Chromosome> list=new LinkedList<>();
        list.addAll(getFa());
        list.addAll(getSon());
        for(Chromosome chromosome:list){
            chromosome.setBetterNum(0);
            chromosome.setPoorNum(0);
            chromosome.getPoor().clear();
            chromosome.getBetter().clear();
        }
        for(int i=0;i<list.size();++i){
            Chromosome chromosome=list.get(i);
            for(int j=i+1;j<list.size();++j){
                Chromosome other=list.get(j);
                if(chromosome.getMakeSpan()-other.getMakeSpan()<0.0001 && chromosome.getCost()-other.getCost()<0.0001){
                    chromosome.getPoor().add(other);
                    other.getBetter().add(chromosome);
                    chromosome.addPoor();
                    other.addBetter();
                }else if(other.getMakeSpan()-chromosome.getMakeSpan()<0.0001 && other.getCost()-chromosome.getCost()<0.0001){
                    other.getPoor().add(chromosome);
                    chromosome.getBetter().add(other);
                    chromosome.addBetter();
                    other.addPoor();
                }
            }
        }
        setRank(new LinkedList<>());
        while (hasBetter(list)) {
            for (Chromosome chromosome : list) {
                LinkedList<Chromosome> rankList = new LinkedList<>();
                if (chromosome.getBetterNum() == 0) {
                    chromosome.reduceBetter();
                    rankList.add(chromosome);
                    for (Chromosome worse : chromosome.getPoor()) {
                        worse.reduceBetter();
                    }
                }
                getRank().add(rankList);
            }
        }
    }

    private boolean hasBetter(List<Chromosome> list){
        for(Chromosome chromosome:list){
            if(chromosome.getBetterNum()>=0) return true;
        }
        return false;
    }

    @Override
    public void doEliminate() {
        getFa().clear();
        for(List<Chromosome> list:getRank()){
            if(getFa().size()+list.size()<=getSize()){
                getFa().addAll(list);
            }else {
                int i=0;
                while (getFa().size()<getSize()){
                    getFa().add(list.get(i++));
                }
            }
        }
    }
}
