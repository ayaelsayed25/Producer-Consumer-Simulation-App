//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Machine implements ISubject, Runnable {
    final int minimum = 10000;
    final int maximum = 30000;
    Thread t;
    LinkedList<Queue> queues = new LinkedList<>();
    Queue queue_after;
    Product currentProduct;
    String id;
    mxCell vertex;
    mxGraph graph;
    boolean empty = true;

    public Machine(String id, mxGraph graph, Object parent, int x, int y, LinkedList<Queue> queueBefore, Queue queue_after) {
        this.setId(id);
        this.graph = graph;
        this.queues = queueBefore;
//        this.addQueueBefore(queueBefore);
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
        this.vertex = (mxCell)graph.insertVertex(parent, "M" + this.id, "M" + this.id, x, y, 30.0D, 30.0D, "strokeColor=#66FF00;fillColor=#ffffff;shape=ellipse");
        this.vertex.setEdge(false);
        this.vertex.setConnectable(true);
        this.vertex.setStyle("fillColor=#ffffff");
        graph.refresh();
        this.vertex.setAttribute("strokeColor", "#66FF00");
        graph.insertEdge(parent, null, "", this.vertex, this.queue_after.vertex, "startArrow=none;strokeWidth=2;strokeColor=#66FF00");

        for (Queue q : this.queues) {
            graph.insertEdge(parent, null, "", q.vertex, this.vertex, "startArrow=none;strokeWidth=2;strokeColor=#66FF00");
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
        Random r = new Random();
        int time = r.nextInt((maximum - minimum) + 1) + minimum;;
        Thread.sleep(time);
        String var10001 = this.currentProduct.color;
        System.out.println("Product" + var10001 + "by machine" + this.getId());
        this.consume();
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
