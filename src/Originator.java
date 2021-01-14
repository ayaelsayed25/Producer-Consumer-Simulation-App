import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Originator {

    String color;

    public void setColor()
    {
        this.color = getRandomColor();
    }

    public Product getProduct()
    {
        Product product = new Product();
        product.setColor(color);
        return product;
    }
    public  String getRandomColor() {

        // create random object - reuse this as often as possible
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(0xffffff + 1);

        // format it as hexadecimal string (with hashtag and leading zeros)
        String colorCode = String.format("#%06x", nextInt);

        // print it
        return  colorCode;
    }
}
