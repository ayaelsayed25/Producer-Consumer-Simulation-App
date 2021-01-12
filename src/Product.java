import java.awt.*;
import java.util.Random;

public class Product {
	public String id;
	String color;
	int time ;
	public Product()
	{
		this.color = getRandomColor();
		this.time = getRandomTime();

	}
	public int getRandomTime(){
		Random r=new Random();
		return r.nextInt((1000 - 100) + 1) + 100;
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
