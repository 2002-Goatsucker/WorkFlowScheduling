package entity;

import lombok.Data;

@Data
public class DataPool {
    public static Task[] tasks;
    public static TaskGraph graph;
}
