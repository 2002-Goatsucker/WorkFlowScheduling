package test;

import entity.DataPool;
import entity.TaskGraph;
import service.io.Input;
import service.io.impl.ConsoleInputImpl;
import service.io.impl.XMLInputImpl;
import utils.DataUtils;

public class TopologicalTest {
    public static void main(String[] args) {
        Input input=new ConsoleInputImpl();
        input.input();
        for(int i=0;i<20;++i){
            int[] num = DataUtils.getRandomTopologicalSorting();
            for(int n:num){
                System.out.print(n+" ");
            }
            System.out.println();
        }
//        for(int num:DataPool.graph.TopologicalSorting()){
//            System.out.println(num);
//        }
    }
}
