package service.algorithm;

import entity.Chromosome;

import java.util.List;

public interface EvolutionAlgorithm {
    public Chromosome mutate();
    public List<Chromosome> crossover();

}
