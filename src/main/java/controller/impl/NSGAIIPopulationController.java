package controller.impl;

import entity.Chromosome;
import entity.DataPool;
import service.algorithm.impl.NSGAII;
import utils.ConfigUtils;
import utils.DataUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NSGAIIPopulationController extends AbstractPopulationController {
    private final NSGAII nsgaii = DataPool.nsgaii;

    @Override
    public void doInitial() throws CloneNotSupportedException {
        int size = getSize();
//        Chromosome heft=HEFT.generateChromosome();
//        heft.setCost(DataUtils.getCost(heft));
//        heft.setMakeSpan(DataUtils.getMakeSpan(heft));
//        getFa().add(heft);
        while (getFa().size() < getSize()) {
            Chromosome chromosome=DataUtils.getRandomChromosome();
            if(!getFa().contains(chromosome)) getFa().add(chromosome);
        }
    }

    @Override
    public void doProduce() {
        try {
            Random random = new Random();
            while (getSon().size()<getFa().size()) {
                int num1 = random.nextInt(getSize());
                int num2 = random.nextInt(getSize());
                while (num1 == num2) {
                    num2 = random.nextInt(getSize());
                }
                Chromosome parent1 = getFa().get(num1).clone();
                Chromosome parent2 = getFa().get(num2).clone();
                List<Chromosome> childList = DataPool.nsgaii.crossover(parent1,parent2);
                Chromosome child1=childList.get(0);
                Chromosome child2=childList.get(1);
                if(random.nextInt(10000) < Double.parseDouble(ConfigUtils.get("evolution.population.mutation")) * 10000){
                    child1 = DataPool.nsgaii.mutate(child1);
                    child2 = DataPool.nsgaii.mutate(child2);
                }
                child1.setCost(DataUtils.getCost(child1));
                child1.setMakeSpan(DataUtils.getMakeSpan(child1));
                child2.setCost(DataUtils.getCost(child2));
                child2.setMakeSpan(DataUtils.getMakeSpan(child2));
                getSon().add(child1);
                getSon().add(child2);
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doSort() {
        List<Chromosome> list = new LinkedList<>();
        list.addAll(getFa());
        list.addAll(getSon());

        for (Chromosome chromosome : list) {
            chromosome.setBetterNum(0);
            chromosome.setPoorNum(0);
            chromosome.getPoor().clear();
            chromosome.getBetter().clear();
        }
        for (int i = 0; i < list.size(); ++i) {
            Chromosome chromosome = list.get(i);
            for (int j = i + 1; j < list.size(); ++j) {
                Chromosome other = list.get(j);
                if (chromosome.getMakeSpan() - other.getMakeSpan() < -0.0001 && chromosome.getCost() - other.getCost() < -0.0001) {
                    setBetterAndPoor(chromosome, other);
                } else if (other.getMakeSpan() - chromosome.getMakeSpan() < -0.0001 && other.getCost() - chromosome.getCost() < -0.0001) {
                    setBetterAndPoor(other, chromosome);
                } else if (Math.abs(chromosome.getMakeSpan() - other.getMakeSpan()) < 0.0001) {
                    if (chromosome.getCost() - other.getCost() < -0.0001) {
                        setBetterAndPoor(chromosome, other);
                    } else if (other.getCost() - chromosome.getCost() < -0.0001) {
                        setBetterAndPoor(other, chromosome);
                    }
                } else if (Math.abs(chromosome.getCost() - other.getCost()) < 0.0001) {
                    if (chromosome.getMakeSpan() - other.getMakeSpan() < -0.0001) {
                        setBetterAndPoor(chromosome, other);
                    } else if (other.getMakeSpan() - chromosome.getMakeSpan() < -0.0001) {
                        setBetterAndPoor(other, chromosome);
                    }
                }
            }
        }
        setRank(new LinkedList<>());
        while (hasBetter(list)) {
            LinkedList<Chromosome> rankList = new LinkedList<>();
            List<Chromosome> temp = new LinkedList<>();
            for (Chromosome chromosome : list) {
                if (chromosome.getBetterNum() == 0) {
                    chromosome.reduceBetter();
                    rankList.add(chromosome);
                    temp.add(chromosome);
                }
            }
            for (Chromosome chromosome : temp) {
                for (Chromosome worse : chromosome.getPoor()) {
                    worse.reduceBetter();
                }
            }
            getRank().add(rankList);
        }
    }

    private boolean hasBetter(List<Chromosome> list) {
        for (Chromosome chromosome : list) {
            if (chromosome.getBetterNum() >= 0) return true;
        }
        return false;
    }

    @Override
    public void doEliminate() {
        getFa().clear();
        for (List<Chromosome> list : getRank()) {
            for(Chromosome chromosome:list){
                if(!getFa().contains(chromosome)) getFa().add(chromosome);
                if(getFa().size()>=getSize()) return;
            }
        }
    }

    public void setBetterAndPoor(Chromosome better, Chromosome poor) {
        better.getPoor().add(poor);
        poor.getBetter().add(better);
        better.addPoor();
        poor.addBetter();
    }

}
