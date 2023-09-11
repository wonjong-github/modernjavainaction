package chap6;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import chap4.Dish;
import chap4.Type;
import chap5.Transaction;

public class Main {
    public static void main(String[] args) {

        enum CaloricLevel { DIET, NORMAL, FAT };

        Map<String, List<String>> dishTags = Dish.dishTags;
        List<Dish> menu = Dish.menu;
        Map<Type, List<Dish>> collect2 = menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
        System.out.println(collect2);
        Map<Type, List<Dish>> collect = menu.stream().collect(groupingBy(Dish::getType, Collectors.filtering(dish -> dish.getCalories() > 500, Collectors.toList())));
        System.out.println(collect);
        Map<Type, List<String>> collect3 = menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, Collectors.toList())));
        System.out.println(collect3);

        Map<Type, Set<String>> collect4 = menu.stream().collect(groupingBy(Dish::getType, Collectors.flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));
        System.out.println(collect4);
        

        Map<Type, Map<CaloricLevel, List<String>>> collect5 = menu.stream().collect(groupingBy(Dish::getType, groupingBy(dish -> {
            if(dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }, mapping(Dish::getName, Collectors.toList()))));
        System.out.println(collect5);

        Map<Type, Long> collect6 = menu.stream().collect(groupingBy(Dish::getType, Collectors.counting()));
        System.out.println(collect6);
        Map<Type, Optional<Dish>> collect7 = menu.stream().collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
        System.out.println(collect7);
        Map<Type, Dish> collect8 = menu.stream().collect(groupingBy(Dish::getType, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(collect8);
        Map<Type, String> collect9 = menu.stream().collect(groupingBy(Dish::getType, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), dish -> dish.get().getName())));
        System.out.println(collect9);

        Map<Type, Set<CaloricLevel>> collect10 = menu.stream().collect(groupingBy(Dish::getType, mapping(dish -> {
            if(dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }, toSet())));
        System.out.println(collect10);

        Map<Type, HashSet<CaloricLevel>> collect11 = menu.stream().collect(groupingBy(Dish::getType, mapping(dish -> {
            if(dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }, Collectors.toCollection(HashSet::new))));

        System.out.println(collect11);


        Map<Boolean, List<Dish>> collect12 = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println(collect12);
        

        System.out.println(collect12.get(true));

        Map<Boolean, Map<Type, Set<String>>> collect13 = menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType, mapping(Dish::getName, toSet()))));

        System.out.println(collect13);

        Map<Boolean, Dish> collect14 = menu.stream().collect(partitioningBy(Dish::isVegetarian, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(collect14);

        Map<Boolean, Map<Boolean, Set<String>>> collect15 = menu.stream().collect(partitioningBy(Dish::isVegetarian, partitioningBy(d -> d.getCalories() > 500, mapping(Dish::getName, toSet()))));
        System.out.println(collect15);

        Map<Boolean, Set<String>> collect16 = menu.stream().collect(partitioningBy(Dish::isVegetarian, mapping(Dish::getName, toSet())));
        System.out.println(collect16);
        Map<Boolean, Long> collect17 = menu.stream().collect(partitioningBy(Dish::isVegetarian, Collectors.counting()));
        System.out.println(collect17);

        // IntStream.range(2, 10).boxed().collect(partitioningBy(c -> isPrime(c)));

        Map<Boolean, List<Integer>> partitionPrimes = partitionPrimes(50);
        System.out.println(partitionPrimes);

        List<Dish> collect18 = menu.stream().collect(new ToListCollector<>());
        System.out.println(collect18);

        ArrayList<Object> collect19 = menu.stream().collect(ArrayList::new, List::add, List::addAll);
        System.out.println(collect19);

        Map<Boolean, List<Integer>> partitionPrimes2 = partitionPrimes(50);
        System.out.println(partitionPrimes2);
    }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return primes.stream().takeWhile(i -> i <= candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    // public static Map<Boolean, List<Integer>> partitionPrimes(int candidate){
    //     return IntStream.rangeClosed(2, candidate).boxed().collect(partitioningBy(c -> isPrime(c)));
    // }

    public static Map<Boolean, List<Integer>> partitionPrimes(int candidate){
        return IntStream.rangeClosed(2, candidate).boxed().collect(new PrimeNumberCollector());
    }
}

