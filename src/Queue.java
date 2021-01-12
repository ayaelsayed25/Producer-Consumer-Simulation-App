import java.util.LinkedList;

public class Queue implements Observer {

    private LinkedList<Machine> machines;
    private LinkedList<Product> products;

    public Queue() {
        machines = new LinkedList<>();
        products = new LinkedList<>();
    }

    public LinkedList<Machine> getMachines() {
        return machines;
    }
    public void setMachines(LinkedList<Machine> machines) {
        this.machines = machines;
    }
    public void addProduct(Product product){
        products.add(product);
    }

    public LinkedList<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Product> products) {
        this.products = products;
    }

    public void sendProduct() throws InterruptedException {
        for (Machine machine : machines) {
            if (machine.empty) {
                if (products.isEmpty())return;
                Product product = products.removeLast();
                machine.setEmpty(false);
                machine.setCurrentProduct(product);
            }
        }
    }


    @Override
    public void update() throws InterruptedException {
        sendProduct();
    }
}
