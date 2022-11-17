package controller.impl;

import utils.ConfigUtils;

import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractPopulationController {
    private final int size;
    private final int generation;
    private final double mutation;

    public AbstractPopulationController(){
        size = Integer.parseInt(ConfigUtils.get("evolution.population.size"));
        generation = Integer.parseInt(ConfigUtils.get("evolution.population.generation"));
        mutation = Double.parseDouble(ConfigUtils.get("evolution.population.mutation"));
    }


    public abstract void initiatePopulation();

    public abstract




}
