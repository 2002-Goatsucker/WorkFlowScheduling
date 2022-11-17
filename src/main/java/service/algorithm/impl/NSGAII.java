package service.algorithm.impl;

import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import service.algorithm.EvolutionAlgorithm;
import utils.ConfigUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NSGAII implements EvolutionAlgorithm {
    public static Random random = new Random();
    public static int typeNumber;
    public static int insNumber;
    @Override
    public Chromosome mutate(Chromosome c) {
        Chromosome chromosome = null;
        try {
            chromosome = (Chromosome) c.clone();
            double rate = Double.parseDouble(ConfigUtils.get("evolution.population.mutation"));
            double r1 = random.nextDouble(0, 1);
            double r2 = random.nextDouble(0, 1);
            double r3 = random.nextDouble(0, 1);
            if (r1 < rate){
                mutateOrder(chromosome);
            }
            if (r2 < rate){
                mutateIns(chromosome);
            }
            if (r3 < rate){
                mutateType(chromosome);
            }



        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return chromosome;
    }
    public static void mutateOrder(Chromosome X) {
        int pos = random.nextInt(X.getTask().length);
        Chromosome nc = null;
        try {
            nc = (Chromosome) X.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        int n = nc.getTask().length;
        Task task = DataPool.tasks[nc.getTask()[pos]];
        int start = pos;
        int end = pos;
        while (start >= 0 && !task.getPredecessor().contains(DataPool.tasks[nc.getTask()[start]].getIndex())) {
            start--;
        }
        while (end < n && !task.getSuccessor().contains(DataPool.tasks[nc.getTask()[end]].getIndex())) {
            end++;
        }
        int posN = random.nextInt(end - start - 1) + start + 1;
        int temp = nc.getTask()[pos];
        if (posN < pos) {
            for (int i = pos; i > posN; i--) {
                nc.getTask()[i] = nc.getTask()[i-1];
            }
        } else if (pos < posN) {
            for (int i = pos ; i < posN; i++) {
                nc.getTask()[i] = nc.getTask()[i+1];
            }
        }
        nc.getTask()[posN] = temp;

    }

    public static void mutateIns(Chromosome X) {
        int number = X.getTask2ins().length;
        int p = random.nextInt(number);//generate the position where mutate occurs
        int instance = random.nextInt(insNumber);//m is the number of instances available
        X.getTask2ins()[p] = instance;
    }

    public static void mutateType(Chromosome X) {
        int number = X.getIns2type().length;
        int p = random.nextInt(number);//generate the position where mutate occurs
        int instance = random.nextInt(insNumber);//m is the number of instances available
        X.getIns2type()[p] = instance;
    }

    @Override
    public List<Chromosome> crossover(Chromosome A, Chromosome B) {
        Chromosome chromosome1 = null;
        Chromosome chromosome2 = null;
        try {
            chromosome1 = (Chromosome) A.clone();
            chromosome2 = (Chromosome) B.clone();
            crossoverOrder(chromosome1, chromosome2);
            crossoverIns(chromosome1, chromosome2);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        List<Chromosome> list = new ArrayList<Chromosome>();
        list.add(chromosome1);
        list.add(chromosome2);
        return list;

    }
    public static void crossoverOrder(Chromosome A, Chromosome B) {

        //n is the number of tasks
        int n = A.getTask().length;
        //random is a random number generator
        //p is the cut position
        int p = random.nextInt(n);
        int cursorA = p+1;
        int cursorB = p+1;
        int[] orderA = new int[n];
        int[] orderB = new int[n];
        //
        for (int i = 0; i <= p; i++) {
            orderA[i] = B.getTask()[i];
            orderB[i] = A.getTask()[i];
        }
        //这里我产生了一个问题，会不会在Chromosome里面把int[]改成List会更好
        for (int num : A.getTask()) {
            if (!isContains(orderA, 0, p, num)) {
                orderA[cursorA] = num;
                cursorA++;
            }
        }

        for (int num : B.getTask()) {
            if (!isContains(orderB, 0, p, num)) {
                orderB[cursorB] = num;
                cursorB++;
            }
        }

        A.setTask(orderA);
        B.setTask(orderB);
    }
    public static boolean isContains(int[] arr, int start, int end, int num) {
        for (int i = start; i <= end; ++i) {
            if (arr[i] == num) return true;
        }
        return false;
    }
    public static void crossoverIns(Chromosome A, Chromosome B) {
        Random random = new Random();
        int n = A.getTask().length;
        int p = random.nextInt(n);

        for (int i = 0; i < p; i++) {
            int task = A.getTask()[i];
            decideType(A, B, task, p);
            decideType(B, A, task, p);
            int temp = A.getTask2ins()[task];
            A.getTask2ins()[task] = B.getTask2ins()[task];
            B.getTask2ins()[task] = temp;
        }
    }
    public static void decideType(Chromosome A, Chromosome B, int task, int p) {
        int Instance = A.getTask2ins()[task];
        int TypeA = A.getIns2type()[Instance];
        int TypeB = B.getIns2type()[Instance];
        for (int i = p; i < A.getTask().length; i++) {
            if (B.getTask2ins()[task] == Instance) {
                if (TypeA != TypeB) {
                    int r = random.nextInt(2);
                    B.getIns2type()[Instance] = r == 0 ? TypeA : TypeB;
                    return;
                }
            }
        }
        B.getIns2type()[Instance] = TypeA;
        //mutate Pa with a small probability
        int r = random.nextInt(1000);
        if (r == 1) {
            TypeA = random.nextInt(typeNumber);
        }
    }


}
