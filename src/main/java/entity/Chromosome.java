package entity;
import lombok.Data;

@Data
public class Chromosome {
    private int[] task;
    private double cost;
    private double makeSpan;
}
