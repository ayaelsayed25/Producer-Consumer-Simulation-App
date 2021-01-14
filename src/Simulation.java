public class Simulation {

    Queue start;
    CareTaker careTaker;
    double numberOfProducts;

    public Simulation(Queue start,double numberOfProducts) {
        this.start = start;
        this.numberOfProducts = numberOfProducts;

    }
    public void play(boolean replay) throws InterruptedException {

        Thread thread = new Thread(() -> {
            System.out.println("WHY WHY WHY");
            if(!replay)
            {
                Originator originator = new Originator();
                careTaker = new CareTaker();
                for (int i = 0; i <numberOfProducts ; i++) {

                    originator.setColor();
                    originator.setRate();
                    Product product = originator.getProduct();
                    start.addProduct(product);
                    careTaker.add(product);
                }


            }
            else
            {   for (int i = 0; i <numberOfProducts ; i++) {
                System.out.println(careTaker.products);
                start.addProduct(careTaker.get());
            }
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
