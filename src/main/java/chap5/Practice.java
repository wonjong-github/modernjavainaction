package chap5;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntSupplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import chap4.Dish;

public class Practice {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

        List<Transaction> collect = transactions.stream().filter(t -> t.getYear() == 2011).sorted(comparing(Transaction::getValue)).collect(toList());
        System.out.println(collect);
        List<String> collect2 = transactions.stream().map(transaction -> transaction.getTrader().getCity()).distinct().collect(toList());
        transactions.stream().map(transaction -> transaction.getTrader().getCity()).collect(toSet());
        System.out.println(collect2);
        List<String> collect3 = transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).map(t -> t.getTrader().getName()).distinct().sorted().collect(toList());
        List<Trader> collect4 = transactions.stream().map(Transaction::getTrader).filter(t -> t.getCity().equals("Cambridge")).distinct().sorted(comparing(Trader::getName)).collect(toList());
        System.out.println(collect3);
        System.out.println(collect4);
        List<String> collect5 = transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().collect(toList());
        String reduce = transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().reduce("", (a, b) -> a + " " + b);
        System.out.println(collect5);
        System.out.println(reduce);
        boolean anyMatch = transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(anyMatch);
        List<Integer> reduce2 = transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).map(Transaction::getValue).collect(toList());
        System.out.println(reduce2);
        Integer reduce3 = transactions.stream().map(Transaction::getValue).reduce(0, Integer::max);
        System.out.println(reduce3);
        Optional<Integer> reduce4 = transactions.stream().map(Transaction::getValue).reduce(Integer::min);
        System.out.println(reduce4.get());
        
        List<Dish> menu = Dish.menu;
        menu.stream().mapToInt(Dish::getCalories).sum();
        IntStream.rangeClosed(0, 100).forEach(System.out::print);
        
        // IntStream.rangeClosed(0, 100).filter(b -> Math.sqrt(a*a + b*b)%1==0).mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a*a + b*b)});
        Stream<int[]> flatMap = IntStream.rangeClosed(1, 100).boxed()
                    .flatMap(a -> IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a*a + b*b)%1==0).mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a*a + b*b)}));

        System.out.println();
        flatMap.limit(5).forEach(t -> System.out.println(t[0] + " " + t[1] + " " + t[2]));

        IntStream.rangeClosed(1, 100).boxed()
                    .flatMap(a -> IntStream.rangeClosed(a, 100).mapToObj(b -> new double[]{a, b, Math.sqrt(a*a + b*b)}))
                    .filter(t -> t[2] % 1 == 0)
                    .forEach(t -> System.out.println(t[0] + " " + t[1] + " " + t[2]));
        Stream<String> of = Stream.of("null", "javaddd");
        of.map(String::toUpperCase).forEach(System.out::println);

        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0]+t[1]}).limit(10).forEach(t -> System.out.println(t[0] + " " + t[1]));
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
        IntSupplier intSupplier = new IntSupplier() {

            private int previous = 0;
            private int next = 1;

            @Override
            public int getAsInt() {
                int temp = next;
                this.next = previous + next;
                previous = temp;
                return this.next;
            }
            
        };

        IntStream.generate(intSupplier).limit(5).forEach(System.out::println);

        Map<Currency, List<Transaction>> tMap = new HashMap<>();
        for (Transaction transaction : transactions) {
            
        }
    }
}
