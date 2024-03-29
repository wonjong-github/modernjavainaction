package chap16.v2;

import java.util.Random;

import static chap16.Util.delay;
import static chap16.Util.format;

public class Shop {
    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return name + ":" + price + ":" + code;
    }

    public double calculatePrice(String product) {
        delay();
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        Discount.Code[] values = Discount.Code.values();
        for (Discount.Code value : values) {
            System.out.println("value.toString() = " + value.toString());
        }
        System.out.println("values = " + values);
    }
}
