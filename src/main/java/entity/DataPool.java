package entity;


import service.algorithm.impl.NSGAII;

public class DataPool {
    public static Task[] tasks;
    public static TaskGraph graph;

    public static NSGAII nsgaii = new NSGAII();
    public static int insNum;
    public static int typeNum;
    public static Type[] types;

}
