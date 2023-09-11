package chap10;

import chap10.model.Order;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numbers.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("integer = " + integer);
            }
        });

        numbers.forEach(System.out::println);
        Order end = MethodChainingOrderBuilder.forCustomer("Bigbank")
                .buy(10)
                .stock("IBM")
                .on("NYSE")
                .at(125.00)
                .sell(50)
                .stock("Google")
                .on("NASDAQ")
                .at(375.00)
                .end();

    }
}
