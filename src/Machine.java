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

    public Machine(String id, mxGraph graph, Object parent, int x, int y, Queue queueBefore, Queue queue_after) {
        Random r = new Random();
        time = r.nextInt((maximum - minimum) + 1) + minimum;
        this.setId(id);
        this.graph = graph;
        this.queues = new LinkedList();
        this.addQueueBefore(queueBefore);
        this.setQueue_after(queue_after);
        this.addToqueue(this);
        this.drawMachine(graph, parent, x, y);
    }

    public void addToqueue(Machine machine) {
        Iterator var2 = this.queues.iterator();

        while(var2.hasNext()) {
            Queue q = (Queue)var2.next();
            q.addMachine(machine);
        }

    }

    public void addQueueBefore(Queue queue) {
        this.queues.add(queue);
    }

    public void drawMachine(mxGraph graph, Object parent, int x, int y) {
        this.vertex = (mxCell)graph.insertVertex(parent, "M" + this.id, "M" + this.id, (double)x, (double)y, 30.0D, 30.0D, "strokeColor=#66FF00;fillColor=#ffffff;shape=ellipse");
        this.vertex.setEdge(false);
        this.vertex.setConnectable(true);
        this.vertex.setStyle("fillColor=#ffffff");
        graph.refresh();
        this.vertex.setAttribute("strokeColor", "#66FF00");
        graph.insertEdge(parent, (String)null, "", this.vertex, this.queue_after.vertex, "startArrow=none;strokeWidth=2;strokeColor=#66FF00");
        Iterator var5 = this.queues.iterator();

        while(var5.hasNext()) {
            Queue q = (Queue)var5.next();
            graph.insertEdge(parent, (String)null, "", q.vertex, this.vertex, "startArrow=none;strokeWidth=2;strokeColor=#66FF00");
        }

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
        Iterator var1 = this.queues.iterator();

        while(var1.hasNext()) {
            Queue queue = (Queue)var1.next();
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
