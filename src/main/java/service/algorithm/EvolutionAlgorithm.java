package service.algorithm;

import entity.Chromosome;

import java.util.List;

public interface EvolutionAlgorithm {
    public abstract Chromosome mutate(Chromosome c);
    public abstract List<Chromosome> crossover(Chromosome a, Chromosome b);

}
