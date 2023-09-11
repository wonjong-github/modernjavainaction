package chap16.v2;

import static chap16.Util.delay;
import static chap16.Util.format;

public class Discount {
    public enum Code {
        NONE(0), SILVER(5), COLD(10), PLATINUM(15), DIAMOND(20);
        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }
    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    private static double apply(double price, Code code) {
        delay();
        return format(price * (100 - code.percentage) / 100);
    }

    public static void main(String[] args) {

    }

}
