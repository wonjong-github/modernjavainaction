package chap5;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import chap4.Dish;
import chap4.Type;

public class Main {
    
    public static void main(String[] args) {
        final List<Dish> menu = Dish.menu;
        List<Dish> vegetarianMenu = menu.stream()
                                        .filter(Dish::isVegetarian)
                                        .distinct()
                                        .collect(toList());

        List<Dish> specialMenu = Arrays.asList(
        new Dish("season fruit", true, 120, Type.OTHER),
        new Dish("prawns", false, 300, Type.FISH),
        new Dish("rice", true, 350, Type.OTHER),
        new Dish("chicken", false, 400, Type.MEAT),
        new Dish("french fries", true, 530, Type.OTHER));

        List<Dish> slicedMenu1 = specialMenu.stream().takeWhile(dish -> dish.getCalories() < 320).collect(toList());
        List<Dish> slicedMenu2 = specialMenu.stream().dropWhile(dish -> dish.getCalories() < 320).collect(toList());
        List<Dish> dishes = menu.stream().filter(d -> d.getCalories() > 300).collect(toList());
        System.out.println(dishes);
        List<Dish> dishes2 = menu.stream().filter(d -> d.getCalories() > 300).skip(2).collect(toList());
        menu.stream().limit(2).forEach(d -> System.out.println(d));
        System.out.println(dishes2);

        System.out.println();

        menu.stream().map(Dish::getName).map(String::length).forEach(d -> System.out.println(d));
        List<String> word = Arrays.asList("Hello", "world");
        word.stream().map(w -> w.split("")).flatMap(Arrays::stream).distinct().forEach(d -> System.out.println(d));
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        nums.stream().map(n -> n*n).forEach(v -> System.out.println(v));
        List<Integer> nums2 = Arrays.asList(6, 7);
        List<int[]> collect = nums.stream().flatMap(i -> nums2.stream().map(j -> new int[]{i, j})).collect(toList());
        List<int[]> collect2 = nums.stream().flatMap(i -> nums2.stream().filter(j -> (i+j)%3==0).map(j -> new int[]{i, j})).collect(toList());
        System.out.println(collect2);

        if(menu.stream().anyMatch(Dish::isVegetarian)){
            System.out.println("Vegetarian!");
        }

        if(menu.stream().allMatch(dish -> dish.getCalories() < 10000)){
            System.out.println("all!");
        }

        boolean noneMatch = menu.stream().noneMatch(dish -> dish.getCalories() > 10000);
        System.out.println(noneMatch);
        Dish orElse = menu.stream().filter(Dish::isVegetarian).findAny().orElse(null);
        List<Integer> someIntegers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer orElse2 = someIntegers.stream().map(s -> s*s).filter(s -> {
                                                        System.out.println(s);
                                                        return s%3==0;}).findFirst().orElse(-1);
        System.out.println(orElse2);

        Integer reduce = nums.stream().reduce(1, (a, b) -> a*b);
        System.out.println(reduce);
        nums.stream().reduce(1, Integer::sum);
        Optional<Integer> reduce2 = nums.stream().reduce(Integer::sum);
        System.out.println(reduce2.isPresent());
        Integer reduce3 = nums.stream().reduce(0, Integer::max);
        System.out.println(reduce3);
        
        Integer reduce4 = menu.stream().map(d -> 1).reduce(0, Integer::sum);
        System.out.println(reduce4);
    }
}
