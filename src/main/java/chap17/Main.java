package chap17;

import java.util.concurrent.Flow;

public class Main {
    public static void main(String[] args) {
//        getTemperatures("New York").subscribe(new TempSubscriber());
        getCeliusTemperatures("New York").subscribe(new TempSubscriber());
    }

    private static Flow.Publisher<TempInfo> getTemperatures(String town) {
        return subscriber -> {
            subscriber.onSubscribe(
                    new TempSubscription(subscriber, town)
            );
        };
    }

    private static Flow.Publisher<TempInfo> getCeliusTemperatures(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            subscriber.onSubscribe(
                    new TempSubscription(subscriber, town)
            );
        };
    }

}
