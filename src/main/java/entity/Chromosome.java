package entity;

import java.util.LinkedList;
import java.util.List;

public class Chromosome implements Cloneable {
    private int[] task;
    private int[] task2ins;
    private int[] ins2type;
    private double cost;
    private double makeSpan;

    private final List<Chromosome> better=new LinkedList<>();
    private final List<Chromosome> poor=new LinkedList<>();

    private int betterNum;
    private int poorNum;

    public void setBetterNum(int betterNum) {
        this.betterNum = betterNum;
    }

    public void setPoorNum(int poorNum) {
        this.poorNum = poorNum;
    }

    public int getBetterNum() {
        return betterNum;
    }

    public int getPoorNum() {
        return poorNum;
    }

    public void addBetter(){
        betterNum++;
    }
    public void addPoor(){
        poorNum++;
    }
    public void reduceBetter(){
        betterNum--;
    }
    public void reducePoor(){
        poorNum--;
    }

    public List<Chromosome> getBetter() {
        return better;
    }

    public List<Chromosome> getPoor() {
        return poor;
    }

    public int[] getTask() {
        return task;
    }

    public void setTask(int[] task) {
        this.task = task;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getMakeSpan() {
        return makeSpan;
    }

    public void setMakeSpan(double makeSpan) {
        this.makeSpan = makeSpan;
    }

    public int[] getTask2ins() {
        return task2ins;
    }

    public void setTask2ins(int[] task2ins) {
        this.task2ins = task2ins;
    }

    public int[] getIns2type() {
        return ins2type;
    }

    public void setIns2type(int[] ins2type) {
        this.ins2type = ins2type;
    }

    @Override
    public Chromosome clone() throws CloneNotSupportedException {
        super.clone();
        Chromosome chromosome = new Chromosome();
        chromosome.task = new int[task.length];
        chromosome.task2ins = new int[task2ins.length];
        chromosome.ins2type = new int[ins2type.length];
        chromosome.cost = cost;
        chromosome.makeSpan = makeSpan;
        System.arraycopy(task, 0, chromosome.task, 0, task.length);
        System.arraycopy(task2ins, 0, chromosome.task2ins, 0, task2ins.length);
        System.arraycopy(ins2type, 0, chromosome.ins2type, 0, ins2type.length);
        return chromosome;
    }
}
