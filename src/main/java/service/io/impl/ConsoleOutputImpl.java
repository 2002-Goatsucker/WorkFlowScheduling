package service.io.impl;

import entity.Chromosome;
import service.io.Output;

import java.util.List;

public class ConsoleOutputImpl implements Output {
    @Override
    public void output(List<Chromosome> list) {
        System.out.println("--------------------Pareto Set--------------------");
        list.forEach(c->System.out.println("MakeSpan: "+c.getMakeSpan()+" & Cost: "+c.getCost()));
        System.out.println("--------------------End Line--------------------");
    }
}
