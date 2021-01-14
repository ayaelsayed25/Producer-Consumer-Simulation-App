import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Originator {

    String color;
    int rate;
    Random r=new Random();
    final int maximum = 200;
    final int minimum = 100;
    public void setColor()
    {
        this.color = getRandomColor();
    }

    public Product getProduct()
    {
        Product product = new Product();
        product.setColor(color);
        product.setRate(rate);
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
    public void setRate(){
        rate= r.nextInt((maximum - minimum) + 1) + minimum;
    }
}
