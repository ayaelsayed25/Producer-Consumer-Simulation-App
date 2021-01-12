// consumer problem.

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
    LinkedList<Queue> queues;
    Queue queue_after;
    Product currentProduct;
    String id = "";
    boolean empty =  true;

    public Queue getQueue_after() {
        return queue_after;
    }

    public void setQueue_after(Queue queue_after) {
        this.queue_after = queue_after;
    }


    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
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
    public Machine() {
        Thread t = new Thread(this, "Thread " + id);
    }
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
    public void produce() throws InterruptedException
    {
        while (true) {
            synchronized (this)
            {
                System.out.println("Product" + currentProduct.id);
                Random r = new Random();
                int time = r.nextInt((10000 - 1000) + 1) + 1000;
                Thread.sleep(time);
                consume();
            }
        }
    }

    // Function called by consumer thread
    public void consume()
    {
        while (true) {
            synchronized (this)
            {
                queue_after.addProduct(currentProduct);
                notifyAllObservers();
            }
        }
    }

    @Override
    public void notifyAllObservers() {
        for (Queue queue : queues) {
            queue.update();
        }
        queue_after.update();
    }

    @Override
    public void run() {

    }

}