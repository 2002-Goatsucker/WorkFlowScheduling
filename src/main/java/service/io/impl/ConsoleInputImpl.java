package service.io.impl;

import entity.DataPool;
import entity.Task;
import entity.TaskGraph;
import service.io.Input;

import java.util.Scanner;

public class ConsoleInputImpl implements Input {
    @Override
    public void input() {
        Scanner input = new Scanner(System.in);
        //规则阐释
        System.out.println("--------------------Input start--------------------");
        System.out.println("Rules as follows:");
        System.out.println("1. Please input your tasks which is a Directed Acyclic Graph");
        System.out.println("2. First line should be a integer means the quantity of tasks");
        System.out.println("3. Then please input two integers each lines, for example: \"0 1\" means there is an edge from ver0->ver1");
        System.out.println("4. Finally please input 'x' to finish your input");
        int total = input.nextInt();
        //初始化数据池(task及其依赖关系，graph用作拓扑排序)
        DataPool.tasks=new Task[total];
        DataPool.graph=new TaskGraph(total);
        for(int i=0;i<DataPool.tasks.length;++i){
            DataPool.tasks[i]=new Task(i);
        }
        while (true) {
            String str=input.nextLine();
            if(str.equals("x")) break;
            else {
                int ver1=Integer.parseInt(input.next());
                int ver2=Integer.parseInt(input.next());
                DataPool.tasks[ver1].getSuccessor().add(ver2);
                DataPool.tasks[ver2].getPredecessor().add(ver1);
                DataPool.graph.addEdge(ver1,ver2);
            }
        }
        System.out.println("--------------------End line--------------------");
    }
}
