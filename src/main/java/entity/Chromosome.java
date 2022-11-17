package entity;

public class Chromosome {
    private int[] task;
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
}
