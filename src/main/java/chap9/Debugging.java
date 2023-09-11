package chap9;

import chap2.Predicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Debugging {
    public static void main(String[] args) {
//        List<Point> points = Arrays.asList(new Point(10, 10), null);
//        points.stream().map(Point::getX).forEach(System.out::println);
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
//        numbers.stream().map(Debugging::divideZero).forEach(System.out::println);
        List<Integer> collect = numbers.stream()
                .peek(x -> System.out.println("from stream: " + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map: " + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter: " + x))
                .limit(3)
                .peek(x -> System.out.println("after limit: " + x))
                .collect(Collectors.toList());
        System.out.println("collect = " + collect);

        numbers.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("integer = " + integer);
            }
        });
    }
    public static int divideZero(int n) {
        return n/0;
    }
    private static class Point{
        private int x;
        private int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }

    @Test
    public void testComparingTwoPoints() {
        Point p1 = new Point(10, 15);
        Point p2 = new Point(10, 20);

        Assertions.assertThat(p1).isNotEqualTo(p2);
    }

    @Test
    void testFilter() {
        List<Integer> numbers = List.of(1, 2, 3, 4);
        List<Integer> even = filter(numbers, n -> n % 2 == 0);
        List<Integer> smallerThanThree = filter(numbers, n -> n < 3);
        Assertions.assertThat(Arrays.asList(2, 4)).isEqualTo(even);
        Assertions.assertThat(Arrays.asList(1, 2)).isEqualTo(smallerThanThree);
    }

    private List<Integer> filter(List<Integer> l, Predicate<Integer> predicate) {
        List<Integer> newList = new ArrayList<>();
        for (Integer n : l) {
            if (predicate.test(n)) {
                newList.add(n);
            }
        }
        return newList;
    }
}
