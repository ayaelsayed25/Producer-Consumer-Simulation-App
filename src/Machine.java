//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import java.util.*;

public class Machine implements ISubject, Runnable {
    final int minimum = 1000;
    final int maximum = 5000;
    Thread t;
    LinkedList<Queue> queues;
    Queue queue_after;
    Product currentProduct;
    String id;
    mxCell vertex;
    mxGraph graph;
    int time;
    boolean empty = true;

    public Machine(String id, mxGraph graph, Object parent, int x, int y, LinkedList<Queue> queueBefore, Queue queue_after) {
        Random r = new Random();
        time = r.nextInt((maximum - minimum) + 1) + minimum;
        this.setId(id);
        this.graph = graph;
        this.queues = queueBefore;
        this.setQueue_after(queue_after);
        this.addToqueue(this);
        this.drawMachine(graph, parent, x, y);
    }

    public void addToqueue(Machine machine) {

        for (Queue q : this.queues) {
            q.addMachine(machine);
        }

    }

    public void addQueueBefore(Queue queue) {
        this.queues.add(queue);
    }

    public void drawMachine(mxGraph graph, Object parent, int x, int y) {
        this.vertex = (mxCell)graph.insertVertex(parent, "M" + this.id, "M" + this.id, x, y, 30.0D, 30.0D, "strokeColor=#66FF00;fillColor=#ffffff;shape=ellipse");
        this.vertex.setEdge(false);
        this.vertex.setConnectable(true);
        this.vertex.setStyle("fillColor=#ffffff");
        graph.refresh();
        this.vertex.setAttribute("strokeColor", "#66FF00");
        graph.insertEdge(parent, null, "", this.vertex, this.queue_after.vertex, "startArrow=none;strokeWidth=2;strokeColor=#66FF00");

        for (Queue q : this.queues) {
            graph.insertEdge(parent, null, "", q.vertex, this.vertex, "startArrow=none;strokeWidth=2;strokeColor=#66FF00");
            q.setEdge(false);
        }

    }

    public static boolean checkValidity(ArrayList<Queue> allQueues, LinkedList<Queue>toQueue,Queue after){
        if(toQueue.contains(after))
            return false;
        for (Queue queue : allQueues) {
            if (queue.isEdge() && !toQueue.contains(queue))
                return true;
        }
    return false;
    }

    public Queue getQueue_after() {
        return this.queue_after;
    }

    public void setQueue_after(Queue queue_after) {
        this.queue_after = queue_after;
    }

    public Product getCurrentProduct() {
        return this.currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) throws InterruptedException {
        this.currentProduct = currentProduct;
        System.out.println(Thread.getAllStackTraces().keySet().size());
        this.t = new Thread(this, "Thread " + this.id);
        this.t.start();
        System.out.println(Thread.getAllStackTraces().keySet().size());
        this.produce();
    }

    public LinkedList<Queue> getQueues() {
        return this.queues;
    }

    public void setQueues(LinkedList<Queue> queues) {
        this.queues = queues;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void produce() throws InterruptedException {
        this.vertex.setStyle("fillColor=" + this.currentProduct.color);
        this.graph.refresh();
        Timer timer = new Timer();
        String var10001 = this.currentProduct.color;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, time);
    }

    public void consume() throws InterruptedException {
        this.vertex.setStyle("fillColor=#ffffff");
        this.graph.refresh();
        this.queue_after.addProduct(this.currentProduct);
        this.empty = true;
        this.t.join();
        System.out.println(this.t.isAlive());
        this.notifyAllObservers();
    }

    public void notifyAllObservers() throws InterruptedException {

        for (Queue queue : this.queues) {
            queue.update();
        }

        this.queue_after.update();
    }

    public void run() {
            try {
                this.notifyAllObservers();
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }
}
