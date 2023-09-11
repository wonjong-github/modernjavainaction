package chap4;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

public class Main {
    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish dish : menu) {
            if(dish.getCalories() < 400){
                lowCaloricDishes.add(dish);
            }
        }

        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });

        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish dish : lowCaloricDishes) {
            lowCaloricDishesName.add(dish.getName());
        }

        lowCaloricDishesName = menu.stream().filter(dish -> dish.getCalories() < 400)
                                            .sorted(comparing(Dish::getCalories))
                                            .map(Dish::getName)
                                            .collect(toList());
        List<String> threeHighCaloricDishNames = menu.stream()
                                                .filter(dish -> {
                                                    System.out.println("filtering : " + dish.getName() + dish.getCalories());
                                                    return dish.getCalories() > 300;})
                                                .sorted(comparing(Dish::getCalories))
                                                .map(dish -> {
                                                    System.out.println("mapping : " + dish.getName());
                                                    return dish.getName();
                                                })
                                                .limit(3)
                                                .collect(toList());

        System.out.println(threeHighCaloricDishNames);

        List<String> names = menu.stream().map(Dish::getName).collect(toList());

        
        
    }
}
