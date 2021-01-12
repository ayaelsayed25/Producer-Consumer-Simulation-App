// consumer problem.

import org.jgraph.graph.BasicMarqueeHandler;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;


//
//    public static void main(String[] args) throws InterruptedException
//    {
//        // Object of a class that has both produce()
//        // and consume() methods
//        final PC pc = new PC();
//
//        // Create producer thread
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run()
//            {
//                try {
//                    pc.produce();
//                }
//                catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        // Create consumer thread
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run()
//            {
//                try {
//                    pc.consume();
//                }
//                catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        // Start both threads
//        t1.start();
//        t2.start();
//
//        // t1 finishes before t2
//        t1.join();
//        t2.join();
//    }

    // This class has a list, producer (adds items to list 
    // and consumer (removes items).
public class Machine implements ISubject, Runnable{
    Thread t;
    LinkedList<Queue> queues;
    Queue queue_after;
    Product currentProduct;
    String id = "";
    boolean empty =  true;
    public Machine(String id) {
        this.setId(id);
        t= new Thread(this, "Thread " + id);
        t.start();
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
        t.run();
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
        this.id = id;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void produce() throws InterruptedException
    {
            System.out.println("Product" + currentProduct.color + "by machine" + this.getId());
            Random r = new Random();
            int time = r.nextInt((1000 - 100) + 1) + 100;
            Thread.sleep(time);
            consume();

    }

    // Function called by consumer thread
    public void consume() throws InterruptedException {
            queue_after.addProduct(currentProduct);
            empty = true;
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
        while (!empty){
            try {
                notifyAllObservers();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

        public static void main(String[] args) throws InterruptedException {
            Queue after = new Queue();
            LinkedList<Queue> queue1 = new LinkedList<>();
            Queue start = new Queue();
            queue1.add(start);

            LinkedList<Queue> queue2 = new LinkedList<>();
            queue2.add(after);

            LinkedList<Machine> machinesOfQueue2 = new LinkedList<>();
            start.addProduct(new Product(Color.BLACK));
            start.addProduct(new Product(Color.BLUE));
            start.addProduct(new Product(Color.RED));
            LinkedList<Machine> machinesOfQueue1 = new LinkedList<>();

            Machine m1 = new Machine("first");
            Machine m2 = new Machine("second");

            m1.setQueues(queue1);
            m1.setQueue_after(after);

            m2.setQueue_after(new Queue());
            m2.setQueues(queue2);

            machinesOfQueue2.add(m2);
            after.setMachines(machinesOfQueue2);

            machinesOfQueue1.add(m1);
            start.setMachines(machinesOfQueue1);

            start.sendProduct();
        }
}