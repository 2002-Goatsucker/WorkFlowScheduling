package controller.impl;

import controller.PopulationController;
import entity.Chromosome;
import entity.DataPool;
import service.algorithm.impl.NSGAII;
import utils.ConfigUtils;
import utils.DataUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class AbstractPopulationController implements PopulationController {
    private final int size;
    private final int generation;
    private final double mutation;

    private List<Chromosome> fa;
    private final List<Chromosome> son;

    private List<List<Chromosome>> rank;

    public List<List<Chromosome>> getRank() {
        return rank;
    }

    public void setRank(List<List<Chromosome>> rank) {
        this.rank = rank;
    }

    public AbstractPopulationController() {
        size = Integer.parseInt(ConfigUtils.get("evolution.population.size"));
        generation = Integer.parseInt(ConfigUtils.get("evolution.population.generation"));
        mutation = Double.parseDouble(ConfigUtils.get("evolution.population.mutation"));
        fa = new LinkedList<>();
        son = new LinkedList<>();
    }

    @Override
    public void initialPopulation() {
        try {
            doInitial();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void produceOffspring() {
        doProduce();
    }

    @Override
    public void sorting() {
        doSort();
    }

    @Override
    public void eliminate() {
        doEliminate();
    }

    @Override
    public List<List<Chromosome>> iterate() {
        int generation = Integer.parseInt(ConfigUtils.get("evolution.population.generation"));
        try {
            doInitial();
            if(generation==0) {
                List<List<Chromosome>> list=new ArrayList<>();
                list.add(fa);
                return list;
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < generation; ++i) {
            doProduce();
            doSort();
            doEliminate();
            son.clear();
            for (Chromosome chromosome : fa) {
                chromosome.setBetterNum(0);
                chromosome.setPoorNum(0);
                chromosome.getBetter().clear();
                chromosome.getPoor().clear();
            }
            List<Chromosome> temp = fa.stream().distinct().toList();
            temp = new LinkedList<>(temp);
            while (temp.size() < getSize()) {
                Chromosome chromosome = DataUtils.getRandomChromosome();
                if(!temp.contains(chromosome)) getFa().add(chromosome);
            }
            fa = temp;
        }
        return rank;
    }


    public abstract void doInitial() throws CloneNotSupportedException;

    public abstract void doProduce();

    public abstract void doSort();

    public abstract void doEliminate();

    public int getSize() {
        return size;
    }

    public int getGeneration() {
        return generation;
    }

    public double getMutation() {
        return mutation;
    }

    public List<Chromosome> getFa() {
        return fa;
    }

    public List<Chromosome> getSon() {
        return son;
    }
}
