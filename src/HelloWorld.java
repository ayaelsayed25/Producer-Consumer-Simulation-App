import javax.swing.*;


import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.*;
import java.util.LinkedList;

public class HelloWorld extends JFrame
{
    private static final long serialVersionUID = -2707712944901661771L;
    static mxGraph graph = new mxGraph();
    static Object parent = graph.getDefaultParent();

    public HelloWorld() throws InterruptedException {
        super("Hello, World!");

       graph.setCellsSelectable(false);
        graph.getModel().beginUpdate();
        graph.getModel().endUpdate();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);








    }

    public static void simulate() throws InterruptedException {
        Queue after = new Queue("2",graph,parent,200,50);
        LinkedList<Queue> queue1 = new LinkedList<>();
        Queue start = new Queue("1",graph,parent,500,50);
        queue1.add(start);

        LinkedList<Queue> queue2 = new LinkedList<>();
        queue2.add(after);


        start.addProduct(new Product());
        start.addProduct(new Product());
        start.addProduct(new Product());


        Machine m1 = new Machine("1",graph,parent,250,50,queue1,after);
        Machine m2 = new Machine("2",graph,parent,100,50,queue2,new Queue("3",graph,parent,50,50));


        start.sendProduct();
        System.out.println(Thread.getAllStackTraces().keySet().size());


    }
    public static void main(String[] args) throws InterruptedException {
        HelloWorld frame = new HelloWorld();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.simulate();




    }

}