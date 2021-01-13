public class Simulation {

    Queue start;
    public Simulation(Queue start) throws InterruptedException {
        this.start = start;

        Thread thread = new Thread(() -> {
            System.out.println("WHY WHY WHY");
            start.addProduct(new Product());
            start.addProduct(new Product());
            start.addProduct(new Product());

            try {
                start.sendProduct();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("END");
        });
        thread.start();
        thread.join();
    }
}
