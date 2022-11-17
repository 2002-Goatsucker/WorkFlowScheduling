package controller.impl;

import utils.ConfigUtils;

import java.util.ResourceBundle;

public abstract class AbstractPopulationController {
    private int size;
    private double mutation;

    public void initiatePopulation(){
        size= Integer.parseInt(ConfigUtils.get("evolution.population.size"));

    }



}
