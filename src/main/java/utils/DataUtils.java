package utils;
import entity.*;
import service.algorithm.impl.NSGAII;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class DataUtils {
    private static final Random random=new Random();
    public static double getMakeSpan(Chromosome chromosome) {
        double[] availableTime = new double[DataPool.insNum];
        double exitTime = 0;
        for (int taskIndex : chromosome.getTask()){
            Task task = DataPool.tasks[taskIndex].clone();
            int insIndex = chromosome.getTask2ins()[taskIndex];
            int typeIndex = chromosome.getIns2type()[insIndex];
            if (task.getPredecessor().size() == 0) {
                chromosome.getStart()[taskIndex] = Math.max(0, availableTime[insIndex]);
                chromosome.getEnd()[taskIndex] = chromosome.getStart()[taskIndex] + task.getReferTime() / DataPool.types[typeIndex].cu;
                availableTime[insIndex] = chromosome.getEnd()[taskIndex];
            } else {
                chromosome.getStart()[taskIndex] = Math.max(getStartTime(chromosome, taskIndex), availableTime[insIndex]);
                chromosome.getEnd()[taskIndex] = chromosome.getStart()[taskIndex] + DataPool.tasks[taskIndex].getReferTime() / DataPool.types[typeIndex].cu;
                availableTime[insIndex] = chromosome.getEnd()[taskIndex];
            }
            if (task.getSuccessor().size() == 0) {
                exitTime = Math.max(exitTime, chromosome.getEnd()[taskIndex]);
            }
        }
        return exitTime;
    }

    public static double getCost(Chromosome chromosome){
        double sum = 0;
        for (int taskIndex : chromosome.getTask()) {
            int instanceIndex = chromosome.getTask2ins()[taskIndex];
            int typeIndex = chromosome.getIns2type()[instanceIndex];
            sum += DataPool.types[typeIndex].p * (DataPool.tasks[taskIndex].getReferTime()/DataPool.types[typeIndex].cu);
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

    public static Chromosome getRandomChromosome(){
        int[] taskOrder = DataUtils.getRandomTopologicalSorting();
        Chromosome chromosome = new Chromosome();
        chromosome.setTask(taskOrder);
        chromosome.setTask2ins(NSGAII.getRandomInstance(taskOrder.length));
        chromosome.setIns2type(NSGAII.getRandomType(taskOrder.length));
        chromosome.setCost(DataUtils.getCost(chromosome));
        chromosome.setMakeSpan(DataUtils.getMakeSpan(chromosome));
        return chromosome;
    }

    public static Chromosome getInitialChromosome(){
        int[] order=DataUtils.getRandomTopologicalSorting();
        int[] ins=new int[order.length];
        int[] type=new int[order.length];
        int num=random.nextInt(10);
        if(num<5) {
            for(int i=0;i<ins.length;++i){
                ins[i]=random.nextInt(order.length);
            }
        }
        int t=random.nextInt(DataPool.types.length);
        Arrays.fill(type, t);
        Chromosome chromosome =  new Chromosome(order,ins,type);
        chromosome.setCost(DataUtils.getCost(chromosome));
        chromosome.setMakeSpan(DataUtils.getMakeSpan(chromosome));
        return chromosome;
    }

    public static int[] getRandomTopologicalSorting(){
        TaskGraph graph=DataPool.graph.clone();
        return graph.TopologicalSorting();
    }

    public static double getStartTime(Chromosome chromosome, int taskIndex){
        List<Integer> preTaskIndexes = DataPool.tasks[taskIndex].getPredecessor();
        int n = preTaskIndexes.size();
        int instanceIndex = chromosome.getTask2ins()[taskIndex];
        int typeIndex = chromosome.getIns2type()[instanceIndex];
        double[] communicationTime = new double[n];
        double[] after_communicationTime = new double[n];
        for (int i = 0; i < n; i++) {
            int preTaskIndex = preTaskIndexes.get(i);
            int preInstanceIndex = chromosome.getTask2ins()[preTaskIndex];
            int preTypeIndex = chromosome.getIns2type()[preInstanceIndex];
            double bw_min = Math.min(DataPool.types[typeIndex].bw, DataPool.types[preTypeIndex].bw);
            communicationTime[i] = DataPool.tasks[preTaskIndex].getDataSize() / bw_min;
            after_communicationTime[i] = chromosome.getEnd()[preTaskIndex] + communicationTime[i];
        }
        double max_after_communication_time = 0;
        for(double time : after_communicationTime){
            if(time > max_after_communication_time){
                max_after_communication_time = time;
            }
        }
        return max_after_communication_time;
    }
}
