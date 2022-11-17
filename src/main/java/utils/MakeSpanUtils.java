package utils;

import com.sun.tools.javac.Main;
import entity.Chromosome;
import entity.DataPool;
import entity.Task;
import entity.Type;

public class MakeSpanUtils {
    public static Type[] types;

    public static double getCompTime(double referTime,double cu){
        return referTime/cu;
    }

    public static double getCommTime(double dataSize,double brandWidth){
        return dataSize/brandWidth;
    }

    public static double getStartTime(double availableTime, int task, double dataSize, double brandWidth){
        double max = 0;
        for(int temp: DataPool.tasks[task].getPredecessor()){
            double bw = Math.min(brandWidth,types[temp].bw);
            double commTime = getCommTime(dataSize,bw);
            max = Math.max(max,commTime+DataPool.tasks[temp].getFinalTime());
        }
        return Math.max(availableTime,max);
    }


}
