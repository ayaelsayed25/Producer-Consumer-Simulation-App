import java.util.LinkedList;

public class CareTaker {
    LinkedList<Product> products;

    public CareTaker()
    {
        products = new LinkedList<Product>();
    }
    public void add(Product product)
    {
        products.add(product);
    }

    public Product get()
    {
        Product product = products.removeFirst();
        products.add(product);
        return product;
    }
}
