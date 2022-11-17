package service.algorithm.impl;

import entity.Chromosome;
import service.algorithm.EvolutionAlgorithm;

import java.util.List;
import java.util.Random;

public class NSGAII implements EvolutionAlgorithm {
    public static Random random = new Random();
    public static int typeNumber;
    @Override
    public Chromosome mutate(Chromosome c, double mutate_rate) {

    }

    @Override
    public List<Chromosome> crossover(Chromosome A, Chromosome B) {

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
