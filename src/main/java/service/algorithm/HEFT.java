package service.algorithm;

import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import entity.Type;

/**
 * Heterogeneous Earliest-Finish-Time (HEFT)
 * The HEFT algorithm selects the task with the highest upward rank value at each step
 * and assigns the selected task to the processor,
 * which minimizes its earliest finish time with an insertion-based approach.
 *
 * Another difference is in the processor selection phase,
 * which schedules the critical tasks onto the processor
 * that minimizes the total execution time of the critical tasks.
 */
public class HEFT {
    public static Chromosome generateChromosome(){
        Chromosome c =  new Chromosome();
        Task[] tasks = DataPool.tasks;
        int n = tasks.length;
        Type[] types = DataPool.types;
        double[] average_execution_time = new double[tasks.length];
        for (int i = 0; i < n; i++) {
            for (Type type : types) {
                average_execution_time[i] += tasks[i].getReferTime() / type.cu;
            }
        }
        for (int i = 0; i < n; i++) {
            average_execution_time[i] = average_execution_time[i]/ types.length;
        }


    }
}
