package chap16.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static chap16.v2.ExchangeService.DEFAULT_RATE;

public class Main {
    static List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavouriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"),
            new Shop("HelloShop"),
            new Shop("ByeShop"),
            new Shop("ShopShop"));

    private static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public static void main(String[] args) {
//        long start = System.nanoTime();
//        System.out.println(findPrices("myPhone27S"));
//        long duration = (System.nanoTime() - start) / 1_000_000;
//        System.out.println("Done in " + duration + "msecs");


        findPricesStream("myPhone").map(f -> f.thenAccept(System.out::println));

    }



//    public static List<String> findPrices(String product) {
//        return shops.stream().map(shop -> shop.getPrice(product)).collect(Collectors.toList());
//    }

//    public static List<String> findPrices(String product) {
//        return shops.stream()
//                .map(shop -> shop.getPrice(product))
//                .map(Quote::parse)
//                .map(Discount::applyDiscount)
//                .collect(Collectors.toList());
//    }
//
    public static List<String> findPrices(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                        .map(future -> future.thenApply(Quote::parse))
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(
                                        () -> Discount.applyDiscount(quote), executor
                                ))).collect(Collectors.toList());
        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executor)));
    }
}
