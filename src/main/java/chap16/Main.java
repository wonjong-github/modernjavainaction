package chap16;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Main {

    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    static List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavouriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"),
            new Shop("HelloShop"),
            new Shop("ByeShop"),
            new Shop("ShopShop"));

    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my Favorite product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("invocationTime = " + invocationTime);

        // do Something else

        try {
            Double price = futurePrice.get();
            System.out.println("price = " + price);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("retrievalTime = " + retrievalTime);

        start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + "msecs");
    }

//    public static List<String> findPrices(String product) {
//        return shops.stream().map(shop -> {
//            return String.format("%s price is %.2f", shop.getName(), shop.getPrice(product));
//        }).collect(Collectors.toList());
//    }

//    public static List<String> findPrices(String product) {
//        return shops.parallelStream()
//                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))).collect(Collectors.toList());
//    }

    public static List<String> findPrices(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream().map(shop -> CompletableFuture.supplyAsync(
                () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
        )).collect(Collectors.toList());
        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

}
