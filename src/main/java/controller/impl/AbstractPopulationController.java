package controller.impl;

import controller.PopulationController;
import entity.Chromosome;
import utils.ConfigUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractPopulationController implements PopulationController {
    private final int size;
    private final int generation;
    private final double mutation;

    private final List<Chromosome> fa;
    private final List<Chromosome> son;


    public AbstractPopulationController(){
        size = Integer.parseInt(ConfigUtils.get("evolution.population.size"));
        generation = Integer.parseInt(ConfigUtils.get("evolution.population.generation"));
        mutation = Double.parseDouble(ConfigUtils.get("evolution.population.mutation"));
        fa=new LinkedList<>();
        son=new LinkedList<>();
    }

    @Override
    public void initialPopulation() {
        doInitial();
    }
    @Override
    public void produceOffspring() {

    }

    @Override
    public void sorting() {

    }

    @Override
    public void eliminate() {

    }

    @Override
    public List<Chromosome> iterate() {
        int generation = Integer.parseInt(ConfigUtils.get("evolution.population.generation"));
        doInitial();
        for(int i=0;i<generation;++i){
            doProduce();
            doSort();
            doEliminate();
        }
        return fa;
    }



    public abstract void doInitial();
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
