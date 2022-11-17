package entity;

public class Chromosome {
    private int[] task;
    private int[] task2ins;
    private int[] ins2type;
    private double cost;
    private double makeSpan;


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
}
