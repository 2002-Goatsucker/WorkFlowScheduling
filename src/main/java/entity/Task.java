package entity;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Task {
    private int index;
    private double dataSize;
    private double referTime;
    private double startTime;
    private double finalTime;
    private List<Integer> successor;
    private List<Integer> predecessor;
    public Task(int index){
        this.index = index;
        successor=new LinkedList<>();
        predecessor=new LinkedList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getDataSize() {
        return dataSize;
    }

    public void setDataSize(double dataSize) {
        this.dataSize = dataSize;
    }

    public double getReferTime() {
        return referTime;
    }

    public void setReferTime(double referTime) {
        this.referTime = referTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(double finalTime) {
        this.finalTime = finalTime;
    }

    public List<Integer> getSuccessor() {
        return successor;
    }

    public void setSuccessor(List<Integer> successor) {
        this.successor = successor;
    }

    public List<Integer> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(List<Integer> predecessor) {
        this.predecessor = predecessor;
    }

    public List<Integer> getSuccessors() {
        return successor;
    }
}