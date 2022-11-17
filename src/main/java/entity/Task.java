package entity;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
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
}