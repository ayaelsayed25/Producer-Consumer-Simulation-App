import java.util.LinkedList;

public class Queue implements Observer {

    private LinkedList<Machine> after ;
    private LinkedList<Product> products;

    public LinkedList<Machine> getAfter() {
        return after;
    }

    public void setAfter(LinkedList<Machine> after) {
        this.after = after;
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

    public void sendProduct(){
        for (Machine machine : after) {
            if (machine.empty) {
                if (products.isEmpty())return;
                Product product = products.removeLast();
                machine.setCurrentProduct(product);
                machine.setEmpty(false);
            }
        }
    }


    @Override
    public void update() {
        sendProduct();
    }
}
