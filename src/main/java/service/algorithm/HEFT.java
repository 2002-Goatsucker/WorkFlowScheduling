package service.algorithm;

import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import entity.Type;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Heterogeneous Earliest-Finish-Time (HEFT)
 * The HEFT algorithm selects the task with the highest upward rank value at each step
 * and assigns the selected task to the processor,
 * which minimizes its earliest finish time with an insertion-based approach.
 *
 * Another difference is in the processor selection phase,
 * which schedules the critical tasks onto the processor
 * that minimizes the total execution time of the critical tasks.
 *
 * Because here we are in the cloud environment, we have infinite instances and infinite types,
 * we can all choose the best performance type.
 * All we need to do is decide which instance to take.
 */
public class HEFT {
    public static Chromosome generateChromosome(){
        Chromosome c =  new Chromosome();
        Task[] tasks = DataPool.tasks;
        int n = tasks.length;
        Type[] types = DataPool.types;
        Type best_type = types[0];
        for(Type type : types){
            if (type.cu > best_type.cu){
                best_type = type;
            }
        }
        int instance_index = 0;
        int order_index = 0;
        int[] order = new int[n];
        int[] task2ins = new int[n];
        int[] ins2type = new int[n];
        for (int i = 0; i < n; i++) {
            ins2type[i] = best_type.id;
        }
        int task_index = 0;
        for (int i = 0; i < n; i++) {
            if (tasks[i].getPredecessor().size()==0){
                task_index = i;
                break;
            }
        }
        order[order_index] = task_index;
        order_index++;
        task2ins[task_index] = instance_index;
        instance_index++;
        List<Integer> finished_tasks = new ArrayList<Integer>();
        Queue queue = new LinkedList();
        queue.add(task_index);
        while(!queue.isEmpty()){
            int index_maxsize = 0;
            double maxsize = 0;
            task_index = (int)queue.remove();
            instance_index = task2ins[task_index];
            finished_tasks.add(task_index);
//            tasks[task_index].getSuccessor().stream()
            for (Integer integer : tasks[task_index].getSuccessor()){
                if (tasks[integer].getDataSize() > maxsize && !finished_tasks.contains(integer)){
                    index_maxsize = integer;
                    queue.add(integer);
                }
            }
            order[order_index] = index_maxsize;
            order_index++;
            task2ins[index_maxsize] = task2ins[task_index];
            for (Integer integer : tasks[task_index].getSuccessor()){
                instance_index++;
                if (integer != index_maxsize && !finished_tasks.contains(integer)){
                    order[order_index] = integer;
                    order_index++;
                    task2ins[integer] = instance_index;
                }
            }


        }
        c.setTask(order);
        c.setTask2ins(task2ins);
        c.setIns2type(ins2type);

        return c;
    }
}