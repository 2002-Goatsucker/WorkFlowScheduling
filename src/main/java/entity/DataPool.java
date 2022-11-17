package entity;

import lombok.Data;

@Data
public class DataPool {
    private static Task[] tasks;
    private static TaskGraph graph;
}
