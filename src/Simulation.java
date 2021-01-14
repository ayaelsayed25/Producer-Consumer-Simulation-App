import java.util.Timer;
import java.util.TimerTask;

public class Simulation {

    Queue start;
    CareTaker careTaker;
    double numberOfProducts;
    Thread thread;
    public Simulation(Queue start,double numberOfProducts) {
        this.start = start;
        this.numberOfProducts = numberOfProducts;
    }
    public void play(boolean replay) {

            thread = new Thread(() -> {
            System.out.println("WHY WHY WHY");
            if(!replay)
            {
                Originator originator = new Originator();
                careTaker = new CareTaker();
                for (int i = 0; i <numberOfProducts ; i++) {
                    originator.setColor();
                    originator.setRate();
                    Product product = originator.getProduct();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            start.addProduct(product);
                            careTaker.add(product);
                            try {
                                start.sendProduct();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }, product.rate);
                }
            }
            else
            {   for (int i = 0; i <numberOfProducts ; i++) {
                System.out.println(careTaker.products);
                Product product = careTaker.get();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        start.addProduct(product);
                        try {
                            start.sendProduct();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, product.rate);
            }
        }

            System.out.println("END");
        });
        thread.start();
    }

}

