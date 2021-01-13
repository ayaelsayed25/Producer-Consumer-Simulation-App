// consumer problem.



import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;


public class Machine implements ISubject, Runnable{
    final int minimum = 100;
    final int maximum = 1000;
    Thread t;
    LinkedList<Queue> queues;
    Queue queue_after;
    Product currentProduct;
    String id ;
    mxCell vertex;
    mxGraph graph;
    boolean empty =  true;
    public Machine(String id, mxGraph graph ,Object parent,int x,int y,Queue queueBefore,Queue queue_after) {
        this.setId(id);
        this.graph=graph;
        queues = new LinkedList<>();
        addQueueBefore(queueBefore);
        setQueue_after(queue_after);
        addToqueue(this);
        drawMachine(graph,parent,x,y);

    }
    public void addToqueue(Machine machine){
        for (Queue q:queues){
            q.addMachine(machine);
        }
    }
    public void addQueueBefore(Queue queue){
        queues.add(queue);
    }
    public void drawMachine (mxGraph graph ,Object parent,int x,int y){
        vertex = (mxCell) graph.insertVertex(parent,"M"+id, "M"+id, x, y,30, 30,"strokeColor=#66FF00;fillColor=#ffffff;shape=ellipse");
        vertex.setEdge(false);
        vertex.setConnectable(true);
        vertex.setStyle("fillColor=#ffffff");
        graph.refresh();
        vertex.setAttribute("strokeColor","#66FF00");

        graph.insertEdge(parent, null, "", this.vertex,queue_after.vertex,"startArrow=none;strokeWidth=2;strokeColor=#66FF00");
        for(Queue q:queues) {
            graph.insertEdge(parent, null, "", q.vertex, this.vertex, "startArrow=none;strokeWidth=2;strokeColor=#66FF00");
        }
        }

    public Queue getQueue_after() {
        return queue_after;
    }

    public void setQueue_after(Queue queue_after) {
        this.queue_after = queue_after;
    }


    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) throws InterruptedException {
        this.currentProduct = currentProduct;
        System.out.println(Thread.getAllStackTraces().keySet().size());
        t= new Thread(this, "Thread " + id);
        t.start();
        System.out.println(Thread.getAllStackTraces().keySet().size());

        produce();
    }

    public LinkedList<Queue> getQueues() {
        return queues;
    }

    public void setQueues(LinkedList<Queue> queues) {
        this.queues = queues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
       this.id=id;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void produce() throws InterruptedException
    {
            vertex.setStyle("fillColor="+currentProduct.color);
            graph.refresh();
            Random r=new Random();
            int time= r.nextInt((maximum - minimum) + 1) + minimum;
            Thread.sleep(time);
            System.out.println("Product" + currentProduct.color + "by machine" + this.getId());
            consume();

    }

    // Function called by consumer thread
    public void consume() throws InterruptedException {
            vertex.setStyle("fillColor=#ffffff");
            graph.refresh();
            queue_after.addProduct(currentProduct);
            empty = true;
            t.join();
            System.out.println(t.isAlive());
            notifyAllObservers();
    }

    @Override
    public void notifyAllObservers() throws InterruptedException {
        for (Queue queue : queues) {
            queue.update();
        }
        queue_after.update();
    }

    @Override
    public void run() {
            try {
                notifyAllObservers();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}