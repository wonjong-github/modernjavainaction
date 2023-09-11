package chap2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Main {
    public Callable<String> fetch(){
        return () -> "hello";
    }
    public static void main(String[] args) {
        Apple apple1 = new Apple(Color.GREEN, "a1", 10);
        Apple apple2 = new Apple(Color.RED, "a2", 20);
        Apple apple3 = new Apple(Color.GREEN, "a3", 30);
        Apple apple4 = new Apple(Color.RED, "a4", 40);

        apple1.go();
        List<Apple> inventories = new ArrayList<>();
        inventories.add(apple1);
        inventories.add(apple2);
        inventories.add(apple3);
        inventories.add(apple4);

//        List<chap2.Apple> result = filterGreenApples(inventories);
//        List<Apple> result = filterApplesByColor(inventories, Color.GREEN);

//        5. 익명 클래스 사용 : 많은 공간 차지
//        List<Apple> result  = filterApple(inventories, new ApplePredicate() {
//            @Override
//            public boolean test(Apple apple) {
//                return apple.getColor().equals(Color.GREEN);
//            }
//        });

//        6. 람다사용
//        List<Apple> result = filterApple(inventories, (apple -> apple.getColor().equals(Color.GREEN)));

        List<Apple> result = filter(inventories, (apple -> apple.getColor().equals(Color.RED)));
        prettyPrintApple(result, (apple -> apple.getName() + " " + apple.getColor() + " " + apple.getWeight()));

//        7. 스트림 사용
        List<Apple> result2 = inventories.parallelStream().filter((apple -> apple.getColor().equals(Color.GREEN)))
                .collect(Collectors.toList());

        prettyPrintApple(result2, (apple -> apple.getName() + " " + apple.getColor() + " " + apple.getWeight()));

//        inventories.sort(new Comparator<Apple>() {
//            @Override
//            public int compare(Apple o1, Apple o2) {
//                return o2.getWeight() - o1.getWeight();
//            }
//        });
        inventories.sort(((o1, o2) -> o2.getWeight() - o1.getWeight()));
        prettyPrintApple(inventories, (apple -> apple.getName() + " " + apple.getColor() + " " + apple.getWeight()));

        inventories.sort(Comparator.comparingInt(Apple::getWeight));
        Thread t = new Thread(() -> System.out.println("Main.main"));
    }


    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
    public static void prettyPrintApple(List<Apple> inventories, PrettyPrint p){
        for (Apple inventory : inventories) {
            System.out.println(p.print(inventory));
        }
    }
    public static List<Apple> filterApple(List<Apple> inventories, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for (Apple inventory : inventories) {
            if (p.test(inventory)) {
                result.add(inventory);
            }
        }
        return result;
    }
    // 2. 색을 파라미터 화 : 색에 대한 요구사항 변경시 대응 가능, 필터
    public static List<Apple> filterApplesByColor(List<Apple> inventories, Color color){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventories) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventories, int weight){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventories) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    // 1. 녹색 사과 필터링
    public static List<Apple> filterGreenApples(List<Apple> inventories){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventories) {
            if (apple.getColor().equals(Color.GREEN)) {
                result.add(apple);
            }
        }
        return result;
    }

}