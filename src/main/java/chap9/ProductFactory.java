package chap9;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ProductFactory {
    final static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
        System.out.println("ProductFactory.static initializer");
    }
    public static void main(String[] args) {
        Supplier<Product> loadSupplier = Loan::new;
        Loan loan = (Loan) loadSupplier.get();

    }
//    public static Product createProduct(String name) {
//        switch (name) {
//            case "loan" :
//                return new Loan();
//            case "stock" :
//                return new Stock();
//            case "bond" :
//                return new Bond();
//            default:
//                throw new RuntimeException("No such product : " + name);
//        }
//    }

    public static Product createProduct(String name) {
        Supplier<Product> p = map.get(name);
        if (p != null) return p.get();
        throw new IllegalArgumentException("No such Product " + name);
    }

    private static class Product {
    }

    private static class Loan extends Product {
    }

    private static class Stock extends Product {
    }

    private static class Bond extends Product {
    }
}
