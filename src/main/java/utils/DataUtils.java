package utils;

import controller.impl.AbstractPopulationController;
import entity.Chromosome;
import entity.DataPool;
import service.algorithm.impl.NSGAII;

import java.util.Random;
import entity.Task;
import entity.Type;
import static entity.DataPool.tasks;

public class DataUtils {
    private static final Random random=new Random();
    public static double getMakeSpan(Chromosome chromosome) {
        double[] availableTime = new double[DataPool.insNum];
        Type[] types = new Type[chromosome.getTask().length];
        for (int i = 0; i < types.length; i++) {
            types[i] = DataPool.types[chromosome.getIns2type()[chromosome.getTask2ins()[i]]];
        }
        MakeSpanUtils.types = types;
        double exitTime = 0;
        for (int i : chromosome.getTask()) {
            Task task=DataPool.tasks[i];
            int insIndex = chromosome.getTask2ins()[i];
            int typeIndex = chromosome.getIns2type()[insIndex];
            if (task.getPredecessor().size() == 0) {
                task.setStartTime(Math.max(0, availableTime[insIndex]));
                task.setFinalTime(task.getStartTime() + MakeSpanUtils.getCompTime(task.getReferTime(), types[typeIndex].cu));
                availableTime[insIndex] = task.getFinalTime();
            } else {
                task.setStartTime(MakeSpanUtils.getStartTime(availableTime[insIndex], task.getIndex(), task.getDataSize(), types[typeIndex].bw));
                task.setFinalTime(task.getStartTime() + MakeSpanUtils.getCompTime(task.getReferTime(), types[typeIndex].cu));
                availableTime[insIndex] = task.getFinalTime();
            }
            if (task.getSuccessor().size() == 0) {
                exitTime = Math.max(exitTime, task.getFinalTime());
            }
        }
        return exitTime;
    }

    public static double getCost(Chromosome chromosome){
        double sum = 0;
        for (int i : chromosome.getIns2type()) {
            sum += DataPool.types[i].p;
        }
        return sum;
    }

    public static Chromosome getBetter(Chromosome chromosome1, Chromosome chromosome2) throws CloneNotSupportedException {

        Chromosome c1=chromosome1.clone();
        Chromosome c2=chromosome2.clone();

        NSGAII.crossoverOrder(c1,c2);
        NSGAII.crossoverIns(c1,c2);

        if(random.nextInt(10000) < Double.parseDouble(ConfigUtils.get("evolution.population.mutation")) * 10000){
            DataPool.nsgaii.mutate(c1);
            DataPool.nsgaii.mutate(c2);
        }
        c1.setMakeSpan(getMakeSpan(c1));
        c1.setCost(getCost(c1));
        c2.setMakeSpan(getMakeSpan(c2));
        c2.setCost(getCost(c2));

        if (c1.getCost() - c2.getCost() < -0.0001 && c1.getMakeSpan() - c2.getMakeSpan() < -0.0001) {
            return c1;
        }

        if (c2.getCost() - c1.getCost() < -0.0001 && c2.getMakeSpan() - c1.getMakeSpan() < -0.0001) {
            return c2;
        } else return random.nextInt(2) == 0 ? c1 : c2;

    }

}
