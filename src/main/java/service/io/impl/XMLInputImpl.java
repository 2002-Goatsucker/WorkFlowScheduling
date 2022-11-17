package service.io.impl;

import entity.DataPool;
import entity.TaskGraph;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import service.io.Input;

import java.util.ResourceBundle;

public class XMLInputImpl implements Input {
    @Override
    public void input() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            int size= Integer.parseInt(bundle.getString("file.taskGraph.size"));
            DataPool.graph=new TaskGraph(size);

            SAXReader reader=new SAXReader();
            Document document=reader.read(XMLInputImpl.class.getClassLoader().getResource(bundle.getString("file.taskGraph.path")));
            Element root=document.getRootElement();
            for(Element child:root.elements()){
                if(child.getName().equals("child")){
                    int ver2=Integer.parseInt(child.getText().substring(2));
                    for(Element parent:root.elements()){
                        int ver1= Integer.parseInt(parent.getText().substring(2));
                        DataPool.graph.addEdge(ver1,ver2);
                    }
                }
            }

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
