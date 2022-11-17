package utils;

import entity.Chromosome;
import entity.Task;
import entity.Type;

import static controller.impl.NSGAIIPopulationController.insNumber;
import static controller.impl.NSGAIIPopulationController.types;
import static entity.DataPool.tasks;

public class DataUtils {
    public static double getMakeSpan(Chromosome chromosome){
        double[] availableTime = new double[insNumber];
        Type[] types = new Type[chromosome.getTask().length];
        for (int i = 0; i < types.length; i++) {
            types[i] = types[chromosome.getIns2type()[chromosome.getTask2ins()[i]]];
        }
        MakeSpanUtils.types = types;
        double exitTime = 0;
        for (Task task : tasks) {
            int insIndex = chromosome.getTask2ins()[task.getIndex()];
            int typeIndex = chromosome.getIns2type()[chromosome.getTask2ins()[task.getIndex()]];
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
            sum += types[i].p;
        }
        return sum;
    }
}
