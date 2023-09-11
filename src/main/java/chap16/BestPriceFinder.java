package chap16;

import chap16.v2.ExchangeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static chap16.v2.ExchangeService.DEFAULT_RATE;

public class BestPriceFinder {
    private static final List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll")/*,
      new Shop("ShopEasy")*/);

    public List<String> findPrices(String product) {
        return shops.stream().map(shop -> {
            return String.format("%s price is %.2f", shop.getName(), shop.getPrice(product));
        }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        chap16.v2.Shop shop = new chap16.v2.Shop("Hello");
        System.out.println(shop.getPrice("pro"));
    }

    public static List<String> findPriceInUSD(String product) {
        List<CompletableFuture<Double>> priceFutures = new ArrayList<>();

        for (Shop shop : shops) {
            CompletableFuture<Double> futurePriceInUSD =
                    CompletableFuture.supplyAsync(() -> shop.getPrice(product))
                            .thenCombine(
                                    CompletableFuture.supplyAsync(
                                                    () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD))
                                            .completeOnTimeout(DEFAULT_RATE, 1, TimeUnit.SECONDS),
                                    (price, rate) -> price * rate
                            )
                            .orTimeout(3, TimeUnit.SECONDS);
            priceFutures.add(futurePriceInUSD);
        }
        List<String> prices = priceFutures.stream()
                .map(CompletableFuture::join)
                .map(price -> "price is" + price)
                .collect(Collectors.toList());
        return prices;
    }
}
