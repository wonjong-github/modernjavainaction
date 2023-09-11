package chap9;

import java.util.ArrayList;
import java.util.List;

public interface Observer {
    void notify(String tweet);

    class NYTimes implements Observer {
        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        }
    }

    class Guadinan implements Observer {
        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Breaking news from London " + tweet);
            }
        }
    }

    class Lemonde implements Observer {
        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("wine")) {
                System.out.println("Today cheese, wine and new! " + tweet);
            }
        }
    }
}

interface Subject{
    void registerObserver(Observer observer);

    void notifyObservers(String tweet);

    class Feed implements Subject {
        private final List<Observer> observers = new ArrayList<>();
        @Override
        public void registerObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void notifyObservers(String tweet) {
            observers.forEach(o -> o.notify(tweet));
        }
    }
}