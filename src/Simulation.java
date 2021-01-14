public class Simulation {

    Queue start;
    CareTaker careTaker;
    public Simulation(Queue start) throws InterruptedException {
        this.start = start;
    }
    public void play(boolean replay) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("WHY WHY WHY");
            if(!replay)
            {
                Originator originator = new Originator();
                careTaker = new CareTaker();
                originator.setColor();
                Product product = originator.getProduct();
                start.addProduct(product);
                careTaker.add(product);
                originator.setColor();
                Product product1 = originator.getProduct();
                start.addProduct(product1);
                careTaker.add(product1);
                originator.setColor();
                Product product2 = originator.getProduct();
                start.addProduct(product2);
                careTaker.add(product2);
            }
            else
            {
                start.addProduct(careTaker.get());
                start.addProduct(careTaker.get());
                start.addProduct(careTaker.get());
            }
            try {
                start.sendProduct();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("END");
        });
        thread.start();
//        thread.join();
    }

}
